package com.frcteam1939.steamworks2017.robot.commands.gearIntake;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GearRampIn extends Command {

	public GearRampIn() {
		//requires a gear intake to run
		this.requires(Robot.gearIntake);
	}

	@Override
	protected void initialize() {}

	@Override
	protected void execute() {
		// closes ramp
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
