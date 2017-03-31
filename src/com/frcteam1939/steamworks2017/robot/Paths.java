package com.frcteam1939.steamworks2017.robot;

public class Paths {

	private static final double FIELD_LENGTH = 652.0;

	private static final double[] boilerStart = { 8.688, 56, 0 };
	private static final double[] boilerStartLeadup = { 35, 56, 0 };
	private static final double[] boilerMidField = { 120, 76.392, 0 };

	private static final double[] boilerPeg = { 116.3, 111.5, Math.toRadians(70) };
	private static final double[] boilerPegLeadup = { boilerPeg[0] + 20 * Math.cos(boilerPeg[2] + Math.PI), boilerPeg[1] + 20 * Math.sin(boilerPeg[2] + Math.PI), boilerPeg[2] };
	private static final double[] boilerPegBackup = { 60, 76.392, 0 };

	private static final double[] slotsStart = { 8.688, 267, 0 };
	private static final double[] slotsStartLeadup = { 15, 267, 0 };
	private static final double[] slotsMidField = { 120, 248.108, 0 };

	private static final double[] slotsPeg = { 116.3, 216.5, Math.toRadians(300) };
	private static final double[] slotsPegLeadup = { slotsPeg[0] + 20 * Math.cos(slotsPeg[2] + Math.PI), slotsPeg[1] + 20 * Math.sin(slotsPeg[2] + Math.PI), slotsPeg[2] };
	private static final double[] slotsPegBackup = { 60, 248.108, 0 };

	private static final double[] centerStart = { 8.688, 162.25, 0 };
	private static final double[] centerStartLeadup = { 35, 162.25, 0 };

	private static final double[] centerPeg = { 86, 162.25, 0 };
	private static final double[] centerPegLeadup = { centerPeg[0] - 50, 162.25, 0 };
	private static final double[] centerPegBackup = { 60, 162.25, 0 };
	private static final double[] centerPegSlotsBackup = { 35, 140, Math.toRadians(75) };
	private static final double[] centerPegBoilerBackup = { 35, 184, Math.toRadians(-75) };

	private static final double[] oppositeSlots = { 416.5, 285, 0 };

	private static final double[] hopper = { 91.5, 27.313, Math.toRadians(-90) };
	private static final double[] hopperBlueBackup = { 21.5, 100, Math.toRadians(-90) };
	private static final double[] hopperRedBackup = { 150, 60, Math.toRadians(180) };
	private static final double[] goalBlue = { 26.362, 38.378, Math.toRadians(310) };
	private static final double[] goalBlueLeadup = { 21.5, 50, Math.toRadians(-90) };
	private static final double[] goalRed = { 38.971, 26.424, Math.toRadians(130) };
	private static final double[] goalRedLeadup = { 50, 21.5, Math.toRadians(180) };

	public static final double[][] boilerToBoilerPeg = { boilerStart, boilerStartLeadup, boilerPegLeadup, boilerPeg };
	public static final double[][] boilerToBoilerVision = { boilerStart, boilerStartLeadup, boilerPegLeadup };
	public static final double[][] slotsToSlotsPeg = { slotsStart, slotsStartLeadup, slotsPegLeadup, slotsPeg };
	public static final double[][] slotsToSlotsVision = { slotsStart, slotsStartLeadup, slotsPegLeadup };
	public static final double[][] centerToCenterPeg = { centerStart, centerStartLeadup, centerPegLeadup, centerPeg };
	public static final double[][] centerToCenterVision = { centerStart, centerStartLeadup, centerPegLeadup };

	public static final double[][] backupToBoilerPeg = { boilerPegBackup, boilerPegLeadup, boilerPeg };
	public static final double[][] backupToSlotsPeg = { slotsPegBackup, slotsPegLeadup, slotsPeg };
	public static final double[][] backupToBoilerMidField = { boilerPegBackup, boilerMidField };
	public static final double[][] backupToSlotsMidField = { slotsPegBackup, slotsMidField };
	public static final double[][] backupBoilerToOpposite = { boilerPegBackup, boilerMidField, oppositeSlots };
	public static final double[][] backupSlotsToOpposite = { slotsPegBackup, slotsMidField, oppositeSlots };
	public static final double[][] backupToCenterPeg = { centerPegBackup, centerPeg };
	public static final double[][] backupSlotsToCenterPeg = { centerPegSlotsBackup, centerPegLeadup, centerPeg };
	public static final double[][] backupBoilerToCenterPeg = { centerPegBoilerBackup, centerPegLeadup, centerPeg };
	public static final double[][] backupCenterToSlotsMidField = { centerPegSlotsBackup, slotsMidField };
	public static final double[][] backupCenterToBoilerMidField = { centerPegBoilerBackup, boilerMidField };
	public static final double[][] backupCenterToSlotsCross = { centerPegSlotsBackup, slotsMidField, oppositeSlots };
	public static final double[][] backupCenterToBoilerCross = { centerPegBoilerBackup, boilerMidField, oppositeSlots };

	public static final double[][] backupToHopper = { boilerPegBackup, hopper };
	public static final double[][] blueBackupToHopper = { hopperBlueBackup, hopper };
	public static final double[][] redBackupToHopper = { hopperRedBackup, hopper };
	public static final double[][] backupToBlueGoal = { hopperBlueBackup, goalBlueLeadup, goalBlue };
	public static final double[][] backupToRedGoal = { hopperRedBackup, goalRedLeadup, goalRed };

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