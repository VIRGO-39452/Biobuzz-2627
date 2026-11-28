package org.firstinspires.ftc.teamcode;

// names of constants go here , import them in other files

public class Constants {

    // All subsystems
    public static final String CONTROL_HUB = "Control Hub";
    public static final double MIN_CURRENT = 0.25;
    public static final double MAX_CURRENT = 18.5;
    public static final double MIN_VELOCITY = 25.0;
    public static final double MIN_VOLTAGE = 8.5;
    public static final double BROWNOUT_RECOVERY_VOLTAGE = MIN_VOLTAGE + 0.5;
    public static final double TRACK_WIDTH = 15.53; //in inches

    public static final double STALL_TIME_SECONDS = 0.45;
    public static final double BROWNOUT_TIME_SECONDS = 0.1;
    public static final double BROWNOUT_SHUTOFF_SECONDS = 0.3;


    // Intake
    public static final String LEFT_INTAKE = "leftIntake";
    public static final String RIGHT_INTAKE = "rightIntake";
    public static final String YELLOW_JACKET_INTAKE = "yellowJacketIntake";

    // Shooter
    public static final String YELLOW_JACKET_SHOOTER = "yellowJacketShooter";
    public static final double TARGET_TPS = 1125;

    // Indexer
    public static final String SERVO_INDEXER = "servoIndexer";

    // Drivetrain
    public static final String LEFT_WHEEL = "leftWheel";
    public static final String RIGHT_WHEEL = "rightWheel";

    /**
     * diameter of wheel in mm (96 mm)
     */
    public static final double WHEEL_DIAMETER = 96.0; // millimeters
}
