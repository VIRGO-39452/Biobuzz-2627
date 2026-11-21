package org.firstinspires.ftc.teamcode.Logging;

public class ShooterLog {
    private final double power;
    private final boolean on;

    public ShooterLog(double power, boolean on) {
        this.power = power;
        this.on = on;
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
}
