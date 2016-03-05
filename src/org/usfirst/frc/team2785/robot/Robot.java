
package org.usfirst.frc.team2785.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team2785.misc.Player;
import org.usfirst.frc.team2785.misc.Recorder;
import org.usfirst.frc.team2785.robot.commands.CalibrateMarvinArm;
import org.usfirst.frc.team2785.robot.commands.DriveDistance;
import org.usfirst.frc.team2785.robot.commands.Nothing;
import org.usfirst.frc.team2785.robot.commands.PlayRecordedTeleop;
import org.usfirst.frc.team2785.robot.commands.SetMarvinArm;
import org.usfirst.frc.team2785.robot.commands.Turn;
import org.usfirst.frc.team2785.robot.commands.batchjobs.*;
import org.usfirst.frc.team2785.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static OI oi;
    public static DriveBase driveBase;
    public static CameraMount cameraMount;
    public static MarvinArm marvinArm;
    public static Recorder recorder;
    public static Player player;
    Command autonomousCommand;
    Command teleopCommand;
    SendableChooser chooser;
    public static SendableChooser recordingChooser;
    CommandGroup command;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        recorder = new Recorder();
        driveBase = new DriveBase();
        // Commented out until needed.
        // cameraMount = new CameraMount(RobotMap.cameraHorizontal,
        // RobotMap.cameraVertical, RobotMap.camera);
        marvinArm = new MarvinArm();
        chooser = new SendableChooser();
        recordingChooser = new SendableChooser();
        player = new Player();
        oi = new OI();
        //This gives us the nice radio buttons on the SmartDashboard
        //chooser.addDefault("drive forward", new DriveDistance(60, 60, 0.5, 0.5));
        //chooser.addObject("do a 180", new Turn(180, 0.75));
        chooser.addObject("debug mode", new Nothing());
        chooser.addObject("replay selected recording", new PlayRecordedTeleop());
        /*
         * TODO: implement, duh.
         *  chooser.addObject("portcullis", new
         * BreachPortcullis()); chooser.addObject("chevals", new
         * BreachChevals()); chooser.addObject("moat", new BreachMoat());
         * chooser.addObject("ramparts", new BreachRamparts());
         * chooser.addObject("drawbridge", new BreachDrawbridge());
         * chooser.addObject("sally port", new BreachSallyPort());
         * chooser.addObject("rock wall", new BreachRockWall());
         * chooser.addObject("rough terrain", new BreachTerrain());
         */
        chooser.addDefault("low bar", new BreachLowBar());
        recordingChooser.addObject("portcullis", RobotMap.portcullisFileName);
        recordingChooser.addObject("chevals", RobotMap.chevalsFileName);
        recordingChooser.addObject("moat", RobotMap.moatFileName);
        recordingChooser.addObject("ramparts", RobotMap.rampartsFileName);
        recordingChooser.addObject("drawbridge", RobotMap.drawbridgeFileName);
        recordingChooser.addObject("sally port", RobotMap.sallyPortFileName);
        recordingChooser.addObject("rock wall", RobotMap.rockWallFileName);
        recordingChooser.addObject("rough terrain", RobotMap.roughTerrainFileName);
        recordingChooser.addDefault("low bar", RobotMap.lowBarFileName);
        // (String) recordingChooser.getSelected();
        SmartDashboard.putData("Recording Name", recordingChooser);
        // chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
        //putting commands to the Dashboard makes nice little command buttons
        SmartDashboard.putData("Debug DriveDistance", new DriveDistance());
        SmartDashboard.putData("Debug Turn", new Turn());
        SmartDashboard.putData("Debug SetMarvinArm", new SetMarvinArm());
        SmartDashboard.putData("Replay Selected Recording", new PlayRecordedTeleop());
        SmartDashboard.putData("Debug CalibrateMarvinArm", new CalibrateMarvinArm());

    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    public void disabledInit() {

    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString code to get the auto name from the text box below the Gyro
     *
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();

        /*
         * String autoSelected = SmartDashboard.getString("Auto Selector",
         * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
         * = new MyAutoCommand(); break; case "Default Auto": default:
         * autonomousCommand = new ExampleCommand(); break; }
         */

        // schedule the autonomous command (example)
        if (autonomousCommand != null)
            autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null)
            autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
