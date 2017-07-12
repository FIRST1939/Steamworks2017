package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TurnToAngle extends Command {

	private final double angle;

	private boolean intialized = false;

	public TurnToAngle(double angle) {
		this.requires(Robot.drivetrain);
		this.angle = angle;
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.resetGyro();
		Robot.drivetrain.turnPID.reset();
		Robot.drivetrain.turnPID.enable();
		Robot.drivetrain.turnPID.setSetpoint(this.angle);
		this.intialized = true;
	}

	@Override
	protected void execute() {
		Robot.drivetrain.drive(0, Robot.drivetrain.turnPID.get(), 0);
	}

	@Override
	protected boolean isFinished() {
		return this.intialized && !Robot.drivetrain.isMoving() && Math.abs(this.angle - Robot.drivetrain.getHeading()) < 10;
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();
		this.intialized = false;
	}

	@Override
	protected void interrupted() {
		Robot.drivetrain.stop();
		this.intialized = false;
	}
}
