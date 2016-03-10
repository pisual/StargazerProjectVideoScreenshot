package com.stargazer.video;

import static org.bytedeco.javacpp.opencv_highgui.CV_CAP_PROP_POS_FRAMES;
import static org.bytedeco.javacpp.opencv_highgui.cvQueryFrame;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;

import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui.CvCapture;

public class ScreenshotThread extends Thread {
	
	private double screenshotVideoPosition;
	private CvCapture cvCapture;
	private String fileName;
	
	public ScreenshotThread(double screenshotVideoPosition,CvCapture cvCapture,String fileName,String sourceFilename) {
		this.screenshotVideoPosition = screenshotVideoPosition;
		this.fileName = fileName;
		this.cvCapture = opencv_highgui.cvCreateFileCapture(sourceFilename);
	}
	
	@Override
	public void run() {
		System.out.println("启动截图线程  Position: "+screenshotVideoPosition);
		opencv_highgui.cvSetCaptureProperty(cvCapture, CV_CAP_PROP_POS_FRAMES, screenshotVideoPosition);
		IplImage iplImage = cvQueryFrame(cvCapture);
		cvSaveImage(fileName,iplImage);
		System.out.println("截图线程执行完毕  Position: "+screenshotVideoPosition);
	}
}
