package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.LEFT_INTAKE;
import static org.firstinspires.ftc.teamcode.Constants.RIGHT_INTAKE;
import static org.firstinspires.ftc.teamcode.Constants.YELLOW_JACKET_INTAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Logging.IntakeLog;

public class Intake {
    private final CRServo m_left;
    private final CRServo m_right;
    private final DcMotorEx m_yellowjacket;
    private double power;

    public Intake(HardwareMap hardwareMap) {
        m_left = hardwareMap.get(CRServo.class, LEFT_INTAKE);
        m_right = hardwareMap.get(CRServo.class, RIGHT_INTAKE);
        m_yellowjacket = hardwareMap.get(DcMotorEx.class, YELLOW_JACKET_INTAKE);

        m_yellowjacket.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        m_yellowjacket.setDirection(DcMotorSimple.Direction.FORWARD);
        m_yellowjacket.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void runIntake(double power) {

        this.power = power;

        m_left.setPower(power);
        m_right.setPower(-power);
        m_yellowjacket.setPower(-power/100); // high gear ratio makes little motor revolving = HUGE intake spin
    }

    public void stopIntake() {
        this.power = 0.0;

        m_left.setPower(0.0);
        m_right.setPower(0.0);
        m_yellowjacket.setPower(0.0);
    }

    // TODO verify if encoders are used in this subsystem to add stalling and brownout detection

    public IntakeLog logging() {
        return new IntakeLog(
                Math.abs(m_left.getPower()) > 0.001,
                Math.abs(m_right.getPower()) > 0.001,
                Math.abs(m_yellowjacket.getPower()) > 0.001,
                this.power
        );
    }
}
