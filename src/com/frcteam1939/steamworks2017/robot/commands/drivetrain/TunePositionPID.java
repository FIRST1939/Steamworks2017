package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

public class TunePositionPID extends TunePID {

	public TunePositionPID() {
		super(5, 10);
	}

	@Override
	public void init() {
		Robot.drivetrain.zeroEncoders();
		Robot.drivetrain.sidewinderDown();
	}

	@Override
	public void setPID(double P, double I, double D) {
		Robot.drivetrain.setPositionPID(P, I, D);
	}

	@Override
	public void setSetpoint(double setpoint) {
		Robot.drivetrain.drivePosition(-setpoint, setpoint);
	}

}
