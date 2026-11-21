package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.LEFT_INTAKE;
import static org.firstinspires.ftc.teamcode.Constants.RIGHT_INTAKE;
import static org.firstinspires.ftc.teamcode.Constants.YELLOW_JACKET_INTAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
//this is a test to see if my github functionality works
    private final CRServo m_left;
    private final CRServo m_right;
    private final DcMotor m_yellowjacket;

    /**
     * Stores the current state of the intake subsystem for logging purposes.
     *
     * <p>Contains the activation states of the left servo, right servo,
     * and Gecko wheels, along with the current power being applied
     * to the intake.</p>
     */
    public static class IntakeLogs {

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
        public IntakeLogs(
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

    /**
     * new intake subsystem
     *
     * @param hardwareMap hardware map
     */
    public Intake(HardwareMap hardwareMap) {
        // initialize the hardware maps
        m_left = hardwareMap.get(CRServo.class, LEFT_INTAKE);
        m_right = hardwareMap.get(CRServo.class, RIGHT_INTAKE);
        m_yellowjacket = hardwareMap.get(DcMotor.class, YELLOW_JACKET_INTAKE);

        m_yellowjacket.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        m_yellowjacket.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * run the intake
     * left and right servo goes full power
     */
    public void runIntake(double power) {
        // TODO verify directions
        m_left.setPower(-1 * power);
        m_right.setPower(power);
        m_yellowjacket.setPower(power);
    }

    /**
     * stop the intake
     * sets both right and left servo power to zero
     */
    public void stopIntake() {
        double stopPower = 0;
        m_left.setPower(stopPower);
        m_right.setPower(stopPower);
        m_yellowjacket.setPower(stopPower);
    }

    /**
     * Checks whether each component of the intake subsystem is running by
     * checking whether its motor power is nonzero.
     *
     * @return a boolean array where:
     *         index 0 represents the left servo,
     *         index 1 represents the right servo,
     *         index 2 represents the Yellow Jacket motor,
     *         index 3 represents the entire subsystem.
     *         Each value is true if the corresponding motor(s) have nonzero
     *         power, and false otherwise.
     */
    public boolean[] isOn() {
        return new boolean[] {
                m_left.getPower() != 0,
                m_right.getPower() != 0,
                m_yellowjacket.getPower() != 0,
                m_left.getPower() != 0 && m_right.getPower() != 0 && m_yellowjacket.getPower() != 0
        };
    }
}
