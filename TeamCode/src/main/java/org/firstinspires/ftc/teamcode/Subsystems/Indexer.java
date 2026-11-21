package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.SERVO_INDEXER;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Logging.IndexerLog;

/**
 * Represents the indexer subsystem of the robot.
 *
 * <p>The indexer uses a continuous rotation servo to move game
 * elements through the indexing mechanism. This class provides
 * methods to control the servo's power, stop the indexer, and
 * retrieve its current state for logging.</p>
 */
public class Indexer {

    /**
     * The continuous rotation servo responsible for operating
     * the indexer mechanism.
     */
    private final CRServo m_servo;

    /**
     * Initializes the indexer subsystem by mapping its continuous
     * rotation servo from the FTC hardware configuration.
     *
     * @param hardwareMap the hardware map used to retrieve the
     *                   indexer servo
     */
    public Indexer(HardwareMap hardwareMap) {
        m_servo = hardwareMap.get(CRServo.class, SERVO_INDEXER);
    }

    /**
     * Starts or operates the indexer at the specified power.
     *
     * <p>Power values range from -1.0 to 1.0, where the sign
     * determines the direction of rotation and the magnitude
     * determines the speed.</p>
     *
     * @param power the power applied to the indexer servo,
     *              ranging from -1.0 to 1.0
     */
    public void startIndexer(double power) {
        // TODO verify direction
        m_servo.setPower(power);
    }

    /**
     * Stops the indexer by setting the servo power to zero.
     */
    public void stop() {
        m_servo.setPower(0.0);
    }

    /**
     * Retrieves the current state of the indexer for logging.
     *
     * <p>The returned log contains the servo's current power
     * and whether the indexer is currently operating.</p>
     *
     * @return an {@link IndexerLog} containing the indexer's
     *         current power and operational state
     */
    public IndexerLog logging() {
        double power = m_servo.getPower();

        return new IndexerLog(
                power,
                power != 0
        );
    }
}