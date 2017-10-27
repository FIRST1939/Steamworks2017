package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.DistanceConstants;
import com.frcteam1939.steamworks2017.robot.Robot;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DriveDistance;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.TurnToAngle;
import com.frcteam1939.steamworks2017.robot.commands.gearIntake.PlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToBlueBoilerPeg extends CommandGroup {

	public DriveToBlueBoilerPeg() {
		this.addSequential(new DriveDistance(DistanceConstants.BOILER_FIRST));
		if (!Robot.gearOutput.leftIsAligned()) {
			// Try -90
			this.addSequential(new TurnToAngle(DistanceConstants.BOILER_BLUE_ANGLE));
		} else if (Robot.gearOutput.leftIsAligned()) {
			this.addSequential(new DriveDistance(DistanceConstants.BOILER_SECOND));
		}
		this.addSequential(new PlaceGear());
		this.addSequential(new DriveDistance(DistanceConstants.BACKUP));
	}
}
