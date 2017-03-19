package com.frcteam1939.steamworks2017.robot;

import com.frcteam1939.steamworks2017.robot.commands.auton.BoilerPegBackup;
import com.frcteam1939.steamworks2017.robot.commands.auton.BoilerPegCross;
import com.frcteam1939.steamworks2017.robot.commands.auton.BoilerPegMidField;
import com.frcteam1939.steamworks2017.robot.commands.auton.CenterPegBackup;
import com.frcteam1939.steamworks2017.robot.commands.auton.CenterPegBoilerCross;
import com.frcteam1939.steamworks2017.robot.commands.auton.CenterPegBoilerMidField;
import com.frcteam1939.steamworks2017.robot.commands.auton.CenterPegSlotsCross;
import com.frcteam1939.steamworks2017.robot.commands.auton.CenterPegSlotsMidField;
import com.frcteam1939.steamworks2017.robot.commands.auton.SlotsPegBackup;
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
	private static AnalogInput pressureSensor = new AnalogInput(RobotMap.pressureSensor);

	private Command selectedCommand;
	private Command autonomousCommand;
	private SendableChooser<Command> chooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		oi = new OI();

		this.chooser.addDefault("Do Nothing", new DoNothing());
		this.chooser.addObject("Boiler Peg Cross", new BoilerPegCross());
		this.chooser.addObject("Boiler Peg Mid Field", new BoilerPegMidField());
		this.chooser.addObject("Slots Peg Cross", new SlotsPegCross());
		this.chooser.addObject("Slots Peg Mid Field", new SlotsPegMidField());
		this.chooser.addObject("Center Peg and Backup", new CenterPegBackup());
		this.chooser.addObject("Center Peg Boiler Mid Field", new CenterPegBoilerMidField());
		this.chooser.addObject("Center Peg Boiler Cross", new CenterPegBoilerCross());
		this.chooser.addObject("Center Peg Slots Mid Field", new CenterPegSlotsMidField());
		this.chooser.addObject("Center Peg Slots Cross", new CenterPegSlotsCross());
		this.chooser.addObject("Boiler Peg Backup", new BoilerPegBackup());
		this.chooser.addObject("Slots Peg Backup", new SlotsPegBackup());
		SmartDashboard.putData("Autonomous Chooser", this.chooser);
		SmartDashboard.putData(new FindMaxSpeed());
		SmartDashboard.putData(new FindTurnF());
		SmartDashboard.putData(new TunePositionPID());
		SmartDashboard.putData(new TuneTurnPID());
		SmartDashboard.putData(new TuneVelocityPID());

		Vision.init();
	}

	@Override
	public void robotPeriodic() {
		this.selectedCommand = this.chooser.getSelected();
	}

	@Override
	public void disabledInit() {
		Robot.drivetrain.disableBraking(); // Make robot not resist pushing while disabled
		Robot.drivetrain.brakeUp();
		Robot.climber.disableBraking();
		Robot.gearIntake.rampIn();
		Robot.gearOutput.retract();
		Robot.fuelIntake.setOutput(0);
		Robot.fuelOutput.setOutput(0);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		Robot.drivetrain.enableBraking();
		Robot.climber.enableBraking();
		this.autonomousCommand = this.selectedCommand;
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
		Robot.drivetrain.enableBraking();
		Robot.climber.enableBraking();
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

	public static double getPressure() {
		return 250.0 * (pressureSensor.getVoltage() / 5.0) - 25.0;
	}

}
