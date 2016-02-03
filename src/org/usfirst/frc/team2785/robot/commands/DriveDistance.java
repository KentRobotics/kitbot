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
	private double diam;
	private boolean finished = false;
	private boolean _debug = false;
    public DriveDistance(double dLeft, double dRight, double pLeft, double pRight, double diameter) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.drivebase);
    	drive = Robot.drivebase;
    	left = dLeft;
    	right = dRight;
    	leftp = pLeft;
    	rightp = pRight;
    	diam = diameter;
    }
    public DriveDistance(double dLeft, double dRight) {
    	this(dLeft, dRight, 0.5, 0.5, 360); 
    }
    public DriveDistance(boolean _debug) {
    	this(-1, -1, -1, -1, -1); //values will be overwritten anyway
    	this._debug = true;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (_debug) {
    		double dLeft = SmartDashboard.getNumber("dLeft");
    		double dRight = SmartDashboard.getNumber("dRight");
    		double pLeft = SmartDashboard.getNumber("pLeft");
    		double pRight = SmartDashboard.getNumber("pRight");
    		double diameter = SmartDashboard.getNumber("diameter");
    		left = dLeft;
        	right = dRight;
        	leftp = pLeft;
        	rightp = pRight;
        	diam = diameter;
    	}
    	drive.setupEncoderDistance(diam);
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
    	// for debugging purposes
    	//SmartDashboard.putBoolean("driving", false);
    	drive.stopPID();
    	drive.resetEncoderDistance();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
