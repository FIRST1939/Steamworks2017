package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AimForCenter extends Command {

    public AimForCenter() {
        requires(Robot.camera);
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.resetGyro();
    	Robot.drivetrain.drive(0,Robot.camera.getCenterX()*0.002 , 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.camera.getCenterX() >-3 &&Robot.camera.getCenterX() <3){
    		return true;
    	}else{
    		return false;
    	}
        
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
