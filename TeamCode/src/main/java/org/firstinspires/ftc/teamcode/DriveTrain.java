package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.IMU;

public class DriveTrain {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private IMU imu;

    public DriveTrain(Hardware hardwareMap) {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

    }

    public void drive() {

    }

    public void stop() {

    }

    // Returns position of bot
    public double getHeading() {
        return 0; // Change return statement later
    }



}
