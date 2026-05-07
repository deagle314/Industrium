package com.industrium.core.common.integration;

import com.industrium.core.common.machine.UnifiedMachine;
import com.industrium.core.common.system.MachineStatus;

/**
 * Motor Pump - integrates power, rotation, and fluid.
 * Converts power + rotation -> fluid flow
 */
public class MotorPumpMachine extends UnifiedMachine {
    
    public MotorPumpMachine() {
        super();
    }
    
    @Override
    public void tickServer() {
        setStatus(MachineStatus.RUNNING);
    }
}