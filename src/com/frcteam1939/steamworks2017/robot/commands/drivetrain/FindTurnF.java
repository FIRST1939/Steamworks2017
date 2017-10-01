package com.frcteam1939.steamworks2017.robot.commands.drivetrain;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FindTurnF extends Command {

	private boolean intialized = false;

	private double currentOutput;
	private double step;
	private boolean lessThan;

	public FindTurnF() {
		this.requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		if (!this.intialized) {
			SmartDashboard.putNumber("Stafe Voltage Setpoint", 8);
			this.intialized = true;
		}
		this.currentOutput = 0;
		this.step = 0.01;
		this.lessThan = true;
		Robot.drivetrain.sidewinderDown();
		this.setTimeout(1.0);
	}

	@Override
	protected void execute() {
		double requestedVoltage = SmartDashboard.getNumber("Stafe Voltage Setpoint", 12);
		if (this.isTimedOut()) {
			if (Robot.drivetrain.getTurnSpeed() > 0) {
				if (this.lessThan) {
					this.step = -this.step / 2;
				}
				this.lessThan = false;
			} else {
				if (!this.lessThan) {
					this.step = -this.step / 2;
				}
				this.lessThan = true;
			}
			this.currentOutput += this.step;
		}
		Robot.drivetrain.drive(0, this.currentOutput, requestedVoltage / 12);
		SmartDashboard.putNumber("Current Turn Output", this.currentOutput);
		SmartDashboard.putNumber("Calcualted Turn F", this.currentOutput * 12 / requestedVoltage);
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
}
