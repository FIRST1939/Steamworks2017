package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.Paths;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DrivePath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterPegSlotsCross extends CommandGroup {

	public CenterPegSlotsCross() {
		this.addSequential(new DrivePath(Paths.centerToCenterPeg));
		this.addSequential(new PlaceGear());
		this.addSequential(new ConditionallyDriveAway(Paths.backupSlotsToCenterPeg, Paths.backupCenterToSlotsCross));
	}
}
