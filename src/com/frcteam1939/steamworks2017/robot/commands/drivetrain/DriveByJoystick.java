package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveByJoystick extends Command {

	public DriveByJoystick() {
		requires(Robot.drivetrain);
	}

	protected void initialize() {}

	protected void execute() {
		double move = Robot.oi.left.getY();
		double rotate = Robot.oi.right.getX();
		double strafe = Robot.oi.right.getY();

		Robot.drivetrain.drive(move, rotate, strafe);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {}

	protected void interrupted() {}
}
