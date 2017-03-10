package org.usfirst.frc.team4229.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Encoders extends Subsystem {
	
	public Encoder encRight = new Encoder(0,1); 
	public Encoder encLeft = new Encoder(2,3);
	
	public Encoders(){
		super();
		encLeft.setDistancePerPulse(-0.042);
		encRight.setDistancePerPulse(-0.042);
		LiveWindow.addSensor("Drive Train", "Left Encoder", encLeft);
		LiveWindow.addSensor("Drive Train", "Right Encoder", encRight);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void reset() {
    	encLeft.reset();
    	encRight.reset();
    }
    
    public double getDistance() {
    	return (encLeft.getDistance()+encRight.getDistance());
    }
    

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    

    public void log(){
		SmartDashboard.putNumber("Left Speed", encLeft.getRate());
		SmartDashboard.putNumber("Right Speed", encRight.getRate());
		SmartDashboard.putNumber("Distance", getDistance());
    }
}

