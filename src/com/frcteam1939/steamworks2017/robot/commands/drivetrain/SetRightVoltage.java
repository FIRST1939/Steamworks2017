package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetRightVoltage extends Command {

	private double voltage;

	public SetRightVoltage(double voltage) {
		this.requires(Robot.drivetrain);
		this.voltage = voltage;
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.setRightVoltage(this.voltage);
	}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return true;
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
