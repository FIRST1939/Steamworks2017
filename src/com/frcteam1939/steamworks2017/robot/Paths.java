package com.frcteam1939.steamworks2017.robot;

public class Paths {

	private static final double FIELD_LENGTH = 652.0;

	public static final double[][] boilerToBoilerPeg = { { 17.5, 55.5, 0 }, { 50, 55.5, 0 }, { 107.708, 87.438, Math.toRadians(60.6) }, { 123.66, 115.753, Math.toRadians(60.6) } };
	public static final double[][] slotsToSlotsPeg = { { 17.5, 266.5, 0 }, { 50, 266.5, 0 }, { 107.708, 235.562, Math.toRadians(299.4) }, { 123.66, 207.247, Math.toRadians(299.4) } };
	public static final double[][] centerToCenterPeg = { { 17.5, 161.5, 0 }, { 97.0, 161.5, 0 } };
	public static final double[][] boilerToSlots = { { 17.5, 55.5, 0 }, { 132.5, 55.5, 0 }, { 413, 266.5, 0 } };
	public static final double[][] slotsToSlots = { { 17.5, 266.5, 0 }, { 413, 266.5, 0 } };

	public static double[][] flip(double[][] path) {
		double[][] n = new double[path.length][path[0].length];
		for (int i = 0; i < path.length; i++) {
			n[i][0] = FIELD_LENGTH - path[i][0];
			n[i][1] = path[i][1];
			n[i][2] = path[i][2] > 0 ? Math.PI - path[i][2] : -Math.PI - path[i][2];
		}
		return n;
	}

	public static double[][] invert(double[][] path) {
		double[][] n = new double[path.length][path[0].length];
		for (int i = 0; i < n.length; i++) {
			double[] d = path[path.length - 1 - i];
			n[i][0] = d[0];
			n[i][1] = d[1];
			n[i][2] = Math.PI + d[2];
		}
		return n;
	}

}