package com.frcteam1939.steamworks2017.robot.subsystems;

import com.frcteam1939.steamworks2017.robot.Robot;
import com.frcteam1939.steamworks2017.robot.RobotMap;
import com.frcteam1939.steamworks2017.robot.commands.gearIntake.GearIntakeGamepadControl;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearIntake extends Subsystem {

	private Solenoid solenoid = new Solenoid(RobotMap.PCM, RobotMap.gearRampOutSolenoid);
	private DigitalInput banner = new DigitalInput(RobotMap.gearBannerSensor);

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new GearIntakeGamepadControl());
	}

	public void rampOut() {
		this.solenoid.set(true);
		Robot.gearOutput.retract();
	}

	public void rampIn() {
		this.solenoid.set(false);
	}

	public boolean haveGear() {
		return this.banner.get();
	}

}
