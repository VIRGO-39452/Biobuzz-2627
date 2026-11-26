package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.BROWNOUT_RECOVERY_VOLTAGE;
import static org.firstinspires.ftc.teamcode.Constants.BROWNOUT_SHUTOFF_SECONDS;
import static org.firstinspires.ftc.teamcode.Constants.BROWNOUT_TIME_SECONDS;
import static org.firstinspires.ftc.teamcode.Constants.LEFT_WHEEL;
import static org.firstinspires.ftc.teamcode.Constants.MIN_CURRENT;
import static org.firstinspires.ftc.teamcode.Constants.MIN_VELOCITY;
import static org.firstinspires.ftc.teamcode.Constants.MIN_VOLTAGE;
import static org.firstinspires.ftc.teamcode.Constants.RIGHT_WHEEL;
import static org.firstinspires.ftc.teamcode.Constants.STALL_TIME_SECONDS;
import static org.firstinspires.ftc.teamcode.Constants.TRACK_WIDTH;
import static org.firstinspires.ftc.teamcode.Constants.WHEEL_DIAMETER;


import java.lang.Math;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ElectricalSafeguards;
import org.firstinspires.ftc.teamcode.Logging.DriveTrainLog;

public class DriveTrain implements ElectricalSafeguards {
    private final DcMotorEx m_left;
    private final DcMotorEx m_right;
    private final ElapsedTime stallTimer = new ElapsedTime();
    private final ElapsedTime brownOutTimer = new ElapsedTime();
    private final ElapsedTime brownoutDurationTimer = new ElapsedTime();
    private boolean inBrownoutState = false;
   // private final VoltageSensor m_voltageSensor;

    public DriveTrain(HardwareMap hardwareMap) {

        // to read voltage
        // m_voltageSensor = hardwareMap.get(VoltageSensor.class, VOLTAGE_SENSOR);

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
            return;
        }

        double leftPower = forward - turn;
        double rightPower = forward + turn;

        double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));

        if (max > 1.0) {
            leftPower /= max;
            rightPower /= max;
        }

        if (isBrownout()) {
            if (brownoutDurationTimer.seconds() >= BROWNOUT_SHUTOFF_SECONDS) {
                driveStop();
                return;
            }

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

    /**
     * move forward
     * @param distance how far the bot needs to go in inches
     */
    public void moveForward(double distance) {
        double radius = (WHEEL_DIAMETER / DistanceUnit.mmPerInch) / 2;
        double circumference = 2 * Math.PI * radius;
        double rotations = distance / circumference;
        int targetTicks = (int) Math.round(rotations * 1425.1 * 1); // 1425.1 pulse/rev

        // reset motors
        m_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m_left.setTargetPosition(targetTicks);
        m_right.setTargetPosition(targetTicks);

        // switch to run to position mode
        m_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // drive until wheel reaches target position
        double driveSpeed = 0.6; // TODO adjust speed later
        while (m_left.isBusy() && m_right.isBusy()) {
            arcadeDrive(driveSpeed, 0);

            // Exit early if arcadeDrive triggered a stall or brownout shutoff
            if (isStalled() || (isBrownout() && brownoutDurationTimer.seconds() >= BROWNOUT_SHUTOFF_SECONDS)) {
                break;
            }
        }

        // stop and restore to regular teleop mode
        driveStop();
        m_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * turn angles. positive is clockwise, negative is anticlockwise
     * @param angle in degrees
     */
    public void turn(double angle) {
        double turnCircumference = Math.PI * TRACK_WIDTH;
        double wheelDistance = turnCircumference * (angle / 360.0);

        double wheelCircumference = Math.PI * (WHEEL_DIAMETER / DistanceUnit.mmPerInch);
        double rotations = wheelDistance / wheelCircumference;
        int targetTicks = (int) Math.round(rotations * 1425.1 * 1);

        m_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m_left.setTargetPosition(targetTicks);
        m_right.setTargetPosition(-targetTicks);
        m_left.setTargetPositionTolerance(15);
        m_right.setTargetPositionTolerance(15);

        m_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double turnSpeed = 0.5;
        while (!isMovementFinished()) {
            arcadeDrive(0, turnSpeed);

            if (isStalled() || (isBrownout() && brownoutDurationTimer.seconds() >= BROWNOUT_SHUTOFF_SECONDS)) {
                break;
            }
        }

        driveStop();
        m_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public boolean isMovementFinished() {
        return !m_left.isBusy() && !m_right.isBusy();
    }

    @Override
    public boolean isStalled() {
        double leftVelocity = Math.abs(m_left.getVelocity());
        double rightVelocity = Math.abs(m_right.getVelocity());

        double leftCurrent = m_left.getCurrent(CurrentUnit.AMPS);
        double rightCurrent = m_right.getCurrent(CurrentUnit.AMPS);

        boolean leftCheck = leftCurrent > MIN_CURRENT && leftVelocity < MIN_VELOCITY;
        boolean rightCheck = rightCurrent > MIN_CURRENT && rightVelocity < MIN_VELOCITY;

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

        return 0;
       // return m_voltageSensor.getVoltage();
    }

    @Override
    public boolean isBrownout() {
        double voltage = getVoltage();

        if (!inBrownoutState) {
            if (voltage < MIN_VOLTAGE) {
                if (brownOutTimer.seconds() >= BROWNOUT_TIME_SECONDS) {
                    inBrownoutState = true;
                    brownoutDurationTimer.reset();
                    return true;
                }
            } else {
                brownOutTimer.reset();
            }
            return false;
        } else {
            if (voltage >= BROWNOUT_RECOVERY_VOLTAGE) {
                inBrownoutState = false;
                brownOutTimer.reset();
                return false;
            }
            return true;
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
