// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.lang.annotation.ElementType;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.AlgaePivot;
import frc.robot.subsystems.AlgaeSpinner;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralPivot;
import frc.robot.subsystems.CoralSpinner;
import frc.robot.subsystems.Elevator;

public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandXboxController joystick = new CommandXboxController(0);

    private final Joystick xBox = new Joystick(1);
    private final JoystickButton algaeUp = new JoystickButton(xBox, 1);
    private final JoystickButton algaeDown = new JoystickButton(xBox, 2);

    private final JoystickButton algaeReach= new JoystickButton(xBox, 6);
    private final JoystickButton algaeScore = new JoystickButton(xBox, 6);
    private final JoystickButton algaeStow = new JoystickButton(xBox, 6);

    
    private final JoystickButton algaeSpinnerOut = new JoystickButton(xBox, 3);
    private final JoystickButton algaeSpinnerIn = new JoystickButton(xBox, 4);

    private final JoystickButton coralPivUp = new JoystickButton(xBox, 5);
    private final JoystickButton coralPivDown = new JoystickButton(xBox, 7);
    private final JoystickButton coralFunnel = new JoystickButton(xBox, 6);
    private final JoystickButton coralLHPS = new JoystickButton(xBox, 6);
    private final JoystickButton coralLOne = new JoystickButton(xBox, 6);
    private final JoystickButton coralLTwo = new JoystickButton(xBox, 6);
    private final JoystickButton coralLThree = new JoystickButton(xBox, 6);

    private final JoystickButton coralSpinnerOut = new JoystickButton(xBox, 8);
    private final JoystickButton coralSpinnerIn = new JoystickButton(xBox, 9);

    private final JoystickButton elevatorUp = new JoystickButton(xBox, 10);
    private final JoystickButton elevatorDown= new JoystickButton(xBox, 11);

    private final JoystickButton elevatorHPS = new JoystickButton(xBox, 5);
    private final JoystickButton elevatorLTwo = new JoystickButton(xBox, 5);
    private final JoystickButton elevatorLThree = new JoystickButton(xBox, 5);
    private final JoystickButton elevatorFunnel = new JoystickButton(xBox, 5);
    private final JoystickButton elevatorReset = new JoystickButton(xBox, 5);

    // Multi Commands
    private final JoystickButton HPSButton = new JoystickButton(xBox, 5);
    private final JoystickButton funnelButton = new JoystickButton(xBox, 5);



    public final CommandSwerveDrivetrain drivetrain;
    //Command Instantiation
    private final Command Funnel;
    private final Command HPS;
    private final Command levelOne;
    private final Command levelTwo;
    private final Command levelThree;
    // Class Instantiation
    private final AlgaePivot m_AlgaePivot;
    private final AlgaeSpinner m_AlgaeSpinner;
    private final CoralPivot m_CoralPivot;
    private final CoralSpinner m_CoralSpinner;
    private final Elevator m_Elevator;

    private SendableChooser<Command> autoChooser;
    public RobotContainer() {
        m_AlgaePivot = new AlgaePivot();
        m_AlgaeSpinner = new AlgaeSpinner();
        m_CoralPivot = new CoralPivot();
        m_CoralSpinner = new CoralSpinner();
        m_Elevator = new Elevator();

        Funnel = m_Elevator.Funnel()
        .andThen(m_CoralPivot.Funnel())
        .andThen(m_CoralSpinner.intake());

        HPS = m_Elevator.Funnel()
        .andThen(m_CoralPivot.HPS())
        .andThen(m_CoralSpinner.outtake());

        levelOne = m_CoralPivot.lOne()
            .andThen(m_Elevator.lOne())
            .andThen(m_CoralSpinner.outtake());

         levelTwo = m_CoralPivot.lTwo()
            .andThen(m_Elevator.lTwo())
            .andThen(m_CoralSpinner.outtake());

        levelThree = m_CoralPivot.lThree()
            .andThen(m_Elevator.lThree())
            .andThen(m_CoralSpinner.outtake());
        
        drivetrain = TunerConstants.createDrivetrain();//?

        try {
            autoChooser = AutoBuilder.buildAutoChooser();
            SmartDashboard.putData("Auto Chooser", autoChooser);
        }
        catch (Exception ex) {
            DriverStation.reportError("AutoBuilder not made", ex.getStackTrace());
            autoChooser = null;
        }



        configureBindings();
        regitsterNamedCommands();
    }
    public void regitsterNamedCommands(){
        NamedCommands.registerCommand("levelOne", levelOne);
        NamedCommands.registerCommand("coralOut", m_CoralSpinner.outtake());
        NamedCommands.registerCommand("coralPivHPS", m_CoralPivot.HPS());
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on left bumper press
        joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);

        //Up, Down, and Spin
        algaeUp.whileTrue(m_AlgaePivot.up());
        algaeDown.whileTrue(m_AlgaePivot.down());
        algaeReach.onTrue(m_AlgaePivot.Reach());

        algaeSpinnerIn.whileTrue(m_AlgaeSpinner.intake());
        algaeSpinnerOut.whileTrue(m_AlgaeSpinner.outtake());

        coralPivDown.whileTrue(m_CoralPivot.Down());
        coralPivUp.whileTrue(m_CoralPivot.Up());

        coralSpinnerOut.whileTrue(m_CoralSpinner.intake());
        coralSpinnerOut.whileTrue(m_CoralSpinner.outtake());

        elevatorUp.whileTrue(m_Elevator.up());
        elevatorDown.whileTrue(m_Elevator.down());

        // Muilti Commands
        funnelButton.onTrue(Funnel);
        HPSButton.onTrue(HPS);
    }

    public Command getAutonomousCommand() {
        if(autoChooser != null){
            return autoChooser.getSelected();
        }
        return null;
    }
}
