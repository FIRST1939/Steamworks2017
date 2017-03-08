package com.frcteam1939.steamworks2017.robot.vision;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class Vision {

	private static boolean intialized = false;

	private static Pipe pipe = new Pipe();
	private static final int IMG_WIDTH = 640;
	private static final int IMG_HEIGHT = 480;
	private static double centerX;
	private static double angle;
	private static double contours;
	private static double distance;
	private final static Object imgLock = new Object();

	public static void init() {
		if (!intialized) {
			UsbCamera cam = CameraServer.getInstance().startAutomaticCapture();
			cam.setResolution(IMG_WIDTH, IMG_HEIGHT);
			cam.setBrightness(10);
			new VisionThread(cam, pipe, pipeline -> {
				if (pipeline.filterContoursOutput().size() == 2) {
					Rect r = Imgproc.boundingRect(pipe.filterContoursOutput().get(0));
					Rect r1 = Imgproc.boundingRect(pipe.filterContoursOutput().get(1));
					double center = (r.x + r.x + r1.x + r1.width) / 2 - IMG_WIDTH / 2;
					//finding the angle
					double constant = 8.5 / Math.abs(r.x - (r1.x + r1.width));
					double angleToGoal = 0;
					//Looking for the 2 blocks to actually start trig
					if (pipeline.filterContoursOutput().size() == 2) {

						// this calculates the distance from the center of goal to center of webcam
						double distanceFromCenterPixels = center;
						// Converts pixels to inches using the constant from above.
						double distanceFromCenterInch = distanceFromCenterPixels * constant;
						angleToGoal = Math.atan(distanceFromCenterInch / (5738 / Math.abs(r.x - (r1.x + r1.width))));
						angleToGoal = Math.toDegrees(angleToGoal);
					}

					synchronized (imgLock) {
						centerX = center;
						angle = angleToGoal;
						contours = pipe.filterContoursOutput().size();
						distance = 5738 / Math.abs(r.x - (r1.x + r1.width));
					}
				}

			}).start();
			intialized = true;
		}
	}

	public static double getAngle() {
		synchronized (imgLock) {
			return angle;
		}
	}

	public static double getDistance() {
		synchronized (imgLock) {
			return distance;
		}
	}

}
