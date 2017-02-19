package com.frcteam1939.steamworks2017.robot.commands.vision;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SendAngle extends Command {
	VisionProcessing vision;
	GRIPipe p;

	public SendAngle(GRIPipe pipe, VisionProcessing processing) {
		vision = processing;
		p = pipe;
		this.requires(Robot.drivetrain);
		
	}

	@Override
	protected void initialize() {
		SmartDashboard.putNumber("Angle", VisionProcessing.getAngle(p));
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("Angle", VisionProcessing.getAngle(p));
		
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	
}
