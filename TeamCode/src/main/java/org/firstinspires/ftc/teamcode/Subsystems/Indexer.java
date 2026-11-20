package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;


import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Indexer {

    // declare servo motor and indexer
    final Servo servoTest;
    Indexer indexer = new Indexer(hardwareMap);

    public Indexer(HardwareMap hardwareMap) {
        servoTest = hardwareMap.get(Servo.class, "servoTest");
    }

    public void setPos(double position) {
        servoTest.setPosition(position);
    }
    public void stop() {
        servoTest.setPosition(0.0);
    }

    // Add logic in this command later
    // Makes sure one ball is taken up
    public void indexOneBall(Indexer indexer) {

    }

}