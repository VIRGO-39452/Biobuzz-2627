package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

//TODO finish
public class Autonomous2 extends LinearOpMode {

    private Robot robot;

    private final ElapsedTime autoTimer2 = new ElapsedTime();

    private double x, y;

    private enum States2 {
        BEGIN_DRIVE_2,
        ROTATE,
        DRIVE_BACK_2
    }

    private States2 step2;

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap);
        step2 = States2.BEGIN_DRIVE_2;

        telemetry.addLine("Autonomous2 initialized");
        telemetry.update();

        waitForStart();
        autoTimer2.reset();

        while (opModeIsActive()) {
            x = 9.0;
            y = 7.5;

            if (autoTimer2.seconds() <= 30) {

            }

        }


    }

}
