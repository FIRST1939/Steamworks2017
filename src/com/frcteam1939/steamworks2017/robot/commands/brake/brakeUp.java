package com.frcteam1939.steamworks2017.robot.commands.brake;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class brakeUp extends Command {

	public brakeUp() {
		requires(Robot.brake);

	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.brake.brakeUp();
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
