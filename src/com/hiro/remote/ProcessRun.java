package com.hiro.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProcessRun {

	Process 			p 		= null;
	String 				Cmd 	= "";
	String 				rString = "";
	ArrayList<String> 	rList	= null;	//result
	
	public ProcessRun() {
		rList 	= new ArrayList();
	}
	
//	private ArrayList GetListResult()
//	{
//		return rList;
//	}
//	
//	private String GetStringResult()
//	{
//		return rString;
//	}
//	
//	public ArrayList<String> Exec(String _cmd)
//	{
//		return Exec((_cmd.split(" ")));
//	}
	
	
	public ArrayList<String> Exec(String _cmd)
	{
		String 			line 	= "";	
		BufferedReader 	reader 	= null;
				
		rString = "";
		rList.clear();
		
		try {
			//p = Runtime.getRuntime().exec(new String[]{"bash","-c", _cmd});
			p = Runtime.getRuntime().exec(_cmd);
			p.waitFor();
			
			reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			while ((line = reader.readLine())!= null) {
				rList.add(line);
				rString+=(line + "\n");
				//System.out.println(line);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rList;
	}
	
	public ArrayList<String> Exec(String[] _cmd)
	{
		String 			line 	= "";	
		BufferedReader 	reader 	= null;
				
		rString = "";
		rList.clear();
		
		try {
			p = Runtime.getRuntime().exec(_cmd);
			p.waitFor();
			
			reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			while ((line = reader.readLine())!= null) {
				rList.add(line);
				rString+=(line + "\n");
				//System.out.println(line);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rList;
	}
	
	public static void main(String[] args) {
		
		ProcessRun r = new ProcessRun();
		
//		r.Exec("./dial.sh");
//		System.exit(0);
		

		String CMD_LS  = "adb devices"; //"ls"; 


		//Find Vendor ID
		ArrayList<String> list = (ArrayList<String>) r.Exec(CMD_LS).clone();


		
	}

}
