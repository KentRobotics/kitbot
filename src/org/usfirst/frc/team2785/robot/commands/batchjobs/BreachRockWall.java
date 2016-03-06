package org.usfirst.frc.team2785.robot.commands.batchjobs;

import org.usfirst.frc.team2785.robot.commands.CalibrateMarvinArm;
import org.usfirst.frc.team2785.robot.commands.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The name is misleading. 
 * Proven to also breach moats and rocky terrain.
 */
public class BreachRockWall extends CommandGroup {

    public BreachRockWall() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        // addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        // addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        addSequential(new CalibrateMarvinArm());
        addSequential(new DriveDistance(108*2, 108*2, 1, 1)); // approximate length
    }
}
