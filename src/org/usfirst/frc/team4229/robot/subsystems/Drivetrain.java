package org.usfirst.frc.team4229.robot.subsystems;

import org.usfirst.frc.team4229.robot.ADXRS453Gyro;
import org.usfirst.frc.team4229.robot.Robot;
import org.usfirst.frc.team4229.robot.commands.JoyDrive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drivetrain extends Subsystem {
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private SpeedController motLeft = new Talon(3);
	private SpeedController motRight = new Talon(0);
	private RobotDrive drive = new RobotDrive(motLeft, motRight);

	static final double kDEADZONE = 0.1;
	
	public Drivetrain(){
		super();
		
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new JoyDrive());
    }
    
    public void drive(double left, double right){
    	drive.tankDrive(left, right);
    }
    
    public void log(){


		SmartDashboard.putNumber("Joystick L", -Robot.oi.stickL.getY());
		SmartDashboard.putNumber("Joystick R", -Robot.oi.stickR.getY());
		SmartDashboard.putBoolean("trigL", Robot.oi.trigL.get());
    }
    

    

    
    public double deadzone(double n){
    	// && is (AND)
    	// || is (OR)
    	
    	if (n<kDEADZONE && n>-kDEADZONE){
    		n=0;
    	}
    	
    	else{
    		
    		if (n>0){
    			n=(n-kDEADZONE)/(1-kDEADZONE);
    		}
    		
    		if (n<0){
    			n=(n+kDEADZONE)/(1-kDEADZONE);
    		}
    		
    	}
    	return n;
    }
    
    public double analogue(double n){
    	if (n<-0.5&&n>-0.5 || n>0.5){
    		n=n*0.5;
    	}
    	return n;
    }
}

