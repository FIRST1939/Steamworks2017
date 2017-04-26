package com.frcteam1939.steamworks2017.util;

import java.io.File;

import com.ctre.CANTalon.TrajectoryPoint;
import com.frcteam1939.steamworks2017.robot.Paths;
import com.frcteam1939.steamworks2017.robot.subsystems.Drivetrain;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class MotionProfile {

	private static final double V_THROTTLE = 0.7;
	private static final double A_THROTTLE = 0.7;

	private static final double MAX_V = Drivetrain.MAX_SPEED / 60.0 * V_THROTTLE;
	private static final double MAX_A = Drivetrain.MAX_A * A_THROTTLE;
	private static final double MAX_J = Drivetrain.MAX_JERK * A_THROTTLE;

	private final double period;
	private final Trajectory trajectory;
	private final Trajectory leftTrajectory;
	private final Trajectory rightTrajectory;
	private final double totalTime;
	private final TrajectoryPoint[] left;
	private final TrajectoryPoint[] right;

	public MotionProfile(double[][] waypoints) {
		this(waypoints, false);
	}

	public MotionProfile(double[][] waypoints, boolean backwards) {
		if (backwards) {
			waypoints = Paths.invert(waypoints);
		}
		Waypoint[] points = new Waypoint[waypoints.length];
		for (int i = 0; i < waypoints.length; i++) {
			points[i] = new Waypoint(inchesToRev(waypoints[i][0]), inchesToRev(waypoints[i][1]), waypoints[i][2]);
		}

		this.period = Drivetrain.MP_UPDATE_MS / 1000.0;

		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_QUINTIC, Trajectory.Config.SAMPLES_HIGH, this.period, MAX_V, MAX_A, MAX_J);
		String hash = hash(waypoints, config);

		File file;
		if (System.getProperty("os.name").contains("Linux")) {
			file = new File("/home/lvuser/" + hash + ".traj");
		} else {
			file = new File(hash + ".traj");
		}
		if (file.exists()) {
			this.trajectory = Pathfinder.readFromFile(file);
		} else {
			this.trajectory = Pathfinder.generate(points, config);
			Pathfinder.writeToFile(file, this.trajectory);
		}

		TankModifier modifier = new TankModifier(this.trajectory);
		modifier.modify(inchesToRev(Drivetrain.WHEEL_BASE));
		this.leftTrajectory = modifier.getLeftTrajectory();
		this.rightTrajectory = modifier.getRightTrajectory();

		this.totalTime = this.trajectory.length() * this.period;

		if (backwards) {
			this.left = generatePoints(this.rightTrajectory, false);
			this.right = generatePoints(this.leftTrajectory, true);
		} else {
			this.left = generatePoints(this.leftTrajectory, true);
			this.right = generatePoints(this.rightTrajectory, false);
		}
	}

	public Trajectory getTrajectory() {
		return this.trajectory;
	}

	public Trajectory getLeftTrajectory() {
		return this.leftTrajectory;
	}

	public Trajectory getRightTrajectory() {
		return this.rightTrajectory;
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

	private static TrajectoryPoint[] generatePoints(Trajectory t, boolean invert) {
		TrajectoryPoint[] points = new TrajectoryPoint[t.length()];
		for (int i = 0; i < points.length; i++) {
			Segment s = t.get(i);
			TrajectoryPoint point = new TrajectoryPoint();
			point.position = s.position;
			point.velocity = s.velocity * 60;
			point.timeDurMs = (int) (s.dt * 1000.0);
			point.profileSlotSelect = 1;
			point.velocityOnly = false;
			point.zeroPos = i == 0;
			point.isLastPoint = i == t.length() - 1;

			if (invert) {
				point.position = -point.position;
				point.velocity = -point.velocity;
			}

			points[i] = point;
		}
		return points;
	}

	private static double inchesToRev(double inches) {
		return inches / (Drivetrain.WHEEL_DIAMETER * Math.PI);
	}

	private static String hash(double[][] waypoints, Trajectory.Config config) {
		String s = "";
		s += config.dt + " ";
		s += config.fit.name() + " ";
		s += config.max_velocity + " ";
		s += config.max_acceleration + " ";
		s += config.max_jerk + " ";
		s += config.sample_count + " ";
		for (double[] d : waypoints) {
			s += d[0] + " ";
			s += d[1] + " ";
			s += d[2] + " ";
		}
		return String.valueOf(s.hashCode());
	}

}