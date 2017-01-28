package com.frcteam1939.steamworks2017.robot.subsystems;

import com.frcteam1939.steamworks2017.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Brake extends Subsystem {

	private Solenoid solenoid = new Solenoid(RobotMap.brakeDownSolenoid);

	@Override
	public void initDefaultCommand() {

	}

	public void brakeDown() {
		this.solenoid.set(true);
	}

	public void brakeUp() {
		this.solenoid.set(false);
	}

}