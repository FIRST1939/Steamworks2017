package com.frcteam1939.steamworks2017.robot.commands.gearoutput;

import com.frcteam1939.steamworks2017.robot.Robot;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelIntake;

import edu.wpi.first.wpilibj.command.Command;

public class RetractGearPusher extends Command {

	public RetractGearPusher() {
		this.requires(Robot.gearOutput);
	}

	@Override
	protected void initialize() {
		Robot.fuelIntake.setOutput(FuelIntake.IN_SPEED);
	}

	@Override
	protected void execute() {
		Robot.gearOutput.retract();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}
}
