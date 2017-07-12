package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;
import com.frcteam1939.steamworks2017.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDistance extends Command {

	private double setpoint;

	private boolean intialized = false;;

	public DriveDistance(double inches) {
		this.requires(Robot.drivetrain);
		this.setpoint = inches / (Math.PI * Drivetrain.WHEEL_DIAMETER);
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.zeroEncoders();
		Robot.drivetrain.posPID.reset();
		Robot.drivetrain.posPID.enable();
		Robot.drivetrain.posPID.setSetpoint(this.setpoint);
		this.intialized = true;
	}

	@Override
	protected void execute() {
		double pid = -Robot.drivetrain.posPID.get();
		if (Math.abs(pid) == 0.4) {
			Robot.drivetrain.setRampRate(18, 24);
		} else {
			Robot.drivetrain.resetRampRate();
		}
		Robot.drivetrain.drive(pid, 0, 0);
	}

	@Override
	protected boolean isFinished() {
		return this.intialized && !Robot.drivetrain.isMoving() && Math.abs(Robot.drivetrain.posPID.getError()) < 0.5;
	}

	@Override
	protected void end() {
		Robot.drivetrain.resetRampRate();
		Robot.drivetrain.stop();
		this.intialized = false;
	}

	@Override
	protected void interrupted() {
		Robot.drivetrain.resetRampRate();
		Robot.drivetrain.stop();
		this.intialized = false;
	}
}
