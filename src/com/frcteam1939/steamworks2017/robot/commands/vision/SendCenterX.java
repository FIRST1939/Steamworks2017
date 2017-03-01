package com.frcteam1939.steamworks2017.robot.commands.vision;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SendCenterX extends Command{
	VisionProcessing vision;
	GRIPipe p;
	double[] centerx;

	public SendCenterX(GRIPipe pipe, VisionProcessing processing) {
		vision = processing;
		p = pipe;
		this.requires(Robot.drivetrain);
		
	}

	@Override
	protected void initialize() {
		VisionProcessing.returnCenterX(p);
		SmartDashboard.putNumber("Length Between Contours", vision.returnCenterX(p));
		
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("Length Between Contours", vision.returnCenterX(p));
		
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
