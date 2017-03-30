package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.Paths;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DrivePath;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionCenterPeg extends CommandGroup {

    public VisionCenterPeg() {
        addSequential(new AimPeg());
        addSequential(new DrivePath(Paths.centerToCenterPeg));
        addSequential(new PlaceGear());
    }
}
