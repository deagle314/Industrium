package com.industrium.core.common.integration;

import com.industrium.core.common.machine.UnifiedMachine;
import com.industrium.core.common.system.MachineStatus;

/**
 * Electric Motor - integrates power and rotation.
 * Converts FE -> RPM
 */
public class ElectricMotorMachine extends UnifiedMachine {
    
    public ElectricMotorMachine() {
        super();
    }
    
    @Override
    public void tickServer() {
        setStatus(MachineStatus.RUNNING);
    }
}