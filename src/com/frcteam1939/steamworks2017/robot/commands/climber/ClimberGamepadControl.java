package com.frcteam1939.steamworks2017.robot.commands.climber;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberGamepadControl extends Command {

	private static final double DEAD_BAND = 0.1;

	public ClimberGamepadControl() {
		this.requires(Robot.climber);
	}

	@Override
	protected void initialize() {}

	@Override
	protected void execute() {
		double move = Robot.oi.gamepad.getRightY();
		if (Math.abs(move) < DEAD_BAND || move > 0) {
			move = 0;
		}
		Robot.climber.setOutput(move);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}
}
