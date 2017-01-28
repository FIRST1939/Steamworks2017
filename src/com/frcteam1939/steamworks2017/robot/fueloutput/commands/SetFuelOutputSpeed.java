package com.frcteam1939.steamworks2017.robot.fueloutput.commands;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetFuelOutputSpeed extends Command {

    public SetFuelOutputSpeed() {
    	requires(Robot.fuelOutput);
    }

    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
