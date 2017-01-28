package com.frcteam1939.steamworks2017.robot.subsystems;

import com.frcteam1939.steamworks2017.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearIntake extends Subsystem {

	private Solenoid solenoid = new Solenoid(RobotMap.PCM, RobotMap.gearRampOutSolenoid);

	@Override
	public void initDefaultCommand() {

	}

	public void rampOut() {
		this.solenoid.set(true);
	}

	public void rampIn() {
		this.solenoid.set(false);
	}
}
