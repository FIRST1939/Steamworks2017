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
		Robot.drivetrain.turnToAngle(this.angle);
		this.setTimeout(0.5);
	}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return this.isTimedOut() && Robot.drivetrain.getLeftSpeed() == 0 && Robot.drivetrain.getRightSpeed() == 0;
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
