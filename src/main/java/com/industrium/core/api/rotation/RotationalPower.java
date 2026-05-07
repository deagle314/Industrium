package com.industrium.core.api.rotation;

/**
 * Rotational power system - represents rotational motion with speed (RPM) and torque (PU).
 * 
 * RPM = rotations per minute
 * PU = power units representing rotational force/torque
 */
public class RotationalPower {
    
    public static final RotationalPower NONE = new RotationalPower(0, 0);
    public static final double BASE_RPM = 8.0;  // Base RPM reference
    public static final double BASE_PU = 100.0;  // Base power units
    
    private final double rpm;
    private final double powerUnits;
    
    public RotationalPower(double rpm, double powerUnits) {
        this.rpm = Math.max(0, rpm);
        this.powerUnits = Math.max(0, powerUnits);
    }
    
    /**
     * Gets rotations per minute.
     */
    public double getRpm() {
        return rpm;
    }
    
    /**
     * Gets power units (torque).
     */
    public double getPowerUnits() {
        return powerUnits;
    }
    
    /**
     * Gets angular velocity in radians per tick.
     */
    public double getAngularVelocity() {
        return (rpm * 2.0 * Math.PI) / 720.0; // Convert RPM to rad/tick
    }
    
    /**
     * Gets mechanical power in watts (simplified).
     */
    public double getPower() {
        return (rpm * powerUnits) / 60.0;
    }
    
    /**
     * Creates a new rotational power with modified RPM.
     */
    public RotationalPower withRpm(double rpm) {
        return new RotationalPower(rpm, powerUnits);
    }
    
    /**
     * Creates a new rotational power with modified power units.
     */
    public RotationalPower withPower(double pu) {
        return new RotationalPower(rpm, pu);
    }
    
    /**
     * Adds this to another rotational power (for series connection).
     */
    public RotationalPower add(RotationalPower other) {
        return new RotationalPower(
            (rpm + other.rpm) / 2.0,  // Average RPM
            powerUnits + other.powerUnits  // Sum power
        );
    }
    
    /**
     * Multiplies RPM and power (for gear ratio).
     */
    public RotationalPower multiply(double ratio) {
        return new RotationalPower(rpm * ratio, powerUnits / ratio);
    }
    
    @Override
    public String toString() {
        return String.format("RotationalPower[%.1f RPM, %.1f PU]", rpm, powerUnits);
    }
}