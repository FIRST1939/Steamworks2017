package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DriveDistance;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DrivePath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGearAndBackup extends CommandGroup {

	public PlaceGearAndBackup(double[][] path) {
		this.addSequential(new DrivePath(path));
		this.addSequential(new PlaceGear());
		this.addSequential(new DriveDistance(-36));
	}
}
