package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.frcteam1939.steamworks2017.robot.RobotMap;
import com.frcteam1939.steamworks2017.robot.commands.climber.ClimberGamepadControl;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

	private CANTalon talon = new CANTalon(RobotMap.climberTalon);

	public Climber() {
		this.talon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		this.talon.enableBrakeMode(true);
		this.talon.setVoltageRampRate(24);
		this.talon.setNominalClosedLoopVoltage(12.0);
	}

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new ClimberGamepadControl());
	}

	public void setOutput(double output) {
		this.talon.changeControlMode(TalonControlMode.PercentVbus);
		this.talon.set(output);
	}

	public double getPosition() {
		return this.talon.getPosition();
	}

	public double getSpeed() {
		return this.talon.getSpeed();
	}

	public void enableBraking() {
		this.talon.enableBrakeMode(true);
	}

	public void disableBraking() {
		this.talon.enableBrakeMode(false);
	}

}
