package frc.robot.Commands;

import com.revrobotics.spark.SparkFlex;
import edu.wpi.first.wpilibj2.command.Command;

public class MoveSpark extends Command {
    public Command spinUp(SparkFlex motor, double speed){
        return new Command(){
            @Override
            public void execute(){
                motor.set(speed);
            }
            @Override
            public void end(boolean interrupted){
                motor.stopMotor();
            }
        };
    }
        public Command spinDown(SparkFlex motor, double speed){
        return new Command(){
            @Override
            public void execute(){
                motor.set(-speed);
            }
            @Override
            public void end(boolean interrupted){
                motor.stopMotor();
            }
        };
    }
    
}
