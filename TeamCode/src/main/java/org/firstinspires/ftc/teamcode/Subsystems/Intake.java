package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.LEFT_INTAKE;
import static org.firstinspires.ftc.teamcode.Constants.RIGHT_INTAKE;
import static org.firstinspires.ftc.teamcode.Constants.YELLOW_JACKET_INTAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Logging.IntakeLog;

public class Intake {
    private final CRServo m_left;
    private final CRServo m_right;
    private final DcMotor m_yellowjacket;

    private double power;

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
        m_yellowjacket.setDirection(DcMotorSimple.Direction.FORWARD); // TODO verify direction
        m_yellowjacket.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * run the intake
     * left and right servo goes full power
     * -1.0 to 1.0, positive pulls in and negative pushes out to get rid of jams
     */
    public void runIntake(double power) {
        // TODO verify directions
        this.power = power;
        m_left.setPower(-1 * power);
        m_right.setPower(power);
        m_yellowjacket.setPower(power);
    }

    /**
     * stop the intake
     * sets both right and left servo power to zero
     */
    public void stopIntake() {
        this.power = 0.0;
        m_left.setPower(0.0);
        m_right.setPower(0.0);
        m_yellowjacket.setPower(0.0);
    }

    /**
     * logging purposes
     * @return IntakeLog
     */
    public IntakeLog logging() {
        return new IntakeLog(
                Math.abs(m_left.getPower()) > 0.001,
                Math.abs(m_right.getPower()) > 0.001,
                Math.abs(m_yellowjacket.getPower()) > 0.001,
                this.power
        );
    }
}
