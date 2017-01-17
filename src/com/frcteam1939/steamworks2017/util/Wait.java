package com.frcteam1939.steamworks2017.util;

import edu.wpi.first.wpilibj.command.Command;

public class Wait extends Command {

	private double timeout;
	
    public Wait(double timeout) {
    	this.timeout = timeout;
    }

    protected void initialize() {
    	this.setTimeout(timeout);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return this.isTimedOut();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
