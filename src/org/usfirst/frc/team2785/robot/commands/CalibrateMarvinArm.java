package org.usfirst.frc.team2785.robot.commands;

import org.usfirst.frc.team2785.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CalibrateMarvinArm extends Command {

    public CalibrateMarvinArm() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.marvinArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.marvinArm.stopPID();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.marvinArm.set(-1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.marvinArm.getLimit();
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.marvinArm.resetSensors();
        Robot.marvinArm.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        Robot.marvinArm.set(0);
    }
}
