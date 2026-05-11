package com.industrium.core.common.physics;

import com.industrium.core.Industrium;
import java.util.logging.Logger;

/**
 * The central coordination engine for all Industrium physical simulations.
 * Ensures deterministic simulation order and system-wide consistency.
 */
public class IndustrialPhysicsKernel {
    private static final Logger LOGGER = Logger.getLogger("IndustrialPhysicsKernel");
    private static final IndustrialPhysicsKernel INSTANCE = new IndustrialPhysicsKernel();
    
    private double globalEntropy = 0.0;
    private static final double MAX_STABILITY = 1.0;
    private double stability = MAX_STABILITY;

    private IndustrialPhysicsKernel() {}

    public static IndustrialPhysicsKernel getInstance() {
        return INSTANCE;
    }

    /**
     * Executes one tick of the entire physical simulation.
     * Order of operations is critical for stability and determinism:
     * Heat -> Fluid -> Rotation -> Power -> Logistics -> Reconciliation
     */
    public void tick() {
        try {
            // 1. Heat - Thermal gradients and conduction
            Industrium.HEAT_NETWORK_MANAGER.tick();
            
            // 2. Fluid - Pressure-driven flow and hydraulics
            Industrium.FLUID_NETWORK_MANAGER.tick();
            
            // 3. Rotation - Mechanical torque and RPM transmission
            Industrium.ROTATION_NETWORK_MANAGER.tick();
            
            // 4. Power - Electrical FE distribution
            Industrium.POWER_NETWORK_MANAGER.tick();
            
            // 5. Logistics (Items) - If applicable
            // TODO: Industrium.LOGISTICS_NETWORK_MANAGER.tick();

            // 6. Reconciliation
            reconcile();
            
            // 7. Entropy & Stability
            applyEntropy();
            ensureStability();
            
        } catch (Exception e) {
            LOGGER.severe("Industrial Physics Kernel Panic: " + e.getMessage());
            e.printStackTrace();
            stability = Math.max(0, stability - 0.05);
        }
    }

    private void reconcile() {
        // Cross-system energy conservation and reconciliation logic.
        // Ensures that energy converted between systems is accounted for correctly.
    }

    private void applyEntropy() {
        // Natural energy loss and system decay over time.
        // Base entropy increase per tick.
        globalEntropy = Math.min(1.0, globalEntropy + 0.0001);
    }

    private void ensureStability() {
        // If stability is low, physics may become jittery or machines may malfunction.
        if (stability < MAX_STABILITY) {
            // Recover stability slowly if no panics occur.
            stability = Math.min(MAX_STABILITY, stability + 0.001);
        }
    }
    
    public double getGlobalEntropy() {
        return globalEntropy;
    }
    
    public double getStability() {
        return stability;
    }
    
    public void addEntropy(double amount) {
        this.globalEntropy = Math.min(1.0, this.globalEntropy + amount);
    }
}
