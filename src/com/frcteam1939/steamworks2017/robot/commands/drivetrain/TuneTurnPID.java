package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

public class TuneTurnPID extends TunePID {

	private double setpoint;

	public TuneTurnPID() {
		super(0, 90);
	}

	@Override
	public void init() {
		Robot.drivetrain.resetGyro();
		Robot.drivetrain.sidewinderDown();
	}

	@Override
	public void setPID(double P, double I, double D) {
		Robot.drivetrain.setTurnPID(P, I, D);
	}

	@Override
	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}

	@Override
	public void exec() {
		Robot.drivetrain.turnToAngle(this.setpoint);
	}

}
