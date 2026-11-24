package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

public class Autonomous extends OpMode {

    private final ElapsedTime autoTimer = new ElapsedTime();
    private Robot robot;

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        telemetry.addLine("Autonomous Initialized");
        telemetry.update();

    }

    @Override
    public void loop() {
        double y, x;

        /*
        * the y value would represent the bot moving up (24 in) and the x value
        * represents the estimated amount of distance the bot is from the wall.
        * In order for auto mode to work, we need methods to turn the bot CCW and
        * CW and make the bot go forward using DriverTrain class (maybe)
        * */
        /*I agree - Aadithya, making methods to turn the robot a certain number
        * of degrees or to an angle would be helpful, or even a method to set
        * angles and other variables based on measurements we calculate at the arena would be nice
         */
    }
}
