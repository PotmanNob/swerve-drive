// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;
import frc.robot.Constants;


import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import swervelib.SwerveDrive;
import swervelib.math.SwerveMath;
import swervelib.parser.SwerveParser;

/**
* The VM is configured to automatically run this class, and to call the functions corresponding to
* each mode, as described in the TimedRobot documentation. If you change the name of this class or
* the package after creating this project, you must also update the build.gradle file in the
* project.
*/
public class Robot extends TimedRobot {
/**
* This function is run when the robot is first started up and should be used for any
* initialization code.
*/

//initalize xbox controller
    XboxController controller = new XboxController(0);

    boolean isFieldRelative = true;

    SwerveDrive swerveDrive;

    @Override
    public void robotInit() {
        try {
            File swerveParserFile = new File(Filesystem.getDeployDirectory(), "swerve/neokraken");
            SwerveParser swerveParser = new SwerveParser(swerveParserFile);

            double driveConversionFactor = SwerveMath.calculateMetersPerRotation(Constants.Drive.wheelDiameter, Constants.Drive.driveGearRatio, 1);
            double angleConversionFactor = SwerveMath.calculateDegreesPerSteeringRotation(Constants.Drive.angleGearRatio, 1);

            swerveDrive = swerveParser.createSwerveDrive(Constants.Drive.maxSpeed, angleConversionFactor, driveConversionFactor); // random num
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void robotPeriodic() {}

    @Override
    public void autonomousInit() {}

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {}

    @Override
    public void teleopPeriodic() {
        if (controller.getAButtonPressed())
        {
            isFieldRelative = isFieldRelative ? false : true;
        }

        //controls are amplified
        double yMovement = controller.getLeftY() * -3; //inversed y movement
        double xMovement = controller.getLeftX() * 3;
        double rotation = controller.getRightX() * 2;

        swerveDrive.drive(new Translation2d(xMovement, yMovement), rotation, isFieldRelative, false);
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void testInit() {}

    @Override
    public void testPeriodic() {}

    @Override
    public void simulationInit() {}

    @Override
    public void simulationPeriodic() {}
}
