package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DrivePath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGearAndCross extends CommandGroup {

	public PlaceGearAndCross(double[][] place, double[][] cross) {
		this.addSequential(new DrivePath(place));
		this.addSequential(new PlaceGear());
		this.addSequential(new DrivePath(place, true));
		this.addSequential(new DrivePath(cross));
	}
}
