package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.frcteam1939.steamworks2017.robot.RobotMap;
import com.frcteam1939.steamworks2017.robot.commands.climber.ClimberGamepadControl;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

	private CANTalon talon = new CANTalon(RobotMap.climberTalon);

	public Climber() {
		this.talon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
	}

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new ClimberGamepadControl());
	}

	public void setOutput(double output) {
		this.talon.set(output);
	}

}
