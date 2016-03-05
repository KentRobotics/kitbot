package org.usfirst.frc.team2785.robot.commands;

import java.io.File;

import org.usfirst.frc.team2785.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RecordTeleopMain extends Command {
    private static final String savePath = "/home/lvuser/data";
    public RecordTeleopMain() {
        SmartDashboard.putBoolean("recordingTeleop", false);
        File dataDir = new File(savePath);
        dataDir.mkdir();
        // Use requires() here to declare 0subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.recorder.clear();
        Robot.recorder.setEnabled(true);
        SmartDashboard.putBoolean("recordingTeleop", true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        SmartDashboard.putBoolean("recordingTeleop", false);
        Robot.recorder.write(savePath + "/" + (String) Robot.recordingChooser.getSelected());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
