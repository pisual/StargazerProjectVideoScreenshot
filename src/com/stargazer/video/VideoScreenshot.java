package com.stargazer.video;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_highgui.CvCapture;

public class VideoScreenshot{
	private int numberOfShots;
	private String targetMenu;
	
	public VideoScreenshot(String sourceFilename,String targetMenu,int numberOfShots) {
		this.numberOfShots = numberOfShots;
		this.targetMenu = targetMenu;
		this.startScreenshot(sourceFilename);
	}
	
	public void startScreenshot(String sourceFilename) {
		CvCapture cvCapture = opencv_highgui.cvCreateFileCapture(sourceFilename);
		double frameCount = opencv_highgui.cvGetCaptureProperty(cvCapture, opencv_highgui.CV_CAP_PROP_FRAME_COUNT);
		double fps = opencv_highgui.cvGetCaptureProperty(cvCapture, opencv_highgui.CV_CAP_PROP_FPS);
		double intervalBetweenFrames = frameCount/numberOfShots;
		double scheduleFrames=0;
		ExecutorService pool = Executors.newFixedThreadPool(2);
		for(int i=0;i<numberOfShots;i++){
			if((scheduleFrames+intervalBetweenFrames+(fps*5))>frameCount){   break;  }
			ScreenshotThread screenshotThread = new ScreenshotThread(scheduleFrames+intervalBetweenFrames,cvCapture,targetMenu+scheduleFrames+intervalBetweenFrames+".jpg",sourceFilename);
			pool.execute(screenshotThread);
			scheduleFrames=scheduleFrames+intervalBetweenFrames;
		}
		pool.shutdown();
	}
	public static void main(String[] args) {
		VideoScreenshot videoScreenshot = new VideoScreenshot("/Users/Felixerio/Workspaces/StargazerProject/StargazerProjectVideoScreenshot/src/com/test/www/ed.mp4","/Users/Felixerio/Workspaces/StargazerProject/StargazerProjectVideoScreenshot/src/com/test/www/pic/",30);
	
	}
}
