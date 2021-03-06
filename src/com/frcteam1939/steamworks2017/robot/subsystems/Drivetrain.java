package com.frcteam1939.steamworks2017.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.frcteam1939.steamworks2017.robot.RobotMap;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DriveByJoystick;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends Subsystem {

	public static final double WHEEL_DIAMETER = 6; // Inches
	public static final double MAX_SPEED = 430; // RPM
	public static final double MAX_A = 3.0; // Max Acceleration in R/S^2
	public static final double MAX_JERK = MAX_A * 4; // R/S^3

	public static final int MP_UPDATE_MS = 20; // Time per MP frame
	public static final int CPR = 256; // Counts per Revolution of Encoders
	public static final int SIDEWINDER_CPR = 12;

	private static final int NATIVE_UNITS_PER_100MS = (int) (MAX_SPEED / 60.0 / 10.0 * (CPR * 4));

	public static final double velF = 1.4;
	private static final double velFRightAdjustment = 0.0;
	private static final double velP = 0.8999999999999999;
	private static final double velI = 0;
	private static final double velD = 0.0312500000025;

	private static final double posP = 0.4;
	private static final double posI = 0.0;
	private static final double posD = 0.0;

	private static final double MAX_TURN_OUPUT = 0.25;
	private static final double turnF = 0.071;
	private static final double turnP = 0.02;
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
	private DoubleSolenoid brake = new DoubleSolenoid(RobotMap.PCM, RobotMap.brakeDownSolenoid, RobotMap.brakeUpSolenoid);

	private AHRS navx;
	public PIDController posPID;
	public PIDController turnPID;

	private boolean driveBySpeed = true;

	public Drivetrain() {
		// Setup Master Talons for Encoders and PID
		this.resetConfigs();

		// Make other Talons follow the Master Talons
		setFollower(this.midLeft, this.frontLeft);
		setFollower(this.backLeft, this.frontLeft);
		setFollower(this.midRight, this.frontRight);
		setFollower(this.backRight, this.frontRight);

		this.frontLeft.setPIDSourceType(PIDSourceType.kDisplacement);
		this.posPID = new PIDController(posP, posI, posD, this.frontLeft, output -> {});
		this.posPID.setOutputRange(-0.4, 0.4);

		this.navx = new AHRS(SPI.Port.kMXP);
		this.navx.setPIDSourceType(PIDSourceType.kDisplacement);
		this.turnPID = new PIDController(turnP, turnI, turnD, this.navx, output -> {});
		this.turnPID.setInputRange(-180, 180);
		this.turnPID.setContinuous(true);
		this.turnPID.setOutputRange(-MAX_TURN_OUPUT, MAX_TURN_OUPUT);
		this.turnPID.setSetpoint(0);
		this.turnPID.enable();

		this.sidewinder.setNominalClosedLoopVoltage(12.0);
	}

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new DriveByJoystick());
	}

	/*
	 * Get Methods
	 */

	public boolean isMoving() {
		return Math.abs(this.getLeftSpeed()) > 1 || Math.abs(this.getRightSpeed()) > 1;
	}

	public double getLeftSpeed() {
		return this.frontLeft.getSpeed();
	}

	public double getRightSpeed() {
		return this.frontRight.getSpeed();
	}

	public double getLeftPosition() {
		return this.frontLeft.getPosition();
	}

	public double getRightPosition() {
		return this.frontRight.getPosition();
	}

	public double getLeftError() {
		return this.frontLeft.getClosedLoopError();
	}

	public double getRightError() {
		return this.frontRight.getClosedLoopError();
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
		if (this.navx.isConnected()) return this.navx.pidGet();
		else return 0;
	}

	public double getTurnSpeed() {
		if (this.navx.isConnected()) return this.navx.getRate();
		else return 0;
	}

	/*
	 * Set Methods
	 */

	public void zeroEncoders() {
		this.frontLeft.setEncPosition(0);
		this.frontRight.setEncPosition(0);
	}

	public void sidewinderDown() {
		this.sidewinderSolenoid.set(DoubleSolenoid.Value.kForward);
	}

	public void sidewinderUp() {
		this.sidewinderSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	public void brakeDown() {
		this.brake.set(Value.kForward);
	}

	public void brakeUp() {
		this.brake.set(Value.kReverse);
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

	public void resetGyro() {
		if (this.navx != null) {
			this.navx.reset();
		}
	}

	private boolean correcting = false;

	public void drive(double moveValue, double rotateValue, double strafeValue) {
		// Correct with gyro
		if (rotateValue == 0 && (moveValue != 0 || strafeValue != 0)) {
			if (!this.correcting) {
				this.resetGyro();
				this.turnPID.reset();
				this.turnPID.enable();
				this.turnPID.setSetpoint(0);
				this.correcting = true;
			}
			rotateValue = this.turnPID.get() + turnF * strafeValue;
		} else if (this.correcting) {
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
			this.driveSpeed(-leftMotorSpeed * MAX_SPEED, rightMotorSpeed * MAX_SPEED);
		} else {
			this.setPercentVBus(-leftMotorSpeed, rightMotorSpeed);
		}
		SmartDashboard.putNumber("Move Output", moveValue);
		SmartDashboard.putNumber("Turn Output", rotateValue);
		SmartDashboard.putNumber("Strafe Output", strafeValue);

		SmartDashboard.putNumber("Turn Setpoint", this.turnPID.getSetpoint());
		SmartDashboard.putNumber("Turn PID Output", this.turnPID.get());
		SmartDashboard.putNumber("Pos Setpoint", this.posPID.getSetpoint());
		SmartDashboard.putNumber("Pos PID Input", this.frontLeft.pidGet());
		SmartDashboard.putNumber("Pos PID Output", this.posPID.get());
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

	public void setVelocityPID(double P, double I, double D) {
		setPID(this.frontLeft, 0, P, I, D, velF);
		setPID(this.frontRight, 0, P, I, D, velF);
	}

	public void setPositionPID(double P, double I, double D) {
		setPID(this.frontLeft, 1, P, I, D, 0);
		setPID(this.frontRight, 1, P, I, D, 0);
	}

	public void setTurnPID(double P, double I, double D) {
		this.turnPID.setPID(P, I, D);
	}

	public void setRightF(double F) {
		this.frontRight.setProfile(0);
		this.frontRight.setF(F);
		this.frontRight.setProfile(1);
		this.frontRight.setF(F);
	}

	public void resetConfigs() {
		setupMasterTalon(this.frontLeft, velF);
		setupMasterTalon(this.frontRight, velF + velFRightAdjustment);
	}

	public void setRampRate(double left, double right) {
		this.frontLeft.setVoltageRampRate(left);
		this.frontRight.setVoltageRampRate(right);
	}

	public void resetRampRate() {
		this.frontLeft.setVoltageRampRate(MAX_JERK / MAX_A * 12);
		this.frontRight.setVoltageRampRate(MAX_JERK / MAX_A * 12);
	}

	/*
	 * Static Convenience Methods
	 */

	private static void setPID(CANTalon talon, int profile, double P, double I, double D, double F) {
		talon.setProfile(profile); // Switch to profile 0 and save PIDF terms for velocity control
		talon.setP(P);
		talon.setI(I);
		talon.setD(D);
		talon.setF(F);
		talon.setIZone(0);
		talon.setVoltageRampRate(MAX_JERK / MAX_A * 12);
	}

	private static void setupMasterTalon(CANTalon talon, double vF) {
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder); // Tell Talon that a Quadrature Encoder is attached
		talon.configEncoderCodesPerRev(CPR); // Tell Talon the resolution of the encoder (how many signals per revolution)
		talon.reverseSensor(true);
		talon.configNominalOutputVoltage(+0.0f, -0.0f); // Set the minimum output of the Talon in volts
		talon.configPeakOutputVoltage(+12.0f, -12.0f); // Set the maximum output of the Talon in volts
		setPID(talon, 0, velP, velI, velD, vF);
		setPID(talon, 1, posP, posI, posD, vF);
		talon.changeMotionControlFramePeriod(MP_UPDATE_MS / 2); // No idea, directly from Talon Software Refrence
		talon.setNominalClosedLoopVoltage(12.0); // Make Talons compensate for changes in battery voltage
		talon.setMotionMagicCruiseVelocity(Drivetrain.MAX_SPEED * 0.7);
		talon.setMotionMagicAcceleration(Drivetrain.MAX_A * 0.7 * 60);
	}

	private static void setFollower(CANTalon talon, CANTalon master) {
		talon.changeControlMode(TalonControlMode.Follower); // Tell the Talon that it should follow another Talon
		talon.set(master.getDeviceID()); // Tell the Talon to follow the master Talon
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