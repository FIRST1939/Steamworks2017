package com.frcteam1939.steamworks2017.robot.commands.gearIntake;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GearIntakeGamepadControl extends Command {

	public GearIntakeGamepadControl() {
		//requires a gear intake to run
		this.requires(Robot.gearIntake);
	}

	@Override
	protected void initialize() {}

	@Override
	protected void execute() {
		// checking if x on gamepad is pressed and the robot does not have a gear
		if (Robot.oi.gamepad.x.get() && !Robot.gearIntake.haveGear()) {
			// opening the ramp
			Robot.gearIntake.rampOut();
		}
		//checking if a on the gamepad is pressed and if the robot has a gear
		else if (Robot.oi.gamepad.a.get() || Robot.gearIntake.haveGear()) {
			// closes ramp
			Robot.gearIntake.rampIn();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}
}
