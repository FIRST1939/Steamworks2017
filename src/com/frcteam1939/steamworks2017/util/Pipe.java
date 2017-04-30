package com.frcteam1939.steamworks2017.util;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.wpilibj.vision.VisionPipeline;
	/**
	 * 
	 * @author Paul Case
	 * @author GRIP
	 *
	 */
public class Pipe implements VisionPipeline {
	/*
	 * Quick Vocab Lesson:
	 * rgbThreshold = RedGreenBlue Threshold(pretty self explanatory)
	 * Contours = Shapes in image AKA the targets
	 * Thresholds are in 0-255 format
	 * This code is the processing of the image from the camera
	 */

	//Outputs
	private Mat rgbThresholdOutput = new Mat();
	private ArrayList<MatOfPoint> findContoursOutput = new ArrayList<>();
	private ArrayList<MatOfPoint> filterContoursOutput = new ArrayList<>();

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * This is the primary method that runs the entire pipeline and updates the outputs.
	 */
	@Override
	public void process(Mat source0) {
		// Step RGB_Threshold0:
		Mat rgbThresholdInput = source0;
		double[] rgbThresholdRed = { 0.0, 141.86006825938568 };
		double[] rgbThresholdGreen = { 222.29288933845592, 255.0 };
		double[] rgbThresholdBlue = { 0.0, 255.0 };
		this.rgbThreshold(rgbThresholdInput, rgbThresholdRed, rgbThresholdGreen, rgbThresholdBlue, this.rgbThresholdOutput);
		// Step Find_Contours0:
		Mat findContoursInput = this.rgbThresholdOutput;
		boolean findContoursExternalOnly = false;
		this.findContours(findContoursInput, findContoursExternalOnly, this.findContoursOutput);

		// Filters the Conttours by a:
		ArrayList<MatOfPoint> filterContoursContours = this.findContoursOutput;
		double filterContoursMinArea = 100.0;
		double filterContoursMinPerimeter = 0;
		double filterContoursMinWidth = 0;
		double filterContoursMaxWidth = 1000;
		double filterContoursMinHeight = 0;
		double filterContoursMaxHeight = 1000;
		double[] filterContoursSolidity = { 0, 100 };
		double filterContoursMaxVertices = 1000000;
		double filterContoursMinVertices = 0;
		double filterContoursMinRatio = 0;
		double filterContoursMaxRatio = 1000;
		this.filterContours(filterContoursContours, filterContoursMinArea, filterContoursMinPerimeter, filterContoursMinWidth, filterContoursMaxWidth, filterContoursMinHeight, filterContoursMaxHeight, filterContoursSolidity, filterContoursMaxVertices, filterContoursMinVertices, filterContoursMinRatio, filterContoursMaxRatio, this.filterContoursOutput);
	}

	/**
	 * This method is a generated getter for the output of a Find_Contours.
	 * 
	 * @return ArrayList<MatOfPoint> output from Find_Contours.
	 */
	public ArrayList<MatOfPoint> findContoursOutput() {
		return this.findContoursOutput;
	}

	/**
	 * This method is a generated getter for the output of a Filter_Contours.
	 * 
	 * @return ArrayList<MatOfPoint> output from Filter_Contours.
	 */
	public ArrayList<MatOfPoint> filterContoursOutput() {
		return this.filterContoursOutput;
	}

	public Mat rgbThresholdOutput() {
		return this.rgbThresholdOutput;
	}

	/**
	 * Segment an image based on color ranges.
	 * 
	 * @param input
	 *            The image on which to perform the RGB threshold.
	 * @param red
	 *            The min and max red.
	 * @param green
	 *            The min and max green.
	 * @param blue
	 *            The min and max blue.
	 * @param output
	 *            The image in which to store the output.
	 */
	private void rgbThreshold(Mat input, double[] red, double[] green, double[] blue, Mat out) {
		Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2RGB);
		Core.inRange(out, new Scalar(red[0], green[0], blue[0]), new Scalar(red[1], green[1], blue[1]), out);
	}

	/**
	 * Sets the values of pixels in a binary image to their distance to the nearest black pixel.
	 * 
	 * @param input
	 *            The image on which to perform the Distance Transform.
	 * @param type
	 *            The Transform.
	 * @param maskSize
	 *            the size of the mask.
	 * @param output
	 *            The image in which to store the output.
	 */
	private void findContours(Mat input, boolean externalOnly, List<MatOfPoint> contours) {
		Mat hierarchy = new Mat();
		contours.clear();
		int mode;
		if (externalOnly) {
			mode = Imgproc.RETR_EXTERNAL;
		} else {
			mode = Imgproc.RETR_LIST;
		}
		int method = Imgproc.CHAIN_APPROX_SIMPLE;
		Imgproc.findContours(input, contours, hierarchy, mode, method);
	}

	/**
	 * Filters out contours that do not meet certain criteria.
	 * 
	 * @param inputContours
	 *            is the input list of contours
	 * @param output
	 *            is the the output list of contours
	 * @param minArea
	 *            is the minimum area of a contour that will be kept
	 * @param minPerimeter
	 *            is the minimum perimeter of a contour that will be kept
	 * @param minWidth
	 *            minimum width of a contour
	 * @param maxWidth
	 *            maximum width
	 * @param minHeight
	 *            minimum height
	 * @param maxHeight
	 *            maximimum height
	 * @param Solidity
	 *            the minimum and maximum solidity of a contour
	 * @param minVertexCount
	 *            minimum vertex Count of the contours
	 * @param maxVertexCount
	 *            maximum vertex Count
	 * @param minRatio
	 *            minimum ratio of width to height
	 * @param maxRatio.3
	 *            maximum ratio of width to height
	 */
	private void filterContours(List<MatOfPoint> inputContours, double minArea, double minPerimeter, double minWidth, double maxWidth, double minHeight, double maxHeight, double[] solidity, double maxVertexCount, double minVertexCount, double minRatio, double maxRatio, List<MatOfPoint> output) {
		final MatOfInt hull = new MatOfInt();
		output.clear();
		//operation
		for (int i = 0; i < inputContours.size(); i++) {
			final MatOfPoint contour = inputContours.get(i);
			final Rect bb = Imgproc.boundingRect(contour);
			if (bb.width < minWidth || bb.width > maxWidth) {
				continue;
			}
			if (bb.height < minHeight || bb.height > maxHeight) {
				continue;
			}
			final double area = Imgproc.contourArea(contour);
			if (area < minArea) {
				continue;
			}
			if (Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true) < minPerimeter) {
				continue;
			}
			Imgproc.convexHull(contour, hull);
			MatOfPoint mopHull = new MatOfPoint();
			mopHull.create((int) hull.size().height, 1, CvType.CV_32SC2);
			for (int j = 0; j < hull.size().height; j++) {
				int index = (int) hull.get(j, 0)[0];
				double[] point = new double[] { contour.get(index, 0)[0], contour.get(index, 0)[1] };
				mopHull.put(j, 0, point);
			}
			final double solid = 100 * area / Imgproc.contourArea(mopHull);
			if (solid < solidity[0] || solid > solidity[1]) {
				continue;
			}
			if (contour.rows() < minVertexCount || contour.rows() > maxVertexCount) {
				continue;
			}
			final double ratio = bb.width / (double) bb.height;
			if (ratio < minRatio || ratio > maxRatio) {
				continue;
			}
			output.add(contour);
		}
	}

}