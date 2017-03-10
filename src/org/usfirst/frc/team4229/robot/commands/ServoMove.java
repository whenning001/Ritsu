package org.usfirst.frc.team4229.robot.commands;

import org.usfirst.frc.team4229.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ServoMove extends Command {

    public ServoMove() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.servoMotor);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.servoMotor.rotateServo(1);//tried changing number, couldn't make servo move further. - Q
    	SmartDashboard.putNumber("servoposi", 1);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.servoMotor.rotateServo(SmartDashboard.getNumber("Servo Base", 0)); //This is basically the limit to how far up it can go before there's problems. Feel free to test more if you want, but I'm pretty happy with it.
    	SmartDashboard.putNumber("servoposi", SmartDashboard.getNumber("Servo Base", 0));
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
