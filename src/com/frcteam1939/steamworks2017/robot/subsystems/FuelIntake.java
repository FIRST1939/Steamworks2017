package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.frcteam1939.steamworks2017.robot.RobotMap;
import com.frcteam1939.steamworks2017.robot.commands.fuelintake.FuelIntakeGampadControl;

import edu.wpi.first.wpilibj.command.Subsystem;

public class FuelIntake extends Subsystem {

	private CANTalon talon = new CANTalon(RobotMap.fuelIntakeTalon);

    public void initDefaultCommand() {
    	setDefaultCommand(new FuelIntakeGampadControl());
    }
  
    public void setOutput(double speed){
    	talon.set(speed);
    }
}