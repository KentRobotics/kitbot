package org.usfirst.frc.team2785.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class ExamplePIDSubsystem extends PIDSubsystem {

    // Initialize your subsystem here
    public ExamplePIDSubsystem(String name, double p, double i, double d) {
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	super(name, p, i, d);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	return 0.0;
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    }
}
