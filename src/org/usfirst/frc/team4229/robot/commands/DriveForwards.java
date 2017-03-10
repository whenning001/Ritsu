package org.usfirst.frc.team4229.robot.commands;

import org.usfirst.frc.team4229.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForwards extends Command {

    public DriveForwards() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	requires(Robot.encoders);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.encoders.reset();
    	Robot.drivetrain.drive(0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.encoders.getDistance()<4.0 && Robot.encoders.getDistance()>-1.0){
    		Robot.drivetrain.drive(0.5, 0.5);
    	}
    	Robot.drivetrain.log();
    	Robot.encoders.log();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.encoders.getDistance()>4.0;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
