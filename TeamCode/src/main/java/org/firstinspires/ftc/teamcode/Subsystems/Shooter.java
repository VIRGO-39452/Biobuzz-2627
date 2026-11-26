package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.BROWNOUT_RECOVERY_VOLTAGE;
import static org.firstinspires.ftc.teamcode.Constants.MIN_CURRENT;
import static org.firstinspires.ftc.teamcode.Constants.MIN_VELOCITY;
import static org.firstinspires.ftc.teamcode.Constants.MIN_VOLTAGE;
import static org.firstinspires.ftc.teamcode.Constants.YELLOW_JACKET_SHOOTER;
import static org.firstinspires.ftc.teamcode.Constants.BROWNOUT_TIME_SECONDS;
import static org.firstinspires.ftc.teamcode.Constants.BROWNOUT_SHUTOFF_SECONDS;
import static org.firstinspires.ftc.teamcode.Constants.STALL_TIME_SECONDS;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.ElectricalSafeguards;
import org.firstinspires.ftc.teamcode.Logging.ShooterLog;

public class Shooter implements ElectricalSafeguards {

    private final DcMotorEx m_yellowjacket;
    // private final VoltageSensor m_voltageSensor;
    private final ElapsedTime stallTimer = new ElapsedTime();
    private final ElapsedTime brownOutTimer = new ElapsedTime();
    private final ElapsedTime brownoutDurationTimer = new ElapsedTime();
    private boolean inBrownoutState = false;
    private final double TPR = 537.7;

    /**
     * make a new shooter subsystem
     *
     * @param hardwareMap - hardware map
     */
    public Shooter(HardwareMap hardwareMap) {
        m_yellowjacket = hardwareMap.get(DcMotorEx.class, YELLOW_JACKET_SHOOTER);
        // m_voltageSensor = hardwareMap.get(VoltageSensor.class, VOLTAGE_SENSOR);
        m_yellowjacket.setDirection(DcMotorEx.Direction.FORWARD); // TODO verify direction
        m_yellowjacket.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        m_yellowjacket.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    /**
     * runs the shooter
     *
     * @param targetRPM - set the targetRPM to run the shooter
     *                    (negative runs backwards, positive runs forward)
     */
    public void runShooter(double targetRPM) {
        if (isStalled()) {
            stopShooter();
            return;
        }

        if (isBrownout()) {
            if (brownoutDurationTimer.seconds() >= BROWNOUT_SHUTOFF_SECONDS) {
                stopShooter();
                return;
            }

            double scaledRPM = targetRPM * 0.5;
            double ticksPerSecond = (scaledRPM * TPR) / 60.0;
            m_yellowjacket.setVelocity(ticksPerSecond);
            return;
        }

        if (m_yellowjacket.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            m_yellowjacket.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
    // uses timer to prevent motor stopping on start
    @Override
    public boolean isStalled() {
        double velocity = Math.abs(m_yellowjacket.getVelocity());
        double current = getCurrent();

        // have yet to clarify the values for min/max voltage and min/max current

        if (current > MIN_CURRENT && velocity < MIN_VELOCITY) {
            return stallTimer.seconds() >= STALL_TIME_SECONDS;
        } else {
            stallTimer.reset();
            return false;
        }
    }

    // get current
    @Override
    public double getCurrent() {
        return m_yellowjacket.getCurrent(CurrentUnit.AMPS);
    }

    // get voltage
    @Override
    public double getVoltage() {
        return 0;
        //return m_voltageSensor.getVoltage();
    }

    @Override
    public boolean isBrownout() {
        double voltage = getVoltage();

        if (!inBrownoutState) {
            if (voltage < MIN_VOLTAGE) {
                if (brownOutTimer.seconds() >= BROWNOUT_TIME_SECONDS) {
                    inBrownoutState = true;
                    brownoutDurationTimer.reset();
                    return true;
                }
            } else {
                brownOutTimer.reset();
            }
            return false;
        } else {
            // Stay in brownout until voltage recovers above the recovery threshold
            if (voltage >= BROWNOUT_RECOVERY_VOLTAGE) {
                inBrownoutState = false;
                brownOutTimer.reset();
                return false;
            }
            return true;
        }
    }

    /**
     * Retrieves the current shooter state for logging.
     *
     * @return a ShooterLog object containing the shooter's power
     *         and whether it is on
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