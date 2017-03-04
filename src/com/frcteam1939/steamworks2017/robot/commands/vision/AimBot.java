package com.frcteam1939.steamworks2017.robot.commands.vision;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AimBot extends Command {
	@Override
	protected void initialize() {		
	}

	@Override
	protected void execute() {
		while (Robot.centerX >-10 && Robot.centerX<10){
			synchronized (Robot.imgLock) {
				
			}
			if (2 == Robot.contours){
			Robot.drivetrain.drive(0,0 , Robot.centerX * 0.001);
			}
		}
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
