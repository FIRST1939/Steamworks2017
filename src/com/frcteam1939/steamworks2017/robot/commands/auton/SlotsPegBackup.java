package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.Paths;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DrivePath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SlotsPegBackup extends CommandGroup {

	public SlotsPegBackup() {
		this.addSequential(new DrivePath(Paths.slotsToSlotsPeg));
		this.addSequential(new PlaceGear());
		this.addSequential(new DrivePath(Paths.backupToSlotsPeg, true));
	}
}
