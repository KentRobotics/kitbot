package org.usfirst.frc.team2785.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.vision.USBCamera;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
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
	public static CANTalon leftFrontTalon = new CANTalon(1);
	public static CANTalon leftBackTalon = new CANTalon(2);
	public static CANTalon rightFrontTalon = new CANTalon(3);
	public static CANTalon rightBackTalon = new CANTalon(4);
	public static CANTalon marvinTalon = new CANTalon(5);
	public static Encoder  rightEncoder = new Encoder(2, 3, true, Encoder.EncodingType.k4X);
	public static Encoder  leftEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	public static AnalogGyro gyro = new AnalogGyro(1);
	public static Servo cameraVertical = new Servo(1);
	public static Servo cameraHorizontal = new Servo(0);
	public static USBCamera camera = new USBCamera("cam0");
	
	public static final double wheelDiameter = 8; //inches
	public static final double encP = 0.006;
	public static final double encI = 0;
	public static final double encD = 0.003;
	public static final double gyrP = 0.006;
	public static final double gyrI = 0;
	public static final double gyrD = 0.003;
	public static final double encoderTolerance = 5;
	public static final double gyroTolerance = 2;
	public static final int cameraQuality = 50;
	
}
