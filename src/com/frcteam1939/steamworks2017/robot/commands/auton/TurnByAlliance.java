package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.commands.drivetrain.TurnToAngle;
import com.frcteam1939.steamworks2017.util.ConditionalByAlliance;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TurnByAlliance extends CommandGroup {

	public TurnByAlliance(double red, double blue) {
		this.addSequential(new ConditionalByAlliance(new TurnToAngle(red), new TurnToAngle(blue)));
	}

}
