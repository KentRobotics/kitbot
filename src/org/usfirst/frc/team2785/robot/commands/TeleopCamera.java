package org.usfirst.frc.team2785.robot.commands;

import org.usfirst.frc.team2785.robot.OI;
import org.usfirst.frc.team2785.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopCamera extends Command {

    public TeleopCamera() {
        /**This runs the camera and the camera server during teleop.
         * Unfortunately, we don't use the camera so the code is just sitting here.**/
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.cameraMount);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //Robot.cameraMount.resetPosition();
        //Robot.cameraMount.startServer();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //Robot.cameraMount.pushData();
        //Robot.cameraMount.moveCamera(OI.happyStick.getX(), -OI.happyStick.getY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.cameraMount.stopServer();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
