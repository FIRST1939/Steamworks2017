package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.commands.drivetrain.TurnToAngle;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Command;

public class TurnByAlliance extends Command {

	private double red;
	private double blue;

	private Command command;

	public TurnByAlliance(double red, double blue) {
		this.red = red;
		this.blue = blue;
	}

	@Override
	protected void initialize() {
		if (DriverStation.getInstance().getAlliance() == Alliance.Red) {
			this.command = new TurnToAngle(this.red);
		} else {
			this.command = new TurnToAngle(this.blue);
		}
		this.command.start();
	}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return !this.command.isRunning();
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}
}
