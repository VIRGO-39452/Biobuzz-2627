package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotContainer;

@TeleOp(name = "Indexer Test")
public class IndexerTest extends LinearOpMode {
    private RobotContainer robotContainer;

    @Override
    public void runOpMode() throws InterruptedException {
        robotContainer = new RobotContainer(hardwareMap);

        telemetry.addLine("Initialized Indexer");
        telemetry.addLine("Cross for regular, R1 for reverse");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            boolean key = gamepad2.a; // cross on ps5
            double power = 0.5;
            boolean reverse = gamepad2.right_bumper;
            if (reverse) power *= -1;

            if (key) {
                robotContainer.indexer.startIndexer(power);

                double realPower = robotContainer.indexer.logging().getPower();

                telemetry.addData("Indexer power:", realPower);
                telemetry.addData("Reversed?:", realPower < 0);
            }
            else{
                robotContainer.indexer.stop();
        }

        telemetry.update();
    }
        robotContainer.indexer.stop();
    }
}
