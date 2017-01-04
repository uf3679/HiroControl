package com.hiro.remote;

import java.io.File;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.InstallException;


public class controller {

	ProcessRun process;
	AndroidDebugBridge bridge = null;
	
	public controller() {
		// TODO Auto-generated constructor stub
		
		process = new ProcessRun();
		//process.Exec("adb shell input keyevent 'KEYCODE_POWER'");
		
		AndroidDebugBridge.init(false);
		bridge = AndroidDebugBridge.createBridge();  
		
	}
	
	public IDevice[] getDeviceList() {  

		IDevice[] devices = null;
		
	    int count = 0;  

	    while(bridge.hasInitialDeviceList() == false) {   

	       try{  

	       Thread.sleep(100); // Wait if can't get devices.  

	       count++;  

	       } catch(InterruptedException e) {}  

	       if(count > 20) {    // Timeout 100*20
	    	   return null;
	       

	       }  

	    }  
	    
	    if(bridge.hasInitialDeviceList())
	    	devices = bridge.getDevices(); 
	    
	    return devices;
	}
	
	public IDevice getFirstDevice() { 
		IDevice device=null;
		IDevice[] devices =  getDeviceList();
		
		if (devices.length!=0)
			device=devices[0];
		
		//test(device);
		return device;
	}
	
	public boolean isConnected()
	{
		boolean connected = bridge.getDevices().length>0 ? true:false;
		
		return connected;
	}
	
	private void test(IDevice device)
	{
		//IDevice device= getFirstDevice();
		//devices.
		String result;

	//	result = InstallPackage(device, "C:\\Users\\Hiro\\Desktop\\1.apk", true, "");
	//	result="";
	//	result = uninstallPackage(device, "com.voicedragon.musicclient.googleplay");
		result="";
	}

	
	public String InstallPackage(IDevice device, String apkPath, boolean reinstall, String... extraArgs)
	{
		String result; //null is success
		
		try {
			result = device.installPackage(apkPath, reinstall, extraArgs);
			
			if(result==null)
				result = "APK installed";
			
		} catch (InstallException e) {
			// TODO Auto-generated catch block
			result = e.toString();
		}
		
		return result;
	}
	
	public String uninstallPackage(IDevice device, String packageName)
	{
		String result;	//null is success 

		try {
			result = device.uninstallPackage(packageName);
		} catch (InstallException e) {
			// TODO Auto-generated catch block
			result = e.toString();
		}
		
		return result;
	}
	
	public void Query_devices()
	{
		String cmd = String.format(CMD.ADB_DEVICES);
		process.Exec(cmd);
	}
	
	public void SendSwipe(int x1, int y1, int x2, int y2)
	{
		String cmd = String.format(CMD.ADB_INPUT_SWIPE, x1, y1,x2, y2);
		process.Exec(cmd);
	}
	
	public void SendSetting()
	{
		String cmd = String.format(CMD.ADB_SETTING);
		process.Exec(cmd);
	}
	
	public void SendCall(String number)
	{
		if(!number.isEmpty()){
			String cmd = String.format(CMD.ADB_CALL, number);
			process.Exec(cmd);
		}
	}
	
	public void SendEndCall()
	{
		String cmd = String.format(CMD.ADB_INPUT_KEYEVENT, CMD.KEYCODE_ENDCALL);
		process.Exec(cmd);
	}
	
	
	public void SendTouch(int x, int y)
	{
		String cmd = String.format(CMD.ADB_INPUT_TOUCH, x, y);
		process.Exec(cmd);
	}
	
	public void SendKEY_POWER()
	{
		String cmd = String.format(CMD.ADB_INPUT_KEYEVENT, CMD.KEYCODE_POWER);
		process.Exec(cmd);
	}
	
	public void SendKEY_DPAD_UP()
	{
		String cmd = String.format(CMD.ADB_INPUT_KEYEVENT, CMD.KEYCODE_DPAD_UP);
		process.Exec(cmd);
	}
	public void SendKEY_DPAD_DOWN()
	{
		String cmd = String.format(CMD.ADB_INPUT_KEYEVENT, CMD.KEYCODE_DPAD_DOWN);
		process.Exec(cmd);
	}
	public void SendKEY_DPAD_LEFT()
	{
		String cmd = String.format(CMD.ADB_INPUT_KEYEVENT, CMD.KEYCODE_DPAD_LEFT);
		process.Exec(cmd);
	}
	public void SendKEY_DPAD_RIGHT()
	{
		String cmd = String.format(CMD.ADB_INPUT_KEYEVENT, CMD.KEYCODE_DPAD_RIGHT);
		process.Exec(cmd);
	}
	public void SendKEY_HOME()
	{
		String cmd = String.format(CMD.ADB_INPUT_KEYEVENT, CMD.KEYCODE_HOME);
		process.Exec(cmd);
	}
	public void SendKEY_BACK()
	{
		String cmd = String.format(CMD.ADB_INPUT_KEYEVENT, CMD.KEYCODE_BACK);
		process.Exec(cmd);
	}
	
	public void SendKEY_MENU()
	{
		String cmd = String.format(CMD.ADB_INPUT_KEYEVENT, CMD.KEYCODE_MENU);
		process.Exec(cmd);
	}
	
	public void SendKEY_DPAD_CENTER()
	{
		String cmd = String.format(CMD.ADB_INPUT_KEYEVENT, CMD.KEYCODE_DPAD_CENTER);
		process.Exec(cmd);
	}
	
	public void SendKEY_ENTER(String txt)
	{
		String cmd = "";
		
		cmd = String.format(CMD.ADB_INPUT_TEXT, txt);
		process.Exec(cmd);
		
		cmd = String.format(CMD.ADB_INPUT_KEYEVENT, CMD.KEYCODE_ENTER);
		process.Exec(cmd);
	}
	
	public void Send_TEXT(String text, boolean enterkey)
	{
		String cmd = String.format(CMD.ADB_INPUT_TEXT, text);
		process.Exec(cmd);
		
		if(enterkey)
			SendKEY_ENTER(text);
	}
	
	public String Get_Snapshot(String file)
	{
		String cmd = String.format(CMD.ADB_SCREENCAPTURE, "/sdcard/"+file);
		process.Exec(cmd);
		
		cmd = String.format(CMD.ADB_PULL, "/sdcard/"+file, file);
		process.Exec(cmd);
		
		return file;
	}
	
	public boolean init_environment(){
		
		boolean result = true;
		
		if(!del_file(CMD.STRING_FILE_SCREENCAPTURE))
			return result;
		
		
		return result;
	}
	
	public boolean release_environment(){
		
		boolean result = true;
		
		if(!del_file(CMD.STRING_FILE_SCREENCAPTURE))
			return result;
		
		
		return result;
	}
	
	private boolean del_file(String fn)
	{
		boolean result = false;
		
		try{
			File file = new File(fn);
			
			if(file.exists())
				result = file.delete();
			
		}catch(Exception e){
			//e.printStackTrace();
		}
		
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new controller().SendKEY_DPAD_RIGHT();;
		//new controller().Send_MENUKEY();;;
		//new controller().Send_TEXT("yahoo", true);;
		//new controller().Send_HOMEKEY();;;;
		
		new controller().Get_Snapshot("~/abc.png");
		
	}

}
