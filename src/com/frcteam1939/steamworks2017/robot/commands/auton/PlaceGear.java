package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.commands.fuelintake.SetFuelIntakeSpeed;
import com.frcteam1939.steamworks2017.robot.commands.gearoutput.PushOutGear;
import com.frcteam1939.steamworks2017.robot.commands.gearoutput.RetractGearPusher;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelIntake;
import com.frcteam1939.steamworks2017.util.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGear extends CommandGroup {

	public PlaceGear() {
		this.addSequential(new SetFuelIntakeSpeed(FuelIntake.IN_SPEED));
		this.addSequential(new PushOutGear());
		this.addSequential(new Wait(0.5));
		this.addSequential(new RetractGearPusher());
		this.addSequential(new SetFuelIntakeSpeed(0));
	}
}
