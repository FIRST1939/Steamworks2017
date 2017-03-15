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
		// setting the move, rotate, and strafe values to left Y axis, right x axis, and left x axis respectively
		double move = Robot.oi.left.getY();
		double rotate = Robot.oi.right.getX();
		double strafe = Robot.oi.left.getX();
		// setting the turbo boolean to whether or not the 1 button on the left or right button is pushed
		boolean turbo = Robot.oi.left.getRawButton(1) || Robot.oi.right.getRawButton(1);

		// J-Turn Button
		if (Robot.oi.left.getRawButton(2)) {
			// 
			Robot.drivetrain.sidewinderDown();
			Robot.drivetrain.disableBraking();
			//setting turbo to true
			turbo = true;
			// setting move value to 0;
			move = 0;
		} else {
			Robot.drivetrain.enableBraking();
		}
		// checking if move value is drift
		if (Math.abs(move) < DEAD_BAND) {
			// set move value  to  0;
			move = 0;
		} 
		else {
			if (turbo) {
				//setting  move max speed to 100%
				move = map(move, 0, 1.0);
			} else {
				//setting move max speed to 50%	
				move = map(move, 0, 0.5);
			}
		}
		//checking if rotate value is drift
		if (Math.abs(rotate) < DEAD_BAND) {
			//sets rotate value to 0 if it is drifting
			rotate = 0;
		} else {
			//checking if robot is in turbo
			if (turbo) {
				// setting max speed on rotate to 70%
				rotate = map(rotate, 0, 0.7);
			} else {
				//setting max rotate speed to 40%
				rotate = map(rotate, 0, 0.4);
			}
		}
		// checking if strafe value is drift
		if (Math.abs(strafe) < 0.4) {
			// pulls up sidewinder
			Robot.drivetrain.sidewinderUp();
			// sets strafe to 0
			strafe = 0;
		} else {
			// pushes the sidewinderr down
			Robot.drivetrain.sidewinderDown();
			// check if robot is in turbo
			if (turbo) {
				// sets strafe max speed to 100%
				strafe = map(strafe, 0.3, 1.0, 0, 1.0);
			} else {
				// sets max strafe speed to 70%
				strafe = map(strafe, 0.3, 1.0, 0, 0.7);
			}
		}
		// drives robot at move, rotate and strafe values
		Robot.drivetrain.drive(move, rotate, strafe);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		// sets drivetrain to off
		Robot.drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		// sets drivetrain to off
		Robot.drivetrain.stop();
	}
	/**
	 *  setting values to power percentages (0 = 0%, 1.0 = 100%)
	 * @param x value to switch 
	 * @param out_min minimum output power
	 * @param out_max maximum output power
	 * @return new move, strafe, or rotate value
	 */
	private static double map(double x, double out_min, double out_max) {
		return map(x, DEAD_BAND, 1.0, out_min, out_max);
	}
	
	private static double map(double x, double in_min, double in_max, double out_min, double out_max) {
		//checking if number is negative
		boolean negative = x < 0;
		// finding new value of move, strafe or rotate
		double newValue = (Math.abs(x) - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
		// if the number is negative, it will switch new value to a negitive and returning it
		if (negative) return -newValue;
		//returning newValue
		else return newValue;
	}

}
