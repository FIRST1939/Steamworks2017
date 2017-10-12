package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.DistanceConstants;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DriveDistance;
import com.frcteam1939.steamworks2017.robot.commands.gearIntake.PlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToCenterPeg extends CommandGroup {

	public DriveToCenterPeg() {
		this.addSequential(new DriveDistance(DistanceConstants.CENTER));
		this.addSequential(new PlaceGear());
	}

}
