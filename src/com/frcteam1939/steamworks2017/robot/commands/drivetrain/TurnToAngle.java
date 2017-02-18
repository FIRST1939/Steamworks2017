package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TurnToAngle extends Command {

	private final double angle;

	public TurnToAngle(double angle) {
		this.requires(Robot.drivetrain);
		this.angle = angle;
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.resetGyro();
		this.setTimeout(0.5);
	}

	@Override
	protected void execute() {
		Robot.drivetrain.turnToAngle(this.angle);
	}

	@Override
	protected boolean isFinished() {
		return this.isTimedOut() && Math.abs(Robot.drivetrain.getLeftSpeed()) < 1;
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
