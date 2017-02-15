package com.frcteam1939.steamworks2017.robot.subsystems;

import com.frcteam1939.steamworks2017.robot.commands.smartdashboard.SmartDashboardUpdater;

import edu.wpi.first.wpilibj.command.Subsystem;

public class SmartDashboardSubsystem extends Subsystem {

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new SmartDashboardUpdater());
	}
}
