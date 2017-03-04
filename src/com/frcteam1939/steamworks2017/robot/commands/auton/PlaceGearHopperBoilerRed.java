package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.Paths;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DrivePath;
import com.frcteam1939.steamworks2017.robot.commands.fueloutput.SetFuelOutputSpeed;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelOutput;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGearHopperBoilerRed extends CommandGroup {

	public PlaceGearHopperBoilerRed() {
		this.addSequential(new PlaceGearAndToHopper()); // 8.2s + turn and wait
		this.addSequential(new DrivePath(Paths.forwardToBoilerRed)); // 2.32s
		this.addSequential(new SetFuelOutputSpeed(FuelOutput.OUT_SPEED));
	}

}
