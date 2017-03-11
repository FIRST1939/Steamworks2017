package com.frcteam1939.steamworks2017.util;

import edu.wpi.first.wpilibj.command.Command;

public class RunCode extends Command {

	private Runnable run;

	public RunCode(Runnable run) {
		this.run = run;
	}

	@Override
	protected void initialize() {
		this.run.run();
	}

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
