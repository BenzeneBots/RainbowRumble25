package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkFlex;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Commands.Move;
import frc.robot.Commands.MoveSpark;
import frc.robot.RobotConstants.CoralSpinnerConstants;

import com.revrobotics.spark.SparkLowLevel.MotorType;
public class CoralSpinner extends SubsystemBase{
    private final TalonFX coralSpinner = new TalonFX(55, "BB_CANIVORE");
    private final Move spin = new Move();
    public CoralSpinner(){}    
    public Command intake(){
        return spin.spinUp(coralSpinner, CoralSpinnerConstants.coralSpinnerIn);
    }

    public Command outtake(){
        return spin.spinDown(coralSpinner, CoralSpinnerConstants.coralSpinnerOut);
    }
}
