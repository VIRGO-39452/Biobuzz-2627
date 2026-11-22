package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Utils;

@TeleOp(name = "Main TeleOp")
public class TeleOpMode extends OpMode {

    private Robot robot;

    // keys
    private double driveForward = -gamepad1.left_stick_y; // up and down for forwards and backwards
    private double driveTurn = gamepad1.right_stick_x; // left and right for turning
    private boolean intakeIsReversed = gamepad2.left_bumper;
    private float intakeTrigger = gamepad2.left_trigger;
    private boolean shooterIndexerKey = gamepad2.a; // cross on PS5
    private double shooterIndexerPower = 0.5;
    private boolean shooterIndexerReverse = gamepad2.right_bumper;

    private boolean indexerFeeding = false;

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        telemetry.addLine("TeleOp initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // DriveTrain
        if (Math.abs(driveForward) < 0.1) driveForward = 0;
        if (Math.abs(driveTurn) < 0.1) driveTurn = 0;

        robot.drive.arcadeDrive(driveForward, driveTurn);

        telemetry.addData("Forward: ", driveForward);
        telemetry.addData("Turn: ", driveTurn);

        // Intake
        float deadzone = 0.05f;

        if (intakeTrigger > deadzone) { // if the trigger amt is more than deadzone
            if (intakeIsReversed) { // if reversed...
                robot.intake.runIntake(intakeTrigger);   // reverse
            } else { // what else can it be?
                robot.intake.runIntake(-intakeTrigger);  // normal
            }
        } else { // finally if the trigger is not greater than the deadzone we want to inactivate b/c we'd still be feeding some small amount
            robot.intake.stopIntake();
        }

        // Shooter + Indexer
        if (shooterIndexerReverse) shooterIndexerPower *= -1;

        if (shooterIndexerKey) {
            robot.shooter.runShooter(shooterIndexerPower);

            double realPower = robot.shooter.logging().getPower();
            double targetRPM = Utils.calculateTargetRPM(realPower);
            // Remember to also fix Shooter.logging() so getSpeed() is ticks/sec (or convert rad/s -> RPM)
            double actualRPM = Utils.calculateActualRPM(robot.shooter.logging().getSpeed());

            indexerFeeding = Utils.isReadyToFeed(targetRPM, actualRPM, indexerFeeding);

            telemetry.addData("Shooter Power:", realPower);
            telemetry.addData("Reversed?:", realPower < 0);
            telemetry.addData("Target RPM:", targetRPM);
            telemetry.addData("Actual RPM:", actualRPM);
            telemetry.addData("RPM Error:", targetRPM - actualRPM);
            telemetry.addData("Ready to Feed:", indexerFeeding);

            if (indexerFeeding) {
                robot.indexer.startIndexer(shooterIndexerPower);

                double indexerPower = robot.indexer.logging().getPower();
                telemetry.addData("Indexer Power:", indexerPower);
                telemetry.addData("Indexer Reversed?:", indexerPower < 0);
            } else {
                robot.indexer.stop();
            }
        } else {
            indexerFeeding = false;
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
