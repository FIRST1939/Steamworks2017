package com.frcteam1939.steamworks2017.util;

import com.ctre.CANTalon.TrajectoryPoint;
import com.frcteam1939.steamworks2017.robot.subsystems.Drivetrain;

import usfirst.frc.team2168.robot.FalconPathPlanner;

public class MotionProfile {

	private final int period;
	private final FalconPathPlanner plan;
	private final double totalTime;
	private final TrajectoryPoint[] left;
	private final TrajectoryPoint[] right;

	public MotionProfile(double[][] waypoints, double time, boolean backwards) {
		for (int i = 0; i < waypoints.length; i++) {
			for (int j = 0; j < waypoints[i].length; j++) {
				waypoints[i][j] = inchesToRev(waypoints[i][j]);
			}
		}
		this.period = Drivetrain.MP_UPDATE_MS;
		this.plan = new FalconPathPlanner(waypoints);
		this.plan.calculate(time, this.period / 1000.0, inchesToRev(Drivetrain.WHEEL_BASE));

		double[] lp = normalizePositions(this.plan.leftPath);
		double[] lv = normalizeVelocities(this.plan.smoothLeftVelocity);
		double[] rp = normalizePositions(this.plan.rightPath);
		double[] rv = normalizeVelocities(this.plan.smoothRightVelocity);

		this.totalTime = lp.length * this.period / 1000.0;

		if (backwards) {
			this.left = this.generatePoints(lp, lv, false);
			this.right = this.generatePoints(rp, rv, true);
		} else {
			this.left = this.generatePoints(lp, lv, true);
			this.right = this.generatePoints(rp, rv, false);
		}
	}

	public FalconPathPlanner getPlan() {
		return this.plan;
	}

	public double getTotalTime() {
		return this.totalTime;
	}

	public TrajectoryPoint[] getLeftPoints() {
		return this.left;
	}

	public TrajectoryPoint[] getRightPoints() {
		return this.right;
	}

	private TrajectoryPoint[] generatePoints(double[] positions, double[] velocities, boolean invert) {
		TrajectoryPoint[] points = new TrajectoryPoint[positions.length];
		for (int i = 0; i < points.length; i++) {
			TrajectoryPoint point = new TrajectoryPoint();
			point.position = positions[i];
			point.velocity = velocities[i] * 60;
			point.timeDurMs = this.period;
			point.profileSlotSelect = 1;
			point.velocityOnly = false;
			point.zeroPos = i == 0;
			point.isLastPoint = i == positions.length - 1;

			if (invert) {
				point.position = -point.position;
				point.velocity = -point.velocity;
			}

			points[i] = point;
		}
		return points;
	}

	private static double[] normalizePositions(double[][] p) {
		double[] n = new double[p.length];
		for (int i = 0; i < n.length; i++) {
			n[i] = calcDistance(p, i);
		}
		return n;
	}

	private static double[] normalizeVelocities(double[][] v) {
		double[] n = new double[v.length];
		for (int i = 0; i < n.length; i++) {
			n[i] = v[i][1];
		}
		return n;
	}

	private static double inchesToRev(double inches) {
		return inches / (Drivetrain.WHEEL_DIAMETER * Math.PI);
	}

	private static double calcDistance(double[][] path, double term) {
		double sum = 0;
		for (int i = 1; i <= term; i++) {
			sum += Math.sqrt(Math.pow(path[i][0] - path[i - 1][0], 2) + Math.pow(path[i][1] - path[i - 1][1], 2));
		}
		return sum;
	}

}