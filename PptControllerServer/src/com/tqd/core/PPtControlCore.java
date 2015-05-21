package com.tqd.core;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class PPtControlCore {

	Robot mRobot;

	public PPtControlCore() {
		// TODO Auto-generated constructor stub
		try {
			mRobot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public static void main(String[] args) {

		PPtControlCore pcc = new PPtControlCore();

		// pcc.typeKeys(KeyEvent.VK_WINDOWS,KeyEvent.VK_R);
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pcc.typeKeys(KeyEvent.VK_SHIFT, KeyEvent.VK_F5);
	}
}
