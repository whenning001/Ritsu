package org.usfirst.frc.team4229.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import org.usfirst.frc.team4229.robot.GRIPGreenMaskPipeline;
import org.usfirst.frc.team4229.robot.FURetro;

public class RetroThread {
	Thread visionThread;
	Object imglock;
	
	public void init() {
		visionThread = new Thread(() -> {
			// Get the UsbCamera from CameraServer
			AxisCamera camera = CameraServer.getInstance().addAxisCamera("axis-camera.local");
			
			//Create a pipeline that will filter out all but green
			//GRIPGreenMaskPipeline greenmask = new GRIPGreenMaskPipeline();
			
			//Create a pipeline to detect contours
			FURetro furetro = new FURetro();
			
			// Set the resolution
			camera.setResolution(640, 480);

			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo();
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream2 = CameraServer.getInstance().putVideo("Mask", 640, 480);
			//CvSource outputStream3 = CameraServer.getInstance().putVideo("Countours", 640, 480);

			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat = new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
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
				furetro.process(mat);
				
				// Put a rectangle on the image
				//Imgproc.rectangle(greenmask.hsvThresholdOutput(), new Point(50, 50), new Point(200, 240),
						//new Scalar(255, 255, 255), 5);
				
				// Give the output stream a new image to display
				outputStream2.putFrame(mat);
				
				//Alright, let's grab some crap here and do some math
				 Rect r = Imgproc.boundingRect(furetro.filterContoursOutput().get(0));
				 synchronized (imglock){
					 int x = r.x;
					 int y = r.y;
					 int h = r.height;
					 int w =r.width;
				 }
				 
				 
			}
		});
		visionThread.setDaemon(true);
		visionThread.start();
	}
}
