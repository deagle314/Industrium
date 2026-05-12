package com.industrium.core.common.rotation.blockentity;

import com.industrium.core.Industrium;
import com.industrium.core.api.network.IFluidNode;
import com.industrium.core.api.network.SystemType;
import com.industrium.core.common.registry.ModBlockEntities;
import com.industrium.core.common.system.MachineStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

/**
 * Steam Engine block entity.
 * Converts steam pressure into rotational mechanical power.
 */
public class SteamEngineBlockEntity extends BaseRotationMachineBlockEntity implements IFluidNode {
    
    private double steamPressure = 0.0;
    private double efficiency = 0.8;
    private long fluidCapacity = 10000;
    private FluidStack fluidStack = FluidStack.EMPTY;
    private long fluidNetworkId = -1;
    
    private static final double STEAM_TO_POWER_RATIO = 0.3;
    private static final double MAX_STEAM_PRESSURE = 100.0;
    
    public SteamEngineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STEAM_ENGINE.get(), pos, state);
    }
    
    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null && !level.isClientSide) {
            Industrium.FLUID_NETWORK_MANAGER.registerNode(this);
        }
    }

    @Override
    public void setRemoved() {
        if (level != null && !level.isClientSide) {
            Industrium.FLUID_NETWORK_MANAGER.unregisterNode(this);
        }
        super.setRemoved();
    }

    @Override
    public void tickServer() {
        super.tickServer();
        
        // Convert steam from tank to pressure
        if (!fluidStack.isEmpty()) {
            // Assume the fluid is steam-like
            steamPressure = Math.min(steamPressure + (fluidStack.getAmount() / 1000.0), MAX_STEAM_PRESSURE);
            // Consume steam
            int toConsume = Math.min(fluidStack.getAmount(), 10);
            fluidStack.shrink(toConsume);
            if (fluidStack.isEmpty()) fluidStack = FluidStack.EMPTY;
        } else {
            steamPressure = Math.max(steamPressure - 0.5, 0);
        }
        
        // Convert steam pressure to rotational power
        if (steamPressure > 10.0) {
            double powerOutput = steamPressure * STEAM_TO_POWER_RATIO * efficiency;
            torque = powerOutput;
            rpm = 100 + (steamPressure * 5);
            setStatus(MachineStatus.RUNNING);
        } else {
            torque = 0;
            rpm = 0;
            setStatus(MachineStatus.IDLE);
        }
    }
    
    // IRotationNode is handled by base class
    
    // IFluidNode implementation
    @Override
    public FluidStack getFluid() { return fluidStack; }
    @Override
    public long getCapacity() { return fluidCapacity; }
    @Override
    public double getTemperature() { return 100.0; }
    @Override
    public long getPressure() { return (long) steamPressure; }
    @Override
    public void setPressure(long pressure) { this.steamPressure = pressure; }
    @Override
    public long getThroughput() { return 100; }
    @Override
    public double getViscosity() { return 0.5; }
    @Override
    public double getDiameter() { return 1.0; }
    @Override
    public long getNetworkId() { return fluidNetworkId; }
    @Override
    public void setNetworkId(long id) { this.fluidNetworkId = id; }
    
    @Override
    public int fill(FluidStack fluid, boolean simulate) {
        if (fluid.isEmpty()) return 0;
        int canFill = (int) Math.min(fluid.getAmount(), fluidCapacity - fluidStack.getAmount());
        if (!simulate && canFill > 0) {
            if (fluidStack.isEmpty()) {
                fluidStack = fluid.copy();
                fluidStack.setAmount(canFill);
            } else {
                fluidStack.grow(canFill);
            }
        }
        return canFill;
    }

    @Override
    public FluidStack drain(long amount, boolean simulate) {
        if (fluidStack.isEmpty() || amount <= 0) return FluidStack.EMPTY;
        int canDrain = (int) Math.min(fluidStack.getAmount(), amount);
        FluidStack drained = fluidStack.copy();
        drained.setAmount(canDrain);
        if (!simulate && canDrain > 0) {
            fluidStack.shrink(canDrain);
            if (fluidStack.isEmpty()) fluidStack = FluidStack.EMPTY;
        }
        return drained;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putDouble("SteamPressure", steamPressure);
        tag.put("Fluid", fluidStack.writeToNBT(new CompoundTag()));
        tag.putLong("FluidNetworkId", fluidNetworkId);
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        steamPressure = tag.getDouble("SteamPressure");
        fluidStack = FluidStack.loadFluidStackFromNBT(tag.getCompound("Fluid"));
        fluidNetworkId = tag.getLong("FluidNetworkId");
    }
}
