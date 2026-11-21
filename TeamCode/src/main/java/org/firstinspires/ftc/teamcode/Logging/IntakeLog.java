package org.firstinspires.ftc.teamcode.Logging;

/**
 * Stores the current state of the intake subsystem for logging purposes.
 *
 * <p>Contains the activation states of the left servo, right servo,
 * and Gecko wheels, along with the current power being applied
 * to the intake.</p>
 */
public class IntakeLog {

    /** Whether the left intake servo is currently enabled. */
    private final boolean leftServoOn;

    /** Whether the right intake servo is currently enabled. */
    private final boolean rightServoOn;

    /** Whether the Gecko wheels are currently enabled. */
    private final boolean geckoWheelsOn;

    /** The power currently being applied to the intake. */
    private final double power;

    /**
     * Constructs an IntakeLogs object containing the current intake state.
     *
     * @param leftServoOn whether the left servo is enabled
     * @param rightServoOn whether the right servo is enabled
     * @param geckoWheelsOn whether the Gecko wheels are enabled
     * @param power the current intake power
     */
    public IntakeLog(
            boolean leftServoOn,
            boolean rightServoOn,
            boolean geckoWheelsOn,
            double power
    ) {
        this.leftServoOn = leftServoOn;
        this.rightServoOn = rightServoOn;
        this.geckoWheelsOn = geckoWheelsOn;
        this.power = power;
    }

    /**
     * Returns whether the left intake servo is enabled.
     *
     * @return true if the left servo is enabled, false otherwise
     */
    public boolean isLeftServoOn() {
        return leftServoOn;
    }

    /**
     * Returns whether the right intake servo is enabled.
     *
     * @return true if the right servo is enabled, false otherwise
     */
    public boolean isRightServoOn() {
        return rightServoOn;
    }

    /**
     * Returns whether the Gecko wheels are enabled.
     *
     * @return true if the Gecko wheels are enabled, false otherwise
     */
    public boolean areGeckoWheelsOn() {
        return geckoWheelsOn;
    }

    /**
     * Returns the current intake power.
     *
     * @return the intake power
     */
    public double getPower() {
        return power;
    }

    /**
     * Returns a string representation of the current intake state.
     *
     * @return a formatted string containing all intake log values
     */
    @Override
    public String toString() {
        return "IntakeLogs{" +
                "leftServoOn=" + leftServoOn +
                ", rightServoOn=" + rightServoOn +
                ", geckoWheelsOn=" + geckoWheelsOn +
                ", power=" + power +
                '}';
    }
}

