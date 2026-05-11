package com.industrium.core.common.machine.module;

import net.minecraft.nbt.CompoundTag;

/**
 * Module for handling machine progress (e.g. smelting, processing).
 */
public class ProgressModule implements MachineModule {
    private int progress;
    private int maxProgress;

    public ProgressModule() {
        this.progress = 0;
        this.maxProgress = 0;
    }

    @Override
    public void tick() {}

    @Override
    public void save(CompoundTag tag) {
        tag.putInt("Progress", progress);
        tag.putInt("MaxProgress", maxProgress);
    }

    @Override
    public void load(CompoundTag tag) {
        progress = tag.getInt("Progress");
        maxProgress = tag.getInt("MaxProgress");
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public float getProgressPercentage() {
        if (maxProgress <= 0) return 0;
        return (float) progress / maxProgress;
    }

    public void increment() {
        if (progress < maxProgress) {
            progress++;
        }
    }

    public boolean isFinished() {
        return maxProgress > 0 && progress >= maxProgress;
    }

    public void reset() {
        progress = 0;
        maxProgress = 0;
    }
}
