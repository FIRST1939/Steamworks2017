package com.frcteam1939.steamworks2017.robot.subsystems;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import com.frcteam1939.steamworks2017.util.Pipe;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 *
 */
public class CameraNT extends Subsystem {
	private UsbCamera cam;
	private static boolean intialized = false;
	private static Pipe pipe = new Pipe();
	//set these to the Image width and Height of the camera in pixels (ie 320Pixels by 240 pixels)
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	/**	To calculate the distance constant, choose 5 distances for the robot to sit 
	 * (12, 24, 48, 60, 72 in). Move your robot to each of these distances and record
	 *  the variable lengthBetweenContours, then write that down. Multiply the distance
	 *  and lengthBetweenContours and write what you get down. After you do that for 
	 *  all of the values, average everything and that's the distance constant.
	 */
	private static final double DISTANCE_CONSTANT = 2635.2;
	private static double lengthBetweenContours;
	private static NetworkTable centerX;
	private static double angle;
	private static double contours;
	private static double distance;
	public final static Object imgLock = new Object();
    public CameraNT(){
    	cam = CameraServer.getInstance().startAutomaticCapture();
		cam.setResolution(IMG_WIDTH, IMG_HEIGHT);
		cam.setBrightness(10);
		centerX = NetworkTable.getTable("GRIP/myContoursReport");
    }
}