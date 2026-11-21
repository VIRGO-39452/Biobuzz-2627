package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "My First OpMode")
public class MyFirstOpMode extends LinearOpMode {
    @Override
    public void runOpMode() {
        telemetry.addData("init testing", 123);
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("running testing", 456);
            telemetry.update();
        }
    }
}
