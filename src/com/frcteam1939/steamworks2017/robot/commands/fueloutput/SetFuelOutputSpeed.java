package com.frcteam1939.steamworks2017.robot.commands.fueloutput;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetFuelOutputSpeed extends Command {

	private double speed;

	public SetFuelOutputSpeed(double speed) {
		// command requires the fuel Output
		requires(Robot.fuelOutput);
		// setting speed in the SetFuelOutput Class to speed from constructer
		this.speed = speed;
	}

	protected void initialize() {}

	protected void execute() {
		//setting output speed to speed
		Robot.fuelOutput.setOutput(speed);
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {}

	protected void interrupted() {}
}
