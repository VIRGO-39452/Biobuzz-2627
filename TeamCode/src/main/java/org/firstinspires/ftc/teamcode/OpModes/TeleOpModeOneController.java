package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Utils;

@TeleOp(name = "Main TeleOp - One Controller")
public class TeleOpModeOneController extends OpMode {

    private org.firstinspires.ftc.teamcode.Robot robot;
    private boolean indexerFeeding = false;

    @Override
    public void init() {
        robot = new org.firstinspires.ftc.teamcode.Robot(hardwareMap);
        telemetry.addLine("TeleOp initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // button presses
        double driveForward = -gamepad1.left_stick_y; // up and down for forwards and backwards
        double driveTurn = gamepad1.right_stick_x * 0.75; // left and right for turning
        boolean intakeIsReversed = gamepad1.left_bumper; //gamepad1. L1 intake reverse
        float intakeTrigger = gamepad1.left_trigger; //gamepad1. L2 intake trigger
        float shooterIndexerKey = gamepad1.right_trigger; //gamepad1. R2 shooterindexer trigger
        double shooterTargetRPM = (double) shooterIndexerKey * Utils.calculateActualRPM(1250);
        boolean shooterIndexerTurbo = gamepad1.right_bumper; //gamepad1. R1 shooterindexer turbo...not using
        double indexerPower = -0.65;

        boolean driveSetSpeed = gamepad1.dpad_up; //drive setspeed
        boolean driveReverseSetSpeed = gamepad1.dpad_down; //drive setspeed
        boolean reverseShooter = gamepad1.x; //reverse shooter x
        boolean reverseIndexer = gamepad1.a; //reverse indexer a
        double turboTargetRPM = Utils.calculateActualRPM(1250);

        float driveBackwardTrigger =  0; //gamepad1.left_trigger;
        float driveForwardTrigger = 0; //gamepad1.right_trigger;
        boolean turnLeftBumper = false; //gamepad1.left_bumper;
        boolean turnRightBumper = false; //gamepad1.right_bumper;

        // DriveTrain
        double drive = 0;
        double turn = 0;

        boolean analogMode = Math.abs(driveForward) > 0.1 ||
                Math.abs(driveTurn) > 0.1;
        if (analogMode) {
            // with L3/R3
            drive = Math.abs(driveForward) < 0.1 ? 0 : driveForward;
            turn = Math.abs(driveTurn) < 0.1 ? 0 : driveTurn;

        } else {

            // R2/L2
            drive = driveForwardTrigger - driveBackwardTrigger;

            // d pad overrides trigger movement
            if (driveSetSpeed) {
                drive = 0.75;
            } else if (driveReverseSetSpeed) {
                drive = -0.75;
            }

            // R1/L1
            if (turnLeftBumper) {
                turn -= 0.25;
            }

            if (turnRightBumper) {
                turn += 0.25;
            }
        }

        drive = Math.max(-1, Math.min(1, drive));
        turn = Math.max(-1, Math.min(1, turn));

        robot.drive.arcadeDrive(drive, turn);

        telemetry.addData("Driving Mode:",
                analogMode ? "Analog" : "Buttons");

        telemetry.addData("Forward:", drive);
        telemetry.addData("Turn:", turn);

        // Intake
        float deadzone = 0.05f;

        if (intakeIsReversed) { // if reversed...
            robot.intake.runIntake(1);   // reverse
        } else if (intakeTrigger > deadzone) { // if the trigger amt is more than deadzone
            robot.intake.runIntake(-intakeTrigger);  // normal
        } else { // finally if the trigger is not greater than the deadzone we want to inactivate b/c we'd still be feeding some small amount
            robot.intake.stopIntake();
        }

        // Shooter + Indexer
        if (shooterIndexerTurbo) {
            robot.shooter.runShooter(Utils.calculateActualRPM(1250.0));
            robot.indexer.startIndexer(-1); // full power

            double realPower = robot.shooter.logging().getPower();
            double actualRPM = Math.abs(Utils.calculateActualRPM(robot.shooter.logging().getSpeed()));
            double currentIndexerPower = robot.indexer.logging().getPower();

            telemetry.addData("Indexer Power:", currentIndexerPower);
            telemetry.addData("Indexer Reversed (unexpected)?:", currentIndexerPower > 0);
            telemetry.addData("Shooter Power:", realPower);
            telemetry.addData("Reversed (unexpected)?:", realPower > 0);
            telemetry.addData("Target RPM:", turboTargetRPM);
            telemetry.addData("Actual RPM:", actualRPM);
            telemetry.addData("RPM Error:", turboTargetRPM - actualRPM);
            telemetry.addData("Ready to Feed:", indexerFeeding);
        } else if (shooterIndexerKey > deadzone) {
            robot.shooter.runShooter(shooterTargetRPM);

            double realPower = robot.shooter.logging().getPower();
            double actualRPM = Math.abs(Utils.calculateActualRPM(robot.shooter.logging().getSpeed()));
            indexerFeeding = Utils.isReadyToFeed(shooterTargetRPM, actualRPM, indexerFeeding);

            telemetry.addData("Shooter Power:", realPower);
            telemetry.addData("Reversed (unexpected)?:", realPower > 0);
            telemetry.addData("Target RPM:", shooterTargetRPM);
            telemetry.addData("Actual RPM:", actualRPM);
            telemetry.addData("RPM Error:", shooterTargetRPM - actualRPM);
            telemetry.addData("Ready to Feed:", indexerFeeding);
            telemetry.addData("Shooter Voltage:", robot.shooter.getVoltage());
            telemetry.addData("Shooter Current:", robot.shooter.getCurrent());

            if (indexerFeeding) {
                robot.indexer.startIndexer(indexerPower * shooterIndexerKey);

                double currentIndexerPower = robot.indexer.logging().getPower();
                telemetry.addData("Indexer Power:", currentIndexerPower);
                telemetry.addData("Indexer Reversed (unexpected)?:", currentIndexerPower > 0);
            } else {
                robot.indexer.stop();
            }
        } else if (reverseIndexer || reverseShooter) {
            indexerFeeding = false;

            if (reverseIndexer) {
                robot.indexer.startIndexer(-indexerPower);
            } else robot.indexer.stop();

            if (reverseShooter) {
                robot.shooter.runShooter(-1 * Utils.calculateActualRPM(1250));
            } else robot.shooter.stopShooter();
        } else {
            indexerFeeding = false;
            robot.shooter.stopShooter();
            robot.indexer.stop();
        }

        if (robot.shooter.isBrownout()) {
            telemetry.addLine("Motor is experiencing brownout in Shooter");
            telemetry.addData("Current:", robot.shooter.getCurrent());
            telemetry.addData("Voltage:", robot.shooter.getVoltage());
        } else if (robot.shooter.isStalled()) {
            telemetry.addLine("Motor is stalling in Shooter");
            telemetry.addData("Current:", robot.shooter.getCurrent());
            telemetry.addData("Voltage:", robot.shooter.getVoltage());
        }

        if (robot.drive.isStalled()) {
            telemetry.addLine("Motor is experiencing brownout in DriveTrain");
            telemetry.addData("Combined Current:", robot.drive.getCurrent());
            telemetry.addData("Voltage:", robot.drive.getVoltage());
        } else if (robot.drive.isBrownout()) {
            telemetry.addLine("Motor is experiencing brownout in DriveTrain");
            telemetry.addData("Combined Current:", robot.drive.getCurrent());
            telemetry.addData("Voltage:", robot.drive.getVoltage());
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
