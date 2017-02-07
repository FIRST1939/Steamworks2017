package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveByJoystick extends Command {

	private static double DEAD_BAND = 0.1;

	public DriveByJoystick() {
		this.requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {}

	@Override
	protected void execute() {
		double move = Robot.oi.left.getY();
		double rotate = Robot.oi.right.getX();
		double strafe = Robot.oi.right.getY();

		boolean turbo = Robot.oi.left.getRawButton(1);

		if (Math.abs(move) < DEAD_BAND) {
			move = 0;
		} else {
			if (turbo) {
				move = map(move, 0, 0.3);
			} else {
				move = map(move, 0, 0.6);
			}
		}
		if (Math.abs(rotate) < DEAD_BAND) {
			rotate = 0;
		} else {
			rotate = map(rotate, 0, 0.3);
		}
		if (Math.abs(strafe) < DEAD_BAND) {
			strafe = 0;
		} else {
			strafe = map(strafe, 0, 0.5);
		}

		Robot.drivetrain.drive(move, rotate, strafe);

		boolean sidewind = Robot.oi.right.getRawButton(1);
		if (sidewind) {
			Robot.drivetrain.sidewinderDown();
		} else {
			Robot.drivetrain.sidewinderUp();
		}
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

	private static double map(double x, double out_min, double out_max) {
		return map(x, DEAD_BAND, 1.0, out_min, out_max);
	}

	private static double map(double x, double in_min, double in_max, double out_min, double out_max) {
		boolean negative = x < 0;
		double newValue = (Math.abs(x) - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
		if (negative) return -newValue;
		else return newValue;
	}

}
