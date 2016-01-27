package org.usfirst.frc.team2785.robot.subsystems;

import org.usfirst.frc.team2785.robot.RobotMap;
import org.usfirst.frc.team2785.robot.commands.TeleopCamera;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CameraMount extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private static Servo horizontalServo;
	private static Servo verticalServo;
	public CameraMount(Servo vertical, Servo horizontal) {
		horizontalServo = horizontal;
		verticalServo = vertical;
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TeleopCamera());
    }
    public void moveCamera(double vertical, double horizontal) {
    	//TODO: implement
    }
}

