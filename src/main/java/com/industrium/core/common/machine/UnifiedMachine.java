package com.industrium.core.common.machine;

import com.industrium.core.common.system.MachineStatus;

/**
 * Unified machine base.
 * All Industrium machines extend this.
 */
public abstract class UnifiedMachine {
    
    private MachineStatus status = MachineStatus.OFFLINE;
    private boolean clientNeedsUpdate;
    
    /**
     * Called each server tick.
     */
    public void tickServer() {
        // Override in subclasses
    }
    
    /**
     * Called each client tick.
     */
    public void tickClient() {
        // Override in subclasses
    }
    
    /**
     * Gets current status.
     */
    public MachineStatus getStatus() {
        return status;
    }
    
    /**
     * Sets status.
     */
    protected void setStatus(MachineStatus newStatus) {
        if (this.status != newStatus) {
            this.status = newStatus;
            this.clientNeedsUpdate = true;
        }
    }
    
    /**
     * Gets if client should sync.
     */
    public boolean needsClientUpdate() {
        return clientNeedsUpdate;
    }
    
    /**
     * Marks client synced.
     */
    public void markClientSynced() {
        this.clientNeedsUpdate = false;
    }
    
    /**
     * Starts the machine.
     */
    public void start() {
        setStatus(MachineStatus.STARTING);
    }
    
    /**
     * Stops the machine.
     */
    public void stop() {
        setStatus(MachineStatus.OFFLINE);
    }
    
    /**
     * Checks if running.
     */
    public boolean isRunning() {
        return status == MachineStatus.RUNNING || status == MachineStatus.STARTING;
    }
}