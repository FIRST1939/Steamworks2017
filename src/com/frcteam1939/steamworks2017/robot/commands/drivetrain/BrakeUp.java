package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class BrakeUp extends Command {

	public BrakeUp() {}

	@Override
	protected void initialize() {}

	@Override
	protected void execute() {
		Robot.drivetrain.brakeUp();
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
