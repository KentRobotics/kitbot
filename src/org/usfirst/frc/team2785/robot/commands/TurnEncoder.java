package org.usfirst.frc.team2785.robot.commands;

import org.usfirst.frc.team2785.robot.Robot;
import org.usfirst.frc.team2785.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnEncoder extends Command {
	private double angle;
	private double power;
	private boolean onTarget = false;
	private static final double FULL_CIRCLE = RobotMap.ROBOT_WIDTH * Math.PI;
    public TurnEncoder(double turn_angle, double max_power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveBase);
    	angle = turn_angle;
    	power = max_power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double target = angle / 360 * FULL_CIRCLE;
    	Robot.driveBase.setupEncoderDistance();
    	Robot.driveBase.setDriveTarget(-target, target);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	onTarget = Robot.driveBase.drivePID(power, power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return onTarget;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveBase.stopPID();
    	Robot.driveBase.resetEncoderDistance();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
