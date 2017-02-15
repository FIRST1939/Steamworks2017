package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.frcteam1939.steamworks2017.robot.RobotMap;
import com.frcteam1939.steamworks2017.robot.commands.climber.ClimberGamepadControl;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

	private static final double P = 0;
	private static final double I = 0;
	private static final double D = 0;
	private static final double F = 0;

	private CANTalon talon = new CANTalon(RobotMap.climberTalon);

	public Climber() {
		this.talon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		this.talon.enableBrakeMode(true);
		this.talon.setProfile(0);
		this.talon.setPID(P, I, D);
		this.talon.setF(F);
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

	public void holdPosition() {
		this.talon.changeControlMode(TalonControlMode.Position);
		this.talon.set(this.talon.getPosition());
	}

	public double getPosition() {
		return this.talon.getPosition();
	}

	public double getSpeed() {
		return this.talon.getSpeed();
	}

	public double getError() {
		return this.talon.getClosedLoopError();
	}

}
