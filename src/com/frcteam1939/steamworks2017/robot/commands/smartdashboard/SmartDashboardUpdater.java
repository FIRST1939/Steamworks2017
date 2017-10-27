package com.frcteam1939.steamworks2017.robot.commands.smartdashboard;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardUpdater extends Command {

	private boolean intialized = false;

	public SmartDashboardUpdater() {
		this.requires(Robot.smartDashboard);
	}

	@Override
	protected void initialize() {
		if (!this.intialized) {
			SmartDashboard.putBoolean("Ramp Override", false);
			SmartDashboard.putBoolean("Output Override", false);
			SmartDashboard.putBoolean("Drive By Speed", true);
			this.intialized = true;
		}
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("Left Speed", Robot.drivetrain.getLeftSpeed());
		SmartDashboard.putNumber("Left Position", Robot.drivetrain.getLeftPosition());
		SmartDashboard.putNumber("Left Voltage", Robot.drivetrain.getLeftVolts());
		SmartDashboard.putNumber("Left Error", Robot.drivetrain.getLeftError());

		SmartDashboard.putNumber("Right Speed", Robot.drivetrain.getRightSpeed());
		SmartDashboard.putNumber("Right Position", Robot.drivetrain.getRightPosition());
		SmartDashboard.putNumber("Right Voltage", Robot.drivetrain.getRightVolts());
		SmartDashboard.putNumber("Right Error", Robot.drivetrain.getRightError());

		SmartDashboard.putNumber("Heading", Robot.drivetrain.getHeading());
		SmartDashboard.putNumber("Turn Speed", Robot.drivetrain.getTurnSpeed());

		SmartDashboard.putNumber("Pressure", Robot.getPressure());

		SmartDashboard.putBoolean("Have Gear", Robot.gearIntake.haveGear());
		SmartDashboard.putBoolean("On Peg", Robot.gearOutput.onPeg());
		SmartDashboard.putBoolean("Is Moving", Robot.drivetrain.isMoving());
		SmartDashboard.putBoolean("Test", Robot.gearIntake.test());
		SmartDashboard.putBoolean("Right Is Aligned", Robot.gearOutput.rightIsAligned());
		SmartDashboard.putBoolean("Left Is Aligned", Robot.gearOutput.leftIsAligned());

		Robot.drivetrain.setDriveBySpeed(SmartDashboard.getBoolean("Drive By Speed", true));
		SmartDashboard.putData("Turn PID", Robot.drivetrain.turnPID);
		SmartDashboard.putData("Move PID", Robot.drivetrain.posPID);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}
}
