package org.usfirst.frc.team2785.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;

import org.usfirst.frc.team2785.misc.PIDOutputAbsorber;
import org.usfirst.frc.team2785.robot.RobotMap;
import org.usfirst.frc.team2785.robot.commands.TeleopDrive;;
/**
 *
 */
public class DriveBase extends Subsystem {
	private static final int PID_MODE_ROTATE = 0;
	private static final int PID_MODE_FORWARDBACK = 1;
	
	public static AnalogGyro gyro;
	public static Encoder leftEncoder;
	private static PIDOutputAbsorber dist_output;
	private static PIDOutputAbsorber gyro_output;
	public static RobotDrive drive;
    public static PIDController distancePID;
    public static PIDController steeringPID;
   
	
	public DriveBase() {
		gyro = RobotMap.gyro;
		leftEncoder = RobotMap.leftEncoder;
		dist_output = new PIDOutputAbsorber();
		gyro_output = new PIDOutputAbsorber();
		drive = new RobotDrive(RobotMap.leftFrontTalon, RobotMap.leftBackTalon, RobotMap.rightFrontTalon, RobotMap.rightBackTalon);
	    distancePID = new PIDController(RobotMap.encP, RobotMap.encI, RobotMap.encD, leftEncoder, dist_output);
	    steeringPID = new PIDController(RobotMap.gyroP, RobotMap.gyroI, RobotMap.gyroD, gyro, gyro_output);
	    setup();
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void setup() {
		leftEncoder.setDistancePerPulse(360/250);
		distancePID.setContinuous();
		distancePID.setAbsoluteTolerance(10);
		steeringPID.setInputRange(0, 360);
		steeringPID.setOutputRange(-1, 1);
		steeringPID.setPercentTolerance(5);
		resetSensors();
	}
	public void resetSensors() {
		gyro.reset();
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
    	drive.arcadeDrive(mag, turn);
    }
    public void setDriveTarget(double dist, double deg) {
    	resetSensors();
    	distancePID.setSetpoint(dist);
    	steeringPID.setSetpoint(deg);
    	distancePID.enable();
    	steeringPID.enable();
    }
    public void driveD() {
    	// assumes setDriveTarget done
    	drive.arcadeDrive(distancePID.get(), steeringPID.get());
    }
    public void pushData() {
    	SmartDashboard.putNumber("leftEncoder", leftEncoder.getDistance());
    	SmartDashboard.putNumber("gyro", gyro.getAngle());
    }
    public void pidConstPush() {
    	
    	SmartDashboard.putNumber("", 12);
    }
    public void pidConstSet() {
    	
    }
}

