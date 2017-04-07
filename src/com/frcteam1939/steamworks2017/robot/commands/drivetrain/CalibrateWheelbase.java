package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CalibrateWheelbase extends Command {

	private boolean intialized;

	public CalibrateWheelbase() {
		this.requires(Robot.drivetrain);
		this.intialized = false;
	}

	@Override
	protected void initialize() {
		if (!this.intialized) {
			SmartDashboard.putNumber("Distance", 24);
			this.intialized = true;
		}
		Robot.drivetrain.resetGyro();
		double distance = SmartDashboard.getNumber("Distance", 24);
		Robot.drivetrain.driveDistance(distance, distance);
		this.setTimeout(0.5);
	}

	@Override
	protected void execute() {
		double s = SmartDashboard.getNumber("Distance", 24);
		double angle = Robot.drivetrain.getHeading();
		double radius = s / Math.toRadians(angle);
		SmartDashboard.putNumber("Calculated Wheelbase", radius * 2);
	}

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
