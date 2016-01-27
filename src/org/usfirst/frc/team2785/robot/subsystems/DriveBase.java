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
	private static DummyOutput leftOutput;
	private static DummyOutput rightOutput;
	private static RobotDrive drive;
    private static PIDController leftPID;
    private static PIDController rightPID;
    private static final double tolDist = 5;
	
	public DriveBase() {
		leftEncoder = RobotMap.leftEncoder;
		rightEncoder = RobotMap.rightEncoder;
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
		rightPID.setContinuous();
		/*SmartDashboard.putNumber("encP", RobotMap.encP);
		SmartDashboard.putNumber("encI", RobotMap.encI);
		SmartDashboard.putNumber("encD", RobotMap.encD);*/ // for tuning purposes
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
    	drive.arcadeDrive(mag, turn, false);
    }
    public void setDriveTarget(double left, double right) {
    	resetSensors();
    	//leftPID.setPID(SmartDashboard.getNumber("encP"), SmartDashboard.getNumber("encI"), SmartDashboard.getNumber("encD"));
    	//rightPID.setPID(SmartDashboard.getNumber("encP"), SmartDashboard.getNumber("encI"), SmartDashboard.getNumber("encD"));
    	leftPID.enable();
    	rightPID.enable();
    	leftPID.setSetpoint(left);
    	rightPID.setSetpoint(right);
    }
    public boolean drivePID(double leftP, double rightP) {
    	// assumes setDriveTarget done
    	drive.tankDrive(-leftPID.get() * leftP, -rightPID.get() * rightP, false);
    	double left_reading = leftEncoder.getDistance();
    	double right_reading = rightEncoder.getDistance();
    	double left_target = leftPID.getSetpoint();
    	double right_target = rightPID.getSetpoint();
    	return (left_reading >= (left_target - tolDist) && left_reading <= (left_target + tolDist)) &&
    		   (right_reading >= (right_target - tolDist) && right_reading <= (right_target + tolDist));
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

