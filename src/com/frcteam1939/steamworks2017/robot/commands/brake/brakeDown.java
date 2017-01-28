package com.frcteam1939.steamworks2017.robot.commands.brake;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class BrakeDown extends Command {

	public BrakeDown() {
		requires(Robot.brake);

	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.brake.BrakeDown();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
