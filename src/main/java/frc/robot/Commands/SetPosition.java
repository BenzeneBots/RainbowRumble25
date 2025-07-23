package frc.robot.Commands;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class SetPosition extends Command{
        public Command setPosition(double Position, TalonFX motor, MotionMagicVoltage controller){
        return new Command(){
            Timer timer = new Timer();
            @Override
            public void initialize(){
                super.initialize();
                timer.start();
            }
            @Override
            public void execute(){
                motor.setControl(controller.withSlot(0).withPosition(Position));
            }
            @Override
            public boolean isFinished(){
                return timer.get() > 1.0;
            }

        };
    }
    
}
