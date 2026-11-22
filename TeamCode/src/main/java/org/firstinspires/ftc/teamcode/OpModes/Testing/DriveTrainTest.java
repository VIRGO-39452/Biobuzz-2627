package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "DriveTrain Test")
public class DriveTrainTest extends LinearOpMode {

    private Robot robot;

    @Override
    public void runOpMode() {

        // Initialize the robot hardware
        robot = new Robot(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Instructions", "Left stick = Forward/Backward");
        telemetry.addData("Instructions", "Right stick = Turn");
        telemetry.update();

        // Wait for the driver to press PLAY
        waitForStart();

        // Main loop
        while (opModeIsActive()) {

            // Read joystick values
            double forward = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            // Apply deadzones
            if (Math.abs(forward) < 0.1) {
                forward = 0;
            }

            if (Math.abs(turn) < 0.1) {
                turn = 0;
            }

            // Control the drivetrain
            robot.drive.arcadeDrive(forward, turn);

            // Display telemetry
            telemetry.addData("Forward", forward);
            telemetry.addData("Turn", turn);
            telemetry.addData("Status", "Running");
            telemetry.update();
        }

        // Stop drivetrain when OpMode ends
        robot.drive.stop();
    }
}