package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.ctre.CANTalon.MotionProfileStatus;
import com.frcteam1939.steamworks2017.robot.Paths;
import com.frcteam1939.steamworks2017.robot.Robot;
import com.frcteam1939.steamworks2017.util.MotionProfile;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Command;

public class DrivePath extends Command {

	private MotionProfile red;
	private MotionProfile blue;

	private boolean started = false;
	private boolean finished = false;

	public DrivePath(double[][] waypoints) {
		this.red = new MotionProfile(waypoints);
		this.blue = new MotionProfile(Paths.flip(waypoints));
		this.requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.setMotionProfile(DriverStation.getInstance().getAlliance() == Alliance.Red ? this.red : this.blue);
		this.started = false;
		this.finished = false;
		Robot.drivetrain.stopMotionProfile();
	}

	@Override
	protected void execute() {
		MotionProfileStatus left = Robot.drivetrain.getLeftMotionProfileStatus();
		MotionProfileStatus right = Robot.drivetrain.getRightMotionProfileStatus();

		if (!this.started) {
			// Check if both Talons have points in buffer
			if (left.btmBufferCnt > 10 && right.btmBufferCnt > 10) {
				Robot.drivetrain.startMotionProfile();
				this.started = true;
			}
		} else {
			// Check if both Talons are finished
			if (left.activePoint.isLastPoint && right.activePoint.isLastPoint) {
				this.finished = true;
			}
		}
	}

	@Override
	protected boolean isFinished() {
		return this.finished;
	}

	@Override
	protected void end() {
		Robot.drivetrain.stopMotionProfile();
		Robot.drivetrain.drive(0, 0, 0);
	}

	@Override
	protected void interrupted() {
		Robot.drivetrain.stopMotionProfile();
		Robot.drivetrain.drive(0, 0, 0);
	}

}