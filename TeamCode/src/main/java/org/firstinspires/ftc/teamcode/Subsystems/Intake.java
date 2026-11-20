package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.LEFT_INTAKE;
import static org.firstinspires.ftc.teamcode.Constants.RIGHT_INTAKE;
import static org.firstinspires.ftc.teamcode.Constants.YELLOW_JACKET_INTAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    private final CRServo m_left;
    private final CRServo m_right;
    private final DcMotor m_yellowjacket;

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
    }

    /**
     * run the intake
     * left and right servo goes full power
     */
    public void runIntake() {
        // TODO verify directions
        m_left.setPower(-1);
        m_right.setPower(1);
        m_yellowjacket.setPower(1);
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
