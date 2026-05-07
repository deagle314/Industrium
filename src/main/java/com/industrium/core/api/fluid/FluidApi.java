package com.industrium.core.api.fluid;

/**
 * Main API entry point for the Industrium Fluid System.
 * Factory and utility methods for fluid handling.
 */
public final class FluidApi {
    
    public static final String FLUID_NETWORK = "industrium:fluid";
    
    /** Default ambient temperature in Celsius */
    public static final double AMBIENT_TEMPERATURE = 20.0;
    
    private FluidApi() {
        // Utility class
    }
    
    /**
     * Creates a new fluid container with default properties.
     */
    public static IFluidContainer createContainer(long capacity) {
        return new CommonFluidContainer(capacity);
    }
    
    /**
     * Creates a fluid container with custom throughput.
     */
    public static IFluidContainer createContainer(long capacity, long throughput) {
        return new CommonFluidContainer(capacity, throughput);
    }
    
    /**
     * Checks if temperature causes phase change for water.
     */
    public static boolean isSteam(double temp) {
        return temp >= 100.0;
    }
    
    /**
     * Checks if temperature causes solidification.
     */
    public static boolean isFrozen(double temp) {
        return temp <= 0.0;
    }
    
    /**
     * Gets the pressure drop over distance.
     */
    public static long getPressureDrop(int distanceBlocks, long flowRate, long pipeCapacity) {
        if (pipeCapacity <= 0) return 0;
        return (distanceBlocks * flowRate) / pipeCapacity;
    }
    
    /**
     * Simple fluid container implementation.
     */
    private static class CommonFluidContainer implements IFluidContainer {
        private final long capacity;
        private final long throughput;
        private long quantity;
        private double temperature;
        private long pressure;
        private boolean isGas;
        
        CommonFluidContainer(long capacity) {
            this(capacity, 100);
        }
        
        CommonFluidContainer(long capacity, long throughput) {
            this.capacity = capacity;
            this.throughput = throughput;
            this.quantity = 0;
            this.temperature = AMBIENT_TEMPERATURE;
            this.pressure = 100;
            this.isGas = false;
        }
        
        @Override
        public net.minecraftforge.fluids.FluidStack getFluid() {
            return net.minecraftforge.fluids.FluidStack.EMPTY;
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
        public long getThroughput() {
            return throughput;
        }
        
        @Override
        public int fill(net.minecraftforge.fluids.FluidStack fluid, boolean simulate) {
            if (fluid.isEmpty()) return 0;
            long toFill = Math.min(fluid.getAmount(), capacity - quantity);
            if (!simulate) {
                quantity += toFill;
            }
            return (int) toFill;
        }
        
        @Override
        public net.minecraftforge.fluids.FluidStack drain(long amount, boolean simulate) {
            long toDrain = Math.min(amount, quantity);
            if (!simulate) {
                quantity -= toDrain;
            }
            return net.minecraftforge.fluids.FluidStack.EMPTY;
        }
    }
}