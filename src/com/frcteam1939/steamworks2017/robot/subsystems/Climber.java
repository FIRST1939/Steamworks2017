package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.frcteam1939.steamworks2017.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

	private CANTalon talon  = new CANTalon(RobotMap.climberTalon);
	//public boolean climbing = false;
  

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setOutput(double output){
    	talon.set(output);
    }
    
}

