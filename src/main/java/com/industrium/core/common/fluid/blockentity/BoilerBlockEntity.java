package com.industrium.core.common.fluid.blockentity;

import com.industrium.core.common.menu.BoilerMenu;
import com.industrium.core.common.registry.ModBlockEntities;
import com.industrium.core.common.system.MachineStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BoilerBlockEntity extends BaseFluidMachineBlockEntity implements MenuProvider {
    private final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private final LazyOptional<IItemHandler> inventoryOptional = LazyOptional.of(() -> inventory);

    private final FluidTank waterTank;
    private final FluidTank steamTank;
    private int burnTime;
    private int maxBurnTime;

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> burnTime;
                case 1 -> maxBurnTime;
                case 2 -> waterTank.getFluidAmount();
                case 3 -> steamTank.getFluidAmount();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> burnTime = value;
                case 1 -> maxBurnTime = value;
                case 2 -> waterTank.getFluid().setAmount(value);
                case 3 -> steamTank.getFluid().setAmount(value);
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public BoilerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BOILER.get(), pos, state, 2000);
        this.waterTank = new FluidTank(10000, fluid -> fluid.getFluid() == Fluids.WATER);
        this.steamTank = new FluidTank(10000);
        this.status = MachineStatus.IDLE;
    }

    @Override
    public void tickServer() {
        super.tickServer();

        boolean changed = false;

        if (burnTime > 0) {
            burnTime--;
            changed = true;
        }

        if (burnTime <= 0) {
            ItemStack fuel = inventory.getStackInSlot(0);
            if (!fuel.isEmpty() && waterTank.getFluidAmount() > 0 && steamTank.getFluidAmount() < steamTank.getCapacity()) {
                maxBurnTime = burnTime = ForgeHooks.getBurnTime(fuel, null);
                if (burnTime > 0) {
                    fuel.shrink(1);
                    changed = true;
                    setStatus(MachineStatus.RUNNING);
                }
            } else {
                setStatus(MachineStatus.IDLE);
            }
        }

        if (burnTime > 0) {
            int waterToConvert = Math.min(waterTank.getFluidAmount(), 10);
            int steamToProduce = waterToConvert * 10;
            
            if (steamTank.getFluidAmount() + steamToProduce <= steamTank.getCapacity()) {
                waterTank.drain(waterToConvert, FluidTank.FluidAction.EXECUTE);
                // Produced steam - for now we use water as a placeholder but we could use a custom fluid if registered
                // Since we don't have custom steam fluid yet, we just fill with water to simulate
                steamTank.fill(new net.minecraftforge.fluids.FluidStack(net.minecraft.world.level.material.Fluids.WATER, steamToProduce), net.minecraftforge.fluids.capability.templates.FluidTank.FluidAction.EXECUTE);
                changed = true;
            }
        }

        if (changed) {
            setChanged();
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return inventoryOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inventoryOptional.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
        tag.put("WaterTank", waterTank.writeToNBT(new CompoundTag()));
        tag.put("SteamTank", steamTank.writeToNBT(new CompoundTag()));
        tag.putInt("BurnTime", burnTime);
        tag.putInt("MaxBurnTime", maxBurnTime);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("Inventory"));
        waterTank.readFromNBT(tag.getCompound("WaterTank"));
        steamTank.readFromNBT(tag.getCompound("SteamTank"));
        burnTime = tag.getInt("BurnTime");
        maxBurnTime = tag.getInt("MaxBurnTime");
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Boiler");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
        return new BoilerMenu(windowId, inv, this, this.data);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public FluidTank getWaterTank() {
        return waterTank;
    }

    public FluidTank getSteamTank() {
        return steamTank;
    }
}
