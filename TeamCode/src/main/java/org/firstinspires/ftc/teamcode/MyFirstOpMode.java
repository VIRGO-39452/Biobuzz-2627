package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

@TeleOp(name = "My First OpMode")
public class MyFirstOpMode extends LinearOpMode {
    @Override
    public void runOpMode() {
        // 1. Initialize your Intake subsystem
        Intake intake = new Intake(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // 2. Control intake with right/left triggers (or A/B buttons)
            if (gamepad1.right_trigger > 0.1) {
                intake.runIntake(gamepad1.right_trigger);
            } else if (gamepad1.left_trigger > 0.1) {
                intake.runIntake(-gamepad1.left_trigger); // Reverse intake
            } else {
                intake.stopIntake();
            }

            telemetry.addData("Intake Active", intake.logging());
            telemetry.update();
        }
    }
}
