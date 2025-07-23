package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotConstants.CoralPivotConstants;
import frc.robot.Commands.SetPosition;

public class CoralPivot {
    private final SetPosition setPos;
    private final TalonFX coralPivotMotor;
    private final MotionMagicVoltage controller;
    private double pos = 0.0;
    public CoralPivot (){
        setPos = new SetPosition();
        coralPivotMotor = new TalonFX(60,"BB_CANIVORE");
        controller = new MotionMagicVoltage(0);
        resetPos();
        config();
    } 
    public void resetPos(){
        this.pos = coralPivotMotor.getPosition().getValueAsDouble();
    }
    public void config(){
        coralPivotMotor.clearStickyFaults();
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
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = this.pos + 4;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = this.pos;

        coralPivotMotor.getConfigurator().apply(config);

    }
    public Command Funnel(){
        return setPos.setPosition(CoralPivotConstants.Funnel, coralPivotMotor, controller);
    }

    public Command lOne(){
        return setPos.setPosition(CoralPivotConstants.lOne, coralPivotMotor, controller);
    }
    public Command lTwo(){
        return setPos.setPosition(CoralPivotConstants.lTwo,coralPivotMotor, controller);
    }
    public Command lThree(){
        return setPos.setPosition(CoralPivotConstants.lThree, coralPivotMotor, controller);
    }
    
}