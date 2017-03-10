package org.usfirst.frc.team4229.robot.commands;

import org.usfirst.frc.team4229.robot.Robot;
import org.usfirst.frc.team4229.robot.subsystems.ServoMotor;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ServAuto extends Command {
	
	double starttime;
	double timer1;
	double timer2;
	int i=0;

    public ServAuto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.servoMotor);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer1 = System.currentTimeMillis();
    	timer2 = -1;
    	i=0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (System.currentTimeMillis()-timer1>1000){
    		timer2=System.currentTimeMillis();
    		Robot.servoMotor.servo.set(1);
    	}
    	else if (timer2 != -1 && System.currentTimeMillis()-timer2>500){
    		timer1=System.currentTimeMillis();
    		Robot.servoMotor.servo.set(0.85);
    		i++;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return i>5;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.servoMotor.servo.set(0.85);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
