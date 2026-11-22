package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "Indexer Test")
public class IndexerTest extends LinearOpMode {
    private Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);

        telemetry.addLine("Initialized Indexer");
        telemetry.addLine("R2 for regular, R1 for reverse");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            boolean key = gamepad2.a; // cross on ps5
            double power = 0.5;
            boolean reverse = gamepad2.right_bumper;
            if (reverse) power *= -1;

            if (key) {
                robot.indexer.startIndexer(power);

                double realPower = robot.indexer.logging().getPower();

                telemetry.addData("Indexer power:", realPower);
                telemetry.addData("Reversed?:", realPower < 0);
            }
        }

        robot.indexer.stop();
    }
}
