package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.Constants.TARGET_TPS;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Utils;

@Autonomous(name = "Start in front of hive", group = "Autonomous")
public class StartInFrontOfHive extends LinearOpMode {
    private Robot robot;
    private final ElapsedTime runTime = new ElapsedTime();

    private enum States {
        DRIVE_TURN_1,
        DRIVE_TURN_2,
        DRIVE_TURN_3,
        SHOOT,
        END

    }

    private States steps;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        steps = States.DRIVE_TURN_1;

        waitForStart();

        while(opModeIsActive()) {

            if (steps == States.DRIVE_TURN_1) {
                robot.drive.moveForward(-5);
                robot.drive.turn(30.0);
                robot.drive.moveForward(-5);

                if (robot.drive.isMovementFinished()) {
                    steps = States.SHOOT;
                }

            } else if (steps == States.SHOOT) {

                runPowerTime(Utils.calculateActualRPM(TARGET_TPS), 7.5);

                if (robot.drive.isMovementFinished()) {
                    steps = States.DRIVE_TURN_2;
                }
            } else if (steps == States.DRIVE_TURN_2) {
                robot.drive.moveForward(7);
                robot.drive.turn(-30.0);
                robot.drive.moveForward(-95);

                if (robot.drive.isMovementFinished()) {
                    steps = States.DRIVE_TURN_3;
                }
            } else if (steps == States.DRIVE_TURN_3) {
                robot.drive.turn(-90);
                robot.drive.moveForward(-20);

                if (robot.drive.isMovementFinished()) {
                    steps = States.END;
                }
            }
        }

        robot.drive.stop();
        robot.indexer.stop();
        robot.shooter.stopShooter();
    }
    public void runPowerTime(double targetRPM, double time) {
        runTime.reset();

        while (opModeIsActive() && runTime.seconds() < time) {
            robot.shooter.runShooter(targetRPM + 10);

            if (runTime.seconds() >= 0.5) {
                robot.indexer.startIndexer(-1);
            }
        }
        robot.shooter.stopShooter();
        robot.indexer.stop();
    }

}
