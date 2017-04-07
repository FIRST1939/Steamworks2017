package com.frcteam1939.steamworks2017.robot.commands.auton;

import java.util.function.BooleanSupplier;

import com.frcteam1939.steamworks2017.robot.Robot;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DrivePath;
import com.frcteam1939.steamworks2017.util.ConditionalCommand;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ConditionallyDriveAway extends CommandGroup {

	public ConditionallyDriveAway(double[][] backup, double[][] path) {
		BooleanSupplier b = () -> {
			return Robot.gearOutput.onPeg();
		};
		Command onTrue = new CommandGroup() {

			{
				this.addSequential(new DrivePath(backup, true));
				this.addSequential(new DrivePath(path));
			}
		};
		Command onFalse = new Backup();
		this.addSequential(new ConditionalCommand(b, onTrue, onFalse));
	}
}
