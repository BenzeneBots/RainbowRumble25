package frc.robot.subsystems;
import com.revrobotics.spark.SparkFlex;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Commands.MoveSpark;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.jni.CANSparkJNI;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfigAccessor;
import edu.wpi.first.wpilibj.DriverStation;
import com.revrobotics.spark.SparkLowLevel.MotorType;
public class AlgaeSpinner extends SubsystemBase{
    private final SparkFlex algaeSpinner = new SparkFlex(23, MotorType.kBrushless);
    private final MoveSpark spinSpark = new MoveSpark();
    public AlgaeSpinner(){}    
    public Command intake(){
        return spinSpark.spinUp(algaeSpinner, 0.1);
    }

    public Command outtake(){
        return spinSpark.spinDown(algaeSpinner, 0.1);
    }
}
