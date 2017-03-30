package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.frcteam1939.steamworks2017.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class FuelIntake extends Subsystem {
	// reverse motor at 100%
	public static final double IN_SPEED = -1.0;
	// creating talon for roller
	private CANTalon talon = new CANTalon(RobotMap.fuelIntakeTalon);

	public FuelIntake() {
		//turning off brake mode
		this.talon.enableBrakeMode(false);
	}

	@Override
	public void initDefaultCommand() {}

	public void setOutput(double speed) {
		// setting the roller to intake at speed
		this.talon.set(speed);
	}
}