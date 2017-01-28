package com.frcteam1939.steamworks2017.util;

import java.util.function.DoubleSupplier;

public class PIDTimer {

	private DoubleSupplier getError;
	private double margin;
	private double time;

	private double timeSettled = 0;

	public PIDTimer(DoubleSupplier getPosition, double setpoint, double margin, double time) {
		this(() -> Math.abs(getPosition.getAsDouble() - setpoint), margin, time);
	}

	public PIDTimer(DoubleSupplier getError, double margin, double time) {
		this.getError = getError;
		this.margin = margin;
		this.time = time;
	}

	public void update() {
		if (Math.abs(this.getError.getAsDouble()) > this.margin) {
			this.timeSettled = 0;
		} else if (this.timeSettled == 0) {
			this.timeSettled = System.currentTimeMillis();
		}
	}

	public boolean isDone() {
		return this.timeSettled != 0 && System.currentTimeMillis() - this.timeSettled > this.time;
	}

}
