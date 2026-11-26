package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "Intake Test")
public class IntakeTest extends LinearOpMode {
    private Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);

        telemetry.addLine("Initialized Intake");
        telemetry.addLine("L2 for intake, L1 for reverse");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double power = gamepad2.left_trigger;
            boolean reverse = gamepad2.left_bumper;

            if (Math.abs(power) < 0.1) power = 0;
            if (!reverse) power *= -1;

            robot.intake.runIntake(power);

            double realPower = robot.intake.logging().getPower();

            telemetry.addData("Intake power:", realPower);
            telemetry.addData("Reversed?:", realPower > 0);
            telemetry.update();
        }

        robot.intake.stopIntake();
    }
}
