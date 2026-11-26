package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.LEFT_WHEEL;
import static org.firstinspires.ftc.teamcode.Constants.RIGHT_WHEEL;
import static org.firstinspires.ftc.teamcode.Constants.WHEEL_DIAMETER;
import static org.firstinspires.ftc.teamcode.Constants.TRACK_WIDTH;


import java.lang.Math;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Logging.DriveTrainLog;

public class DriveTrain {
    private final DcMotorEx m_left;
    private final DcMotorEx m_right;

    public DriveTrain(HardwareMap hardwareMap) {
        // left wheel
        m_left = hardwareMap.get(DcMotorEx.class, LEFT_WHEEL);
        m_left.setDirection(DcMotorEx.Direction.REVERSE); //this is reversed since the motor would be mounted the other way around?
        m_left.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        m_left.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER); //make it runwithencoder if works

        // right wheel
        m_right = hardwareMap.get(DcMotorEx.class, RIGHT_WHEEL);
        m_right.setDirection(DcMotorEx.Direction.FORWARD); //this is forward since the motor would be mounted the correct way around
        m_right.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        m_right.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER); //make it runwithencoder if works
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
        m_left.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        m_right.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        m_left.setTargetPosition(targetTicks);
        m_right.setTargetPosition(targetTicks);

        // switch to run to position mode
        m_left.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        m_right.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        // drive until wheel reaches target position
        double driveSpeed = 0.6; // TODO adjust speed later
        while (m_left.isBusy() && m_right.isBusy()) {
            arcadeDrive(driveSpeed, 0);
        }

        // stop and restore to regular teleop mode
        stop();
        m_left.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        m_right.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
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

        m_left.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        m_right.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        m_left.setTargetPosition(targetTicks);
        m_right.setTargetPosition(-targetTicks);
        m_left.setTargetPositionTolerance(15);
        m_right.setTargetPositionTolerance(15);

        m_left.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        m_right.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        double turnSpeed = 0.5;
        while (!isMovementFinished()) {
            arcadeDrive(0, turnSpeed);
        }

        stop();
        m_left.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        m_right.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public boolean isMovementFinished() {
        return !m_left.isBusy() && !m_right.isBusy();
    }

    //check documentation for how this drive works
    public void arcadeDrive(double forward, double turn) {
        double leftPower = forward + turn;
        double rightPower = forward - turn;

        double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));

        if (max > 1.0) {
            leftPower /= max;
            rightPower /= max;
        }

        m_left.setPower(leftPower);
        m_right.setPower(rightPower);

    }

    public void stop() {
        m_left.setPower(0);
        m_right.setPower(0);
    }

    // TODO verify if encoders are used in this subsystem to add stalling and brownout detection

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
