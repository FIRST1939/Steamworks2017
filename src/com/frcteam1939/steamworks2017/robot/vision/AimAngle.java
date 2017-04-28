package com.frcteam1939.steamworks2017.robot.vision;

import com.frcteam1939.steamworks2017.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AimAngle extends Command {
	double degree;
    public AimAngle() {
    	requires(Robot.camera);
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.camera.getContours() == 2){
    		degree = Robot.camera.getAngle(); 
    		Robot.drivetrain.drive(0, degree, 0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (degree <2 && degree > -2){
    		return true;
    	}
    	else{
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
