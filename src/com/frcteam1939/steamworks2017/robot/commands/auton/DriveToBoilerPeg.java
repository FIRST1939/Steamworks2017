package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.DistanceConstants;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToBoilerPeg extends CommandGroup {

	public DriveToBoilerPeg() {
		this.addSequential(new DriveDistance(DistanceConstants.BOILER_FIRST));
		this.addSequential(new TurnByAlliance(DistanceConstants.BOILER_RED_ANGLE, DistanceConstants.BOILER_BLUE_ANGLE));
		this.addSequential(new DriveDistance(DistanceConstants.BOILER_SECOND));
		this.addSequential(new PlaceGear());
	}

}
