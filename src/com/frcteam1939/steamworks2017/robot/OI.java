package com.frcteam1939.steamworks2017.robot;

import com.frcteam1939.steamworks2017.robot.commands.drivetrain.BrakeDown;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.BrakeUp;
import com.frcteam1939.steamworks2017.robot.commands.fuelintake.SetFuelIntakeSpeed;
import com.frcteam1939.steamworks2017.robot.commands.fueloutput.SetFuelOutputSpeed;
import com.frcteam1939.steamworks2017.robot.commands.gearIntake.GearRampIn;
import com.frcteam1939.steamworks2017.robot.commands.gearIntake.GearRampOut;
import com.frcteam1939.steamworks2017.robot.commands.gearoutput.PushOutGear;
import com.frcteam1939.steamworks2017.robot.commands.gearoutput.RetractGearPusher;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelIntake;
import com.frcteam1939.steamworks2017.robot.subsystems.FuelOutput;
import com.frcteam1939.steamworks2017.util.Gamepad;

import edu.wpi.first.wpilibj.Joystick;

public class OI {

	public final Joystick left = new Joystick(0);
	public final Joystick right = new Joystick(1);

	public final Gamepad gamepad = new Gamepad(2);

	public OI() {
		this.gamepad.x.whenPressed(new GearRampOut());
		this.gamepad.a.whenPressed(new GearRampIn());
		this.gamepad.y.whenPressed(new PushOutGear());
		this.gamepad.b.whenPressed(new RetractGearPusher());
		this.gamepad.rightTrigger.whenPressed(new SetFuelOutputSpeed(FuelOutput.OUT_SPEED));
		this.gamepad.rightTrigger.whenReleased(new SetFuelOutputSpeed(0));
		this.gamepad.leftTrigger.whenPressed(new SetFuelIntakeSpeed(FuelIntake.IN_SPEED));
		this.gamepad.leftTrigger.whenReleased(new SetFuelIntakeSpeed(0));

		this.gamepad.start.whenPressed(new BrakeDown());
		this.gamepad.back.whenPressed(new BrakeUp());
	}

}
