package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.Paths;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DrivePath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class BoilerPegCross extends CommandGroup {

	public BoilerPegCross() {
		System.out.println("12");
		this.addSequential(new DrivePath(Paths.boilerToBoilerPeg));
		System.out.println("13");
		this.addSequential(new PlaceGear());
		System.out.println("14");
		this.addSequential(new DrivePath(Paths.backupToBoilerPeg, true));
		System.out.println("15");
		this.addSequential(new DrivePath(Paths.backupBoilerToOpposite));
		System.out.println("16");
	}
}
