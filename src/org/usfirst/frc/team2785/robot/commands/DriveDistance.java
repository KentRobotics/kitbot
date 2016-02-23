package org.usfirst.frc.team2785.robot.commands;

import org.usfirst.frc.team2785.robot.Robot;
import org.usfirst.frc.team2785.robot.RobotMap;
import org.usfirst.frc.team2785.robot.subsystems.DriveBase;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveDistance extends Command {

	private DriveBase drive;
	private double leftDistance;
	private double rightDistance;
	private double leftMaxPower;
	private double rightMaxPower;
	private boolean finished = false;
	private boolean _debug = false;

	public DriveDistance(double leftDistance, double rightDistance, double leftMaxPower, double rightMaxPower) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);

		requires(Robot.driveBase);
		this.drive = Robot.driveBase;
		this.leftDistance = leftDistance;
		this.rightDistance = rightDistance;
		this.leftMaxPower = leftMaxPower;
		this.rightMaxPower = rightMaxPower;
	}

	public DriveDistance(double leftDistance, double rightDistance) {
		this(leftDistance, rightDistance, RobotMap.NORMAL_MOVE_SPEED, RobotMap.NORMAL_MOVE_SPEED);
	}

	public DriveDistance(boolean _debug) {
		this(-1, -1, -1, -1); // values will be overwritten anyway
		this._debug = true;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (_debug) {
			double dLeft = SmartDashboard.getNumber("dLeft");
			double dRight = SmartDashboard.getNumber("dRight");
			double pLeft = SmartDashboard.getNumber("pLeft");
			double pRight = SmartDashboard.getNumber("pRight");
			this.leftDistance = dLeft;
			this.rightDistance = dRight;
			this.leftMaxPower = pLeft;
			this.rightMaxPower = pRight;
		}
		drive.setupEncoderDistance();
		drive.setDriveTarget(leftDistance, rightDistance);
		SmartDashboard.putBoolean("driving", true);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		drive.pushData();
		finished = drive.drivePID(leftMaxPower, rightMaxPower);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return finished;
	}

	// Called once after isFinished returns true
	protected void end() {
		// for debugging purposes
		// SmartDashboard.putBoolean("driving", false);
		drive.stopPID();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
