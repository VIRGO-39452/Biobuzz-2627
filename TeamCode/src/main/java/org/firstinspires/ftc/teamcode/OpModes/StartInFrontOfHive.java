package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Start in front of hive", group = "Autonomous")
public class StartInFrontOfHive extends LinearOpMode {
    private Robot robot;
    private final ElapsedTime runTime = new ElapsedTime();

    private enum States {
        DRIVE_TURN_1,
        DRIVE_TURN_2,
        DRIVE_TURN_3,
        SHOOT

    }

    private States steps;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        waitForStart();

        while(opModeIsActive()) {

            if (steps == States.DRIVE_TURN_1) {
                robot.drive.moveForward(-5);
                robot.drive.turn(35.0);

                if (robot.drive.isMovementFinished()) {
                    steps = States.SHOOT;
                }

            } else if (steps == States.SHOOT) {

                // shooting method goes here

                if (robot.drive.isMovementFinished()) {
                    steps = States.DRIVE_TURN_2;
                }
            } else if (steps == States.DRIVE_TURN_2) {
                robot.drive.turn(-35.0);
                robot.drive.moveForward(-70);

                if (robot.drive.isMovementFinished()) {
                    steps = States.DRIVE_TURN_3;
                }
            } else if (steps == States.DRIVE_TURN_3) {
                robot.drive.turn(-90);
                robot.drive.moveForward(-14);
            }

            break;

        }


        robot.drive.stop();
    }
    public void runPowerTime(double targetRPM, double time) {
        runTime.reset();

        while (opModeIsActive() && runTime.seconds() < time) {
            robot.shooter.runShooter(targetRPM);

            if (runTime.seconds() >= 1.5) {
                robot.indexer.startIndexer(-0.85);
            }
        }
        robot.shooter.stopShooter();
        robot.indexer.stop();
    }

}
