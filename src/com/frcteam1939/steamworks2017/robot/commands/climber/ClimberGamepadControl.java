package com.frcteam1939.steamworks2017.robot.commands.climber;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberGamepadControl extends Command {

	private static final double DEAD_BAND = 0.1;

	public ClimberGamepadControl() {
		// making a climber required in order to run the command
		this.requires(Robot.climber);
	}

	@Override
	protected void initialize() {}

	@Override
	protected void execute() {
		// getting move value from Y axis on gamepad
		double move = Robot.oi.gamepad.getRightY();
		// checking if move value is slightly off
		if (Math.abs(move) < DEAD_BAND || move > 0) {
			// if getting value due to skew, sets move value to 0
			move = 0;
		}
		// setting the climber spin to move
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
