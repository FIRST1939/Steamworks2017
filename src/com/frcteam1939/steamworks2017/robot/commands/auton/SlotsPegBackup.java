package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.DistanceConstants;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SlotsPegBackup extends CommandGroup {

	public SlotsPegBackup() {
		this.addSequential(new DriveToSlotsPeg());
		this.addSequential(new DriveDistance(DistanceConstants.BACKUP));
	}
}
