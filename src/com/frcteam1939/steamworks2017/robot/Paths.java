package com.frcteam1939.steamworks2017.robot;

public class Paths {

	private static final double FIELD_LENGTH = 652.0;

	private static final double[] boilerStart = { 8.688, 56, 0 };
	private static final double[] boilerStartLeadup = { 15, 56, 0 };
	private static final double[] boilerMidField = { 120, 70, 0 };

	private static final double[] boilerPeg = { 114.5, 101.5, Math.toRadians(60) };
	private static final double[] boilerPegLeadup = { boilerPeg[0] + 15 * Math.cos(boilerPeg[2] + Math.PI), boilerPeg[1] + 15 * Math.sin(boilerPeg[2] + Math.PI), boilerPeg[2] };
	private static final double[] boilerPegBackup = { 60, 70, 0 };

	private static final double[] slotsStart = { 8.688, 267, 0 };
	private static final double[] slotsStartLeadup = { 15, 267, 0 };
	private static final double[] slotsMidField = { 120, 254, 0 };

	private static final double[] slotsPeg = { 118.25 + 21 * Math.cos(5 * Math.PI / 6) + 30 * Math.cos(5 * Math.PI / 3), 217.25 + 21 * Math.sin(5 * Math.PI / 6) + 30 * Math.sin(5 * Math.PI / 3), Math.toRadians(300) };
	private static final double[] slotsPegLeadup = { slotsPeg[0] + 15 * Math.cos(slotsPeg[2] + Math.PI), slotsPeg[1] + 15 * Math.sin(slotsPeg[2] + Math.PI), slotsPeg[2] };
	private static final double[] slotsPegBackup = { 60, 254, 0 };

	private static final double[] slotsPegRed = { 118.25 + 28 * Math.cos(5 * Math.PI / 6) + 35 * Math.cos(5 * Math.PI / 3), 217.25 + 28 * Math.sin(5 * Math.PI / 6) + 35 * Math.sin(5 * Math.PI / 3), Math.toRadians(300) };
	private static final double[] slotsPegLeadupRed = { slotsPegRed[0] + 15 * Math.cos(slotsPegRed[2] + Math.PI), slotsPegRed[1] + 15 * Math.sin(slotsPegRed[2] + Math.PI), slotsPegRed[2] };

	private static final double[] centerStart = { 8.688, 162.25, 0 };
	private static final double[] centerStartLeadup = { 15, 162.25, 0 };

	private static final double[] centerPeg = { 90, 162.25, 0 };
	private static final double[] centerPegLeadup = { centerPeg[0] - 15, 162.25, 0 };
	private static final double[] centerPegBackup = { 60, 162.25, 0 };
	private static final double[] centerPegSlotsBackup = { 35, 140, Math.toRadians(75) };
	private static final double[] centerPegBoilerBackup = { 35, 184, Math.toRadians(-75) };

	private static final double[] oppositeSlots = { 407.5, 267, 0 };

	public static final double[][] boilerToBoilerPeg = { boilerStart, boilerStartLeadup, boilerPegLeadup, boilerPeg };
	public static final double[][] slotsToSlotsPeg = { slotsStart, slotsStartLeadup, slotsPegLeadup, slotsPeg };
	public static final double[][] centerToCenterPeg = { centerStart, centerStartLeadup, centerPegLeadup, centerPeg };

	public static final double[][] backupToBoilerPeg = { boilerPegBackup, boilerPegLeadup, boilerPeg };
	public static final double[][] backupToSlotsPeg = { slotsPegBackup, slotsPegLeadup, slotsPeg };
	public static final double[][] backupToBoilerMidField = { boilerPegBackup, boilerMidField };
	public static final double[][] backupToSlotsMidField = { slotsPegBackup, slotsMidField };
	public static final double[][] backupBoilerToOpposite = { boilerPegBackup, boilerMidField, oppositeSlots };
	public static final double[][] backupSlotsToOpposite = { slotsPegBackup, oppositeSlots };
	public static final double[][] backupToCenterPeg = { centerPegBackup, centerPeg };
	public static final double[][] backupSlotsToCenterPeg = { centerPegSlotsBackup, centerPegLeadup, centerPeg };
	public static final double[][] backupBoilerToCenterPeg = { centerPegBoilerBackup, centerPegLeadup, centerPeg };
	public static final double[][] backupCenterToSlotsMidField = { centerPegSlotsBackup, slotsMidField };
	public static final double[][] backupCenterToBoilerMidField = { centerPegBoilerBackup, boilerMidField };
	public static final double[][] backupCenterToSlotsCross = { centerPegSlotsBackup, slotsMidField, oppositeSlots };
	public static final double[][] backupCenterToBoilerCross = { centerPegBoilerBackup, boilerMidField, oppositeSlots };

	public static final double[][] redSlotsToSlotsPeg = { slotsStart, slotsStartLeadup, slotsPegLeadupRed, slotsPegRed };
	public static final double[][] redBackupToSlotsPeg = { slotsPegBackup, slotsPegLeadupRed, slotsPegRed };

	public static final double[][] backup = { { 0, 0, 0 }, { 24, 0, 0 } };

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