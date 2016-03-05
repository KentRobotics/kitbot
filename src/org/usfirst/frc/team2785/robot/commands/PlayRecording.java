package org.usfirst.frc.team2785.robot.commands;

import org.usfirst.frc.team2785.misc.PlayableSubsystem;
import org.usfirst.frc.team2785.robot.Robot;
import org.usfirst.frc.team2785.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PlayRecording extends Command {
    private Subsystem subsystem;
    private PlayableSubsystem playableSubsystem;
    private boolean finished = false;
    private String SmartDashboardKey;
    public PlayRecording(PlayableSubsystem subsystem, String configKey) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        playableSubsystem = subsystem;
        this.subsystem = subsystem.getSubsystem();
        this.SmartDashboardKey = configKey;
        // in our use cases, subsystem.getSubsystem() == subsystem,
        // but subsystem.getSubsystem just casts to Subsystem to avoid errors
        // this is a situation where duck typing would have been useful.
        requires(this.subsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        finished = !Robot.player.read(RobotMap.RECORDING_SAVE_PATH + "/" + Robot.recordingChooser.getSelected());
        if (finished) {
            return;
        }
        try {
            playableSubsystem.setPlayerUsePID(SmartDashboard.getBoolean(SmartDashboardKey));
            playableSubsystem.playerSetup(Robot.player);
        } catch (Exception e) {
            e.printStackTrace();
            finished = true;
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        try {
            playableSubsystem.play();
        } catch (Exception e) {
            e.printStackTrace();
            finished = true;
        }
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
