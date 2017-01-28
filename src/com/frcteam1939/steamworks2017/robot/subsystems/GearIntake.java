package com.frcteam1939.steamworks2017.robot.subsystems;

import com.frcteam1939.steamworks2017.robot.RobotMap;
import com.frcteam1939.steamworks2017.robot.commands.gearIntake.GearRampIn;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearIntake extends Subsystem {

	private Solenoid solenoid = new Solenoid(RobotMap.gearRampOutSolenoid);

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new GearRampIn());
	}

	public void rampOut() {
		this.solenoid.set(true);
	}

	public void rampIn() {
		this.solenoid.set(false);
	}
}
