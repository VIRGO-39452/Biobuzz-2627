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
            // Intake
            boolean isReversed = gamepad2.left_bumper;
            float intakeTrigger = gamepad2.left_trigger;
            float deadzone = 0.05f;

            if (intakeTrigger > deadzone) //if the trigger amt is more than deadzone
            {
                if (isReversed) //if reversed...
                {
                    robot.intake.runIntake(intakeTrigger);   // reverse
                } else //what else can it be?
                {
                    robot.intake.runIntake(-intakeTrigger);  // normal
                }
            }
            else // finally if the trigger is not greater than the deadzone we want to inactivate b/c we'd still be feeding some small amount
                {
                    robot.intake.stopIntake();
                }
        }
        robot.drive.stop();
    }
}
