package frc.robot.Commands;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;

public class Move extends Command {
    public Command spinUp(TalonFX motor, double speed){
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
        public Command spinDown(TalonFX motor, double speed){
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
