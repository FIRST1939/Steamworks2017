package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class TunePID extends Command {

	private boolean initialized = false;

	private final double setpoint1;
	private final double setpoint2;

	private double currentSetpoint = 0;
	private long time = 0;

	public TunePID(double setpoint1, double setpoint2) {
		this.requires(Robot.drivetrain);
		this.setpoint1 = setpoint1;
		this.setpoint2 = setpoint2;
	}

	@Override
	protected void initialize() {
		if (!this.initialized) {
			SmartDashboard.putNumber("P", 0);
			SmartDashboard.putNumber("Period", 0);
			SmartDashboard.putBoolean("Use Calculated", false);
			SmartDashboard.putBoolean("Manual Tuning", false);
			this.initialized = true;
		}
		this.currentSetpoint = this.setpoint1;
		this.time = System.currentTimeMillis();
		this.init();
	}

	@Override
	protected void execute() {
		if (!SmartDashboard.getBoolean("Manual Tuning", false)) {
			double p = SmartDashboard.getNumber("P", 0);
			double t = SmartDashboard.getNumber("Period", 0);
			double Kp;
			double Ki;
			double Kd;
			if (t != 0.0) {
				Kp = 0.6 * p;
				Ki = 1.2 * p / t;
				Kd = 3 * p * t / 40;
			} else {
				Kp = 0;
				Ki = 0;
				Kd = 0;
			}
			SmartDashboard.putNumber("Kp", Kp);
			SmartDashboard.putNumber("Ki", Ki);
			SmartDashboard.putNumber("Kd", Kd);
			if (SmartDashboard.getBoolean("Use Calculated", false)) {
				this.setPID(Kp, Ki, Kd);
			} else {
				this.setPID(p, 0, 0);
			}
		} else {
			this.setPID(SmartDashboard.getNumber("Kp", 0), SmartDashboard.getNumber("Ki", 0), SmartDashboard.getNumber("Kd", 0));
		}
		if (System.currentTimeMillis() - this.time > 10000) {
			if (this.currentSetpoint == this.setpoint2) {
				this.setSetpoint(this.setpoint1);
				this.currentSetpoint = this.setpoint1;
			} else {
				this.setSetpoint(this.setpoint2);
				this.currentSetpoint = this.setpoint2;
			}
			this.time = System.currentTimeMillis();
		}
		this.exec();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		Robot.drivetrain.stop();
	}

	public abstract void init();

	public abstract void setPID(double P, double I, double D);

	public abstract void setSetpoint(double setpoint);

	public abstract void exec();

}
