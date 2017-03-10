package org.usfirst.frc.team4229.robot;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4229.robot.GRIPGreenMaskPipeline;
import org.usfirst.frc.team4229.robot.FURetro;
import org.usfirst.frc.team4229.robot.RetroPipe;

public class CameraThread extends Thread {
	//Thread visionThread;
	Object imglock;
	RetroPipe pipe;
	AxisCamera camera;
	CvSink cvSink;
	CvSource outputStream2;
	Mat mat;
	double FOVDegrees;
	double tanFOV;
	
	public CameraThread(){
		this.setDaemon(true);
	}
	
	
	public void init() {
			// Get the UsbCamera from CameraServer
			camera = CameraServer.getInstance().addAxisCamera("axis-camera.local");
			
			//Create a pipeline that will filter out all but green
			//GRIPGreenMaskPipeline greenmask = new GRIPGreenMaskPipeline();

			//Create a pipeline to detect contours
			pipe = new RetroPipe();
			
			// Set the resolution
			camera.setResolution(640, 480);

			// Get a CvSink. This will capture Mats from the camera
			cvSink = CameraServer.getInstance().getVideo();
			// Setup a CvSource. This will send images back to the Dashboard
			outputStream2 = CameraServer.getInstance().putVideo("Mask", 640, 480);
			//CvSource outputStream3 = CameraServer.getInstance().putVideo("Countours", 640, 480);

			// Mats are very memory expensive. Lets reuse this Mat.
			mat = new Mat();
			
			double FOVDegrees =39.513843575;
			double tanFOV = Math.tan(Math.toRadians(FOVDegrees));
			SmartDashboard.putNumber("tan", tanFOV);
			SmartDashboard.putNumber("distance", 1);
			
			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			
			
	}
	
	public double distance(double PixelHeight){
		//TargetHeight
		return 0;
	}
	
	public void run(){
		while (!Thread.interrupted()) {
			// Tell the CvSink to grab a frame from the camera and put it
			// in the source mat.  If there is an error notify the output.
			if (cvSink.grabFrame(mat) == 0) {
				// Send the output the error.
				outputStream2.notifyError(cvSink.getError());
				//outputStream3.notifyError(cvSink.getError());
				// skip the rest of the current iteration
				continue;
			}
			//Make Green Mask
			//greenmask.process(mat); // REQUIRED!
			pipe.process(mat);
			//furetro.convertArrays(furetro.filterContoursOutput());
			 
			
			// Put a rectangle on the image
			//Imgproc.rectangle(furetro.hsvThresholdOutput(), new Point(50, 50), new Point(200, 240),
					//new Scalar(255, 255, 255), 5);
			
			// Give the output stream a new image to display
			outputStream2.putFrame(pipe.maskOutput());
			if (pipe.filterContoursOutput().size()>0){
				double recth = Imgproc.boundingRect(pipe.filterContoursOutput().get(0)).height;
				SmartDashboard.putNumber("pixH", recth);
				SmartDashboard.putNumber("distance", (1.0/12.0)*480.0/(2.0*recth*tanFOV));
			}
			
			//Alright, let's grab some crap here and do some math
			 //Rect r = Imgproc.boundingRect(greenmask.filterContoursOutput().get(0));
			 
			// synchronized (imglock){
			//	 int x = r.x;
				// int y = r.y;
				// int h = r.height;
				// int w =r.width;
			 //}
		}	 
			 
		
	}
	}
