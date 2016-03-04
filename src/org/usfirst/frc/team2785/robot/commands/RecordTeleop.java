package org.usfirst.frc.team2785.robot.commands;

import java.io.File;

import org.usfirst.frc.team2785.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RecordTeleop extends Command {
    private boolean on;
    private String fileName;
    private static final String savePath = "/home/lvuser/data";
    public RecordTeleop(boolean yes, String fileName) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        this.on = yes;
        this.fileName = fileName;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.recorder.setEnabled(on);
        if (!on) {
            File dataDir = new File(savePath);
            dataDir.mkdir();
            Robot.recorder.write(savePath + "/" + fileName);
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
