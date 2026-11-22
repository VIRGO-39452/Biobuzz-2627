package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Utils;

@TeleOp(name = "Main TeleOp")
public class TeleOpMode extends OpMode {

    private Robot robot;

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        telemetry.addLine("TeleOp initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // DriveTrain
        double forward = -gamepad1.left_stick_y; // up and down for forwards and backwards
        double turn = gamepad1.right_stick_x; // left and right for turning

        if (Math.abs(forward) < 0.1) forward = 0;
        if (Math.abs(turn) < 0.1) turn = 0;

        robot.drive.arcadeDrive(forward, turn);

        telemetry.addData("Forward: ", forward);
        telemetry.addData("Turn: ", turn);

        // Intake
        boolean isReversed = gamepad2.left_bumper;
        float intakeTrigger = gamepad2.left_trigger;
        float deadzone = 0.05f;

        if (intakeTrigger > deadzone) { // if the trigger amt is more than deadzone
            if (isReversed) { // if reversed...
                robot.intake.runIntake(intakeTrigger);   // reverse
            } else { // what else can it be?
                robot.intake.runIntake(-intakeTrigger);  // normal
            }
        } else { // finally if the trigger is not greater than the deadzone we want to inactivate b/c we'd still be feeding some small amount
            robot.intake.stopIntake();
        }

        // Shooter + Indexer
        boolean shooterIndexerKey = gamepad2.a; // cross on PS5
        double shooterIndexerPower = 0.5;
        boolean shooterIndexerReverse = gamepad2.right_bumper;

        if (shooterIndexerReverse) shooterIndexerPower *= -1;

        if (shooterIndexerKey) {
            robot.shooter.runShooter(shooterIndexerPower);

            double realPower = robot.shooter.logging().getPower();
            double targetRPM = Utils.calculateTargetRPM(realPower);
            double actualRPM = Utils.calculateActualRPM(robot.shooter.logging().getSpeed());
            boolean atSpeed = Utils.isAtSpeed(targetRPM, actualRPM);

            telemetry.addData("Shooter Power:", realPower);
            telemetry.addData("Reversed?:", realPower < 0);
            telemetry.addData("Target RPM:", targetRPM);
            telemetry.addData("Actual RPM:", actualRPM);
            telemetry.addData("RPM Error:", targetRPM - actualRPM);
            telemetry.addData("At Speed:", atSpeed);

            if (atSpeed) {
                robot.indexer.startIndexer(shooterIndexerPower);

                double indexerPower = robot.indexer.logging().getPower();

                telemetry.addData("Indexer Power:", indexerPower);
                telemetry.addData("Indexer Reversed?:", indexerPower < 0);
            } else {
                robot.indexer.stop();
            }
        } else {
            robot.shooter.stopShooter();
            robot.indexer.stop();
        }

        telemetry.update(); // :D
    }

    @Override
    public void stop() {
        robot.drive.stop();
        robot.shooter.stopShooter();
        robot.indexer.stop();
        robot.intake.stopIntake();
    }
}
