package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.BROWNOUT_SHUTOFF_SECONDS;
import static org.firstinspires.ftc.teamcode.Constants.BROWNOUT_TIME_SECONDS;
import static org.firstinspires.ftc.teamcode.Constants.LEFT_WHEEL;
import static org.firstinspires.ftc.teamcode.Constants.MAX_CURRENT;
import static org.firstinspires.ftc.teamcode.Constants.MIN_CURRENT;
import static org.firstinspires.ftc.teamcode.Constants.MIN_VELOCITY;
import static org.firstinspires.ftc.teamcode.Constants.MIN_VOLTAGE;
import static org.firstinspires.ftc.teamcode.Constants.RIGHT_WHEEL;
import static org.firstinspires.ftc.teamcode.Constants.STALL_TIME_SECONDS;
import static org.firstinspires.ftc.teamcode.Constants.VOLTAGE_SENSOR;


import java.lang.Math;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.ElectricalSafeguards;
import org.firstinspires.ftc.teamcode.Logging.DriveTrainLog;

public class DriveTrain implements ElectricalSafeguards {
    private final DcMotorEx m_left;
    private final DcMotorEx m_right;
    private final ElapsedTime stallTimer = new ElapsedTime();
    private final ElapsedTime brownOutTimer = new ElapsedTime();
    private final VoltageSensor m_voltageSensor;

    public DriveTrain(HardwareMap hardwareMap) {

        // to read voltage
        m_voltageSensor = hardwareMap.get(VoltageSensor.class, VOLTAGE_SENSOR);

        // left wheel
        m_left = hardwareMap.get(DcMotorEx.class, LEFT_WHEEL);
        m_left.setDirection(DcMotorEx.Direction.REVERSE); // this is reversed since the motor would be mounted the other way around?
        m_left.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        m_left.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        // right wheel
        m_right = hardwareMap.get(DcMotorEx.class, RIGHT_WHEEL);
        m_right.setDirection(DcMotorEx.Direction.FORWARD); // this is forward since the motor would be mounted the correct way around
        m_right.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        m_right.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }
    // check documentation for how this drive works
    public void arcadeDrive(double forward, double turn) {
        if (isStalled()) {
            driveStop();
        }

        double leftPower = forward - turn;
        double rightPower = forward + turn;

        double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));

        if (max > 1.0) {
            leftPower /= max;
            rightPower /= max;
        }

        if (isBrownout()) {
            leftPower *= 0.5;
            rightPower *= 0.5;
        }

        m_left.setPower(leftPower);
        m_right.setPower(rightPower);

    }

    public void driveStop() {
        m_left.setPower(0);
        m_right.setPower(0);
    }

    @Override
    public boolean isStalled() {
        double leftVelocity = Math.abs(m_left.getVelocity());
        double rightVelocity = Math.abs(m_right.getVelocity());
        double current = getCurrent();

        boolean leftCheck = current < MIN_CURRENT && leftVelocity < MIN_VELOCITY;
        boolean rightCheck = current < MIN_CURRENT && rightVelocity < MIN_VELOCITY;

        if (leftCheck && rightCheck) {
            return stallTimer.seconds() >= STALL_TIME_SECONDS;
        } else {
            stallTimer.reset();
            return false;
        }
    }

    // get current
    @Override
    public double getCurrent() {
        double leftCurrent = m_left.getCurrent(CurrentUnit.AMPS);
        double rightCurrent = m_right.getCurrent(CurrentUnit.AMPS);

        return leftCurrent + rightCurrent;
    }

    // get voltage
    @Override
    public double getVoltage() {
       return m_voltageSensor.getVoltage();
    }

    @Override
    public boolean isBrownout() {
        double voltage = getVoltage();

        double leftCurrent = m_left.getCurrent(CurrentUnit.AMPS);
        double rightCurrent = m_right.getCurrent(CurrentUnit.AMPS);

        double leftVelocity = Math.abs(m_left.getVelocity());
        double rightVelocity = Math.abs(m_right.getVelocity());

        boolean leftBrownOut = leftCurrent > MAX_CURRENT && voltage < MIN_VOLTAGE && leftVelocity < MIN_VELOCITY;
        boolean rightBrownOut = rightCurrent > MAX_CURRENT && voltage < MIN_VOLTAGE && rightVelocity < MIN_VELOCITY;

        if (leftBrownOut || rightBrownOut) {
            return brownOutTimer.seconds() >= BROWNOUT_TIME_SECONDS;
        } else {
            brownOutTimer.reset();
            return false;
        }
    }

    public DriveTrainLog logging() {
        double leftPower = m_left.getPower(); //assigns the left motor's power (-1.0 to 1.0) to a double leftPower
        double rightPower = m_right.getPower(); //assigns the right motor's power (-1.0 to 1.0) to a double rightPower

        return new DriveTrainLog(
                leftPower,
                rightPower,
                rightPower != 0,   //yes or no is the right wheel powered
                leftPower != 0     //yes or no is the left wheel powered
        );
    }
}
