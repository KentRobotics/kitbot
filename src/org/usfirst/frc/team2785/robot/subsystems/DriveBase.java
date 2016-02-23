package org.usfirst.frc.team2785.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
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
		leftEncoder = RobotMap.rightEncoder;
		rightEncoder = RobotMap.leftEncoder;
		gyro = RobotMap.gyro;
		leftOutput = new DummyOutput();
		rightOutput = new DummyOutput();
		gyroOutput = new DummyOutput();
		drive = new RobotDrive(RobotMap.leftFrontTalon, RobotMap.leftBackTalon, RobotMap.rightFrontTalon, RobotMap.rightBackTalon);
	    rightPID = new PIDController(RobotMap.ENCODER_P, RobotMap.ENCODER_I, RobotMap.ENCODER_D, rightEncoder, rightOutput);
	    leftPID = new PIDController(RobotMap.ENCODER_P, RobotMap.ENCODER_I, RobotMap.ENCODER_D, leftEncoder, leftOutput);
	    gyroPID = new PIDController(RobotMap.GYRO_P, RobotMap.GYRO_I, RobotMap.GYRO_D, gyro, gyroOutput);
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
	public void setupEncoderDistance() {
		leftEncoder.setDistancePerPulse(Math.PI * RobotMap.WHEEL_DIAMETER / RobotMap.ENCODER_TICKS_PER_ROTATION);
		rightEncoder.setDistancePerPulse(Math.PI * RobotMap.WHEEL_DIAMETER / RobotMap.ENCODER_TICKS_PER_ROTATION);
	}
	public void resetEncoderDistance() {
		leftEncoder.setDistancePerPulse(360 / RobotMap.ENCODER_TICKS_PER_ROTATION);
		rightEncoder.setDistancePerPulse(360 / RobotMap.ENCODER_TICKS_PER_ROTATION);
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
    	drive.tankDrive(_bound(leftPID.get(), leftMagnitude), _bound(rightPID.get(), rightMagnitude), false);
    	double left_reading = leftEncoder.getDistance();
    	double right_reading = rightEncoder.getDistance();
    	double left_target = leftPID.getSetpoint();
    	double right_target = rightPID.getSetpoint();
    	SmartDashboard.putNumber("leftPID target", left_target);
    	SmartDashboard.putNumber("rightPID target", right_target);
    	return (left_reading >= (left_target - RobotMap.ENCODER_TOLERANCE) && left_reading <= (left_target + RobotMap.ENCODER_TOLERANCE)) &&
    		   (right_reading >= (right_target - RobotMap.ENCODER_TOLERANCE) && right_reading <= (right_target + RobotMap.ENCODER_TOLERANCE));
    }
    public boolean turnPID(double max_speed) {
    	double gyroAngle = gyro.getAngle();
    	double gyroSetpoint = gyroPID.getSetpoint();
    	drive.drive(_bound(gyroPID.get(), max_speed), (gyroAngle > gyroSetpoint) ? 1 : -1);
    	//gyroAngle = gyroAngle % 360; // hack for error checking
    	SmartDashboard.putNumber("gyroPID setpoint", gyroSetpoint);
    	SmartDashboard.putNumber("gyroPID value", gyroPID.get());
    	return (gyroAngle >= (gyroSetpoint - RobotMap.GYRO_TOLERANCE) && gyroAngle <= (gyroSetpoint + RobotMap.GYRO_TOLERANCE));
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
    	SmartDashboard.putNumber("encP", RobotMap.ENCODER_P);
    	SmartDashboard.putNumber("encI", RobotMap.ENCODER_I);
    	SmartDashboard.putNumber("encD", RobotMap.ENCODER_D);
    	SmartDashboard.putNumber("gyrP", RobotMap.GYRO_P);
    	SmartDashboard.putNumber("gyrI", RobotMap.GYRO_I);
    	SmartDashboard.putNumber("gyrD", RobotMap.GYRO_D);
    	SmartDashboard.putNumber("dLeft", 36);
    	SmartDashboard.putNumber("dRight", 36);
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

