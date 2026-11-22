package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.YELLOW_JACKET_SHOOTER;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Logging.ShooterLog;

public class Shooter {
    private final DcMotorEx m_yellowjacket;

    /**
     * make a new shooter subsystem
     *
     * @param hardwareMap - hardware map
     */
    public Shooter(HardwareMap hardwareMap) {
        m_yellowjacket = hardwareMap.get(DcMotorEx.class, YELLOW_JACKET_SHOOTER);
        m_yellowjacket.setDirection(DcMotorEx.Direction.FORWARD); // TODO verify direction
        m_yellowjacket.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        m_yellowjacket.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    /**
     * runs the shooter
     *
     * @param power - set the power to run the shooter (negative runs backwards, positive runs forward)
     */
    public void runShooter(double power) {
        m_yellowjacket.setPower(power);
    }

    /**
     * stop the shooter
     */
    public void stopShooter() {
        m_yellowjacket.setPower(0.0);
    }

    /**
     * Retrieves the current shooter state for logging.
     *
     * @return a ShooterLogs (double power, boolean on) object containing the shooter's power and whether it is on
     */
    public ShooterLog logging() {
        double power = m_yellowjacket.getPower();
        return new ShooterLog(
                power,
                Math.abs(power) > 0.001,
                m_yellowjacket.getVelocity()
        );
    }
}