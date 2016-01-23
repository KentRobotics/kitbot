package org.usfirst.frc.team2785.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
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
	    distancePID = new PIDController(RobotMap.encP, RobotMap.encI, RobotMap.encD, RobotMap.leftEncoder, dist_output);
	    steeringPID = new PIDController(RobotMap.gyroP, RobotMap.gyroI, RobotMap.gyroD, gyro, gyro_output);
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void setup() {
		gyro.reset();
		leftEncoder.setDistancePerPulse(360/250);
		
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
}

