package org.usfirst.frc.team2785.robot.subsystems;

import org.usfirst.frc.team2785.robot.RobotMap;
import org.usfirst.frc.team2785.robot.commands.Nothing;
import org.usfirst.frc.team2785.robot.commands.TeleopCamera;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class CameraMount extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static USBCamera camera;

    public CameraMount(USBCamera cam) {
        //horizontalServo = horizontal;
        //verticalServo = vertical;
        camera = cam;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new TeleopCamera());
    }

    public void startServer() {
        CameraServer server = CameraServer.getInstance();
        server.setQuality(RobotMap.CAMERA_QUALITY);
        server.startAutomaticCapture(camera);
    }

    public void stopServer() {
        CameraServer server = CameraServer.getInstance();
        server.startAutomaticCapture(camera);
    }

}
