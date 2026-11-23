package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.LEFT_INTAKE;
import static org.firstinspires.ftc.teamcode.Constants.MIN_CURRENT;
import static org.firstinspires.ftc.teamcode.Constants.MIN_VELOCITY;
import static org.firstinspires.ftc.teamcode.Constants.RIGHT_INTAKE;
import static org.firstinspires.ftc.teamcode.Constants.VOLTAGE_SENSOR;
import static org.firstinspires.ftc.teamcode.Constants.YELLOW_JACKET_INTAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Interface;
import org.firstinspires.ftc.teamcode.Logging.IntakeLog;

public class Intake implements Interface {

    private final CRServo m_left;
    private final CRServo m_right;
    private final DcMotor m_yellowjacket;
    private final VoltageSensor m_voltageSensor;

    private double power;

    public Intake(HardwareMap hardwareMap) {
        m_left = hardwareMap.get(CRServo.class, LEFT_INTAKE);
        m_right = hardwareMap.get(CRServo.class, RIGHT_INTAKE);
        m_yellowjacket = hardwareMap.get(DcMotor.class, YELLOW_JACKET_INTAKE);

        m_voltageSensor = hardwareMap.get(VoltageSensor.class, VOLTAGE_SENSOR);

        m_yellowjacket.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        m_yellowjacket.setDirection(DcMotorSimple.Direction.FORWARD);

        m_yellowjacket.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // had to change it to RUN_USING_ENCODER for velocity control
    }

    public void runIntake(double power) {

        if (isStalled()) {
            stopIntake();
            return;
        }

        this.power = power;

        m_left.setPower(power);
        m_right.setPower(-power);
        m_yellowjacket.setPower(-power);
    }

    public void stopIntake() {
        this.power = 0.0;

        m_left.setPower(0.0);
        m_right.setPower(0.0);
        m_yellowjacket.setPower(0.0);
    }

    @Override
    public boolean isStalled() {
        double velocity = Math.abs(m_yellowjacket.getVelocity());
        double current = m_yellowjacket.getCurrent(CurrentUnit.AMPS);

        return current > MIN_CURRENT
                && velocity < MIN_VELOCITY;
    }

    @Override
    public double getCurrent() {
        return m_yellowjacket.getCurrent(CurrentUnit.AMPS);
    }

    @Override
    public double getVoltage() {
        return m_voltageSensor.getVoltage();
    }

    public IntakeLog logging() {
        return new IntakeLog(
                Math.abs(m_left.getPower()) > 0.001,
                Math.abs(m_right.getPower()) > 0.001,
                Math.abs(m_yellowjacket.getPower()) > 0.001,
                this.power
        );
    }
}
