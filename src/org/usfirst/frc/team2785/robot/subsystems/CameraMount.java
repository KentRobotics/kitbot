package org.usfirst.frc.team2785.robot.subsystems;

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
	private static final double speed = 2.0d; // 1 deg / 20 milliseconds
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
    	//TODO: fix, duh
    	if (Math.abs(vertical) > 0.01) {
    		verticalServo.setAngle(verticalServo.getAngle() + vertical * speed);
    	}
    	if (Math.abs(horizontal) > 0.01) {
    		horizontalServo.setAngle(horizontalServo.getAngle() + horizontal * speed);
    	}
    }
}

