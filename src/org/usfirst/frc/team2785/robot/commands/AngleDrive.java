package org.usfirst.frc.team2785.robot.commands;

import org.usfirst.frc.team2785.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AngleDrive extends Command {
	double left, right, angle, power;
	boolean finished = false;
    public AngleDrive(double left, double right, double angle, double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveBase);
    	this.left = left;
    	this.right = right;
    	this.angle = angle;
    	this.power = power;
    	this.finished = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveBase.setAngleTurnTarget(left, right, angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	finished = Robot.driveBase.angleTurnPID(power);
    	Robot.driveBase.pushData();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
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
