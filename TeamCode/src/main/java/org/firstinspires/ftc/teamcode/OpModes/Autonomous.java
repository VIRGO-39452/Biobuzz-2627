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

    }

    @Override
    public void loop() {

    }
}
