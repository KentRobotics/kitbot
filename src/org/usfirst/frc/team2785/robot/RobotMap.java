package org.usfirst.frc.team2785.robot;

import org.usfirst.frc.team2785.misc.Player;
import org.usfirst.frc.team2785.misc.Recorder;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * This is in essence a header file for the whole robot. All the individual component
 * objects and constants for PID loops are defined here.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;

    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
    public static CANTalon rightFrontTalon = new CANTalon(1);
    public static CANTalon rightBackTalon = new CANTalon(2);
    public static CANTalon leftFrontTalon = new CANTalon(3);
    public static CANTalon leftBackTalon = new CANTalon(4);
    public static CANTalon marvinTalon = new CANTalon(5);
    public static Encoder leftEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
    public static Encoder rightEncoder = new Encoder(2, 3, true, Encoder.EncodingType.k4X);
    public static DigitalInput marvinLimit = new DigitalInput(4);
    public static AnalogGyro gyro = new AnalogGyro(1);
    public static AnalogGyro armGyro = new AnalogGyro(0);
    public static Servo cameraVertical = new Servo(1);
    public static Servo cameraHorizontal = new Servo(0);
    public static USBCamera camera = null;// new USBCamera("cam0");
    
    public static final double WHEEL_DIAMETER = 8; // inches
    public static final double ENCODER_P = 0.05;
    public static final double ENCODER_I = 0;
    public static final double ENCODER_D = 0.003;
    public static final double GYRO_P = 0.007;
    public static final double GYRO_I = 0;
    public static final double GYRO_D = 0.005; // best for turning 90 degrees
    public static final double ARM_GYRO_P = 1.000;
    public static final double ARM_GYRO_I = 0;
    public static final double ARM_GYRO_D = 0.600; // best for turning 90
                                                   // degrees
    public static final double ARM_TOLERANCE = 2;
    public static final double ENCODER_TICKS_PER_ROTATION = 250; // for standard
    public static final double ENCODER_TOLERANCE = 5;
    public static final double GYRO_TOLERANCE = 3;
    public static final int CAMERA_QUALITY = 50;
    public static final double CAMERA_MOUNT_SPEED = 4.0;
    public static final double NORMAL_TURN_SPEED = 0.6;
    public static final double NORMAL_MOVE_SPEED = 0.5;

}
