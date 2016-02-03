package org.usfirst.frc.team2785.robot.commands;

import org.usfirst.frc.team2785.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Turn extends Command {
	private double angle;
	private double speed;
	private boolean done = false;
	private boolean _debug = false;
    public Turn(double angle, double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.angle = angle;
    	this.speed = speed;
    	requires(Robot.driveBase);
    }
    public Turn(boolean _debug) {
    	this(-1, -1); //setting the debug flag will override these parameters
    	this._debug = true;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (_debug) {
    		angle = SmartDashboard.getNumber("turnAngle");
    		speed = SmartDashboard.getNumber("turnSpeed");
    	}
    	Robot.driveBase.setTurnTarget(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = Robot.driveBase.turnPID(speed); 
    	Robot.driveBase.pushData();
    } // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveBase.stopPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
