package com.frcteam1939.steamworks2017.robot.subsystems;

import com.frcteam1939.steamworks2017.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearOutput extends Subsystem {

	private Solenoid gearSolenoid = new Solenoid(RobotMap.PCM, RobotMap.gearPushSolenoid);
	private DigitalInput limitSwitch = new DigitalInput(RobotMap.gearLimitSwitch);

	@Override
	public void initDefaultCommand() {}

	public void push() {
		if (this.onPeg() || DriverStation.getInstance().isOperatorControl()) {
			this.gearSolenoid.set(true);
		}
	}

	public void retract() {
		this.gearSolenoid.set(false);
	}

	public boolean onPeg() {
		return !this.limitSwitch.get();
	}
}
