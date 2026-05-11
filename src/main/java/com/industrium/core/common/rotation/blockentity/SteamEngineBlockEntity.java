package com.industrium.core.common.rotation.blockentity;

import com.industrium.core.common.registry.RotationModule;
import com.industrium.core.common.system.MachineStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Steam Engine block entity.
 * Converts heat energy into rotational mechanical power.
 * 
 * Burns fuel to produce steam pressure that drives a rotation mechanism.
 */
public class SteamEngineBlockEntity extends BaseRotationMachineBlockEntity {
    
    private double heatInput = 0.0;
    private double steamPressure = 0.0;
    private double efficiency = 0.8;
    private long fuelRemaining = 0;
    private long maxFuel = 2000;
    private boolean isActive = false;
    
    private static final double HEAT_TO_STEAM_RATIO = 0.5;
    private static final double STEAM_TO_POWER_RATIO = 0.3;
    private static final double MAX_PRESSURE = 100.0;
    
    public SteamEngineBlockEntity(BlockPos pos, BlockState state) {
        super(RotationModule.STEAM_ENGINE_BE.get(), pos, state);
    }
    
    @Override
    public void tickServer() {
        super.tickServer();
        
        // Consume fuel to generate heat
        if (fuelRemaining > 0) {
            fuelRemaining--;
            heatInput = 5.0; // Heat units per tick from burning
            isActive = true;
            setStatus(MachineStatus.RUNNING);
        } else {
            heatInput = 0.0;
            isActive = false;
            setStatus(MachineStatus.IDLE);
        }
        
        // Convert heat to steam pressure
        if (heatInput > 0) {
            steamPressure = Math.min(steamPressure + HEAT_TO_STEAM_RATIO, MAX_PRESSURE);
        } else {
            // Pressure dissipates when no heat input
            steamPressure = Math.max(steamPressure - 0.1, 0);
        }
        
        // Convert steam pressure to rotational power
        if (steamPressure > 10.0) {
            double powerOutput = steamPressure * STEAM_TO_POWER_RATIO * efficiency;
            torque = powerOutput;
            rpm = 100 + (steamPressure * 5);
        } else {
            torque = 0;
            rpm = 0;
        }
    }
    
    @Override
    public double getInertia() {
        return 15.0;
    }
    
    @Override
    public double getFriction() {
        return 0.08;
    }
    
    @Override
    public double getMaxRPM() {
        return 2000.0;
    }
    
    @Override
    public double getMaxTorque() {
        return 1500.0;
    }
    
    public void addFuel(long fuel) {
        this.fuelRemaining = Math.min(this.fuelRemaining + fuel, maxFuel);
    }
    
    public long getFuelRemaining() {
        return fuelRemaining;
    }
    
    public long getMaxFuel() {
        return maxFuel;
    }
    
    public double getSteamPressure() {
        return steamPressure;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putDouble("HeatInput", heatInput);
        tag.putDouble("SteamPressure", steamPressure);
        tag.putDouble("Efficiency", efficiency);
        tag.putLong("FuelRemaining", fuelRemaining);
        tag.putLong("MaxFuel", maxFuel);
        tag.putBoolean("IsActive", isActive);
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        heatInput = tag.getDouble("HeatInput");
        steamPressure = tag.getDouble("SteamPressure");
        efficiency = tag.getDouble("Efficiency");
        fuelRemaining = tag.getLong("FuelRemaining");
        maxFuel = tag.getLong("MaxFuel");
        isActive = tag.getBoolean("IsActive");
    }
    
    @Override
    protected void saveClientData(CompoundTag tag) {
        super.saveClientData(tag);
        tag.putDouble("SteamPressure", steamPressure);
        tag.putLong("FuelRemaining", fuelRemaining);
        tag.putBoolean("IsActive", isActive);
    }
    
    @Override
    protected void loadClientData(CompoundTag tag) {
        super.loadClientData(tag);
        if (tag.contains("SteamPressure")) {
            steamPressure = tag.getDouble("SteamPressure");
        }
        if (tag.contains("FuelRemaining")) {
            fuelRemaining = tag.getLong("FuelRemaining");
        }
        if (tag.contains("IsActive")) {
            isActive = tag.getBoolean("IsActive");
        }
    }
}