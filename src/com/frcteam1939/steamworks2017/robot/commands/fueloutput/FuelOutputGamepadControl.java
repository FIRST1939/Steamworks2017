package com.frcteam1939.steamworks2017.robot.commands.fueloutput;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class FuelOutputGamepadControl extends Command {

	public FuelOutputGamepadControl() {
		requires(Robot.fuelOutput);
	}

	protected void initialize() {}

	protected void execute() {

	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {}

	protected void interrupted() {}
}
