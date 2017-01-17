package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.frcteam1939.steamworks2017.robot.RobotMap;
import com.frcteam1939.steamworks2017.robot.drivetrain.commands.DriveByJoystick;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {
	private CANTalon leftFront = new CANTalon(RobotMap.leftFrontTalon);
	private CANTalon leftMid = new CANTalon(RobotMap.leftMidTalon);
	private CANTalon leftBack = new CANTalon(RobotMap.leftBackTalon);
	private CANTalon rightFront = new CANTalon(RobotMap.rightFrontTalon);
	private CANTalon rightMid = new CANTalon(RobotMap.rightMidTalon);
	private CANTalon rightBack = new CANTalon(RobotMap.rightBackTalon);
	private CANTalon sidewinder = new CANTalon(RobotMap.sidewinderTalon);
	
	private RobotDrive drive = new RobotDrive(leftFront, rightFront);
	
	public Drivetrain() {
		leftFront.setControlMode(CANTalon.TalonControlMode.PercentVbus.value);
		leftMid.setControlMode(CANTalon.TalonControlMode.Follower.value);
		leftMid.set(RobotMap.leftFrontTalon);
		leftBack.setControlMode(CANTalon.TalonControlMode.Follower.value);
		leftBack.set(RobotMap.leftFrontTalon);
		
		rightFront.setControlMode(CANTalon.TalonControlMode.PercentVbus.value);
		rightMid.setControlMode(CANTalon.TalonControlMode.Follower.value);
		rightMid.set(RobotMap.rightFrontTalon);
		rightBack.setControlMode(CANTalon.TalonControlMode.Follower.value);
		rightBack.set(RobotMap.leftFrontTalon);
		
		sidewinder.setControlMode(CANTalon.TalonControlMode.PercentVbus.value);
		
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new DriveByJoystick());
    }
    
    public void drive(double move, double rotate, double strafe){
    	// Not Implemented
    }
    
}

