package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotContainer;

@TeleOp(name = "Shooter test")
public class ShooterTest extends LinearOpMode {
    private RobotContainer robotContainer;

    @Override
    public void runOpMode() throws InterruptedException {
        robotContainer = new RobotContainer(hardwareMap);

        telemetry.addLine("Initialized Shooter");
        telemetry.addLine("Cross for regular, L1 for reverse");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            boolean key = gamepad2.a; // cross on ps5
            double targetRPM = 156.0;
            boolean reverse = gamepad2.right_bumper;
            if (reverse) targetRPM *= -1;

            if (key) {
                robotContainer.shooter.runShooter(targetRPM);

                double realPower = robotContainer.shooter.logging().getPower();

                telemetry.addData("Shooter power:", realPower);
                telemetry.addData("Reversed?:", realPower < 0);
            } else {
                robotContainer.shooter.stopShooter();
            }

            telemetry.update();
        }
        robotContainer.shooter.stopShooter();
    }
}
