package org.usfirst.frc.team2785.robot.commands;

import org.usfirst.frc.team2785.robot.OI;
import org.usfirst.frc.team2785.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopMarvinArm extends Command {

    public TeleopMarvinArm() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.marvinarm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (OI.stick.getRawButton(6)) {
    		Robot.marvinarm.set(1);
    	} else if (OI.stick.getRawButton(7)) {
    		Robot.marvinarm.set(-1);
    	} else {
    		Robot.marvinarm.set(0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.marvinarm.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
