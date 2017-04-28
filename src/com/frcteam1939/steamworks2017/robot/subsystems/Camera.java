package com.frcteam1939.steamworks2017.robot.subsystems;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import com.frcteam1939.steamworks2017.robot.vision.Pipe;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 *
 */
public class Camera extends Subsystem {
	
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
	private static double centerX;
	private static double angle;
	private static double contours;
	private static double distance;
	private UsbCamera cam;
	public final static Object imgLock = new Object();
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public Camera(){
    	//Sets Camera to the first camera it finds
    	cam = CameraServer.getInstance().startAutomaticCapture();
		//setting the resolution to the image width and length(see above)
		cam.setResolution(IMG_WIDTH, IMG_HEIGHT);
		// setting the brightness of the camera to 10%
		cam.setBrightness(10);
    }
    /**
     * Vision processing thread
     */
    public void visionInit(){
    	if (!intialized) {
			
			
			new VisionThread(cam, pipe, pipeline -> {
				// checks if it only sees 2 targets
				if (pipeline.filterContoursOutput().size() == 2) {
					//creates first target rectangle
					Rect r = Imgproc.boundingRect(pipe.filterContoursOutput().get(0));
					//creates 2nd rectangle
					Rect r1 = Imgproc.boundingRect(pipe.filterContoursOutput().get(1));
					/*finding the center between the two targets by finding the absolute value 
					*of the center of the first rectanlge minus the center of the second rectangle then
					*subtracting it all by half of the image. 0 would be the center, negitives would err left
					*and positiveswould err right. this is all measured in pixels btw.
					*/ 
					double center = (r.x + r.x + r1.x + r1.width) / 2 - IMG_WIDTH / 2;
					/* Finds the length(in pixels) between the two rectangles by finding the absolute value 
					*of the center of the first rectanlge minus the center of the second rectangle					 * 
					 */
					double LBC = Math.abs(r.x + r.width / 2 - (r1.x + r1.width / 2));
					//finding the angle
					//finds the ratio of inches to pixels
					double constant = 8.5 / Math.abs(r.x - (r1.x + r1.width));
					//resets the angle
					double angleToGoal = 0;
					//Looking for the 2 blocks to actually start trig
					if (pipeline.filterContoursOutput().size() == 2) {
						// this calculates the distance from the center of goal to center of webcam
						double distanceFromCenterPixels = center;
						// Converts pixels to inches using the constant from above.
						double distanceFromCenterInch = distanceFromCenterPixels * constant;
						//finds the angle in radians
						angleToGoal = Math.atan(distanceFromCenterInch / (DISTANCE_CONSTANT / Math.abs(r.x - (r1.x + r1.width))));
						// convertsitinto degrees
						angleToGoal = Math.toDegrees(angleToGoal);
					}

					synchronized (imgLock) {
						//setting the variables
						centerX = center;
						lengthBetweenContours = LBC;
						angle = angleToGoal;
						contours = pipe.filterContoursOutput().size();
						//finds the distance by taking the distance constant(see explanation above) and dividing it by the length between contours
						distance =  DISTANCE_CONSTANT/ Math.abs(r.x - (r1.x + r1.width));
					}
				}

			}).start();
			intialized = true;
		}
    }
    /**
	 * Return the number of targets it detects AKA the number of contours
	 * @return contours
	 */
	public static double getContours(){
		synchronized (imgLock){
			return contours;
		}
	}
	/**
	 * returns the length in pixels between the two targets
	 * @return length between contours(in pixels)
	 */
	public static double getLengthBetweenContours(){
		synchronized (imgLock){
			return lengthBetweenContours;
		}
	}
	/**
	 * Returns the center of the two targets, center = 0; neg = left; pos = right
	 * @return center
	 */
	public static double getCenterX(){
		synchronized (imgLock) {
			return centerX;
		}
	}
	/**
	 * Returns the angle in degrees
	 * @return The angle
	 */
	public static double getAngle() {
		synchronized (imgLock) {
			return angle;
		}
	}
	/** 
	 * Returns the distance to the target in inches
	 * @return distance
	 */
	public static double getDistance() {
		synchronized (imgLock) {
			return distance;
		}
	}
}

