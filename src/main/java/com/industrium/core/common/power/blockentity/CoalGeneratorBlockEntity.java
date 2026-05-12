package com.industrium.core.common.power.blockentity;

import com.industrium.core.api.power.IGenerator;
import com.industrium.core.api.power.VoltageTier;
import com.industrium.core.common.machine.AbstractMachineBlockEntity;
import com.industrium.core.common.machine.module.EnergyModule;
import com.industrium.core.common.registry.ModBlockEntities;
import com.industrium.core.common.system.MachineStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Coal Generator block entity.
 * Burns fuel to generate power.
 */
public class CoalGeneratorBlockEntity extends AbstractMachineBlockEntity implements IGenerator {
    
    private final EnergyModule energyModule;
    private long generationRate;
    private long fuelRemaining;
    private long maxFuel;
    private boolean isBurning;
    
    public CoalGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COAL_GENERATOR.get(), pos, state);
        this.energyModule = addModule(new EnergyModule(1000, VoltageTier.LV));
        this.generationRate = VoltageTier.LV.getTransferRate();
        this.fuelRemaining = 0;
        this.maxFuel = 1000;
        this.status = MachineStatus.IDLE;
    }
    
    @Override
    public void tickServer() {
        super.tickServer();
        
        // Burn fuel if we have it
        if (fuelRemaining > 0) {
            fuelRemaining--;
            isBurning = true;
            setStatus(MachineStatus.RUNNING);
        } else {
            isBurning = false;
            setStatus(MachineStatus.IDLE);
        }
    }
    
    /**
     * Adds fuel to the generator.
     */
    public void addFuel(long fuel) {
        fuelRemaining = Math.min(fuelRemaining + fuel, maxFuel);
        setStatus(MachineStatus.RUNNING);
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
            "Tier: " + energyModule.getVoltageTier().name()
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
        return energyModule.getVoltageTier();
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
    public long getFuelRemaining() {
        return fuelRemaining;
    }

    @Override
    public long getMaxFuel() {
        return maxFuel;
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
