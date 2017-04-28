package com.frcteam1939.steamworks2017.util;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ConditionalByAlliance extends CommandGroup {

	public ConditionalByAlliance(Command red, Command blue) {
		BooleanSupplier b = () -> {
			return DriverStation.getInstance().getAlliance() == Alliance.Red;
		};
		this.addSequential(new ConditionalCommand(b, red, blue));
	}
}
