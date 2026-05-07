package com.industrium.core.common.system;

/**
 * Unified machine status enum.
 * Common status for all Industrium machines.
 */
public enum MachineStatus {
    OFFLINE("Machine powered down"),
    IDLE("Ready but no input"),
    STARTING("Warming up"),
    RUNNING("Operating normally"),
    STARVED("Waiting for input"),
    OVERLOADED("Exceeding capacity"),
    OVERHEATED("Temperature critical"),
    JAMMED("Flow blocked"),
    FAULTED("Error detected");
    
    private final String description;
    
    MachineStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isOperational() {
        return this == RUNNING || this == IDLE;
    }
    
    public boolean needsAttention() {
        return this == OVERLOADED || this == OVERHEATED || this == JAMMED || this == FAULTED;
    }
}