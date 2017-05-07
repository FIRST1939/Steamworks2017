package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FindMaxSpeed extends Command {

	private double maxLeftSpeed;
	private double maxRightSpeed;

	public FindMaxSpeed() {
		this.requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		this.maxLeftSpeed = 0;
		this.maxRightSpeed = 0;
		SmartDashboard.putNumber("Maximum Output Voltage", 12);
	}

	@Override
	protected void execute() {
		double maxOutput = SmartDashboard.getNumber("Maximum Output Voltage", 0);
		double voltage = maxOutput * Robot.oi.left.getY();
		SmartDashboard.putNumber("Current Voltage", voltage);
		Robot.drivetrain.setRightVoltage(voltage);
		Robot.drivetrain.setLeftVoltage(-voltage);

		double left = Robot.drivetrain.getLeftSpeed();
		if (Math.abs(left) > this.maxLeftSpeed) {
			this.maxLeftSpeed = Math.abs(left);
		}
		SmartDashboard.putNumber("Max Left Speed", this.maxLeftSpeed);
		SmartDashboard.putNumber("Current Left Speed", left);

		double right = Robot.drivetrain.getRightSpeed();
		if (Math.abs(right) > this.maxRightSpeed) {
			this.maxRightSpeed = Math.abs(right);
		}
		SmartDashboard.putNumber("Max Right Speed", this.maxRightSpeed);
		SmartDashboard.putNumber("Current Right Speed", right);
	}

	@Override
	protected boolean isFinished() {
		return false;
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
