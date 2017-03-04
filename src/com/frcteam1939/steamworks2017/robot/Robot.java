package com.frcteam1939.steamworks2017.robot;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import com.frcteam1939.steamworks2017.robot.commands.auton.PlaceGearAndBackup;
import com.frcteam1939.steamworks2017.robot.commands.auton.PlaceGearAndCross;
import com.frcteam1939.steamworks2017.robot.commands.auton.PlaceGearHopperBoilerBlue;
import com.frcteam1939.steamworks2017.robot.commands.auton.PlaceGearHopperBoilerRed;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.FindMaxSpeed;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.FindTurnF;

import com.frcteam1939.steamworks2017.robot.commands.vision.VisionProcessing;

import com.frcteam1939.steamworks2017.robot.commands.drivetrain.TunePositionPID;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.TuneTurnPID;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.TuneVelocityPID;
import com.frcteam1939.steamworks2017.robot.subsystems.Climber;
import com.frcteam1939.steamworks2017.robot.subsystems.Drivetrain;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelIntake;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelOutput;
import com.frcteam1939.steamworks2017.robot.subsystems.GearIntake;
import com.frcteam1939.steamworks2017.robot.subsystems.GearOutput;
import com.frcteam1939.steamworks2017.robot.subsystems.SmartDashboardSubsystem;
import com.frcteam1939.steamworks2017.util.DoNothing;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class Robot extends IterativeRobot {

	public static Drivetrain drivetrain;
	public static Climber climber;
	public static GearIntake gearIntake;
	public static GearOutput gearOutput;
	public static FuelIntake fuelIntake;
	public static FuelOutput fuelOutput;
	public static SmartDashboardSubsystem smartDashboard;
	{
		try {
			drivetrain = new Drivetrain();
			climber = new Climber();
			gearIntake = new GearIntake();
			gearOutput = new GearOutput();
			fuelIntake = new FuelIntake();
			fuelOutput = new FuelOutput();
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
	public VisionProcessing processing = new VisionProcessing();
	public Pipe pipe = new Pipe();
	public final int IMG_WIDTH = 640;
	public final int IMG_HEIGHT = 480;
	public static double centerX = 0.0;
	public static double angle;
	private final Object imgLock = new Object();

	@Override
	public void robotInit() {
		oi = new OI();
		
		this.chooser.addDefault("Do Nothing", new DoNothing());
		this.chooser.addObject("Center Peg Backup", new PlaceGearAndBackup(Paths.centerToCenterPeg));
		this.chooser.addObject("Boiler Peg Backup", new PlaceGearAndBackup(Paths.boilerToBoilerPeg));
		this.chooser.addObject("Slots Peg Backup", new PlaceGearAndBackup(Paths.boilerToSlots));
		this.chooser.addObject("Boiler Peg Cross", new PlaceGearAndCross(Paths.boilerToBoilerPeg, Paths.boilerToSlots));
		this.chooser.addObject("Slots Peg Cross", new PlaceGearAndCross(Paths.slotsToSlotsPeg, Paths.slotsToSlots));
		this.chooser.addObject("Red Gear/Hopper/Boiler", new PlaceGearHopperBoilerRed());
		this.chooser.addObject("Blue Gear/Hopper/Boiler", new PlaceGearHopperBoilerBlue());
		SmartDashboard.putData("Autonomous Chooser", this.chooser);
		SmartDashboard.putData(new FindMaxSpeed());
		SmartDashboard.putData(new FindTurnF());
		UsbCamera cam = CameraServer.getInstance().startAutomaticCapture();
		cam.setResolution(IMG_WIDTH, IMG_HEIGHT);
		cam.setBrightness(10);
		VisionThread vision = new VisionThread(cam, pipe, pipeline -> {
			if (pipeline.filterContoursOutput().size() == 2) {
	            Rect r = Imgproc.boundingRect(pipe.filterContoursOutput().get(0));
	            Rect r1 = Imgproc.boundingRect(pipe.filterContoursOutput().get(1));
	            double center = (r.x + (r.x + (r1.x +r1.width)))/2 -612;
	            //finding the angle
	            double constant = 8.5 / Math.abs(r.x -(r1.x + r1.width) );
				double angleToGoal = 0;
					//Looking for the 2 blocks to actually start trig
				if(pipeline.filterContoursOutput().size() == 2){

					
						// this calculates the distance from the center of goal to center of webcam 
						double distanceFromCenterPixels= (center);
						// Converts pixels to inches using the constant from above.
						double distanceFromCenterInch = distanceFromCenterPixels * constant;
						angleToGoal = Math.atan(distanceFromCenterInch / (5738/Math.abs(r.x -(r1.x + r1.width))));
						angleToGoal = Math.toDegrees(angleToGoal);
						
						// prints angle
				
						}
					
	            
	            synchronized (imgLock) {
	                centerX = center;
	                angle = angleToGoal;
	                System.out.println("Center X: " +centerX);
	                System.out.println("Angle: " + angle);
	            }
			}
			
			 
        });
		vision.start();
		SmartDashboard.putData(new TunePositionPID());
		SmartDashboard.putData(new TuneTurnPID());
		SmartDashboard.putData(new TuneVelocityPID());
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
		return 250.0 * (pressureSensor.getAverageVoltage() / 5.0) - 25.0;
	}

}
