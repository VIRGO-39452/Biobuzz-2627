package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.YELLOW_JACKET_SHOOTER;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    private final DcMotor m_yellowjacket;

    public static class ShooterLogs {
        private final double power;
        private final boolean on;

        public ShooterLogs(double power, boolean on) {
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

    /**
     * make a new shooter subsystem
     *
     * @param hardwareMap - hardware map
     */
    public Shooter(HardwareMap hardwareMap) {
        m_yellowjacket = hardwareMap.get(DcMotor.class, YELLOW_JACKET_SHOOTER);
        m_yellowjacket.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m_yellowjacket.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * runs the shooter
     *
     * @param power - set the power to run the shooter (negative runs backwards, positive runs forward)
     */
    public void runShooter(double power) {
        m_yellowjacket.setPower(power);
    }

    /**
     * stop the shooter
     */
    public void stopShooter() {
        m_yellowjacket.setPower(0.0);
    }

    /**
     * Retrieves the current shooter state for logging.
     *
     * @return a ShooterLogs (double power, boolean on) object containing the shooter's power and whether it is on
     */
    public ShooterLogs logs() {
        double power = m_yellowjacket.getPower();
        return new ShooterLogs(
                power,
                power != 0
        );
    }
}