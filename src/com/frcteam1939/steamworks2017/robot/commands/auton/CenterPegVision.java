package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.Paths;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DriveDistance;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DrivePath;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.LineupVision;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterPegVision extends CommandGroup {

	public CenterPegVision() {
		this.addSequential(new DrivePath(Paths.centerToCenterVision));
		this.addSequential(new LineupVision());
		this.addSequential(new DriveDistance(50));
	}

}
