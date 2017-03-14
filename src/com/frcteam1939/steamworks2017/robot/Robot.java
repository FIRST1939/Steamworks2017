package com.frcteam1939.steamworks2017.robot;

import com.frcteam1939.steamworks2017.robot.commands.auton.BoilerPegCross;
import com.frcteam1939.steamworks2017.robot.commands.auton.BoilerPegMidField;
import com.frcteam1939.steamworks2017.robot.commands.auton.CenterPegBackup;
import com.frcteam1939.steamworks2017.robot.commands.auton.CenterPegBoilerCross;
import com.frcteam1939.steamworks2017.robot.commands.auton.CenterPegBoilerMidField;
import com.frcteam1939.steamworks2017.robot.commands.auton.CenterPegSlotsCross;
import com.frcteam1939.steamworks2017.robot.commands.auton.CenterPegSlotsMidField;
import com.frcteam1939.steamworks2017.robot.commands.auton.SlotsPegCross;
import com.frcteam1939.steamworks2017.robot.commands.auton.SlotsPegMidField;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.FindMaxSpeed;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.FindTurnF;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.TunePositionPID;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.TuneTurnPID;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.TuneVelocityPID;
import com.frcteam1939.steamworks2017.robot.subsystems.Climber;
import com.frcteam1939.steamworks2017.robot.subsystems.Drivetrain;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelIntake;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelOutput;
import com.frcteam1939.steamworks2017.robot.subsystems.GearIntake;
import com.frcteam1939.steamworks2017.robot.subsystems.GearOutput;
import com.frcteam1939.steamworks2017.robot.subsystems.Lights;
import com.frcteam1939.steamworks2017.robot.subsystems.SmartDashboardSubsystem;
import com.frcteam1939.steamworks2017.robot.vision.Vision;
import com.frcteam1939.steamworks2017.util.DoNothing;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	//Creating Objects for Drivetrain, climbers, lights, gear intakes, etc.
	public static Drivetrain drivetrain;
	public static Climber climber;
	public static GearIntake gearIntake;
	public static GearOutput gearOutput;
	public static FuelIntake fuelIntake;
	public static FuelOutput fuelOutput;
	public static Lights lights;
	public static SmartDashboardSubsystem smartDashboard;
	{
		try {
			//setting Objects for Drivetrain, climbers, lights, gear intakes, etc.
			drivetrain = new Drivetrain();
			climber = new Climber();
			gearIntake = new GearIntake();
			gearOutput = new GearOutput();
			fuelIntake = new FuelIntake();
			fuelOutput = new FuelOutput();
			lights = new Lights();
			smartDashboard = new SmartDashboardSubsystem();
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	public static OI oi;
	// creates a pressure senser for pnumatics
	private static AnalogInput pressureSensor = new AnalogInput(RobotMap.pressureSensor);
	// current selected Auto
	private Command selectedCommand;
	// the auto that it will run
	private Command autonomousCommand;
	// the autonomous Chooser
	private SendableChooser<Command> chooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		// Creates a new OI for the joySticks and gamepad controller
		oi = new OI();
		//setting default Auto to do nothing
		this.chooser.addDefault("Do Nothing", new DoNothing());
		// adding auto codes to the selection menu
		this.chooser.addObject("Boiler Peg Cross", new BoilerPegCross());
		this.chooser.addObject("Boiler Peg Mid Field", new BoilerPegMidField());
		this.chooser.addObject("Slots Peg Cross", new SlotsPegCross());
		this.chooser.addObject("Slots Peg Mid Field", new SlotsPegMidField());
		this.chooser.addObject("Center Peg and Backup", new CenterPegBackup());
		this.chooser.addObject("Center Peg Boiler Mid Field", new CenterPegBoilerMidField());
		this.chooser.addObject("Center Peg Boiler Cross", new CenterPegBoilerCross());
		this.chooser.addObject("Center Peg Slots Mid Field", new CenterPegSlotsMidField());
		this.chooser.addObject("Center Peg Slots Cross", new CenterPegSlotsCross());
		//adding the auto chooser to the dashboard
		SmartDashboard.putData("Autonomous Chooser", this.chooser);
		// sends the maximum voltage and current voltage of the right and left drivetrain to dashboard
		SmartDashboard.putData(new FindMaxSpeed());
		// sends the rotation values to Dashboard
		SmartDashboard.putData(new FindTurnF());
		// sends the tune position value for PID to Dashboard
		SmartDashboard.putData(new TunePositionPID());
		// sends the tune turn value for PID to Dashboard
		SmartDashboard.putData(new TuneTurnPID());
		// sends the tune velosity value for PID to Dashboard
		SmartDashboard.putData(new TuneVelocityPID());
		// Starting up vision tracking and the camera streaming
		Vision.init();
	}

	@Override
	public void robotPeriodic() {
		// sets the selected command to the selected auto from the chooser
		this.selectedCommand = this.chooser.getSelected();
	}
	@Override
	public void disabledInit() {
		// disabling breaking
		Robot.drivetrain.disableBraking(); // Make robot not resist pushing while disabled
		// pulling up the parking brake
		Robot.drivetrain.brakeUp();
		//disables braking on the climbing mechanism
		Robot.climber.disableBraking();
		//closes the ramp for gears
		Robot.gearIntake.rampIn();
		// sets the gear output solinoid to false
		Robot.gearOutput.retract();
		// sets the fuel intake and outtake speed to 0
		Robot.fuelIntake.setOutput(0);
		Robot.fuelOutput.setOutput(0);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		//enabling Braking on climber and drivetrain
		Robot.drivetrain.enableBraking();
		Robot.climber.enableBraking();
		// setting the autonomous to the selected auto
		this.autonomousCommand = this.selectedCommand;
		//checks if the auto is null
		if (this.autonomousCommand != null) {
			// starting auto
			this.autonomousCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		//turning on breaking for climber and drivetrain
		Robot.drivetrain.enableBraking();
		Robot.climber.enableBraking();
		//Checking if there is an auto code
		if (this.autonomousCommand != null) {
			// shutting off the auto code
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
	/** 
	 * gets the pressure for pnumatics
	 * @return pressure
	 */
	public static double getPressure() {
		return 250.0 * (pressureSensor.getVoltage() / 5.0) - 25.0;
	}

}
