package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.ctre.CANTalon.MotionProfileStatus;
import com.frcteam1939.steamworks2017.robot.Robot;
import com.frcteam1939.steamworks2017.util.MotionProfile;

import edu.wpi.first.wpilibj.command.Command;

public class DriveMotionProfile extends Command {

	private MotionProfile profile;

	private boolean started = false;
	private boolean finished = false;

	public DriveMotionProfile(MotionProfile profile) {
		this.profile = profile;
		this.requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.setMotionProfile(this.profile);
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