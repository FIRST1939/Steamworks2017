package com.frcteam1939.steamworks2017.robot.commands.auton;

import com.frcteam1939.steamworks2017.robot.Paths;
import com.frcteam1939.steamworks2017.robot.commands.drivetrain.DrivePath;
import com.frcteam1939.steamworks2017.robot.vision.AimForCenter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionCenterPeg extends CommandGroup {

    public VisionCenterPeg() {
        addSequential(new AimForCenter());
        addSequential(new DriveUntilLimit());
        addSequential(new PlaceGear());
    }
}
