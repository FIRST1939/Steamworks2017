package com.frcteam1939.steamworks2017.robot.fueloutput.commands;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetFuelOutputSpeed extends Command {
	
	private double speed;

    public SetFuelOutputSpeed(double speed) {
    	requires(Robot.fuelOutput);
    	this.speed = speed;
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.fuelOutput.setOutput(speed);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
