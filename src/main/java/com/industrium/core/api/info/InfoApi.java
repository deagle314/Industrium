package com.industrium.core.api.info;

/**
 * Main API entry point for the Industrium Information & Control System.
 * Factory and utility methods.
 */
public final class InfoApi {
    
    public static final String INFO_NETWORK = "industrium:info";
    
    /** Default update interval in ticks */
    public static final int DEFAULT_TICK_RATE = 4;
    
    private InfoApi() {
        // Utility class
    }
    
    /**
     * Creates a new data provider wrapping a value.
     */
    public static IDataProvider createSensor(double value) {
        return () -> ControlSignal.numeric(value);
    }
    
    /**
     * Creates a binary sensor.
     */
    public static IDataProvider createBinarySensor(boolean state) {
        return () -> ControlSignal.binary(state);
    }
    
    /**
     * Creates a new controllable machine skeleton.
     */
    public static IControllableMachine createController() {
        return new SimpleController();
    }
    
    /**
     * Creates a signal combining many inputs (AND behavior).
     */
    public static ControlSignal and(ControlSignal... inputs) {
        for (ControlSignal s : inputs) {
            if (!s.isActive()) return ControlSignal.OFF;
        }
        return ControlSignal.ON;
    }
    
    /**
     * Creates a signal combining many inputs (OR behavior).
     */
    public static ControlSignal or(ControlSignal... inputs) {
        for (ControlSignal s : inputs) {
            if (s.isActive()) return ControlSignal.ON;
        }
        return ControlSignal.OFF;
    }
    
    /**
     * Inverts a signal.
     */
    public static ControlSignal not(ControlSignal input) {
        return ControlSignal.binary(!input.isActive());
    }
    
    /**
     * Simple controller implementation.
     */
    private static class SimpleController implements IControllableMachine {
        private boolean running;
        
        @Override
        public void start() {
            running = true;
        }
        
        @Override
        public void stop() {
            running = false;
        }
        
        @Override
        public boolean isRunning() {
            return running;
        }
    }
}