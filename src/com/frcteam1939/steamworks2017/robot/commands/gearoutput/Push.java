package com.frcteam1939.steamworks2017.robot.commands.gearoutput;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Push extends Command {

	public Push() {
		this.requires(Robot.gearOutput);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.gearOutput.push();
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
