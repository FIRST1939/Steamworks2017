package com.frcteam1939.steamworks2017.robot.subsystems;

import com.frcteam1939.steamworks2017.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearOutput extends Subsystem {

	private Solenoid gearSolenoid = new Solenoid(RobotMap.gearPushSolenoid);

	@Override
	public void initDefaultCommand() {
	}

	public void pushOutGear() {
		this.gearSolenoid.set(true);
	}

	public void retractGearPusher() {
		this.gearSolenoid.set(false);
	}
}
