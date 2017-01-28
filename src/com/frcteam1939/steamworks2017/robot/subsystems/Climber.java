package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.frcteam1939.steamworks2017.robot.RobotMap;
import com.frcteam1939.steamworks2017.robot.commands.climber.ClimberGamepadControl;


import edu.wpi.first.wpilibj.command.Subsystem;


public class Climber extends Subsystem {

	private CANTalon talon  = new CANTalon(RobotMap.climberTalon);
	private static final int CPR = 12;

	public Climber(){
		talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon.configEncoderCodesPerRev(CPR);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ClimberGamepadControl());
    }
    
    public void setOutput(double output){
    	talon.set(output);
    }
    
    public double getSpeed69(){
    	return talon.getEncPosition();
    }
    
}

