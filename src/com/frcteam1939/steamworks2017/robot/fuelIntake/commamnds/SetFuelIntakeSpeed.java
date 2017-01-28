package com.frcteam1939.steamworks2017.robot.fuelIntake.commamnds;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetFuelIntakeSpeed extends Command {
	
	private double speed;

    public SetFuelIntakeSpeed(double speed) {
    	requires (Robot.fuelIntake);
    	this.speed = speed;
    }

    protected void initialize() {
    }
    protected void execute() {
    	Robot.fuelIntake.setOutput(speed);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
