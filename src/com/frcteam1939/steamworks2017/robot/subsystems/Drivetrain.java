package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
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
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {

	public static final double WHEEL_BASE = 25; // Inches
	public static final double WHEEL_DIAMETER = 6; // Inches
	public static final double MAX_SPEED = 630; // RPM
	public static final double MAX_A = 6.0; // Max Acceleration in R/S^2
	public static final double MAX_JERK = 20.0; // R/S^3
	public static final int MP_UPDATE_MS = 20; // Time per MP frame
	public static final int CPR = 256; // Counts per Revolution of Encoders
	public static final int SIDEWINDER_CPR = 12;

	private static final int NATIVE_UNITS_PER_100MS = (int) (MAX_SPEED / 60.0 / 10.0 * (CPR * 4));

	private static final double velF = 1023.0 / NATIVE_UNITS_PER_100MS;
	private static final double velP = 1023.0 / MAX_SPEED;
	private static final double velI = 0;//velP / 1000.0;
	private static final double velD = 0;//velP * 10.0;

	private static final double posP = 1.0 / 4.0; // Full speed at 4 rev error
	private static final double posI = posP / 8000.0;
	private static final double posD = 0;// posP * 10.0;

	private static final double MAX_TURN_OUPUT = 0.5;
	private static final double turnF = 0; // Need to measure
	private static final double turnP = 0;
	private static final double turnI = 0;
	private static final double turnD = 0;

	private CANTalon frontLeft = new CANTalon(RobotMap.leftFrontTalon);
	private CANTalon midLeft = new CANTalon(RobotMap.leftMidTalon);
	private CANTalon backLeft = new CANTalon(RobotMap.leftBackTalon);
	private CANTalon frontRight = new CANTalon(RobotMap.rightFrontTalon);
	private CANTalon midRight = new CANTalon(RobotMap.rightMidTalon);
	private CANTalon backRight = new CANTalon(RobotMap.rightBackTalon);
	private CANTalon sidewinder = new CANTalon(RobotMap.sidewinderTalon);

	private DoubleSolenoid sidewinderSolenoid = new DoubleSolenoid(RobotMap.PCM, RobotMap.sidewinderDownSolenoid, RobotMap.sidewinderUpSolenoid);
	private Solenoid brake = new Solenoid(RobotMap.PCM, RobotMap.brakeDownSolenoid);

	private AHRS navx;

	private PIDController turnPID = new PIDController(turnP, turnI, turnD, this.navx, output -> {}, 10);

	private boolean driveBySpeed = true;

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

		this.sidewinder.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.sidewinder.configEncoderCodesPerRev(SIDEWINDER_CPR);
		this.sidewinder.setNominalClosedLoopVoltage(12.0);

		this.turnPID.setInputRange(-180, 180);
		this.turnPID.setContinuous();
		this.turnPID.setOutputRange(-MAX_TURN_OUPUT, MAX_TURN_OUPUT);
		this.turnPID.setSetpoint(0);
	}

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new DriveByJoystick());
	}

	/*
	 * Get Methods
	 */

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

	public double getLeftSpeed() {
		return this.frontLeft.getSpeed();
	}

	public double getRightSpeed() {
		return this.frontRight.getSpeed();
	}

	public double getSidewinderSpeed() {
		return this.sidewinder.getSpeed();
	}

	public double getLeftPosition() {
		return this.frontLeft.getPosition();
	}

	public double getRightPosition() {
		return this.frontRight.getPosition();
	}

	public double getSidewinderPosition() {
		return this.sidewinder.getPosition();
	}

	public double getLeftError() {
		return this.frontLeft.getClosedLoopError();
	}

	public double getRightError() {
		return this.frontRight.getClosedLoopError();
	}

	public double getSidewinderError() {
		return this.sidewinder.getClosedLoopError();
	}

	public double getLeftVolts() {
		return this.frontLeft.getOutputVoltage();
	}

	public double getRightVolts() {
		return this.frontRight.getOutputVoltage();
	}

	public double getSidewinderVolts() {
		return this.sidewinder.getOutputVoltage();
	}

	public double getHeading() {
		return this.navx.getYaw();
	}

	public double getTurnSpeed() {
		return this.navx.getRate();
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
		this.sidewinderSolenoid.set(DoubleSolenoid.Value.kForward);
	}

	public void sidewinderUp() {
		this.sidewinderSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	public void brakeDown() {
		this.brake.set(true);
	}

	public void brakeUp() {
		this.brake.set(false);
	}

	public void setVoltage(double voltage) {
		if (voltage != 0) {
			this.brakeUp();
		}
		setVoltage(this.frontLeft, voltage);
		setVoltage(this.frontRight, -voltage);
	}

	public void stop() {
		this.setPercentVBus(0, 0);
		this.strafe(0);
	}

	public void setPercentVBus(double left, double right) {
		if (left != 0 || right != 0) {
			this.brakeUp();
		}
		setPercentVBus(this.frontLeft, left);
		setPercentVBus(this.frontRight, right);
	}

	public void driveSpeed(double left, double right) {
		if (left != 0 || right != 0) {
			this.brakeUp();
		}
		setSpeed(this.frontLeft, left);
		setSpeed(this.frontRight, right);
	}

	public void strafe(double voltage) {
		setVoltage(this.sidewinder, voltage);
	}

	private boolean correcting = false;

	public void drive(double moveValue, double rotateValue, double strafeValue) {
		// Correct with gyro
		if (rotateValue == 0 && moveValue != 0) {
			if (!this.correcting) {
				this.navx.zeroYaw();
				this.correcting = true;
			}
			moveValue = strafeValue * 12 * turnF + this.turnPID.get();
		} else {
			this.correcting = false;
		}

		// Strafe with Sidewinder
		this.strafe(strafeValue * 12);

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
		if (this.driveBySpeed) {
			this.driveSpeed(leftMotorSpeed * MAX_SPEED, -rightMotorSpeed * MAX_SPEED);
		} else {
			this.setPercentVBus(leftMotorSpeed, -rightMotorSpeed);
		}
	}

	public void enableBraking() {
		this.frontLeft.enableBrakeMode(true);
		this.midLeft.enableBrakeMode(true);
		this.backLeft.enableBrakeMode(true);
		this.frontRight.enableBrakeMode(true);
		this.midRight.enableBrakeMode(true);
		this.backRight.enableBrakeMode(true);
		this.sidewinder.enableBrakeMode(true);
	}

	public void disableBraking() {
		this.frontLeft.enableBrakeMode(false);
		this.midLeft.enableBrakeMode(false);
		this.backLeft.enableBrakeMode(false);
		this.frontRight.enableBrakeMode(false);
		this.midRight.enableBrakeMode(false);
		this.backRight.enableBrakeMode(false);
		this.sidewinder.enableBrakeMode(false);
	}

	public void setDriveBySpeed(boolean driveBySpeed) {
		this.driveBySpeed = driveBySpeed;
	}

	/*
	 * Static Convenience Methods
	 */

	private static void setupMasterTalon(CANTalon talon) {
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder); // Tell Talon that a Quadrature Encoder is attached
		talon.configEncoderCodesPerRev(CPR); // Tell Talon the resolution of the encoder (how many signals per revolution)
		talon.configNominalOutputVoltage(+0.0f, -0.0f); // Set the minimum output of the Talon in volts
		talon.configPeakOutputVoltage(+12.0f, -12.0f); // Set the maximum output of the Talon in volts
		talon.setProfile(0); // Switch to profile 0 and save PIDF terms for velocity control
		talon.setP(velP);
		talon.setI(velI);
		talon.setD(velD);
		talon.setF(velF);
		talon.setIZone(0); // Disable IZone
		talon.setVoltageRampRate(24); // Set maximum change in volts per seconds
		talon.setProfile(1); // Switch to profile 1 and save PIDF terms for position control
		talon.setP(posP);
		talon.setI(posI);
		talon.setD(posD);
		talon.setF(velF);
		talon.setIZone(0); // Disable IZone
		talon.setVoltageRampRate(48); // Set maximum change in volts per seconds
		talon.changeMotionControlFramePeriod(MP_UPDATE_MS / 2); // No idea, directly from Talon Software Refrence
		talon.setNominalClosedLoopVoltage(12.0); // Make Talons compensate for changes in battery voltage
	}

	private static void setFollower(CANTalon talon, CANTalon master) {
		talon.changeControlMode(TalonControlMode.Follower); // Tell the Talon that it should follow another Talon
		talon.set(master.getDeviceID()); // Tell the Talon to follow the master Talon
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

	private static void setVoltage(CANTalon talon, double voltage) {
		talon.changeControlMode(TalonControlMode.Voltage);
		talon.setVoltageCompensationRampRate(24.0); // From Talon Software Refrence
		talon.set(voltage);
	}

	private static void setPercentVBus(CANTalon talon, double percentVBus) {
		talon.changeControlMode(TalonControlMode.PercentVbus);
		talon.set(percentVBus);
	}

}