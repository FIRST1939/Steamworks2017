package com.frcteam1939.steamworks2017.robot.commands.vision;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SendContourNumber extends Command {
	VisionProcessing vision;
	GRIPipe p;

	public SendContourNumber(GRIPipe pipe, VisionProcessing processing) {
		vision = processing;
		p = pipe;
		
	}

	@Override
	protected void initialize() {
		SmartDashboard.putNumber("CenterX", p.filterContoursOutput.size());
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("CenterX", p.filterContoursOutput.size());
		
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
