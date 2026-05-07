package com.industrium.core.common.integration;

/**
 * Represents an energy conversion process.
 * Base for all cross-system conversions.
 */
public class ConversionProcess {
    
    private final String inputType;
    private final String outputType;
    private final double efficiency;
    private final double throughput;
    
    public ConversionProcess(String input, String output, double efficiency, double throughput) {
        this.inputType = input;
        this.outputType = output;
        this.efficiency = Math.max(0, Math.min(1, efficiency));
        this.throughput = Math.max(0, throughput);
    }
    
    public String getInputType() {
        return inputType;
    }
    
    public String getOutputType() {
        return outputType;
    }
    
    public double getEfficiency() {
        return efficiency;
    }
    
    public double getThroughput() {
        return throughput;
    }
    
    public double convert(double inputAmount) {
        return inputAmount * efficiency;
    }
}