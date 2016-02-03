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
	
	private static Encoder leftEncoder;
	private static Encoder rightEncoder;
	private static AnalogGyro gyro;
	private static DummyOutput leftOutput;
	private static DummyOutput rightOutput;
	private static DummyOutput gyroOutput;
	private static RobotDrive drive;
    private static PIDController leftPID;
    private static PIDController rightPID;
    private static PIDController gyroPID;
	
	public DriveBase() {
		leftEncoder = RobotMap.leftEncoder;
		rightEncoder = RobotMap.rightEncoder;
		gyro = RobotMap.gyro;
		leftOutput = new DummyOutput();
		rightOutput = new DummyOutput();
		gyroOutput = new DummyOutput();
		drive = new RobotDrive(RobotMap.leftFrontTalon, RobotMap.leftBackTalon, RobotMap.rightFrontTalon, RobotMap.rightBackTalon);
	    rightPID = new PIDController(RobotMap.encP, RobotMap.encI, RobotMap.encD, rightEncoder, leftOutput);
	    leftPID = new PIDController(RobotMap.encP, RobotMap.encI, RobotMap.encD, leftEncoder, rightOutput);
	    gyroPID = new PIDController(RobotMap.gyrP, RobotMap.gyrI, RobotMap.gyrD, gyro, gyroOutput);
	    setup();
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void setup() {
		resetEncoderDistance();
		leftPID.setContinuous();
		rightPID.setContinuous();
		debugInitPID();
		resetSensors();
	}
	public void resetSensors() {
		rightEncoder.reset();
		leftEncoder.reset();
		gyro.reset();
	}
	public void setupEncoderDistance(double wheel_diameter) {
		leftEncoder.setDistancePerPulse(Math.PI * wheel_diameter / 250);
		rightEncoder.setDistancePerPulse(Math.PI * wheel_diameter / 250);
	}
	public void resetEncoderDistance() {
		leftEncoder.setDistancePerPulse(360/250);
		rightEncoder.setDistancePerPulse(360/250);
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
    	drive.arcadeDrive(mag, turn, false);
    }
    public void setDriveTarget(double left, double right) {
    	resetSensors();
    	debugApplyPID();
    	leftPID.enable();
    	rightPID.enable();
    	leftPID.setSetpoint(left);
    	rightPID.setSetpoint(right);
    }
    public void setTurnTarget(double angle) {
    	resetSensors();
    	debugApplyPID();
    	gyroPID.enable();
    	gyroPID.setSetpoint(angle);
    }
    public boolean drivePID(double leftMagnitude, double rightMagnitude) {
    	// assumes setDriveTarget done
    	drive.tankDrive(_bound(-leftPID.get(), leftMagnitude), _bound(-rightPID.get(), rightMagnitude), false);
    	double left_reading = leftEncoder.getDistance();
    	double right_reading = rightEncoder.getDistance();
    	double left_target = leftPID.getSetpoint();
    	double right_target = rightPID.getSetpoint();
    	SmartDashboard.putNumber("leftPID target", left_target);
    	SmartDashboard.putNumber("rightPID target", right_target);
    	return (left_reading >= (left_target - RobotMap.encoderTolerance) && left_reading <= (left_target + RobotMap.encoderTolerance)) &&
    		   (right_reading >= (right_target - RobotMap.encoderTolerance) && right_reading <= (right_target + RobotMap.encoderTolerance));
    }
    public boolean turnPID(double max_speed) {
    	double gyroAngle = gyro.getAngle();
    	double gyroSetpoint = gyroPID.getSetpoint();
    	//turning is reversed
    	drive.drive(_bound(gyroPID.get(), max_speed), (gyroAngle > gyroSetpoint) ? -1 : 1);
    	//gyroAngle = gyroAngle % 360; // hack for error checking
    	SmartDashboard.putNumber("gyroPID setpoint", gyroSetpoint);
    	SmartDashboard.putNumber("gyroPID value", gyroPID.get());
    	return (gyroAngle >= (gyroSetpoint - RobotMap.gyroTolerance) && gyroAngle <= (gyroSetpoint + RobotMap.gyroTolerance));
    }
    public void stopPID() {
    	leftPID.reset();
    	rightPID.reset();
    	gyroPID.reset();
    }
    private void debugApplyPID() {
    	//for pid tuning...
    	leftPID.setPID(SmartDashboard.getNumber("encP"), SmartDashboard.getNumber("encI"), SmartDashboard.getNumber("encD"));
    	rightPID.setPID(SmartDashboard.getNumber("encP"), SmartDashboard.getNumber("encI"), SmartDashboard.getNumber("encD"));
    	gyroPID.setPID(SmartDashboard.getNumber("gyrP"), SmartDashboard.getNumber("gyrI"), SmartDashboard.getNumber("gyrD"));
    }
    private void debugInitPID() {
    	//also for pid tuning
    	SmartDashboard.putNumber("encP", RobotMap.encP);
    	SmartDashboard.putNumber("encI", RobotMap.encI);
    	SmartDashboard.putNumber("encD", RobotMap.encD);
    	SmartDashboard.putNumber("gyrP", RobotMap.gyrP);
    	SmartDashboard.putNumber("gyrI", RobotMap.gyrI);
    	SmartDashboard.putNumber("gyrD", RobotMap.gyrD);
    	SmartDashboard.putNumber("dLeft", 5);
    	SmartDashboard.putNumber("dRight", 5);
    	SmartDashboard.putNumber("pLeft", 0.5);
    	SmartDashboard.putNumber("pRight", 0.5);
    	SmartDashboard.putNumber("diameter", 8);
    	SmartDashboard.putNumber("turnAngle", 180);
    	SmartDashboard.putNumber("turnSpeed", 0.75);
    }
    private double _bound(double value, double range) {
    	if (value > range) {
    		return range;
    	} else if (value < -range) {
    		return -range;
    	}
    	return value;
    }
    public void pushData() {
    	SmartDashboard.putNumber("leftEncoder", leftEncoder.getDistance());
    	SmartDashboard.putNumber("rightEncoder", rightEncoder.getDistance());
    	SmartDashboard.putNumber("gyro", gyro.getAngle());
    }
}

