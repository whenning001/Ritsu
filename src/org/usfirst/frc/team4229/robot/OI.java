package org.usfirst.frc.team4229.robot;

import org.usfirst.frc.team4229.robot.commands.FireAway;
import org.usfirst.frc.team4229.robot.commands.LTurn;
import org.usfirst.frc.team4229.robot.commands.RTurn;
import org.usfirst.frc.team4229.robot.commands.ServAuto;
import org.usfirst.frc.team4229.robot.commands.ServoMove;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);
	
	public Joystick stickL = new Joystick(0);
	public Joystick stickR = new Joystick(1); 
	public Button trigL = new JoystickButton(stickL, 1);
	public Button trigR = new JoystickButton(stickR, 1);
	public Button b11L = new JoystickButton(stickL, 11);
	public Button b3R = new JoystickButton(stickR, 3);
	public Button b3L = new JoystickButton(stickL, 3);
	public OI(){
		trigR.whileHeld(new FireAway());
		trigL.whileHeld(new ServoMove());
		b11L.whileHeld(new ServAuto());
		b3R.whileHeld(new RTurn());
		b3L.whileHeld(new LTurn());
	}
	

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
