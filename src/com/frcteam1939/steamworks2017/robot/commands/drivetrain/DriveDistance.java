package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveDistance extends CommandGroup {

	public DriveDistance(double distance) {
		boolean backwards = distance < 0;
		distance = Math.abs(distance);
		double[][] waypoints = { { 0, 0, 0 }, { distance, 0, 0 } };
		this.addSequential(new DrivePath(waypoints, backwards));
	}

}
