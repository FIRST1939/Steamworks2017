package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.frcteam1939.steamworks2017.robot.RobotMap;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DriveByJoystick;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {

	private CANTalon leftFront = new CANTalon(RobotMap.leftFrontTalon);
	private CANTalon leftMid = new CANTalon(RobotMap.leftMidTalon);
	private CANTalon leftBack = new CANTalon(RobotMap.leftBackTalon);
	private CANTalon rightFront = new CANTalon(RobotMap.rightFrontTalon);
	private CANTalon rightMid = new CANTalon(RobotMap.rightMidTalon);
	private CANTalon rightBack = new CANTalon(RobotMap.rightBackTalon);
	private CANTalon sidewinder = new CANTalon(RobotMap.sidewinderTalon);

	private RobotDrive drive = new RobotDrive(this.leftFront, this.rightFront);

	private DoubleSolenoid sidewinderModule = new DoubleSolenoid(RobotMap.PCM, RobotMap.sidewinderDownSolenoid,
			RobotMap.sidewinderUpSolenoid);

	private int frontEncodersCPR = 132;
	private int sidewinderEncoderCPR = 12;

	private AHRS navx;

	public Drivetrain() {
		this.leftFront.setControlMode(CANTalon.TalonControlMode.PercentVbus.value);
		this.leftMid.setControlMode(CANTalon.TalonControlMode.Follower.value);
		this.leftMid.set(RobotMap.leftFrontTalon);
		this.leftBack.setControlMode(CANTalon.TalonControlMode.Follower.value);
		this.leftBack.set(RobotMap.leftFrontTalon);

		this.rightFront.setControlMode(CANTalon.TalonControlMode.PercentVbus.value);
		this.rightMid.setControlMode(CANTalon.TalonControlMode.Follower.value);
		this.rightMid.set(RobotMap.rightFrontTalon);
		this.rightBack.setControlMode(CANTalon.TalonControlMode.Follower.value);
		this.rightBack.set(RobotMap.leftFrontTalon);

		this.sidewinder.setControlMode(CANTalon.TalonControlMode.PercentVbus.value);

		this.leftFront.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.leftFront.configEncoderCodesPerRev(this.frontEncodersCPR);
		this.rightFront.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.rightFront.configEncoderCodesPerRev(this.frontEncodersCPR);
		this.sidewinder.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.sidewinder.configEncoderCodesPerRev(this.sidewinderEncoderCPR);

		try {
			this.navx = new AHRS(SerialPort.Port.kMXP);
		} catch (Exception e) {
			System.out.println("ERROR: Couldn't intialize navX");
			e.printStackTrace();
		}
	}

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new DriveByJoystick());
	}

	public void drive(double move, double rotate, double strafe) {
		// Not Implemented
	}

	public void sidewinderDown() {
		this.sidewinderModule.set(Value.kForward);
	}

	public void sidewinderUp() {
		this.sidewinderModule.set(Value.kReverse);
	}

	public double getSpeedLeftFront() {
		return this.leftFront.getSpeed();
	}

	public double getSpeedRightFront() {
		return this.rightFront.getSpeed();
	}

	public double getSpeedSidewinder() {
		return this.sidewinder.getSpeed();
	}
}
