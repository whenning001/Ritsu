
package org.usfirst.frc.team4229.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

import org.usfirst.frc.team4229.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4229.robot.subsystems.Encoders;
import org.usfirst.frc.team4229.robot.subsystems.Gyroscope;
import org.usfirst.frc.team4229.robot.subsystems.ServoMotor;
import org.usfirst.frc.team4229.robot.subsystems.Shooter;
import org.usfirst.frc.team4229.robot.subsystems.Testing;
import org.opencv.core.Mat;
import org.usfirst.frc.team4229.robot.GRIPGreenMaskPipeline;
import org.usfirst.frc.team4229.robot.commands.DriveForwards;
import org.usfirst.frc.team4229.robot.CameraThread;
import org.usfirst.frc.team4229.robot.FURetro;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//public static ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static Drivetrain drivetrain = new Drivetrain();
	public static Shooter shooter = new Shooter();
	//public static GRIPGreenMaskPipeline greenMaskPipeline = new GRIPGreenMaskPipeline();
	public static FURetro fuRetro = new FURetro();
	public static ServoMotor servoMotor = new ServoMotor();
	public static Gyroscope gyroscope = new Gyroscope();
	public static Encoders encoders = new Encoders();
	public static CameraThread cameraThread = new CameraThread(); 
	public static Testing testing = new Testing();
	public static OI oi;
	NetworkTable table;
	
	//private static final int IMG_WIDTH = 320;
	//private static final int IMG_HEIGHT = 240;
	
	//private VisionThread visionThread;
	//private double centerX = 0.0;
	
	//private final Object imgLock = new Object();

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		//UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    //camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
	    
	    //visionThread = new VisionThread(camera, new GRIPGreenMaskPipeline(), pipeline -> {
	        //if (pipeline.maskOutput().empty()) {
	            //Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	        	//Mat i = pipeline.maskOutput();
	            //synchronized () {
	                //centerX = r.x + (r.width / 2);
	           // }
	       // }
	    //});
	    //visionThread.start();
	    
	    Robot.cameraThread.init();
	    Robot.cameraThread.start();
		
		oi = new OI();
		table = NetworkTable.getTable("data");
		//chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		
		SmartDashboard.putNumber("Shoot Speed", 0.55);
		SmartDashboard.putData("Auto mode", chooser);
		SmartDashboard.putNumber("Drive Speed", 0.0);
		SmartDashboard.putNumber("Servo Base", 0.85);
		SmartDashboard.putNumber("Brake Speed", 0.5);
		SmartDashboard.putNumber("Brake Thresh", 10.0);
		SmartDashboard.putBoolean("Brakes", true);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		table.putBoolean("autoStart", true);
		Robot.encoders.reset();
		//autonomousCommand = chooser.getSelected();
		autonomousCommand = new DriveForwards();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		
		

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		log();
		/**
		if (Robot.encoders.getDistance()<1){
			Robot.drivetrain.drive(0.75, 0.75);
		}
		**/
		
		/**
		if (gyroscope.gyroSPI.getAngle()>90 || gyroscope.gyroSPI.getAngle()<-90) {
			Robot.drivetrain.drive(-0.25, 0.25);
		}
		**/
		
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		table.putBoolean("teleStart", true);
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		drivetrain.drive(0, 0);
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		log();
		LiveWindow.run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	private void log() {
		drivetrain.log();
		gyroscope.log();
		encoders.log();
	}
}
