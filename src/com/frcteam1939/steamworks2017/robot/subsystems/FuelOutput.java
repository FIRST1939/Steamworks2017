package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.frcteam1939.steamworks2017.robot.Robot;
import com.frcteam1939.steamworks2017.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class FuelOutput extends Subsystem {

	public static final double OUT_SPEED = 1.0;

	private CANTalon talon = new CANTalon(RobotMap.fuelOutputTalon);

	public FuelOutput() {
		this.talon.enableBrakeMode(false);
	}

	@Override
	public void initDefaultCommand() {}

	public void setOutput(double output) {
		this.talon.set(output);
		if (output != 0) {
			Robot.drivetrain.brakeDown();
		}
	}

}
