package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "Drive TeleOp")
public class TeleOpMode extends LinearOpMode {

    private Robot robot;

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive())
        {   //DriveTrain
            double forward = -gamepad1.left_stick_y; //up and down for forwards and backwards
            double turn = gamepad1.right_stick_x; //left and right for turning

            if (Math.abs(forward) < 0.1) forward = 0;
            if (Math.abs(turn) < 0.1) turn = 0;

            robot.drive.arcadeDrive(forward, turn);

            telemetry.addData("Forward", forward);
            telemetry.addData("Turn", turn);
            telemetry.update();
            //Intake
            if (gamepad2.x) {
                robot.intake.runIntake(1.0);        // full on
            } else if (gamepad2.b) {
                robot.intake.runIntake(-1.0);       // full reverse, for clearing jams
            } else {
                robot.intake.stopIntake();          // off
            }

        }
        robot.drive.stop();
    }
}
