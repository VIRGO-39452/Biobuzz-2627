package org.firstinspires.ftc.teamcode.OpModes;

import org.firstinspires.ftc.teamcode.Utils;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp(name = "Main TeleOp")
public class TeleOpMode extends OpMode {

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
        double driveTurn = gamepad1.right_stick_x; // left and right for turning
        boolean intakeIsReversed = gamepad2.left_bumper;
        float intakeTrigger = gamepad2.left_trigger;
        float shooterIndexerKey = gamepad2.right_trigger;
        double shooterTargetRPM = (double) shooterIndexerKey * Utils.calculateActualRPM(1250);
        boolean shooterIndexerTurbo = gamepad2.right_bumper;
        double indexerPower = -0.65;

        boolean intakeSetSpeed = gamepad1.dpad_up;
        boolean intakeReverseSetSpeed = gamepad1.dpad_down;
        boolean reverseShooter = gamepad2.x;
        boolean reverseIndexer = gamepad2.a;
        double turboTargetRPM = Utils.calculateActualRPM(1250.0);

        // DriveTrain
        if (Math.abs(driveForward) < 0.1) driveForward = 0;
        if (Math.abs(driveTurn) < 0.1) driveTurn = 0;

        robot.drive.arcadeDrive(driveForward, driveTurn);

        telemetry.addData("Forward: ", driveForward);
        telemetry.addData("Turn: ", driveTurn);

        // Intake
        float deadzone = 0.05f;

        if (intakeSetSpeed) {
            robot.intake.runIntake(-0.8);
        } else if (intakeReverseSetSpeed) {
            robot.intake.runIntake(0.8);
        } else if (intakeIsReversed) { // if reversed...
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
            indexerFeeding = Utils.isReadyToFeed(Utils.calculateActualRPM(1200), actualRPM, indexerFeeding);

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
