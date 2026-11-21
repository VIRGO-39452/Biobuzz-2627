package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "My First OpMode")
public class MyFirstOpMode extends LinearOpMode {

    private Robot robot;

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double forward = -gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            if (Math.abs(forward) < 0.1) forward = 0;
            if (Math.abs(turn) < 0.1) turn = 0;

            robot.drive.arcadeDrive(forward, turn);

            telemetry.addData("Forward", forward);
            telemetry.addData("Turn", turn);
            telemetry.update();


        }
    }
}
