package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.LEFT_WHEEL;
import static org.firstinspires.ftc.teamcode.Constants.RIGHT_WHEEL;

import java.lang.Math;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Logging.DriveTrainLog;

public class DriveTrain {
    private DcMotor m_left;
    private DcMotor m_right;

    public DriveTrain(HardwareMap hardwareMap) {

        // left wheel
        m_left = hardwareMap.get(DcMotor.class, LEFT_WHEEL);
        m_left.setDirection(DcMotor.Direction.REVERSE); //this is reversed since the motor would be mounted the other way around?
        m_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //backRight
        m_right = hardwareMap.get(DcMotor.class, RIGHT_WHEEL);
        m_right.setDirection(DcMotor.Direction.FORWARD); //this is forward since the motor would be mounted the correct way around
        m_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void drive(double power) {
        m_left.setPower(power);
        m_right.setPower(power);
    }

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

    // Returns position of bot
    public DriveTrainLog logging() {
        return new DriveTrainLog(
                m_left.getPower(),
                m_right.getPower(),
                m_right.getPower() != 0,
                m_left.getPowerFloat()
        );
    }
}

// All coded by Shubham, trust...