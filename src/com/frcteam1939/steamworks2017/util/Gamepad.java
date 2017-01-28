package com.frcteam1939.steamworks2017.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Gamepad extends Joystick {

	private static final int X_BUTTON = 1;
	private static final int A_BUTTON = 2;
	private static final int B_BUTTON = 3;
	private static final int Y_BUTTON = 4;
	private static final int LEFT_BUTTON = 5;
	private static final int RIGHT_BUTTON = 6;
	private static final int LEFT_TRIGGER = 7;
	private static final int RIGHT_TRIGGER = 8;
	private static final int BACK_BUTTON = 9;
	private static final int START_BUTTON = 10;
	private static final int LEFT_JOYSTICK_BUTTON = 11;
	private static final int RIGHT_JOYSTICK_BUTTON = 12;

	private static final int LEFT_X = 0;
	private static final int LEFT_Y = 1;
	private static final int RIGHT_X = 2;
	private static final int RIGHT_Y = 3;

	public final JoystickButton x = new JoystickButton(this, X_BUTTON);
	public final JoystickButton a = new JoystickButton(this, A_BUTTON);
	public final JoystickButton b = new JoystickButton(this, B_BUTTON);
	public final JoystickButton y = new JoystickButton(this, Y_BUTTON);
	public final JoystickButton leftButton = new JoystickButton(this, LEFT_BUTTON);
	public final JoystickButton rightButton = new JoystickButton(this, RIGHT_BUTTON);
	public final JoystickButton leftTrigger = new JoystickButton(this, LEFT_TRIGGER);
	public final JoystickButton rightTrigger = new JoystickButton(this, RIGHT_TRIGGER);
	public final JoystickButton back = new JoystickButton(this, BACK_BUTTON);
	public final JoystickButton start = new JoystickButton(this, START_BUTTON);
	public final JoystickButton leftJoystick = new JoystickButton(this, LEFT_JOYSTICK_BUTTON);
	public final JoystickButton rightJoystick = new JoystickButton(this, RIGHT_JOYSTICK_BUTTON);

	public Gamepad(int port) {
		super(port);
	}

	public double getLeftX() {
		return getRawAxis(LEFT_X);
	}

	public double getLeftY() {
		return getRawAxis(LEFT_Y);
	}

	public double getRightX() {
		return getRawAxis(RIGHT_X);
	}

	public double getRightY() {
		return getRawAxis(RIGHT_Y);
	}

}
