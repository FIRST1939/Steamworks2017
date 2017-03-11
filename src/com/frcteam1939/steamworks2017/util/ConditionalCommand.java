package com.frcteam1939.steamworks2017.util;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.command.Command;

public class ConditionalCommand extends Command {

	private BooleanSupplier condition;
	private Command onTrue;
	private Command onFalse;

	private Command runningCommand;

	public ConditionalCommand(BooleanSupplier condition, Command onTrue, Command onFalse) {
		this.condition = condition;
		this.onTrue = onTrue;
		this.onFalse = onFalse;
	}

	@Override
	protected void initialize() {
		if (this.condition.getAsBoolean()) {
			this.runningCommand = this.onTrue;
		} else {
			this.runningCommand = this.onFalse;
		}
		this.runningCommand.start();
	}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return !this.runningCommand.isRunning();
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}
}
