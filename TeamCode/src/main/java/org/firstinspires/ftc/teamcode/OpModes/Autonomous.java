package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Logging.AutoVariables;
import org.firstinspires.ftc.teamcode.Robot;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Auto 1", group = "Autonomous")
public class Autonomous extends OpMode {

    private final ElapsedTime autoTimer = new ElapsedTime();

    private Robot robot;

    private AutoVariables calcForMovement;

    private int autoStep = 0;


    @Override
    public void init() {

        robot = new Robot(hardwareMap);

        // 30 in and 24 in are placeholders for now
        calcForMovement = new AutoVariables(30, 24);

        telemetry.addLine("Autonomous initialized");
        telemetry.update();
    }


    @Override
    public void start() {

        autoTimer.reset();
    }


    @Override
    public void loop() {

        // this should work :D

        if (autoTimer.seconds() >= 30) {
            robot.drive.driveStop();

            telemetry.addLine("Autonomous timed out");
            telemetry.update();
        }

        if (autoStep == 0) {
            robot.drive.moveForward(24);

            if (robot.drive.isMovementFinished()) {
                autoStep++;
            }

        } else if (autoStep == 1) {
            robot.drive.turn(calcForMovement.getTheta());
            robot.drive.moveForward(calcForMovement.getZ());

            if (robot.drive.isMovementFinished()) {
                autoStep++;
            }

        } else if (autoStep == 2) {
            robot.drive.turn(-(calcForMovement.getTheta()));

            if (robot.drive.isMovementFinished()) {
                robot.drive.driveStop();

                telemetry.addLine("Autonomous session complete");
                telemetry.update();
            }
        }

    }
}