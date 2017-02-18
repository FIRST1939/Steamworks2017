package com.frcteam1939.steamworks2017.robot.subsystems.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import com.frcteam1939.steamworks2017.robot.Pipeline;
import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
public class VisionMoving {

	static{ 
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

	}
//	Constants for known variables
	static Mat matOriginal;
	public static final double OFFSET_TO_FRONT = 0;
	public static final double CAMERA_WIDTH = 640;
	public static final double DISTANCE_CONSTANT= 5738;
	public static final double WIDTH_BETWEEN_TARGET = 8.5;
	public static boolean shouldRun = true;
	static NetworkTable table;
	
	
	static double lengthBetweenContours;
	static double distanceFromTarget;
	static double lengthError;
	static double[] centerX;
	public static double returnCenterX(Pipeline pipe){
		double[] defaultValue = new double[0];
		// This is the center value returned by GRIP thank WPI
			if(!pipe.filterContoursOutput.isEmpty() && pipe.filterContoursOutput.size() >= 2){
				Rect r = Imgproc.boundingRect(pipe.filterContoursOutput.get(1));
				Rect r1 = Imgproc.boundingRect(pipe.filterContoursOutput.get(0)); 
				centerX = new double[]{r1.x + (r1.width / 2), r.x + (r.width / 2)};
				Imgcodecs.imwrite("output.png", matOriginal);
				//System.out.println(centerX.length); //testing
				// this again checks for the 2 shapes on the target
				if(centerX.length == 2){
					// subtracts one another to get length in pixels
					lengthBetweenContours = Math.abs(centerX[0] - centerX[1]);
				}
			}
		return lengthBetweenContours;
	}
	
	public static double distanceFromTarget(){
		// distance costant divided by length between centers of contours
		distanceFromTarget = DISTANCE_CONSTANT / lengthBetweenContours;
		return distanceFromTarget - OFFSET_TO_FRONT; 
	}
	public static double getAngle(Pipeline pipe){
		// 8.5in is for the distance from center to center from goal, then divide by lengthBetweenCenters in pixels to get proportion
		double constant = WIDTH_BETWEEN_TARGET / lengthBetweenContours;
		double angleToGoal = 0;
			//Looking for the 2 blocks to actually start trig
		if(!pipe.filterContoursOutput.isEmpty() && pipe.filterContoursOutput.size() >= 2){

			if(centerX.length == 2){
				// this calculates the distance from the center of goal to center of webcam 
				double distanceFromCenterPixels= ((centerX[0] + centerX[1]) / 2) - (CAMERA_WIDTH / 2);
				// Converts pixels to inches using the constant from above.
				double distanceFromCenterInch = distanceFromCenterPixels * constant;
				// math brought to you buy Chris and Jones
				angleToGoal = Math.atan(distanceFromCenterInch / distanceFromTarget());
				angleToGoal = Math.toDegrees(angleToGoal);
				// prints angle
				//System.out.println("Angle: " + angleToGoal);
				}
			}
			return angleToGoal;
	}

}
