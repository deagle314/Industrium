package com.industrium.core.common.power.blockentity;

import com.industrium.core.common.blockentity.BaseMachineBlockEntity;
import com.industrium.core.common.registry.PowerModule;
import com.industrium.core.api.power.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Coal Generator block entity.
 * Burns fuel to generate power.
 */
public class CoalGeneratorBlockEntity extends BaseMachineBlockEntity implements IGenerator {
    
    private VoltageTier voltageTier;
    private long generationRate;
    private long fuelRemaining;
    private long maxFuel;
    private boolean isBurning;
    
    public CoalGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(PowerModule.COAL_GENERATOR_BE.get(), pos, state);
        this.voltageTier = VoltageTier.LV;
        this.generationRate = VoltageTier.LV.getTransferRate();
        this.fuelRemaining = 0;
        this.maxFuel = 1000;
        this.status = com.industrium.core.common.system.MachineStatus.IDLE;
    }
    
    @Override
    public void tickServer() {
        super.tickServer();
        
        // Burn fuel if we have it
        if (fuelRemaining > 0) {
            fuelRemaining--;
            isBurning = true;
            setStatus(com.industrium.core.common.system.MachineStatus.RUNNING);
        } else {
            isBurning = false;
            setStatus(com.industrium.core.common.system.MachineStatus.IDLE);
        }
    }
    
    /**
     * Adds fuel to the generator.
     */
    public void addFuel(long fuel) {
        fuelRemaining = Math.min(fuelRemaining + fuel, maxFuel);
        setStatus(com.industrium.core.common.system.MachineStatus.RUNNING);
    }
    
    /**
     * Gets current fuel.
     */
    public long getFuel() {
        return fuelRemaining;
    }
    
    /**
     * Gets status text for interaction.
     */
    public String getStatusText() {
        return isBurning ? "Generating " + generationRate + " FE/t" : "No fuel";
    }
    
    /**
     * Checks if burning.
     */
    public boolean isBurning() {
        return isBurning;
    }
    
    /**
     * Gets detailed stats for interaction.
     */
    public String[] getStats() {
        return new String[] {
            "Fuel: " + fuelRemaining + "/" + maxFuel,
            "Gen: " + generationRate + " FE/t",
            "Tier: " + voltageTier.name()
        };
    }
    
    // IGenerator implementation
    
    @Override
    public long generate() {
        return isBurning ? generationRate : 0;
    }
    
    @Override
    public long getProductionRate() {
        return generationRate;
    }
    
    @Override
    public VoltageTier getOutputTier() {
        return voltageTier;
    }
    
    @Override
    public boolean isActive() {
        return isBurning;
    }
    
    @Override
    public String getFuelType() {
        return "coal";
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putLong("FuelRemaining", fuelRemaining);
        tag.putLong("MaxFuel", maxFuel);
        tag.putLong("GenerationRate", generationRate);
        tag.putBoolean("IsBurning", isBurning);
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fuelRemaining = tag.getLong("FuelRemaining");
        maxFuel = tag.getLong("MaxFuel");
        generationRate = tag.getLong("GenerationRate");
        isBurning = tag.getBoolean("IsBurning");
    }
    
    @Override
    protected void saveClientData(CompoundTag tag) {
        super.saveClientData(tag);
        tag.putLong("FuelRemaining", fuelRemaining);
        tag.putBoolean("IsBurning", isBurning);
    }
    
    @Override
    protected void loadClientData(CompoundTag tag) {
        super.loadClientData(tag);
        if (tag.contains("FuelRemaining")) {
            fuelRemaining = tag.getLong("FuelRemaining");
        }
        if (tag.contains("IsBurning")) {
            isBurning = tag.getBoolean("IsBurning");
        }
    }
}