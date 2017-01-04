package com.hiro.remote;

import javax.swing.ImageIcon;

public class CMD {

	final static public String APP			= "HiroRemote";
	final static public String RELEASE  		= "Ver 1.1 ";
	final static public String RELEASEDATE 	= "(20160622)";
	
	final static public String STRING_DEVICEINFO		= "===========================================\n Device=%s\n===========================================";
	final static public String STRING_RESULT_FORMAT		= "**************** Result ****************\n %s \n****************************************\n";

	
	//cmd = "adb shell screencap -p | sed 's/\r$//' > %s"
	final static public String ADB_SCREENCAPTURE 		= "adb shell screencap -p > %s";
	final static public String ADB_INPUT_KEYEVENT 		= "adb shell input keyevent %s";
	final static public String ADB_INPUT_TEXT 			= "adb shell input text \"%s\"";
	final static public String ADB_INPUT_TOUCH 			= "adb shell input tap %s %s";
	final static public String ADB_INPUT_SWIPE 			= "adb shell input swipe %s %s %s %s";
	final static public String ADB_SETTING				= "adb shell am start -n com.android.settings/.Settings";
	final static public String ADB_CALL					= "adb shell am start -a android.intent.action.CALL -d tel:%s";
	final static public String ADB_DEVICES 				= "adb devices";
	final static public String ADB_PULL 				= "adb pull %s %s";
	final static public String KEYCODE_DPAD_UP 			= "KEYCODE_DPAD_UP";
	final static public String KEYCODE_DPAD_DOWN 		= "KEYCODE_DPAD_DOWN";
	final static public String KEYCODE_DPAD_LEFT 		= "KEYCODE_DPAD_LEFT";
	final static public String KEYCODE_DPAD_RIGHT 		= "KEYCODE_DPAD_RIGHT";
	final static public String KEYCODE_DPAD_CENTER 		= "KEYCODE_DPAD_CENTER";
	final static public String KEYCODE_BACK 			= "KEYCODE_BACK";
	final static public String KEYCODE_MENU 			= "KEYCODE_MENU";
	final static public String KEYCODE_HOME 			= "KEYCODE_HOME";
	final static public String KEYCODE_VOLUME_UP 		= "KEYCODE_VOLUME_UP";
	final static public String KEYCODE_VOLUME_DOWN 		= "KEYCODE_VOLUME_DOWN";
	final static public String KEYCODE_POWER 			= "KEYCODE_POWER";
	final static public String KEYCODE_ENTER 			= "KEYCODE_ENTER";
	final static public String KEYCODE_ENDCALL 			= "KEYCODE_ENDCALL";
	final static public String FILENAME_SNAPSHOT 		= "snapshot.png"; 
	final static public int	 IMG_MAXSIZE				= 550;
	final static public String STRING_CONNECTED			= "Connected: %s";
	final static public String STRING_DISCONNECTED		= "Can't find device (%s)";
	final static public String STRING_SAVESNAPSHOT		= "Save snapshot(%s)";
	final static public String STRING_INSTALLAPK		= "Install APK(%s)\n"+ STRING_RESULT_FORMAT;
	final static public String STRING_MAKECALL			= "Make call: (%s)";
	final static public String STRING_ENDCALL			= "End call.";
	final static public String STRING_KEYPRESS			= "Keypress(%s)";
	final static public String STRING_SWIPE				= "Swipe: ratio=%f, X=%d, Y=%d, X1=%d, Y1=%d";
	final static public String STRING_CLICK				= "Click: ratio=%f, X=%d, Y=%d";
	final static public String STRING_DIALOG_TITLE_SAVE_SNAPSHOT = "Save as snapshot";
	final static public String STRING_DIALOG_TITLE_INSTALL_APK 	 = "Install android apk";
	final static public String STRING_FILE_SCREENCAPTURE 		 = "devScreen.png";
	
	final static public ImageIcon ICON_SNAPSHOT		= (new ImageIcon(CMD.class.getResource("/snapshot.png")));
	final static public ImageIcon ICON_RROTATE  	= (new ImageIcon(CMD.class.getResource("/clockwise.png")));
	final static public ImageIcon ICON_LROTATE  	= (new ImageIcon(CMD.class.getResource("/counterclockwise .png")));
	final static public ImageIcon ICON_RED   		= (new ImageIcon(CMD.class.getResource("/ball_red16.png")));
	final static public ImageIcon ICON_GREEN 		= (new ImageIcon(CMD.class.getResource("/ball_green16.png")));
	final static public ImageIcon ICON_UP 			= (new ImageIcon(CMD.class.getResource("/arrow-up.png")));
	final static public ImageIcon ICON_DOWN			= (new ImageIcon(CMD.class.getResource("/arrow-down.png")));
	final static public ImageIcon ICON_LEFT			= (new ImageIcon(CMD.class.getResource("/arrow-left.png")));
	final static public ImageIcon ICON_RIGHT		= (new ImageIcon(CMD.class.getResource("/arrow-right.png")));
	final static public ImageIcon ICON_CALL			= (new ImageIcon(CMD.class.getResource("/call.png")));
	final static public ImageIcon ICON_CALLEND		= (new ImageIcon(CMD.class.getResource("/callend.png")));
	final static public ImageIcon ICON_PACKAGE		= (new ImageIcon(CMD.class.getResource("/package.png")));
	
	final static public String[][] STRING_RELEASENOTE	= new String [][] {
		{"20150409","Ver1.1","New UI, flexible screen size and connect/disconnect detect"},
		{"20150414","Ver1.1","Set cursor on touch screen"},
		{"20150415","Ver1.1","Screen rotate feature"},
		{"20150415","Ver1.1","Screen shot"},
		{"20150522","Ver1.1","Change style dpad arrow icon and unlock behavior(MENU event)"},
		{"20150605","Ver1.1","Add feature: Install package"},};
			
}
