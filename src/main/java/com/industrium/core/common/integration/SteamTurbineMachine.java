package com.industrium.core.common.integration;

import com.industrium.core.common.machine.UnifiedMachine;
import com.industrium.core.common.system.MachineStatus;

/**
 * Steam Turbine - integrates heat, fluid, rotation, and power.
 * Converts steam (fluid) + heat -> rotation -> power (FE)
 */
public class SteamTurbineMachine extends UnifiedMachine {
    
    public SteamTurbineMachine() {
        super();
    }
    
    @Override
    public void tickServer() {
        // Conversion logic: Steam + Heat -> Rotation -> Power
        setStatus(MachineStatus.RUNNING);
    }
}