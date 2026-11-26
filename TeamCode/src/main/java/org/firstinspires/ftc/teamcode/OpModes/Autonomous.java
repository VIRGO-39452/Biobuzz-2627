package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Logging.AutoVariables;
import org.firstinspires.ftc.teamcode.RobotContainer;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Auto 1", group = "Autonomous")
public class Autonomous extends LinearOpMode {
    private final ElapsedTime autoTimer = new ElapsedTime();
    private RobotContainer robotContainer;
    private AutoVariables calcForMovement;

    private enum States {
        BEGIN_DRIVE,
        ROTATE_AND_HYPOTENUSE,
        ROTATE_BACK
    }

    private States step;

    @Override
    public void runOpMode(){
        robotContainer = new RobotContainer(hardwareMap);

        // 30 in and 24 in are placeholders for now
        calcForMovement = new AutoVariables(30, 24);
        step = States.BEGIN_DRIVE;

        telemetry.addLine("Autonomous initialized");
        telemetry.update();

        waitForStart();
        autoTimer.reset();

        while (opModeIsActive()) {
            if (autoTimer.seconds() >= 30) {
                robotContainer.drive.driveStop();

                telemetry.addLine("Autonomous timed out");
                telemetry.update();
                break;
            }

            if (step == States.BEGIN_DRIVE) {
                robotContainer.drive.moveForward(24);

                if (robotContainer.drive.isMovementFinished()) {
                    step = States.ROTATE_AND_HYPOTENUSE;
                }

            } else if (step == States.ROTATE_AND_HYPOTENUSE) {
                robotContainer.drive.turn(calcForMovement.getTheta());
                robotContainer.drive.moveForward(calcForMovement.getZ());

                if (robotContainer.drive.isMovementFinished()) {
                    step = States.ROTATE_BACK;
                }

            } else if (step == States.ROTATE_BACK) {
                robotContainer.drive.turn(-(calcForMovement.getTheta()));

                if (robotContainer.drive.isMovementFinished()) {
                    robotContainer.drive.driveStop();

                    telemetry.addLine("Autonomous session complete");
                    telemetry.update();
                    break;
                }
            }

            break;

        }
    }
}