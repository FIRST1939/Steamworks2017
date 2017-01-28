package com.frcteam1939.steamworks2017.robot.commands.brake;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class BrakeUp extends Command {

	public BrakeUp() {
		requires(Robot.brake);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.brake.up();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
