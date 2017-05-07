package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.DistanceConstants;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DriveDistance;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.SetLeftVoltage;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.SetRightVoltage;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToCenterPeg extends CommandGroup {

	public DriveToCenterPeg() {
		this.addSequential(new SetRightVoltage(12));
		this.addSequential(new SetLeftVoltage(0));
		this.addSequential(new DriveDistance(DistanceConstants.CENTER));
		this.addSequential(new PlaceGear());
	}

}
