package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.Paths;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DrivePath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class BoilerPegBackup extends CommandGroup {

	public BoilerPegBackup() {
		this.addSequential(new DrivePath(Paths.boilerToBoilerPeg));
		this.addSequential(new PlaceGear());
		this.addSequential(new Backup());
	}
}
