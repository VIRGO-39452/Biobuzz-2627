package org.firstinspires.ftc.teamcode;

public interface ElectricalSafeguards {
    boolean isStalled();
    boolean isBrownout();
    double getCurrent();
    double getVoltage();

}
