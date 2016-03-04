package org.usfirst.frc.team2785.robot.subsystems;

import org.usfirst.frc.team2785.misc.Playable;
import org.usfirst.frc.team2785.misc.PlayableSubsystem;
import org.usfirst.frc.team2785.misc.Player;
import org.usfirst.frc.team2785.misc.TableReader;
import org.usfirst.frc.team2785.robot.Robot;
import org.usfirst.frc.team2785.robot.RobotMap;
import org.usfirst.frc.team2785.robot.commands.TeleopMarvinArm;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MarvinArm extends PIDSubsystem implements PlayableSubsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static CANTalon motor;
    private static AnalogGyro gyro;
    private static DigitalInput limit;
    private boolean usePID = false;
    private TableReader angleReader;
    private TableReader speedReader;

    public MarvinArm() {
        super("arm", RobotMap.ARM_GYRO_P, RobotMap.ARM_GYRO_I, RobotMap.ARM_GYRO_D);
        limit = RobotMap.marvinLimit;
        motor = RobotMap.marvinTalon;
        motor.enableBrakeMode(true);
        gyro = RobotMap.armGyro;
        gyro.reset();
        setAbsoluteTolerance(RobotMap.ARM_TOLERANCE);
        SmartDashboard.putNumber("armTarget", 0);
        pushData();
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new TeleopMarvinArm());
    }

    public void setAngle(double angle) {
        enable();
        setSetpoint(angle);
    }

    public void resetSensors() {
        gyro.reset();
    }

    public void stopPID() {
        getPIDController().reset();
    }

    protected boolean isDone() {
        return onTarget();
    }

    public void set(double magnitude) {
        motor.set(magnitude);
    }

    public void stop() {
        motor.set(0);
    }

    public void pushData() {
        SmartDashboard.putNumber("armGyro", returnPIDInput());
        Robot.recorder.put("marvinArm.speed", motor.get());
        Robot.recorder.put("marvinArm.angle", returnPIDInput());
    }

    public double returnPIDInput() {
        return gyro.getAngle();
    }

    protected void usePIDOutput(double output) {
        SmartDashboard.putNumber("armPID", output);
        motor.set(-output);
    }

    public boolean getLimit() {
        return limit.get();
    }
    public void setPlayerUsePID(boolean yes) {
        usePID = true;
    }
    @Override
    public void playerSetup(Player p) {
        if (usePID) {
            angleReader = p.getReader("marvinArm.angle");
        } else {
            stopPID();
            speedReader = p.getReader("marvinArm.speed");
        }
    }

    @Override
    public void play() {
       if (usePID) {
           setAngle(angleReader.getReading());
       } else {
           set(speedReader.getReading());
       }
    }

    @Override
    public boolean donePlaying() {
        // TODO Auto-generated method stub
        if (usePID) {
            return angleReader.hasNext();
        } else {
            return speedReader.hasNext();
        }
    }

    @Override
    public void stopPlaying() {
        stopPID();
        set(0);
    }
}
