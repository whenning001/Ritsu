package org.usfirst.frc.team4229.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ServoMotor extends Subsystem {
	
	public Servo servo = new Servo(7);
	
	public ServoMotor(){
		servo.set(0.85);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void rotateServo(double x){
    	servo.set(x);
    }
}

