package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDistance extends Command {

	private double inches;

	public DriveDistance(double inches) {
		this.requires(Robot.drivetrain);
		this.inches = inches;
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.driveDistance(this.inches);
		this.setTimeout(0.5);
	}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return this.isTimedOut() && !Robot.drivetrain.isMoving();
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		Robot.drivetrain.stop();
	}
}
