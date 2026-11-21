package org.firstinspires.ftc.teamcode.Logging;

/**
 * Represents a snapshot of the indexer's current state.
 *
 * <p>This class stores information about the indexer's power
 * and whether it is currently operating. The values are
 * immutable once the log object is created.</p>
 */
public class IndexerLog {

    /**
     * The current power applied to the indexer servo.
     */
    private final double power;

    /**
     * Indicates whether the indexer is currently operating.
     */
    private final boolean isOn;

    /**
     * Creates a new indexer log containing the specified state.
     *
     * @param power the current power applied to the indexer servo
     * @param isOn  whether the indexer is currently operating
     */
    public IndexerLog(double power, boolean isOn) {
        this.power = power;
        this.isOn = isOn;
    }

    /**
     * Returns the power currently applied to the indexer servo.
     *
     * @return the indexer's power, ranging from -1.0 to 1.0
     */
    public double getPower() {
        return this.power;
    }

    /**
     * Returns whether the indexer is currently operating.
     *
     * @return {@code true} if the indexer is on;
     *         {@code false} otherwise
     */
    public boolean isOn() {
        return this.isOn;
    }
}