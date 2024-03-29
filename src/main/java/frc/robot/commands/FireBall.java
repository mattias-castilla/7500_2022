package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Shooter;

public class FireBall extends CommandBase{

    private Index m_index;

    public FireBall(Index m_index){
        this.m_index = m_index;

    }


    public void initialize(){
        m_index.fireBall();
    }

    public void execute(){
    }

    public boolean isFinished(){
        return m_index.isFiredBalls();
    }

    public void end(boolean interrupted){
        m_index.stop();
        m_index.finishedFiring();
    }
    
}
