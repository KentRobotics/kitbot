package org.usfirst.frc.team2785.misc;

import edu.wpi.first.wpilibj.command.Subsystem;

public interface PlayableSubsystem extends Playable {
    public default Subsystem getSubsystem() {
        return (Subsystem) this;
    };
}
