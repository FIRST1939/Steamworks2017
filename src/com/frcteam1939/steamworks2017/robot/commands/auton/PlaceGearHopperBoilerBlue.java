package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.Paths;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DrivePath;
import com.frcteam1939.steamworks2017.robot.commands.fueloutput.SetFuelOutputSpeed;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelOutput;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGearHopperBoilerBlue extends CommandGroup {

	public PlaceGearHopperBoilerBlue() {
		this.addSequential(new PlaceGearAndToHopper());
		this.addSequential(new DrivePath(Paths.forwardToBoilerBlue, true));
		this.addSequential(new SetFuelOutputSpeed(FuelOutput.OUT_SPEED));
	}

}
