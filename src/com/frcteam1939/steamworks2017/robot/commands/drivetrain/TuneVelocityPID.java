package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

public class TuneVelocityPID extends TunePID {

	public TuneVelocityPID() {
		super(200, 250);
	}

	@Override
	public void init() {
		Robot.drivetrain.zeroEncoders();
	}

	@Override
	public void setPID(double P, double I, double D) {
		Robot.drivetrain.setVelocityPID(P, I, D);
	}

	@Override
	public void setSetpoint(double setpoint) {
		Robot.drivetrain.driveSpeed(-setpoint, setpoint);
	}

}
