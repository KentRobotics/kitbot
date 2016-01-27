package org.usfirst.frc.team2785.robot.commands;

import org.usfirst.frc.team2785.robot.Robot;
import org.usfirst.frc.team2785.robot.subsystems.DriveBase;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveDistance extends Command {

	private static DriveBase drive;
	private double left;
	private double right;
	private double leftp;
	private double rightp;
	private boolean finished = false;
    public DriveDistance(double dLeft, double dRight, double pLeft, double pRight, double diameter) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.drivebase);
    	drive = Robot.drivebase;
    	left = dLeft / diameter / Math.PI * 360;
    	right = dRight / diameter / Math.PI * 360;
    	leftp = pLeft;
    	rightp = pRight;
    }
    public DriveDistance(double dLeft, double dRight) {
    	this(dLeft, dRight, 1, 1, Math.PI/360);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drive.setDriveTarget(left, right);
    	SmartDashboard.putBoolean("driving", true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drive.pushData();
    	finished = drive.drivePID(leftp, rightp);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	SmartDashboard.putBoolean("driving", false);
    	drive.stopPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
