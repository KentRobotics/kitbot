package org.usfirst.frc.team2785.robot.commands;

import org.usfirst.frc.team2785.misc.PlayableSubsystem;
import org.usfirst.frc.team2785.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class PlayRecording extends Command {
    private String fileName;
    private Subsystem subsystem;
    private PlayableSubsystem playableSubsystem;
    private boolean finished = false;
    private boolean usePID;
    public PlayRecording(PlayableSubsystem subsystem, String fileName, boolean usePID) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        playableSubsystem = subsystem;
        this.usePID = usePID;
        this.subsystem = subsystem.getSubsystem(); 
        // in our use cases, subsystem.getSubsystem() == subsystem,
        // but subsystem.getSubsystem just casts to Subsystem to avoid errors
        // this is a situation where duck typing would have been useful.
        requires(this.subsystem);
        this.fileName = fileName;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        try {
            Robot.player.read("/home/lvuser/data/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            finished = true;
            return;
        }
        playableSubsystem.setPlayerUsePID(usePID);
        playableSubsystem.playerSetup(Robot.player);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        playableSubsystem.play();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished || playableSubsystem.donePlaying();
    }

    // Called once after isFinished returns true
    protected void end() {
        playableSubsystem.stopPlaying();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
