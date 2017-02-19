
package com.frcteam1939.steamworks2017.robot;

import org.opencv.core.Mat;

import com.frcteam1939.steamworks2017.robot.commands.auton.PlaceGearAndBackup;
import com.frcteam1939.steamworks2017.robot.commands.auton.PlaceGearAndCross;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.FindMaxSpeed;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.FindTurnF;
import com.frcteam1939.steamworks2017.robot.commands.vision.GRIPipe;
import com.frcteam1939.steamworks2017.robot.commands.vision.SendAngle;
import com.frcteam1939.steamworks2017.robot.commands.vision.SendCenterX;
import com.frcteam1939.steamworks2017.robot.commands.vision.VisionProcessing;
import com.frcteam1939.steamworks2017.robot.subsystems.Climber;
import com.frcteam1939.steamworks2017.robot.subsystems.Drivetrain;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelIntake;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelOutput;
import com.frcteam1939.steamworks2017.robot.subsystems.GearIntake;
import com.frcteam1939.steamworks2017.robot.subsystems.GearOutput;
import com.frcteam1939.steamworks2017.robot.subsystems.SmartDashboardSubsystem;
import com.frcteam1939.steamworks2017.util.DoNothing;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static final Drivetrain drivetrain = new Drivetrain();
	public static final Climber climber = new Climber();
	public static final GearIntake gearIntake = new GearIntake();
	public static final GearOutput gearOutput = new GearOutput();
	public static final FuelIntake fuelIntake = new FuelIntake();
	public static final FuelOutput fuelOutput = new FuelOutput();
	public static final SmartDashboardSubsystem smartDashboard = new SmartDashboardSubsystem();

	public static OI oi;
	public GRIPipe pipe = new GRIPipe();
	private Command selectedCommand;
	private Command autonomousCommand;
	private SendableChooser<Command> chooser = new SendableChooser<>();
	public VisionProcessing processing = new VisionProcessing();

	@Override
	public void robotInit() {
		oi = new OI();
		new Thread(() -> {
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
            camera.setResolution(640, 480);
            camera.setBrightness(10);
            CvSink cvSink = CameraServer.getInstance().getVideo();
            CvSource outputStream = CameraServer.getInstance().putVideo("Processed", 640, 480);
            Mat source = new Mat();
            while(!Thread.interrupted()) {
                cvSink.grabFrame(source);
                pipe.process(source);
                outputStream.putFrame(pipe.rgbThresholdOutput());
            }
        }).start();
		this.chooser.addDefault("Do Nothing", new DoNothing());
		this.chooser.addObject("Center Peg Backup", new PlaceGearAndBackup(Paths.centerToCenterPeg));
		this.chooser.addObject("Boiler Peg Backup", new PlaceGearAndBackup(Paths.boilerToBoilerPeg));
		this.chooser.addObject("Slots Peg Backup", new PlaceGearAndBackup(Paths.boilerToSlots));
		this.chooser.addObject("Boiler Peg Cross", new PlaceGearAndCross(Paths.boilerToBoilerPeg, Paths.boilerToSlots));
		this.chooser.addObject("Slots Peg Cross", new PlaceGearAndCross(Paths.slotsToSlotsPeg, Paths.slotsToSlots));
		SmartDashboard.putData("Autonomous Chooser", this.chooser);
		SmartDashboard.putData(new FindMaxSpeed());
		SmartDashboard.putData(new FindTurnF());
		SmartDashboard.putData(new SendCenterX(pipe, processing));
		SmartDashboard.putData(new SendAngle(pipe, processing));
	}

	@Override
	public void robotPeriodic() {
		this.selectedCommand = this.chooser.getSelected();
	}

	@Override
	public void disabledInit() {
		Robot.drivetrain.disableBraking(); // Make robot not resist pushing while disabled
		Robot.drivetrain.brakeUp();
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
