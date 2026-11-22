package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.MIN_CURRENT;
import static org.firstinspires.ftc.teamcode.Constants.MIN_VELOCITY;
import static org.firstinspires.ftc.teamcode.Constants.VOLTAGE_SENSOR;
import static org.firstinspires.ftc.teamcode.Constants.YELLOW_JACKET_SHOOTER;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Interface;
import org.firstinspires.ftc.teamcode.Logging.ShooterLog;

public class Shooter implements Interface {
    private final DcMotorEx m_yellowjacket;

    private final VoltageSensor m_voltageSensor;

    /**
     * make a new shooter subsystem
     *
     * @param hardwareMap - hardware map
     */
    public Shooter(HardwareMap hardwareMap) {
        m_yellowjacket = hardwareMap.get(DcMotorEx.class, YELLOW_JACKET_SHOOTER);
        m_voltageSensor = hardwareMap.get(VoltageSensor.class, VOLTAGE_SENSOR);
        m_yellowjacket.setDirection(DcMotorEx.Direction.FORWARD); // TODO verify direction
        m_yellowjacket.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        m_yellowjacket.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    /**
     * runs the shooter
     *
     * @param targetRPM - set the targetRPM to run the shooter (negative runs backwards, positive runs forward)
     */

    public void runShooter(double targetRPM) {
        if (isStalled()) {
            stopShooter();
            return;
        }

        double ticksPerSecond = (targetRPM * TPR) / 60.0;
        m_yellowjacket.setVelocity(ticksPerSecond);
    }

    /**
     * stop the shooter
     */
    public void stopShooter() {
        m_yellowjacket.setPower(0.0);
    }

    // check if m_yellowjacket is stalled or not
    @Override
    public boolean isStalled() {
        double velocity = Math.abs(m_yellowjacket.getVelocity());
        double current = m_yellowjacket.getCurrent(CurrentUnit.AMPS);

        return current > MIN_CURRENT && velocity < MIN_VELOCITY;
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