package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class BrakeDown extends Command {

	public BrakeDown() {}

	@Override
	protected void initialize() {}

	@Override
	protected void execute() {
		Robot.drivetrain.brakeDown();
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
