package com.frcteam1939.steamworks2017.util;

import edu.wpi.first.wpilibj.command.Command;

public class DoNothing extends Command {

	public DoNothing() {}

	@Override
	protected void initialize() {}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}
}
