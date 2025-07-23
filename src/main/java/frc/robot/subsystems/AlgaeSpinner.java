package frc.robot.subsystems;
import com.revrobotics.spark.SparkFlex;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Commands.MoveSpark;
import frc.robot.RobotConstants.AlgaeSpinnerConstants;

import com.revrobotics.spark.SparkLowLevel.MotorType;
public class AlgaeSpinner extends SubsystemBase{
    private final SparkFlex algaeSpinner = new SparkFlex(23, MotorType.kBrushless);
    private final MoveSpark spinSpark = new MoveSpark();
    public AlgaeSpinner(){}    
    public Command intake(){
        return spinSpark.spinUp(algaeSpinner, AlgaeSpinnerConstants.algaeSpinnerIn);
    }

    public Command outtake(){
        return spinSpark.spinDown(algaeSpinner, AlgaeSpinnerConstants.algaeSpinnerOut);
    }
}
