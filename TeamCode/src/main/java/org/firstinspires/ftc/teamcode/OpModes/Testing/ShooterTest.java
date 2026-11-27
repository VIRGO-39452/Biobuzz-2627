package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "Shooter test")
public class ShooterTest extends LinearOpMode {
    private Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);

        telemetry.addLine("Initialized Shooter");
        telemetry.addLine("Cross for regular, L1 for reverse");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            boolean key = gamepad2.a; // cross on ps5
            double targetRPM = 300.0;
            boolean reverse = gamepad2.right_bumper;
            if (reverse) targetRPM *= -1;

            if (key) {
                robot.shooter.runShooter(targetRPM);

                double realPower = robot.shooter.logging().getPower();

                telemetry.addData("Shooter power:", realPower);
                telemetry.addData("Reversed?:", realPower < 0);
            } else {
                robot.shooter.stopShooter();
            }

            telemetry.update();
        }
        robot.shooter.stopShooter();
    }
}
