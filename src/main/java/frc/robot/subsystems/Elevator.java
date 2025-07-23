package frc.robot.subsystems;



import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Commands.Move;
import frc.robot.Commands.SetPosition;
import frc.robot.RobotConstants.AlgaePivotConstants;
//import edu.wpi.first.wpilibj.Timer;


public class Elevator extends SubsystemBase{
    private final TalonFX elevatorMotor;
    private final MotionMagicVoltage controller;
    private double resetPos = 0.0;
    private final SetPosition setPos;
    private final Move spin;
    public Elevator(){
        spin = new Move();
        setPos = new SetPosition();
        elevatorMotor = new TalonFX(60);
        controller = new MotionMagicVoltage(0);
        resetPosition();
        configueMotor();
    }
    public void resetPosition(){
        this.resetPos = elevatorMotor.getPosition().getValueAsDouble();
    }
    public void configueMotor(){
        elevatorMotor.clearStickyFaults();
        TalonFXConfiguration config = new TalonFXConfiguration();

        config.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
        config.Slot0.kP = 3.0;
        config.Slot0.kD = 0.15;
        config.Slot0.kI = 15.0;
        config.Slot0.kG = 0.0;

        var motionMagicConfigs = config.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity = 10;
        motionMagicConfigs.MotionMagicAcceleration = 10;
        motionMagicConfigs.MotionMagicJerk = 100;

        config.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = this.resetPos + 4;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = this.resetPos;

        elevatorMotor.getConfigurator().apply(config);

    }
    public Command positionOne(){
        return setPos.setPosition(AlgaePivotConstants.positionOne, elevatorMotor, controller);
    }

    public Command positionTwo(){
        return setPos.setPosition(AlgaePivotConstants.postionTwo, elevatorMotor, controller);
    }
    public Command up(){
        return spin.spinUp( elevatorMotor,0.1);
    }
    public Command down(){
        return spin.spinUp(elevatorMotor, 0.1);
    }

}