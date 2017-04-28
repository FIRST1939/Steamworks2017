package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.DistanceConstants;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToSlotsPeg extends CommandGroup {

	public DriveToSlotsPeg() {
		this.addSequential(new DriveDistance(DistanceConstants.SLOTS_FIRST));
		this.addSequential(new TurnByAlliance(DistanceConstants.SLOTS_RED_ANGLE, DistanceConstants.SLOTS_BLUE_ANGLE));
		this.addSequential(new DriveDistance(DistanceConstants.SLOTS_SECOND));
		this.addSequential(new PlaceGear());
	}
}
