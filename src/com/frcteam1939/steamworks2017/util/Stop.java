package com.frcteam1939.steamworks2017.util;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Stop extends Command {
	protected void initialize() {}

	@Override
	protected void execute() {
		Robot.drivetrain.drive(0,0,0);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}

}
