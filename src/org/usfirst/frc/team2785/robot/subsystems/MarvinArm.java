package org.usfirst.frc.team2785.robot.subsystems;

import org.usfirst.frc.team2785.robot.RobotMap;
import org.usfirst.frc.team2785.robot.commands.TeleopMarvinArm;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MarvinArm extends PIDSubsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private static CANTalon motor;
	private static AnalogGyro gyro;
	public MarvinArm() {
		super("arm", RobotMap.ARM_GYRO_P, RobotMap.ARM_GYRO_I, RobotMap.ARM_GYRO_D);
		motor = RobotMap.marvinTalon;
		motor.enableBrakeMode(true);
		gyro = RobotMap.armGyro;
		gyro.reset();
		getPIDController().setContinuous(false);
		setAbsoluteTolerance(RobotMap.ARM_TOLERANCE);
		SmartDashboard.putNumber("armTarget", 0);
		pushData();
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TeleopMarvinArm());
    }
    public void setAngle(double angle) {
    	enable();
    	getPIDController().reset();
    	setSetpoint(-angle);
    }
    public void resetSensors() {
    	gyro.reset();
    }
    public void setTeleop() {
    	disable();
    }
    protected boolean isDone() {
    	return onTarget();
    }
    public void set(double magnitude) {
    	motor.set(magnitude);
    }
    public void stop() {
    	motor.set(0);
    }
    public void pushData() {
    	SmartDashboard.putNumber("armGyro", gyro.getAngle());
    }
	protected double returnPIDInput() {
		return gyro.getAngle();
	}
	protected void usePIDOutput(double output) {
		motor.pidWrite(output);
	}
}

