package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Indexer;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

public class RobotContainer {
    public DriveTrain drive;
    public Intake intake;
    public Indexer indexer;
    public Shooter shooter;

    public RobotContainer(HardwareMap hardwareMap) {
        drive = new DriveTrain(hardwareMap);
        intake = new Intake(hardwareMap);
        indexer = new Indexer(hardwareMap);
        shooter = new Shooter(hardwareMap);
    }

}