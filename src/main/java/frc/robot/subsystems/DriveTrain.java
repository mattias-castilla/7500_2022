package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase{


    private final CANSparkMax leftMaster = new CANSparkMax(Constants.Motor_Left_Back, MotorType.kBrushless);
    private final CANSparkMax leftSlave = new CANSparkMax(Constants.Motor_Left_Front, MotorType.kBrushless);
    private final CANSparkMax rightMaster = new CANSparkMax(Constants.Motor_Right_Back, MotorType.kBrushless);
    private final CANSparkMax rightSlave = new CANSparkMax(Constants.Motor_Right_Front, MotorType.kBrushless);
    
    boolean lFlip = false;
    boolean rFlip = true;

    static double leftEncoderZero = 0;
    static double rightEncoderZero = 0;

    RelativeEncoder m_leftEncoder = leftMaster.getEncoder();
    RelativeEncoder m_rightEncoder = rightMaster.getEncoder();
    

    public DriveTrain(){

        leftMaster.setInverted(lFlip);
        leftSlave.setInverted(lFlip);
        rightMaster.setInverted(rFlip);
        rightSlave.setInverted(rFlip);

        leftMaster.setIdleMode(IdleMode.kCoast);
        leftSlave.setIdleMode(IdleMode.kCoast);
        rightMaster.setIdleMode(IdleMode.kCoast);
        rightSlave.setIdleMode(IdleMode.kCoast);

        resetEncoders();
        
    }


    public void periodic(){
        //System.out.println(getWheelAverage());
    }


    public void set(double speed){
        leftMaster.set(speed);
        leftSlave.set(speed);
        rightMaster.set(speed);
        rightSlave.set(speed);
    }

    public void set(double lSpeed, double rSpeed){
        leftMaster.set(lSpeed);
        leftSlave.set(lSpeed);
        rightMaster.set(rSpeed);
        rightSlave.set(rSpeed);
    }

    public void driveDistance(double distance, double power){


        resetEncoders();
        
        if(distance < 0){
            
        do{
    
            set(-power, -power);
      
          }while(getWheelAverage() < Units.inchesToMeters(distance - 0.25) || 
                getWheelAverage() > Units.inchesToMeters(distance + 0.25));
      
        }else if(distance> 0){

            do{
    
                set(power, power);
          
              }while(getWheelAverage() < Units.inchesToMeters(distance - 0.65) || 
                    getWheelAverage() > Units.inchesToMeters(distance + 0.65));
          
        }

        set(0, 0);

        
    
    }

    public double getLeftDistanceTraveled(){
        return (getLeftEncoderPos() * (Units.inchesToMeters(Constants.wheelRadius) * 2 * Math.PI));
    }

    public double getRightDistanceTraveled(){
        return (getRightEncoderPos() *(Units.inchesToMeters(Constants.wheelRadius) * 2 * Math.PI));
    }

    public double getLeftEncoderPos(){
        return (m_leftEncoder.getPosition() - leftEncoderZero) / Constants.gearRatio;
    }

    public double getRightEncoderPos(){
        return (m_rightEncoder.getPosition() - rightEncoderZero) / Constants.gearRatio;
    }
    
    public double getWheelAverage(){
        return (getLeftDistanceTraveled() + getRightDistanceTraveled())/2;
    }

    public void resetEncoders(){
        leftEncoderZero = m_leftEncoder.getPosition();
        rightEncoderZero = m_rightEncoder.getPosition();
    }


}
