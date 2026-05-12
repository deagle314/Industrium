package com.industrium.core.common.rotation.blockentity;

import com.industrium.core.common.menu.CrusherMenu;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrusherBlockEntity extends BaseRotationMachineBlockEntity implements MenuProvider {
    private final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private final LazyOptional<IItemHandler> inventoryOptional = LazyOptional.of(() -> inventory);

    private int progress;
    private int maxProgress = 200;

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 1 -> maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public CrusherBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRUSHER.get(), pos, state);
        this.status = MachineStatus.IDLE;
    }

    @Override
    public void tickServer() {
        super.tickServer();

        ItemStack input = inventory.getStackInSlot(0);
        ItemStack output = inventory.getStackInSlot(1);

        if (!input.isEmpty() && canCrush(input, output)) {
            if (rpm > 100 && torque > 50) {
                progress++;
                setStatus(MachineStatus.RUNNING);

                if (progress >= maxProgress) {
                    ItemStack result = getCrushingResult(input);
                    if (output.isEmpty()) {
                        inventory.setStackInSlot(1, result.copy());
                    } else {
                        output.grow(result.getCount());
                    }
                    input.shrink(1);
                    progress = 0;
                }
            } else {
                setStatus(MachineStatus.STARVED);
            }
        } else {
            setStatus(MachineStatus.IDLE);
            progress = 0;
        }
    }

    private boolean canCrush(ItemStack input, ItemStack output) {
        ItemStack result = getCrushingResult(input);
        if (result.isEmpty()) return false;
        if (output.isEmpty()) return true;
        return ItemStack.isSameItem(output, result) && output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private ItemStack getCrushingResult(ItemStack input) {
        if (input.getItem() == Items.IRON_ORE || input.getItem() == Items.RAW_IRON) {
            return new ItemStack(Items.IRON_INGOT, 2);
        }
        if (input.getItem() == Items.COPPER_ORE || input.getItem() == Items.RAW_COPPER) {
            return new ItemStack(Items.COPPER_INGOT, 2);
        }
        if (input.getItem() == Items.GOLD_ORE || input.getItem() == Items.RAW_GOLD) {
            return new ItemStack(Items.GOLD_INGOT, 2);
        }
        if (input.getItem() == Items.COBBLESTONE) {
            return new ItemStack(Items.GRAVEL);
        }
        if (input.getItem() == Items.GRAVEL) {
            return new ItemStack(Items.SAND);
        }
        return ItemStack.EMPTY;
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
        tag.putInt("Progress", progress);
        tag.putInt("MaxProgress", maxProgress);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("Inventory"));
        progress = tag.getInt("Progress");
        maxProgress = tag.getInt("MaxProgress");
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Crusher");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
        return new CrusherMenu(windowId, inv, this, this.data);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }
}
