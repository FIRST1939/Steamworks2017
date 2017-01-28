package com.frcteam1939.steamworks2017.robot.commands.gearIntake;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GearRampIn extends Command {

	public GearRampIn() {
		this.requires(Robot.gearIntake);
	}

	@Override
	protected void initialize() {}

	@Override
	protected void execute() {
		Robot.gearIntake.rampIn();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}
}
