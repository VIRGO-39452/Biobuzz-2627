package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class DriveTrain {

    private DcMotor m_frontLeft;
    private DcMotor m_frontRight;
    private DcMotor m_backLeft;
    private DcMotor m_backRight;
    private IMU m_imu;



    public DriveTrain(HardwareMap hardwareMap) {

        // TODO change this so that the drivetrain uses two motors instead of four
        //frontLeft
        m_frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        m_frontLeft.setDirection(DcMotor.Direction.REVERSE); //this is reversed since the motor would be mounted the other way around?
        m_frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m_frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //frontRight
        m_frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        m_frontRight.setDirection(DcMotor.Direction.FORWARD); //this is forward since the motor would be mounted the correct way around
        m_frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m_frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //backLeft
        m_backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        m_backLeft.setDirection(DcMotor.Direction.REVERSE); //this is reversed since the motor would be mounted the other way around?
        m_backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m_backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //backRight
        m_backRight = hardwareMap.get(DcMotor.class, "backRight");
        m_backRight.setDirection(DcMotor.Direction.FORWARD); //this is forward since the motor would be mounted the correct way around
        m_backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m_backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //depending on if we want to use the encoder or not - if we happen to not use it and the cable is unplugged, issues arise
        m_imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)
        );
                m_imu.initialize(parameters);


    }

    public void drive()
    {

    }

    public void stop() {

    }

    // Returns position of bot
    public double getHeading() {
        return 0; // Change return statement later
    }



}
