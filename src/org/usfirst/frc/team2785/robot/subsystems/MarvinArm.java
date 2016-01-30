package org.usfirst.frc.team2785.robot.subsystems;

import org.usfirst.frc.team2785.robot.RobotMap;
import org.usfirst.frc.team2785.robot.commands.TeleopMarvinArm;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class MarvinArm extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private static CANTalon motor;
	public MarvinArm() {
		motor = RobotMap.marvinTalon;
		motor.enableBrakeMode(true);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TeleopMarvinArm());
    }
    public void set(double magnitude) {
    	motor.set(magnitude);
    }
    public void stop() {
    	motor.set(0);
    }
}

