package com.frcteam1939.steamworks2017.robot;

import com.frcteam1939.steamworks2017.robot.commands.auton.BoilerPegBackup;
import com.frcteam1939.steamworks2017.robot.commands.auton.CenterPegBackup;
import com.frcteam1939.steamworks2017.robot.commands.auton.DriveToBlueSlotsPeg;
import com.frcteam1939.steamworks2017.robot.commands.auton.SlotsPegBackup;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DriveDistance;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.FindMaxSpeed;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.FindRightDriveF;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.FindTurnF;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.ResetGyro;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.TurnToAngle;
import com.frcteam1939.steamworks2017.robot.subsystems.Climber;
import com.frcteam1939.steamworks2017.robot.subsystems.Drivetrain;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelIntake;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelOutput;
import com.frcteam1939.steamworks2017.robot.subsystems.GearIntake;
import com.frcteam1939.steamworks2017.robot.subsystems.GearOutput;
import com.frcteam1939.steamworks2017.robot.subsystems.Lights;
import com.frcteam1939.steamworks2017.robot.subsystems.SmartDashboardSubsystem;
import com.frcteam1939.steamworks2017.util.DoNothing;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
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
		System.out.println("\n==========================================");
		System.out.println("         Steamwork2017 Intializing");
		oi = new OI();

		// this.chooser.addObject("Center Peg Backup", new CenterPegBackup());
		// this.chooser.addObject("Boiler Peg Backup", new BoilerPegBackup());
		// this.chooser.addObject("Slots Peg Backup", new SlotsPegBackup());
		this.chooser.addObject("Center Peg Backup", new CenterPegBackup());
		this.chooser.addObject("Drive To Bolier Peg Backup(Auto Color)", new BoilerPegBackup());
		this.chooser.addObject("Drive To Slots Beg Backup (Auto Color)", new SlotsPegBackup());
		this.chooser.addDefault("Do Nothing", new DoNothing());
		SmartDashboard.putData("Autonomous Chooser", this.chooser);

		SmartDashboard.putData(new FindMaxSpeed());
		SmartDashboard.putData(new FindTurnF());
		SmartDashboard.putData(new FindRightDriveF());
		SmartDashboard.putData(new DriveDistance(36));
		SmartDashboard.putData(new TurnToAngle(90));
		SmartDashboard.putData(new ResetGyro());

		UsbCamera cam = CameraServer.getInstance().startAutomaticCapture();
		cam.setResolution(320, 240);
		cam.setBrightness(10);

		SmartDashboard.putData(Scheduler.getInstance());

		System.out.println("           Finished Intializing");
		System.out.println("==========================================/n");
	}

	@Override
	public void robotPeriodic() {
		this.selectedCommand = this.chooser.getSelected();
		SmartDashboard.putString("Selected Autonomous", this.selectedCommand.getName());
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
