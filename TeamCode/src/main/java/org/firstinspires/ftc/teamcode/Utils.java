package org.firstinspires.ftc.teamcode;

public class Utils {

    private static final double SHOOTER_MAX_RPM = 312.0;
    private static final double SHOOTER_TICKS_PER_REV = 537.7;
    private static final double SHOOTER_RPM_TOLERANCE = 10.0;

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
}