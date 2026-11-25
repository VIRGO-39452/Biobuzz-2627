package org.firstinspires.ftc.teamcode.Logging;

import org.opencv.core.Mat;

/**
 * imagines a right triangle ABC with AB being perpendicular to BC
 * outputs an object with the hypotenuse (z), angle ABC (a) and its supplement (theta)
 */
public class AutoVariables {
    private final double z;
    private final double a;
    private final double theta;

    public AutoVariables(double x, double y) {
        a = calcAAngle(x, y);
        theta = calcThetaAngle(x, y);
        z = calcZSide(x, y);
    }

    private double calcAAngle(double x, double y) {
        return Math.atan2(x, y);
    }

    private double calcZSide(double x, double y) {
        return Math.hypot(x, y);
    }

    private double calcThetaAngle(double x, double y) {
        return (-1 * Math.atan2(x, y)) - 180;
    }

    public double getA() {
        return a;
    }

    public double getZ() {
        return z;
    }

    public double getTheta() {
        return theta;
    }
}
