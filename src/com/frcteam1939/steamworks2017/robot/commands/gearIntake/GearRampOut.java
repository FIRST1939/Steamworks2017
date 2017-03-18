package com.frcteam1939.steamworks2017.robot.commands.gearIntake;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GearRampOut extends Command {

	public GearRampOut() {
		//requires a gear intake
		this.requires(Robot.gearIntake);
	}

	@Override
	protected void initialize() {}

	@Override
	protected void execute() {
		// opens ramp
		Robot.gearIntake.rampOut();
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
