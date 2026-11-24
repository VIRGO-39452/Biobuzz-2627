package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Utils;

@TeleOp(name = "Main TeleOp")
public class TeleOpMode extends OpMode {

    private Robot robot;
    private boolean indexerFeeding = false;

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        telemetry.addLine("TeleOp initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // button presses
        double driveForward = -gamepad1.left_stick_y; // up and down for forwards and backwards
        double driveTurn = gamepad1.right_stick_x; // left and right for turning
        boolean intakeIsReversed = gamepad2.left_bumper;
        float intakeTrigger = gamepad2.left_trigger;
        boolean shooterIndexerKey = gamepad2.a; // cross on PS5
        double shooterTargetRPM = 100.0;
        boolean shooterIndexerReverse = gamepad2.right_bumper;
        double indexerPower = 0.5;

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
        if (shooterIndexerReverse) {
            robot.shooter.runShooter(shooterTargetRPM);
            robot.indexer.startIndexer(indexerPower);
            indexerFeeding = false;

            telemetry.addData("Shooter/Indexer State:", "Reversing");
        } else if (shooterIndexerKey) {
            robot.shooter.runShooter(shooterTargetRPM);

            double realPower = robot.shooter.logging().getPower();
            double actualRPM = Utils.calculateActualRPM(robot.shooter.logging().getSpeed());

            indexerFeeding = Utils.isReadyToFeed(shooterTargetRPM, actualRPM, indexerFeeding);

            telemetry.addData("Shooter Power:", realPower);
            telemetry.addData("Reversed?:", realPower < 0);
            telemetry.addData("Target RPM:", shooterTargetRPM);
            telemetry.addData("Actual RPM:", actualRPM);
            telemetry.addData("RPM Error:", shooterTargetRPM - actualRPM);
            telemetry.addData("Ready to Feed:", indexerFeeding);

            if (indexerFeeding) {
                robot.indexer.startIndexer(indexerPower);

                double currentIndexerPower = robot.indexer.logging().getPower();
                telemetry.addData("Indexer Power:", currentIndexerPower);
                telemetry.addData("Indexer Reversed?:", currentIndexerPower < 0);
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
        robot.drive.driveStop();
        robot.shooter.stopShooter();
        robot.indexer.stop();
        robot.intake.stopIntake();
    }
}
