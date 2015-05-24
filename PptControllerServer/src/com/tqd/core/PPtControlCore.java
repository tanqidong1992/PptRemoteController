package com.tqd.core;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class PPtControlCore {

	Robot mRobot;
	private Rectangle mScreenRect;
	private double mScale=1;

	public PPtControlCore(double width, double height) {
		// TODO Auto-generated constructor stub
		try {
			mRobot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
		mScreenRect=new Rectangle(dimension);
		double scaleW=mScreenRect.getWidth()/width;
		double scaleH=mScreenRect.getHeight()/height;
		mScale=scaleW>scaleH?scaleW:scaleH;
		
	//	mScreenRect=new Rectangle((double)dimension.getWidth(),(double) dimension.getHeight());
	}

	public void processCommand(int cType) {

		switch (cType) {
		case CommandType.START_PPT:
			typeKeys(KeyEvent.VK_SHIFT, KeyEvent.VK_F5);
			break;
		case CommandType.EXIT_PPT:
			typeKeys(KeyEvent.VK_ESCAPE);
			break;
		case CommandType.ENTER_PEN_MODE:
			typeKeys(KeyEvent.VK_CONTROL, KeyEvent.VK_P);
			break;
		case CommandType.EXIT_PEN_MODE:
			typeKeys(KeyEvent.VK_CONTROL, KeyEvent.VK_P);
			break;
		case CommandType.ENTER_COURSE_MODE:
			typeKeys(KeyEvent.VK_CONTROL, KeyEvent.VK_P);
			break;
		case CommandType.MOVE_DOWN:
			typeKeys(KeyEvent.VK_DOWN);
			break;
		case CommandType.MOVE_UP:
			typeKeys(KeyEvent.VK_UP);
			break;
		case CommandType.MOVE_LEFT:
			typeKeys(KeyEvent.VK_LEFT);
			break;
		case CommandType.MOVE_RIGHT:
			typeKeys(KeyEvent.VK_RIGHT);
			break;
		case CommandType.CONNECTION_REQUEST:

			break;
		case CommandType.MOUSE_MOVE_IN_CURSOR_MODE:

			break;
		case CommandType.MOUSE_MOVE_IN_PEN_MODE:

			break;
		case CommandType.CUSTOM_COMMAND:

			break;
		}

	}

	public void typeKeys(int... keycodes) {

		for (int keycode : keycodes) {
			mRobot.keyPress(keycode);
		}

		for (int keycode : keycodes) {
			mRobot.keyRelease(keycode);
		}

	}

	public void typeKey(int keycode) {
		mRobot.keyPress(keycode);
		mRobot.keyRelease(keycode);
	}

	public class CommandType {

		public static final int START_PPT = 1;
		public static final int EXIT_PPT = 2;
		public static final int ENTER_PEN_MODE = 3;
		public static final int EXIT_PEN_MODE = 4;
		public static final int ENTER_COURSE_MODE = 5;
		public static final int MOVE_DOWN = 6;
		public static final int MOVE_UP = 7;
		public static final int MOVE_LEFT = 8;
		public static final int MOVE_RIGHT = 9;
		public static final int CONNECTION_REQUEST = 10;
		public static final int MOUSE_MOVE_IN_CURSOR_MODE = 11;
		public static final int MOUSE_MOVE_IN_PEN_MODE = 12;
		public static final int CUSTOM_COMMAND = 13;

	}

	public BufferedImage screenCapture(int height,int width)
	{
		double scaleW=mScreenRect.getWidth()/width;
		double scaleH=mScreenRect.getHeight()/height;
		double scale=scaleW>scaleH?scaleW:scaleH;
	
		BufferedImage bufferedImage=mRobot.createScreenCapture(mScreenRect);
		
		Image ti=  bufferedImage.getScaledInstance((int)(mScreenRect.getWidth()/scale), (int)(mScreenRect.getHeight()/scale), BufferedImage.SCALE_DEFAULT);
		
		return toBufferedImage(ti);
	}
	
	public BufferedImage screenCapture()
	{
	//	double scaleW=mScreenRect.getWidth()/width;
	//	double scaleH=mScreenRect.getHeight()/height;
	//	double scale=scaleW>scaleH?scaleW:scaleH;
	
		BufferedImage bufferedImage=mRobot.createScreenCapture(mScreenRect);
		
		Image ti=  bufferedImage.getScaledInstance((int)(mScreenRect.getWidth()/mScale), (int)(mScreenRect.getHeight()/mScale), BufferedImage.SCALE_DEFAULT);
		
		return toBufferedImage(ti);
	}
	
	public static void main(String[] args) {

		PPtControlCore pcc = new PPtControlCore(100,100);

		BufferedImage bi=pcc.screenCapture(500, 500);
		
		try {
			ImageIO.write(bi, "jpeg", new File("test.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		// pcc.typeKeys(KeyEvent.VK_WINDOWS,KeyEvent.VK_R);
//		try {
//			TimeUnit.SECONDS.sleep(5);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		pcc.typeKeys(KeyEvent.VK_SHIFT, KeyEvent.VK_F5);
	}
	
	
	public static BufferedImage toBufferedImage(Image image) {
	    if (image instanceof BufferedImage) {
	        return (BufferedImage)image;
	     }
	 
	    // This code ensures that all the pixels in the image are loaded
	     image = new ImageIcon(image).getImage();
	 
	    // Determine if the image has transparent pixels; for this method's
	    // implementation, see e661 Determining If an Image Has Transparent Pixels
	    //boolean hasAlpha = hasAlpha(image);
	 
	    // Create a buffered image with a format that's compatible with the screen
	     BufferedImage bimage = null;
	     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    try {
	        // Determine the type of transparency of the new buffered image
	        int transparency = Transparency.OPAQUE;
	       /* if (hasAlpha) {
	         transparency = Transparency.BITMASK;
	         }*/
	 
	        // Create the buffered image
	         GraphicsDevice gs = ge.getDefaultScreenDevice();
	         GraphicsConfiguration gc = gs.getDefaultConfiguration();
	         bimage = gc.createCompatibleImage(
	         image.getWidth(null), image.getHeight(null), transparency);
	     } catch (HeadlessException e) {
	        // The system does not have a screen
	     }
	 
	    if (bimage == null) {
	        // Create a buffered image using the default color model
	        int type = BufferedImage.TYPE_INT_RGB;
	        //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
	        /*if (hasAlpha) {
	         type = BufferedImage.TYPE_INT_ARGB;
	         }*/
	         bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
	     }
	 
	    // Copy image to buffered image
	     Graphics g = bimage.createGraphics();
	 
	    // Paint the image onto the buffered image
	     g.drawImage(image, 0, 0, null);
	     g.dispose();
	 
	    return bimage;
	}
}
