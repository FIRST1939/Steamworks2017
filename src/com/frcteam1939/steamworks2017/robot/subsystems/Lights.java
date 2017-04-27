package com.frcteam1939.steamworks2017.robot.subsystems;

import com.frcteam1939.steamworks2017.robot.Robot;
import com.frcteam1939.steamworks2017.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lights extends Subsystem {

	private DigitalOutput one = new DigitalOutput(RobotMap.pinOne);
	private DigitalOutput two = new DigitalOutput(RobotMap.pinTwo);
	private DigitalOutput three = new DigitalOutput(RobotMap.pinThree);
	private DigitalOutput four = new DigitalOutput(RobotMap.pinFour);

	private boolean wantFuel = false;

	public Lights() {
		new Thread(() -> {
			while (!Thread.interrupted()) {
				int i;
				if (!DriverStation.getInstance().isDSAttached()) {
					i = 0;
				} else if (!DriverStation.getInstance().isEnabled()) {
					i = 1;
				} else {
					if (DriverStation.getInstance().isAutonomous()) {
						i = 2;
					} else if (Robot.gearOutput.onPeg()) {
						i = 8;
					} else if (Robot.gearIntake.haveGear()) {
						i = 6;
					} else if (Lights.this.wantFuel) {
						i = 12;
					} else {
						i = 4;
					}
					if (DriverStation.getInstance().getAlliance() == Alliance.Blue) {
						i++;
					}
				}
				SmartDashboard.putNumber("i", i);
				String s = Integer.toBinaryString(i);
				if (s.length() == 1) {
					s = "000" + s;
				}
				if (s.length() == 2) {
					s = "00" + s;
				}
				if (s.length() == 3) {
					s = "0" + s;
				}
				String[] a = s.split("");
				Lights.this.one.set(a[3].equals("1"));
				Lights.this.two.set(a[2].equals("1"));
				Lights.this.three.set(a[1].equals("1"));
				Lights.this.four.set(a[0].equals("1"));
				try {
					Thread.sleep(100);
				} catch (Exception e) {}
			}
		}).start();
	}

	@Override
	protected void initDefaultCommand() {}

	public void setWantFuel(boolean b) {
		this.wantFuel = b;
	}

}
