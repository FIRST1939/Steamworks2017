package com.frcteam1939.steamworks2017.robot.commands.vision;

import com.frcteam1939.steamworks2017.robot.subsystems.Drivetrain;

public class CorrectPositionAngle {
	public CorrectPositionAngle(VisionProcessing processing, GRIPipe pipe, Drivetrain dt){	
		double angle = 0;
		while(angle <87 && angle>93){
			angle = VisionProcessing.getAngle(pipe);
			if (angle >93){
				//Turn Left
			}
			if (angle <87){
				//turn right
			}
	}

}
	}
