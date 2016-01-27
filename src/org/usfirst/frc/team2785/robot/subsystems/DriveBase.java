package org.usfirst.frc.team2785.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;

import org.usfirst.frc.team2785.misc.DummyOutput;
import org.usfirst.frc.team2785.robot.RobotMap;
import org.usfirst.frc.team2785.robot.commands.TeleopDrive;;
/**
 *
 */
public class DriveBase extends Subsystem {
	
	public static Encoder leftEncoder;
	public static Encoder rightEncoder;
	private static DummyOutput leftOutput;
	private static DummyOutput rightOutput;
	public static RobotDrive drive;
    public static PIDController leftPID;
    public static PIDController rightPID;
   
	
	public DriveBase() {
		leftEncoder = RobotMap.leftEncoder;
		leftOutput = new DummyOutput();
		rightOutput = new DummyOutput();
		drive = new RobotDrive(RobotMap.leftFrontTalon, RobotMap.leftBackTalon, RobotMap.rightFrontTalon, RobotMap.rightBackTalon);
	    rightPID = new PIDController(RobotMap.encP, RobotMap.encI, RobotMap.encD, rightEncoder, leftOutput);
	    leftPID = new PIDController(RobotMap.encP, RobotMap.encI, RobotMap.encD, leftEncoder, rightOutput);
	    setup();
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void setup() {
		leftEncoder.setDistancePerPulse(360/250);
		rightEncoder.setDistancePerPulse(360/250);
		leftPID.setContinuous();
		leftPID.setAbsoluteTolerance(10);
		rightPID.setContinuous();
		rightPID.setAbsoluteTolerance(10);
		SmartDashboard.putData("rightPID", rightPID);
		SmartDashboard.putData("leftPID", leftPID);
		resetSensors();
	}
	public void resetSensors() {
		rightEncoder.reset();
		leftEncoder.reset();
	}
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TeleopDrive());
    }
    public void setTeleopMode() {
    	drive.setSafetyEnabled(true);
    }
    public void driveV(double mag, double turn) {
    	
    }
    public void setDriveTarget(double left, double right) {
    	resetSensors();
    	leftPID.enable();
    	rightPID.enable();
    	leftPID.setSetpoint(left);
    	rightPID.setSetpoint(right);
    }
    public boolean drivePID() {
    	// assumes setDriveTarget done
    	drive.tankDrive(leftPID.get(), rightPID.get());
    	return leftPID.onTarget() && rightPID.onTarget();
    }
    public void stopPID() {
    	leftPID.disable();
    	rightPID.disable();
    }
    public void pushData() {
    	SmartDashboard.putNumber("leftEncoder", leftEncoder.getDistance());
    	SmartDashboard.putNumber("rightEncoder", rightEncoder.getDistance());
    }
}

