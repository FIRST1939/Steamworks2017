package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.DistanceConstants;
import com.frcteam1939.steamworks2017.robot.Robot;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DriveDistance;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.TurnToAngle;
import com.frcteam1939.steamworks2017.robot.commands.gearIntake.PlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToBlueSlotsPeg extends CommandGroup {

	public DriveToBlueSlotsPeg() {
		this.addSequential(new DriveDistance(DistanceConstants.SLOTS_FIRST));
		if (!Robot.gearOutput.rightIsAligned()) {
			// Try -90
			this.addSequential(new TurnToAngle(DistanceConstants.SLOTS_BLUE_ANGLE));
		} else if (Robot.gearOutput.rightIsAligned()) {
			this.addSequential(new DriveDistance(DistanceConstants.SLOTS_SECOND));
		}

	}
}
