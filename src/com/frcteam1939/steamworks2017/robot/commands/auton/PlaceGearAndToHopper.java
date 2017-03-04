package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.Paths;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DrivePath;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.TurnToAngle;
import com.frcteam1939.steamworks2017.util.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGearAndToHopper extends CommandGroup {

	public PlaceGearAndToHopper() {
		this.addSequential(new DrivePath(Paths.boilerToBoilerPeg)); // 3.3s
		this.addSequential(new PlaceGear()); // 0.5s
		this.addSequential(new DrivePath(Paths.hopperToBoilerPeg, true)); // 2.84s
		this.addSequential(new Wait(3.0)); // Wait for fuel to enter our hopper
		this.addSequential(new DrivePath(Paths.hopperToForward)); // 1.56s
		this.addSequential(new TurnToAngle(-90)); // 3s
	}
}
