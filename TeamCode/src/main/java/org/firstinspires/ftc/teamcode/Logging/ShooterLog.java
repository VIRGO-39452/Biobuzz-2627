package org.firstinspires.ftc.teamcode.Logging;

public class ShooterLog {
    private final double power;
    private final boolean on;
    private final double speed;

    public ShooterLog(double power, boolean on, double speed) {
        this.power = power;
        this.on = on;
        this.speed = speed;
    }

    /**
     * gets the power of the shooter
     * @return - double power of the shooter
     */
    public double getPower() {
        return power;
    }

    /**
     * sees if shooter is on
     * @return - true if it is, false if it isnt
     */
    public boolean isOn() {
        return on;
    }

    /**
     * gets speed of shooter
     * @return - double speed
     */
    public double getSpeed() {
        return speed;
    }
}
