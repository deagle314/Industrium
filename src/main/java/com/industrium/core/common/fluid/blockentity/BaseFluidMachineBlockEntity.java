package com.industrium.core.common.fluid.blockentity;

import com.industrium.core.Industrium;
import com.industrium.core.api.network.IFluidNode;
import com.industrium.core.common.blockentity.BaseMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

/**
 * Base block entity for fluid-handling machines.
 * Handles network registration, fluid storage, and NBT persistence.
 */
public abstract class BaseFluidMachineBlockEntity extends BaseMachineBlockEntity implements IFluidNode {
    
    protected FluidStack fluidStack = FluidStack.EMPTY;
    protected long capacity;
    protected double temperature = 20.0;
    protected long pressure = 100;
    protected double viscosity = 1.0;
    protected double diameter = 1.0;
    protected long networkId = -1;

    public BaseFluidMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, long capacity) {
        super(type, pos, state);
        this.capacity = capacity;
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
    public FluidStack getFluid() {
        return fluidStack;
    }

    @Override
    public long getCapacity() {
        return capacity;
    }

    @Override
    public double getTemperature() {
        return temperature;
    }

    @Override
    public long getPressure() {
        return pressure;
    }

    @Override
    public void setPressure(long pressure) {
        this.pressure = pressure;
    }

    @Override
    public long getThroughput() {
        return 1000; // Default
    }

    @Override
    public double getViscosity() {
        return viscosity;
    }

    @Override
    public double getDiameter() {
        return diameter;
    }

    @Override
    public BlockPos getPos() {
        return worldPosition;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public long getNetworkId() {
        return networkId;
    }

    @Override
    public void setNetworkId(long id) {
        this.networkId = id;
    }

    @Override
    public int fill(FluidStack fluid, boolean simulate) {
        if (fluid.isEmpty() || !canHold(fluid)) return 0;
        
        int canFill = (int) Math.min(fluid.getAmount(), capacity - fluidStack.getAmount());
        if (!simulate && canFill > 0) {
            if (fluidStack.isEmpty()) {
                fluidStack = fluid.copy();
                fluidStack.setAmount(canFill);
            } else {
                fluidStack.grow(canFill);
            }
            setChanged();
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
            if (fluidStack.isEmpty()) {
                fluidStack = FluidStack.EMPTY;
            }
            setChanged();
        }
        return drained;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Fluid", fluidStack.writeToNBT(new CompoundTag()));
        tag.putLong("Capacity", capacity);
        tag.putDouble("Temperature", temperature);
        tag.putLong("Pressure", pressure);
        tag.putDouble("Viscosity", viscosity);
        tag.putDouble("Diameter", diameter);
        tag.putLong("NetworkId", networkId);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fluidStack = FluidStack.loadFluidStackFromNBT(tag.getCompound("Fluid"));
        capacity = tag.getLong("Capacity");
        temperature = tag.getDouble("Temperature");
        pressure = tag.getLong("Pressure");
        viscosity = tag.getDouble("Viscosity");
        diameter = tag.getDouble("Diameter");
        if (tag.contains("NetworkId")) {
            networkId = tag.getLong("NetworkId");
        }
    }

    @Override
    protected void saveClientData(CompoundTag tag) {
        super.saveClientData(tag);
        tag.put("Fluid", fluidStack.writeToNBT(new CompoundTag()));
        tag.putDouble("Temperature", temperature);
        tag.putLong("Pressure", pressure);
    }

    @Override
    protected void loadClientData(CompoundTag tag) {
        super.loadClientData(tag);
        fluidStack = FluidStack.loadFluidStackFromNBT(tag.getCompound("Fluid"));
        temperature = tag.getDouble("Temperature");
        pressure = tag.getLong("Pressure");
    }
}
