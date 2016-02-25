package org.usfirst.frc.team2785.robot.commands;

import org.usfirst.frc.team2785.robot.OI;
import org.usfirst.frc.team2785.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopDrive extends Command {

    public TeleopDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.driveBase);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (OI.stick.getTrigger()) {
            Robot.driveBase.resetSensors();
        }
        double mult = (-OI.stick.getZ() + 1) / 4 + 0.5; // 50% at lowest point,
                                                        // 100% at highest
        Robot.driveBase.driveV(-OI.stick.getY() * mult, -OI.stick.getX() * mult);
        Robot.driveBase.pushData();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
