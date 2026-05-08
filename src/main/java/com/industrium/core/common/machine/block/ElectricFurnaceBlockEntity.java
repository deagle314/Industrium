package com.industrium.core.common.machine.block;

import com.industrium.core.common.blockentity.BaseMachineBlockEntity;
import com.industrium.core.api.power.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * Electric Furnace - powered smelting machine.
 * Consumes LV power to smelt items.
 */
public class ElectricFurnaceBlockEntity extends BaseMachineBlockEntity implements IEnergyConsumer {
    
    private VoltageTier voltageTier = VoltageTier.LV;
    private long powerStored = 0;
    private long maxPower = VoltageTier.LV.getTransferRate() * 10;
    private ItemStack inputItem = ItemStack.EMPTY;
    private ItemStack outputItem = ItemStack.EMPTY;
    private int burnTime = 0;
    private int maxBurnTime = 0;
    private int cookTime = 0;
    private int cookTimeTotal = 200;
    private boolean isSmelting = false;
    
    public ElectricFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(null, pos, state);
        this.status = com.industrium.core.common.system.MachineStatus.IDLE;
    }
    
    @Override
    public void tickServer() {
        super.tickServer();
        
        // Try to start smelting if we have input and no burn in progress
        if (!inputItem.isEmpty() && outputItem.getCount() < 64 && maxBurnTime == 0 && powerStored >= 10) {
            if (isSmeltable(inputItem)) {
                maxBurnTime = 200;
                burnTime = 200;
                inputItem.shrink(1);
            }
        }
        
        // Smelt while burning and have power
        if (burnTime > 0 && powerStored >= 1) {
            burnTime--;
            powerStored--;
            cookTime++;
            isSmelting = true;
            
            if (cookTime >= cookTimeTotal) {
                outputItem = getOutputFor(inputItem);
                if (!inputItem.isEmpty()) {
                    inputItem.shrink(1);
                }
                cookTime = 0;
            }
            setStatus(com.industrium.core.common.system.MachineStatus.RUNNING);
        } else {
            isSmelting = false;
            if (maxBurnTime > 0 && burnTime <= 0) {
                // Finished burning
                maxBurnTime = 0;
            }
            if (powerStored <= 0) {
                setStatus(com.industrium.core.common.system.MachineStatus.OFFLINE);
            } else if (!isSmelting) {
                setStatus(com.industrium.core.common.system.MachineStatus.IDLE);
            }
        }
        
        markClientSync();
    }
    
    /**
     * Checks if item can be smelted.
     */
    private boolean isSmeltable(ItemStack stack) {
        if (stack.isEmpty()) return false;
        // Simple smelting list - just use available items
        return stack.getItem() == Items.IRON_INGOT || 
               stack.getItem() == Items.RAW_IRON ||
               stack.getItem() == Items.RAW_COPPER ||
               stack.getItem() == Items.COPPER_INGOT ||
               stack.getItem() == Items.SAND ||
               stack.getItem() == Items.CLAY_BALL;
    }
    
    /**
     * Gets output for input.
     */
    private ItemStack getOutputFor(ItemStack input) {
        if (input.getItem() == Items.IRON_INGOT || input.getItem() == Items.RAW_IRON) {
            return new ItemStack(Items.IRON_INGOT);
        }
        if (input.getItem() == Items.RAW_COPPER || input.getItem() == Items.COPPER_INGOT) {
            return new ItemStack(Items.COPPER_INGOT);
        }
        if (input.getItem() == Items.SAND) {
            return new ItemStack(Items.GLASS);
        }
        if (input.getItem() == Items.CLAY_BALL) {
            return new ItemStack(Items.BRICK);
        }
        return ItemStack.EMPTY;
    }
    
    /**
     * Gets power stored.
     */
    public long getPowerStored() {
        return powerStored;
    }
    
    /**
     * Adds power to furnace.
     */
    public long receivePower(long amount, boolean simulate) {
        if (simulate) {
            return Math.min(amount, maxPower - powerStored);
        }
        long received = Math.min(amount, maxPower - powerStored);
        powerStored += received;
        return received;
    }
    
    // IEnergyConsumer implementation
    
    @Override
    public VoltageTier getRequiredTier() {
        return voltageTier;
    }
    
    @Override
    public long getConsumptionRate() {
        return isSmelting ? 1 : 0;
    }
    
    @Override
    public boolean canOperate(VoltageTier tier) {
        return tier == voltageTier || tier.compareTo(voltageTier) >= 0;
    }
    
    @Override
    public boolean isOperating() {
        return isSmelting;
    }
    
    @Override
    public long getPowerDemand() {
        return isSmelting ? 1 : 0;
    }
    
    @Override
    public long getMaxPowerReceive() {
        return voltageTier.getTransferRate();
    }
    
    @Override
    public void onPowerReceived(long amount) {
        powerStored += amount;
        if (powerStored > maxPower) {
            powerStored = maxPower;
        }
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putLong("PowerStored", powerStored);
        tag.putLong("MaxPower", maxPower);
        tag.putInt("BurnTime", burnTime);
        tag.putInt("MaxBurnTime", maxBurnTime);
        tag.putInt("CookTime", cookTime);
        tag.putBoolean("IsSmelting", isSmelting);
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        powerStored = tag.getLong("PowerStored");
        maxPower = tag.getLong("MaxPower");
        burnTime = tag.getInt("BurnTime");
        maxBurnTime = tag.getInt("MaxBurnTime");
        cookTime = tag.getInt("CookTime");
        isSmelting = tag.getBoolean("IsSmelting");
    }
    
    @Override
    protected void saveClientData(CompoundTag tag) {
        super.saveClientData(tag);
        tag.putLong("PowerStored", powerStored);
        tag.putBoolean("IsSmelting", isSmelting);
    }
    
    @Override
    protected void loadClientData(CompoundTag tag) {
        super.loadClientData(tag);
        if (tag.contains("PowerStored")) {
            powerStored = tag.getLong("PowerStored");
        }
        if (tag.contains("IsSmelting")) {
            isSmelting = tag.getBoolean("IsSmelting");
        }
    }
    
    /**
     * Gets status text for interaction.
     */
    public String getStatusText() {
        return powerStored + " / " + maxPower + " FE";
    }
}