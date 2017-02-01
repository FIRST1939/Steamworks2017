package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.MotionProfileStatus;
import com.ctre.CANTalon.SetValueMotionProfile;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.TrajectoryPoint;
import com.frcteam1939.steamworks2017.robot.RobotMap;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DriveByJoystick;
import com.frcteam1939.steamworks2017.util.MotionProfile;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {

	public static final double WHEEL_BASE = 32; // Inches
	public static final double WHEEL_DIAMETER = 6; // Inches
	public static final double MAX_SPEED = 630; // RPM
	public static final double MAX_A = 4.0; // Max Acceleration in R/S^2
	public static final double MAX_JERK = 16.0; // R/S^3
	public static final int MP_UPDATE_MS = 50; // Time per MP frame
	public static final int CPR = 256; // Counts per Revolution of Encoders

	private static final int MAX_ERROR_PER_100MS = 120;
	private static final int NATIVE_UNITS_PER_100MS = (int) (MAX_SPEED / 60.0 / 10.0 * (CPR * 4));

	private static final double velF = 1023.0 / NATIVE_UNITS_PER_100MS;
	private static final double velP = 1023.0 / 10.0 / MAX_ERROR_PER_100MS;
	private static final double velI = velP / 1000.0;
	private static final double velD = velP * 10.0;

	private static final double posP = 1.0 / 4.0; // Full speed at 5 rev error
	private static final double posI = posP / 8000.0;
	private static final double posD = 0;// posP * 10.0;

	private CANTalon frontLeft = new CANTalon(RobotMap.leftFrontTalon);
	private CANTalon midLeft = new CANTalon(RobotMap.leftMidTalon);
	private CANTalon backLeft = new CANTalon(RobotMap.leftBackTalon);
	private CANTalon frontRight = new CANTalon(RobotMap.rightFrontTalon);
	private CANTalon midRight = new CANTalon(RobotMap.rightMidTalon);
	private CANTalon backRight = new CANTalon(RobotMap.rightBackTalon);
	private CANTalon sidewinder = new CANTalon(RobotMap.sidewinderTalon);

	private DoubleSolenoid solenoid = new DoubleSolenoid(RobotMap.PCM, RobotMap.sidewinderDownSolenoid, RobotMap.sidewinderUpSolenoid);

	private AHRS navx;

	public Drivetrain() {
		// Setup Master Talons for Encoders and PID
		setupMasterTalon(this.frontLeft);
		setupMasterTalon(this.frontRight);

		// Make other Talons follow the Master Talons
		setFollower(this.midLeft, this.frontLeft);
		setFollower(this.backLeft, this.frontLeft);
		setFollower(this.midRight, this.frontRight);
		setFollower(this.backRight, this.frontRight);

		// Force Motion Profile Updates to be sent to Master Talons
		new Notifier(() -> {
			Drivetrain.this.frontLeft.processMotionProfileBuffer();
			Drivetrain.this.frontRight.processMotionProfileBuffer();
		}).startPeriodic(MP_UPDATE_MS / 2000.0);

		// Try to intialize the navX
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

	/*
	 * Get Methods
	 */

	public double getHeading() {
		return this.navx.getAngle();
	}

	public MotionProfileStatus getLeftMotionProfileStatus() {
		MotionProfileStatus status = new MotionProfileStatus();
		this.frontLeft.getMotionProfileStatus(status);
		return status;
	}

	public MotionProfileStatus getRightMotionProfileStatus() {
		MotionProfileStatus status = new MotionProfileStatus();
		this.frontRight.getMotionProfileStatus(status);
		return status;
	}

	/*
	 * Set Methods
	 */

	public void zeroEncoders() {
		this.frontLeft.setEncPosition(0);
		this.frontRight.setEncPosition(0);
	}

	public void setMotionProfile(MotionProfile profile) {
		sendMotionProfile(this.frontLeft, profile.getLeftPoints());
		sendMotionProfile(this.frontRight, profile.getRightPoints());
	}

	public void startMotionProfile() {
		this.zeroEncoders();
		startMotionProfile(this.frontLeft);
		startMotionProfile(this.frontRight);
	}

	public void stopMotionProfile() {
		stopMotionProfile(this.frontLeft);
		stopMotionProfile(this.frontRight);
	}

	public void sidewinderDown() {
		this.solenoid.set(DoubleSolenoid.Value.kForward);
	}

	public void sidewinderUp() {
		this.solenoid.set(DoubleSolenoid.Value.kReverse);
	}

	public void driveSpeed(double left, double right) {
		setSpeed(this.frontLeft, left);
		setSpeed(this.frontRight, right);
	}

	public void drive(double moveValue, double rotateValue, double strafeValue) {
		// Strafe with Sidewinder
		if (strafeValue == 0) {
			this.sidewinderUp();
		} else {
			this.sidewinderDown();
		}
		this.sidewinder.set(strafeValue);

		// Calculate left and right speeds from move and rotate values
		double leftMotorSpeed;
		double rightMotorSpeed;
		if (moveValue > 0.0) {
			if (rotateValue > 0.0) {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = Math.max(moveValue, rotateValue);
			} else {
				leftMotorSpeed = Math.max(moveValue, -rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			}
		} else {
			if (rotateValue > 0.0) {
				leftMotorSpeed = -Math.max(-moveValue, rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			} else {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
			}
		}

		// Tell Drivetrain to move
		this.driveSpeed(leftMotorSpeed * MAX_SPEED, -rightMotorSpeed * MAX_SPEED);
	}

	/*
	 * Static Convience Methods
	 */

	private static void setupMasterTalon(CANTalon talon) {
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talon.configEncoderCodesPerRev(CPR);
		talon.configNominalOutputVoltage(+0.0f, -0.0f);
		talon.configPeakOutputVoltage(+12.0f, -12.0f);
		talon.setProfile(0);
		talon.setF(velF);
		talon.setP(velP);
		talon.setI(velI);
		talon.setD(velD);
		talon.setProfile(1);
		talon.setF(velF);
		talon.setP(posP);
		talon.setI(posI);
		talon.setD(posD);
		talon.setVoltageRampRate(24);
		talon.changeMotionControlFramePeriod(MP_UPDATE_MS / 2);
	}

	private static void setFollower(CANTalon talon, CANTalon master) {
		talon.changeControlMode(TalonControlMode.Follower);
		talon.set(master.getDeviceID());
	}

	private static void sendMotionProfile(CANTalon talon, TrajectoryPoint[] points) {
		talon.clearMotionProfileTrajectories();
		talon.clearMotionProfileHasUnderrun();
		for (TrajectoryPoint point : points) {
			talon.pushMotionProfileTrajectory(point);
		}
	}

	private static void startMotionProfile(CANTalon talon) {
		talon.setProfile(1);
		talon.changeControlMode(TalonControlMode.MotionProfile);
		talon.set(SetValueMotionProfile.Enable.value);
	}

	private static void stopMotionProfile(CANTalon talon) {
		talon.setProfile(1);
		talon.changeControlMode(TalonControlMode.MotionProfile);
		talon.set(SetValueMotionProfile.Disable.value);
	}

	private static void setSpeed(CANTalon talon, double speed) {
		talon.setProfile(0);
		talon.changeControlMode(TalonControlMode.Speed);
		talon.set(speed);
	}

}