package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Start in loading", group = "Autonomous")
public class StartInLoading extends LinearOpMode {

    private Robot robot;

    private final ElapsedTime autoTimer2 = new ElapsedTime();

    private double x1, x2, y;

    private enum States2 {
        BEGIN_DRIVE_1,
        BEGIN_DRIVE_2,
        ROTATE,
        ROTATE_BACK,
        DRIVE_BACK,
        DONE
    }

    private States2 step2;

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap);
        step2 = States2.BEGIN_DRIVE_1;

        telemetry.addLine("StartInLoading initialized");
        telemetry.update();

        waitForStart();
        autoTimer2.reset();

        while (opModeIsActive()) {
            y = 9.0;
            x1 = 7.5;
            x2 = 4.0;

            if (step2 == States2.BEGIN_DRIVE_1) {

                robot.drive.moveForward(y);

                if (robot.drive.isMovementFinished()) {
                    step2 = States2.ROTATE;
                }

            } else if (step2 == States2.ROTATE) {

                robot.drive.turn(90);


                if (robot.drive.isMovementFinished()) {
                    step2 = States2.BEGIN_DRIVE_2;
                }

            } else if (step2 == States2.BEGIN_DRIVE_2) {

                robot.drive.moveForward(x2);

                if (robot.drive.isMovementFinished()) {
                    step2 = States2.ROTATE_BACK;
                }

            } else if (step2 == States2.ROTATE_BACK) {

                robot.drive.turn(90);


                if (robot.drive.isMovementFinished()) {
                    step2 = States2.DRIVE_BACK;
                }

            } else if (step2 == States2.DRIVE_BACK) {

                robot.drive.moveForward(x1);

                if (robot.drive.isMovementFinished()) {
                    robot.drive.stop();
                    step2 = States2.DONE;
                }

            } else if (step2 == States2.DONE) {
                robot.drive.stop();
                telemetry.addLine("Autonomous session complete");
                telemetry.update();

                break;
            }

        }


    }

}
