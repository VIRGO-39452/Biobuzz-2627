package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotContainer;

@TeleOp(name = "DriveTrain Test")
public class DriveTrainTest extends LinearOpMode {
    private RobotContainer robotContainer;

    @Override
    public void runOpMode() {

        robotContainer = new RobotContainer(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Instructions", "Left stick = Forward/Backward");
        telemetry.addData("Instructions", "Right stick = Turn");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double forward = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            if (Math.abs(forward) < 0.1) {
                forward = 0;
            }

            if (Math.abs(turn) < 0.1) {
                turn = 0;
            }

            robotContainer.drive.arcadeDrive(forward, turn);

            telemetry.addData("Forward", forward);
            telemetry.addData("Turn", turn);
            telemetry.addData("Status", "Running");
            telemetry.update();
        }

        robotContainer.drive.driveStop();
    }
}