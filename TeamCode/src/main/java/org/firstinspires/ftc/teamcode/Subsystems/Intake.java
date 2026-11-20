package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.LEFT_INTAKE;
import static org.firstinspires.ftc.teamcode.Constants.RIGHT_INTAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    private final CRServo m_left;
    private final CRServo m_right;

    /**
     * new intake subsystem
     *
     * @param hardwareMap hardware map
     */
    public Intake(HardwareMap hardwareMap) {
        // initialize the hardware maps
        m_left = hardwareMap.get(CRServo.class, LEFT_INTAKE);
        m_right = hardwareMap.get(CRServo.class, RIGHT_INTAKE);
    }

    /**
     * run the intake
     * left and right servo goes full power
     */
    public void runIntake() {
        m_left.setPower(-1);
        m_right.setPower(1);
    }

    /**
     * stop the intake
     * sets both right and left servo power to zero
     */
    public void stopIntake() {
        double stopPower = 0;
        m_left.setPower(stopPower);
        m_right.setPower(stopPower);
    }

    /**
     * checks to see if intake is on by checking if both servos are at a power that != 0
     * @return a boolean array where index 0 represents the left servo, index 1 represents the right servo, and index 2 represents the entire subsystem. true = nonzero power, false = zero power.
     */
    public boolean[] isOn() {
        return new boolean[] {
                m_left.getPower() != 0,
                m_right.getPower() != 0,
                m_left.getPower() != 0 && m_right.getPower() != 0
        };
    }
}
