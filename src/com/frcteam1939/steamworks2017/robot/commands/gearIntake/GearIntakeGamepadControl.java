package com.frcteam1939.steamworks2017.robot.commands.gearIntake;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearIntakeGamepadControl extends Command {

	public GearIntakeGamepadControl() {
		this.requires(Robot.gearIntake);
	}

	@Override
	protected void initialize() {}

	@Override
	protected void execute() {
		if (Robot.oi.gamepad.x.get() && (!Robot.gearIntake.haveGear() || SmartDashboard.getBoolean("Ramp Override", false))) {
			Robot.gearIntake.rampOut();
		} else if (Robot.oi.gamepad.a.get() || Robot.gearIntake.haveGear() && !SmartDashboard.getBoolean("Ramp Override", false)) {
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
