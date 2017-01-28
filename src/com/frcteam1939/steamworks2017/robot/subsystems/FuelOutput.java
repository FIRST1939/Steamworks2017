package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.frcteam1939.steamworks2017.robot.RobotMap;
import com.frcteam1939.steamworks2017.robot.commands.fueloutput.FuelOutputGamepadControl;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class FuelOutput extends Subsystem {

	private CANTalon talon = new CANTalon(RobotMap.fuelOutputTalon);
	private Solenoid door = new Solenoid(RobotMap.PCM, RobotMap.fuelDoorOutSolenoid);
	private DigitalInput lineBreak = new DigitalInput(RobotMap.fuelLineBreak);

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new FuelOutputGamepadControl());
	}

	public void setOutput(double output) {
		this.talon.set(output);
	}

	public void setDoor(boolean isOut) {
		this.door.set(isOut);
	}

	public boolean isFull() {
		return this.lineBreak.get();
	}
}
