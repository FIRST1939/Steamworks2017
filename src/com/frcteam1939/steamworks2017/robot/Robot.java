
package com.frcteam1939.steamworks2017.robot;

import org.opencv.core.Mat;

import com.frcteam1939.steamworks2017.robot.subsystems.Brake;
import com.frcteam1939.steamworks2017.robot.subsystems.Climber;
import com.frcteam1939.steamworks2017.robot.subsystems.Drivetrain;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelIntake;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelOutput;
import com.frcteam1939.steamworks2017.robot.subsystems.GearIntake;
import com.frcteam1939.steamworks2017.robot.subsystems.GearOutput;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.vision.CameraServer;
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
	public Pipeline pipe = new Pipeline();

	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture("cam0");
		oi = new OI();
		// chooser.addObject("My Auto", new MyAutoCommand());
		
//		new Thread(() -> {
//            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//            camera.setResolution(640, 480);
//            camera.setBrightness(10);
//            CvSink cvSink = CameraServer.getInstance().getVideo();
//            CvSource outputStream = CameraServer.getInstance().putVideo("Processed", 640, 480);
//            Mat source = new Mat();
//            while(!Thread.interrupted()) {
//                cvSink.grabFrame(source);
//                pipe.process(source);
//                outputStream.putFrame(pipe.rgbThresholdOutput());
//                
//            }
//        }).start();
		
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		this.autonomousCommand = this.chooser.getSelected();

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
