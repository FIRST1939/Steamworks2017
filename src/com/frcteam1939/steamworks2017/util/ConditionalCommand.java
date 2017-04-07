package com.frcteam1939.steamworks2017.util;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.command.Command;

public class ConditionalCommand extends edu.wpi.first.wpilibj.command.ConditionalCommand {

	private BooleanSupplier condition;

	public ConditionalCommand(BooleanSupplier condition, Command onTrue, Command onFalse) {
		super(onTrue, onFalse);
		this.condition = condition;
	}

	@Override
	protected boolean condition() {
		return this.condition.getAsBoolean();
	}
}
