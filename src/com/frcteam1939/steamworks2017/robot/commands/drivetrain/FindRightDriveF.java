package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;
import com.frcteam1939.steamworks2017.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FindRightDriveF extends Command {

	private double step;
	private double f;
	private boolean intialized = false;
	private long start;
	private boolean toFast;

	public FindRightDriveF() {
		this.requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		this.step = Drivetrain.velF / 5.0;
		this.f = Drivetrain.velF;
		this.intialized = false;
		Robot.drivetrain.setVelocityPID(0, 0, 0);
		this.start = System.currentTimeMillis();
	}

	@Override
	protected void execute() {
		Robot.drivetrain.drive(-0.2, 0, 0);
		long time = this.start - System.currentTimeMillis();
		double left = Robot.drivetrain.getLeftSpeed();
		double right = Robot.drivetrain.getRightSpeed();
		if (!this.intialized) {
			if (time > 2000) {
				this.intialized = true;
				this.toFast = right > left;
			}
		} else {
			if (time > 500) {
				boolean isToFast = right > left;
				if (this.toFast != isToFast) {
					this.step = this.step / 2;
					this.toFast = isToFast;
				}
				if (isToFast) {
					this.f -= this.step;
				} else {
					this.f += this.step;
				}
				this.start = System.currentTimeMillis();
			}
		}
		SmartDashboard.putNumber("Step", this.step);
		SmartDashboard.putNumber("Left F", Drivetrain.velF);
		SmartDashboard.putNumber("Right F", this.f);
		Robot.drivetrain.setRightF(this.f);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.drivetrain.resetConfigs();
	}

	@Override
	protected void interrupted() {
		Robot.drivetrain.resetConfigs();
	}
}
