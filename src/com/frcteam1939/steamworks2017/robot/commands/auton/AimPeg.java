package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.vision.AimAngle;
import com.frcteam1939.steamworks2017.robot.vision.AimCenter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AimPeg extends CommandGroup {

    public AimPeg() {
        addSequential(new AimAngle());
        addSequential(new AimCenter());
    }
}
