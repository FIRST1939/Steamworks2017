package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.Paths;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DrivePath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterPegBoilerMidField extends CommandGroup {

	public CenterPegBoilerMidField() {
		this.addSequential(new DrivePath(Paths.centerToCenterPeg));
		this.addSequential(new PlaceGear());
		this.addSequential(new ConditionallyDriveAway(Paths.backupBoilerToCenterPeg, Paths.backupCenterToBoilerMidField));
	}
}
