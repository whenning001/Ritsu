package org.usfirst.frc.team4229.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private SpeedController motLeft = new Talon(2);
	private SpeedController motRight = new Talon(1);
	public double count = 0;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void Shoot() {
    	double speedMod=SmartDashboard.getNumber("Shoot Speed", 0.55);
    	//double speedMod= 0.55; //Quosig hardcoded this, this speed seems to work
    	motLeft.set(speedMod); 
    	motRight.set(-speedMod);
    }
    
    public void Stop() {
    	motLeft.set(0);
    	motRight.set(0);
    }

	public void setCount(double d) {
		// TODO Auto-generated method stub
		count=d;
	}
	
}

