package org.firstinspires.ftc.teamcode.Logging;

/**
 * Represents a snapshot of the drivetrain's current state.
 *
 * <p>This class stores the power and operational state of both
 * sides of the drivetrain. The values are immutable once the
 * log object is created.</p>
 */
public class DriveTrainLog {

    /**
     * Indicates whether the left side of the drivetrain is
     * currently operating.
     */
    private final boolean leftSideOn;

    /**
     * Indicates whether the right side of the drivetrain is
     * currently operating.
     */
    private final boolean rightSideOn;

    /**
     * The current power applied to the left side of the drivetrain.
     */
    private final double leftSidePower;

    /**
     * The current power applied to the right side of the drivetrain.
     */
    private final double rightSidePower;

    /**
     * Creates a new drivetrain log containing the current state
     * of both sides of the drivetrain.
     *
     * @param leftSidePower  the power applied to the left side
     *                       of the drivetrain
     * @param rightSidePower the power applied to the right side
     *                       of the drivetrain
     * @param rightSideOn    whether the right side of the
     *                       drivetrain is currently operating
     * @param leftSideOn     whether the left side of the
     *                       drivetrain is currently operating
     */
    public DriveTrainLog(
            double leftSidePower,
            double rightSidePower,
            boolean rightSideOn,
            boolean leftSideOn
    ) {
        this.leftSideOn = leftSideOn;
        this.rightSideOn = rightSideOn;
        this.leftSidePower = leftSidePower;
        this.rightSidePower = rightSidePower;
    }

    /**
     * Returns whether the left side of the drivetrain is
     * currently operating.
     *
     * @return {@code true} if the left side is on;
     *         {@code false} otherwise
     */
    public boolean isLeftSideOn() {
        return this.leftSideOn;
    }

    /**
     * Returns whether the right side of the drivetrain is
     * currently operating.
     *
     * @return {@code true} if the right side is on;
     *         {@code false} otherwise
     */
    public boolean isRightSideOn() {
        return this.rightSideOn;
    }

    /**
     * Returns the current power applied to the left side of
     * the drivetrain.
     *
     * @return the left side's power, ranging from -1.0 to 1.0
     */
    public double getLeftSidePower() {
        return leftSidePower;
    }

    /**
     * Returns the current power applied to the right side of
     * the drivetrain.
     *
     * @return the right side's power, ranging from -1.0 to 1.0
     */
    public double getRightSidePower() {
        return rightSidePower;
    }
}