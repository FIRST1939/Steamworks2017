package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

public class TuneTurnPID extends TunePID {

	public TuneTurnPID() {
		super(90, 180);
	}

	@Override
	public void init() {
		Robot.drivetrain.resetGyro();
	}

	@Override
	public void setPID(double P, double I, double D) {
		Robot.drivetrain.setTurnPID(P, I, D);
	}

	@Override
	public void setSetpoint(double setpoint) {
		Robot.drivetrain.turnToAngle(setpoint);
	}

}
