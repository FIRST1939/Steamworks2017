
package com.frcteam1939.steamworks2017.robot;

import com.frcteam1939.steamworks2017.robot.subsystems.Brake;
import com.frcteam1939.steamworks2017.robot.subsystems.Climber;
import com.frcteam1939.steamworks2017.robot.subsystems.Drivetrain;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelIntake;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelOutput;
import com.frcteam1939.steamworks2017.robot.subsystems.GearIntake;
import com.frcteam1939.steamworks2017.robot.subsystems.GearOutput;
import com.frcteam1939.steamworks2017.util.DoNothing;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static final Brake brake = new Brake();
	public static final Drivetrain drivetrain = new Drivetrain();
	public static final Climber climber = new Climber();
	public static final GearIntake gearIntake = new GearIntake();
	public static final GearOutput gearOutput = new GearOutput();
	public static final FuelIntake fuelIntake = new FuelIntake();
	public static final FuelOutput fuelOutput = new FuelOutput();

	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		oi = new OI();

		this.chooser.addDefault("Do Nothing", new DoNothing());
		SmartDashboard.putData("Autonomous Chooser", this.chooser);
	}

	@Override
	public void robotPeriodic() {
		this.autonomousCommand = this.chooser.getSelected();
	}

	@Override
	public void disabledInit() {}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		if (this.autonomousCommand != null) {
			this.autonomousCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (this.autonomousCommand != null) {
			this.autonomousCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

}
