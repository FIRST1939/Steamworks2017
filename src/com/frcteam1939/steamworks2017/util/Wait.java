package com.frcteam1939.steamworks2017.util;

import edu.wpi.first.wpilibj.command.Command;

public class Wait extends Command {
	// wait time in seconds
	private double timeout;

	public Wait(double timeout) {
		this.timeout = timeout;
	}

	protected void initialize() {
		// sets how long the command will wait in seconds
		this.setTimeout(timeout);
	}

	protected void execute() {}

	protected boolean isFinished() {
		// checking if the wait is finished
		return this.isTimedOut();
	}

	protected void end() {}

	protected void interrupted() {}
}
