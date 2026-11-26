package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Logging.AutoVariables;
import org.firstinspires.ftc.teamcode.Robot;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Auto 1", group = "Autonomous")
public class Autonomous extends LinearOpMode {
    private final ElapsedTime autoTimer = new ElapsedTime();
    private Robot robot;
    private AutoVariables calcForMovement;

    private enum States {
        BEGIN_DRIVE,
        ROTATE_AND_HYPOTENUSE,
        ROTATE_BACK
    }

    private States step;

    @Override
    public void runOpMode(){
        robot = new Robot(hardwareMap);

        // 30 in and 24 in are placeholders for now
        calcForMovement = new AutoVariables(30, 24);
        step = States.BEGIN_DRIVE;

        telemetry.addLine("Autonomous initialized");
        telemetry.update();

        waitForStart();
        autoTimer.reset();

        while (opModeIsActive()) {
            if (autoTimer.seconds() >= 30) {
                robot.drive.stop();

                telemetry.addLine("Autonomous timed out");
                telemetry.update();
                break;
            }

            if (step == States.BEGIN_DRIVE) {
                robot.drive.moveForward(24);

                if (robot.drive.isMovementFinished()) {
                    step = States.ROTATE_AND_HYPOTENUSE;
                }

            } else if (step == States.ROTATE_AND_HYPOTENUSE) {
                robot.drive.turn(calcForMovement.getTheta());
                robot.drive.moveForward(calcForMovement.getZ());

                if (robot.drive.isMovementFinished()) {
                    step = States.ROTATE_BACK;
                }

            } else if (step == States.ROTATE_BACK) {
                robot.drive.turn(-(calcForMovement.getTheta()));

                if (robot.drive.isMovementFinished()) {
                    robot.drive.stop();

                    telemetry.addLine("Autonomous session complete");
                    telemetry.update();
                    break;
                }
            }

        }
    }
}