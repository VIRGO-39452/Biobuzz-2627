package org.firstinspires.ftc.teamcode;

public class Utils {

    private static final double SHOOTER_MAX_RPM = 312.0;
    private static final double SHOOTER_TICKS_PER_REV = 537.7;
    private static final double SHOOTER_RPM_TOLERANCE = 10.0;
    private static final double MIN_FEED_SPEED_RATIO = 0.85;
    private static final double FEED_HYSTERESIS_RPM = 8.0;

    /**
     * Calculates the estimated target RPM from motor power.
     *
     * @param power commanded motor power (-1.0 to 1.0)
     * @return estimated target RPM
     */
    public static double calculateTargetRPM(double power) {
        return power * SHOOTER_MAX_RPM;
    }

    /**
     * Converts encoder velocity from ticks per second to RPM.
     *
     * @param velocityTPS motor velocity in ticks per second
     * @return motor velocity in RPM
     */
    public static double calculateActualRPM(double velocityTPS) {
        return (velocityTPS * 60.0) / SHOOTER_TICKS_PER_REV;
    }

    /**
     * Determines whether the shooter is within its target RPM tolerance.
     *
     * @param targetRPM target RPM
     * @param actualRPM actual RPM
     * @return true if actual RPM is within tolerance
     */
    public static boolean isAtSpeed(double targetRPM, double actualRPM) {
        return Math.abs(targetRPM - actualRPM) <= SHOOTER_RPM_TOLERANCE;
    }

    /**
     * Checks whether the shooter is spinning fast enough in the commanded direction
     * to feed/launch, with hysteresis to avoid rapid toggling near the threshold.
     */
    public static boolean isReadyToFeed(
            double targetRPM,
            double actualRPM,
            boolean currentlyFeeding
    ) {
        if (Math.abs(targetRPM) < 1e-6) {
            return false;
        }

        // Support both forward (+) and reverse (-) shooter commands
        double signedMinRPM = targetRPM * MIN_FEED_SPEED_RATIO;
        double signedStopRPM = (targetRPM * MIN_FEED_SPEED_RATIO)
                - (Math.signum(targetRPM) * FEED_HYSTERESIS_RPM);

        if (targetRPM > 0) {
            return currentlyFeeding ? (actualRPM >= signedStopRPM) : (actualRPM >= signedMinRPM);
        } else {
            return currentlyFeeding ? (actualRPM <= signedStopRPM) : (actualRPM <= signedMinRPM);
        }
    }
}