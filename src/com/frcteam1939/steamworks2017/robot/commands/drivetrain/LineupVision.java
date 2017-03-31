package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;
import com.frcteam1939.steamworks2017.robot.vision.Vision;

import edu.wpi.first.wpilibj.command.Command;

public class LineupVision extends Command {

	private Command command;

	@Override
	protected void initialize() {
		Robot.drivetrain.resetGyro();
		this.command = new TurnToAngle(Vision.getAngle());
		this.command.start();
	}

	@Override
	protected boolean isFinished() {
		return !this.command.isRunning();
	}

}
