// i coded this at 1 am im going crazy
package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Loading is to the right", group = "Autonomous")
public class LoadingIsToTheRight extends LinearOpMode {
    private Robot robot;
    private final ElapsedTime timer = new ElapsedTime();
    private double up, right;

    private enum STATES {
        FORWARD,
        TURN,
        BACKUP,
        END
    }

    @Override
    public void runOpMode() {

        // start
        robot = new Robot(hardwareMap);
        up = 17;
        right = 15;
        STATES state = STATES.FORWARD;

        telemetry.addLine("Loading is to the right INIT");
        telemetry.update(); // hi jasmin :D

        waitForStart();
        if (!opModeIsActive()) return;
        timer.reset();

        try {
            while (opModeIsActive() && state != STATES.END) {
                if (timer.seconds() >= 30) {
                    telemetry.addLine("LoadingRight timed out");
                    telemetry.update();
                    break;
                }

                // lowkenuinely smart
                switch (state) {
                    case FORWARD:
                        robot.drive.moveForward(up);
                        state = STATES.TURN;
                        break;

                    case TURN:
                        robot.drive.turn(-90);
                        state = STATES.BACKUP;
                        break;

                    case BACKUP:
                        robot.drive.moveForward(-right);
                        state = STATES.END;
                        break;
                }
            }

            if (opModeIsActive() && state == STATES.END) {
                telemetry.addLine("LoadingIsToTheRight complete!");
                telemetry.update();
            }

        } finally {
            robot.drive.stop();
        }
    }
}
