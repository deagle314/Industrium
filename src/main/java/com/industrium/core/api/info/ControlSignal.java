package com.industrium.core.api.info;

/**
 * Represents a control signal in the Industrium info system.
 * 
 * Can be binary (on/off), analog (0-100%), or data (numeric/text).
 */
public class ControlSignal {
    
    public static final ControlSignal OFF = new ControlSignal(0, SignalType.BINARY);
    public static final ControlSignal ON = new ControlSignal(1, SignalType.BINARY);
    
    public enum SignalType {
        BINARY,    // on/off
        ANALOG,    // 0-100
        NUMERIC,   // any number
        TEXT,      // string message
        DATA      // structured data
    }
    
    private final double value;
    private final SignalType type;
    private final String data;
    private final long timestamp;
    
    public ControlSignal(double value, SignalType type) {
        this(value, type, null);
    }
    
    public ControlSignal(double value, SignalType type, String data) {
        this.value = value;
        this.type = type;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
    
    public double getValue() {
        return value;
    }
    
    public SignalType getType() {
        return type;
    }
    
    public String getData() {
        return data;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public boolean isActive() {
        return value > 0;
    }
    
    public boolean isBinary() {
        return type == SignalType.BINARY;
    }
    
    public boolean isAnalog() {
        return type == SignalType.ANALOG;
    }
    
    public static ControlSignal binary(boolean on) {
        return on ? ON : OFF;
    }
    
    public static ControlSignal analog(double percentage) {
        double clamped = Math.max(0, Math.min(100, percentage));
        return new ControlSignal(clamped, SignalType.ANALOG);
    }
    
    public static ControlSignal numeric(double value) {
        return new ControlSignal(value, SignalType.NUMERIC);
    }
    
    public static ControlSignal text(String message) {
        return new ControlSignal(1, SignalType.TEXT, message);
    }
    
    @Override
    public String toString() {
        return String.format("Signal[%.1f %s]", value, type);
    }
}