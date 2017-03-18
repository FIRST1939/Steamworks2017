package com.frcteam1939.steamworks2017.robot.commands.gearoutput;

import com.frcteam1939.steamworks2017.robot.Robot;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelIntake;

import edu.wpi.first.wpilibj.command.Command;

public class PushOutGear extends Command {

	public PushOutGear() {
		// requires gear output to run
		this.requires(Robot.gearOutput);
	}

	@Override
	protected void initialize() {
		// sets the fuel intake to collect balls
		Robot.fuelIntake.setOutput(FuelIntake.IN_SPEED);
	}

	@Override
	protected void execute() {
		// opens clamps if the gear is on a peg and the operater is in control
		Robot.gearOutput.push();
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
