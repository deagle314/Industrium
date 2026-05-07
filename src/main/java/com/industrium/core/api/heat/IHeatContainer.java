package com.industrium.core.api.heat;

/**
 * Heat Unit (HU) storage interface for the Industrium heat system.
 * 
 * Heat Units represent transferable thermal energy that can flow between
 * blocks via thermal conductors.
 */
public interface IHeatContainer {
    
    /**
     * Gets current heat stored in HU.
     * 
     * @return Current heat
     */
    double getHeat();
    
    /**
     * Gets maximum heat capacity.
     * 
     * @return Max heat capacity
     */
    double getMaxHeat();
    
    /**
     * Gets current temperature in Celsius.
     * 
     * @return Temperature
     */
    double getTemperature();
    
    /**
     * Gets the thermal conductivity (HU/tick per temperature difference).
     * 
     * @return Conductivity factor
     */
    double getConductivity();
    
    /**
     * Receives heat into this container.
     * 
     * @param heat Amount to receive
     * @param simulate If true, don't actually receive
     * @return Heat actually received
     */
    double receiveHeat(double heat, boolean simulate);
    
    /**
     * Extracts heat from this container.
     * 
     * @param heat Amount to extract
     * @param simulate If true, don't actually extract
     * @return Heat actually extracted
     */
    double extractHeat(double heat, boolean simulate);
    
    /**
     * Gets the insulation value (0.0 = no insulation, 1.0 = perfect).
     * 
     * @return Insulation factor
     */
    default double getInsulation() {
        return 0.5;
    }
    
    /**
     * Checks if this container can receive heat.
     */
    default boolean canReceiveHeat() {
        return getHeat() < getMaxHeat();
    }
    
    /**
     * Checks if this container can output heat.
     */
    default boolean canOutputHeat() {
        return getHeat() > 0;
    }
}