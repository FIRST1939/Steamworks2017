package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.frcteam1939.steamworks2017.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class FuelIntake extends Subsystem {

	public static final double IN_SPEED = -1.0;

	private CANTalon talon = new CANTalon(RobotMap.fuelIntakeTalon);

	@Override
	public void initDefaultCommand() {}

	public void setOutput(double speed) {
		this.talon.set(speed);
	}
}