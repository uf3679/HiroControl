package com.hiro.remote;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import net.miginfocom.swing.MigLayout;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.TimeoutException;

public class Remotewindow {
	private boolean debug = false;
	private JFrame frame;
	
	private double ratio = 1.0;
	private controller Controller;
	private dialog Dialog;
	private screenshot Scrshot;
	private Thread _RefreshScreenThread = null;

	int freq = 2*100;	 //screen FPS
	IDevice device;
	int mAngle = 0;	//screen rotation angle
	
	JLabel imgScreen;
	JPanel screenpanel;
	private JButton btnUp;
	private JButton btnDown;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnCenter;
	private JButton btnHome;
	private JButton btnMenu;
	private JButton btnPower;
	private JButton btnInput;
	private JButton btnBack;
	private JButton btnUnlock;
	private JButton btnSetting;
	private JTextField _text;
	private JPanel CallArea;
	private JMenuBar menuBar;
	private JMenu mnAbout;
	private JMenuItem mntmNewMenuItem;
	private JLabel lbAdbStatus;
	

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Remotewindow window = new Remotewindow();
					window.frame.setVisible(true);
					
					//System.out.println("Hiro init.");
					window.StartCapture();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Remotewindow() {
		
		//System.out.println("Hiro initialize.");
		initialize();
		
		//Create adb object
		Controller  = new controller();	
		Dialog		= new dialog();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setTitle(CMD.APP);
		frame.setBounds(100, 100, 800, 560);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		mntmNewMenuItem = new JMenuItem(CMD.APP);
		mnAbout.add(mntmNewMenuItem);
		mntmNewMenuItem = new JMenuItem("Version: 1.2 (20160624)");
		mnAbout.add(mntmNewMenuItem);
		
		mntmUfyahoocomtw = new JMenuItem("Mail: uf3679@yahoo.com.tw");
		mnAbout.add(mntmUfyahoocomtw);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(.9);
		
		functionPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(functionPane);
		
		JPanel controlpanel = new JPanel();
		//splitPane.setRightComponent(controlpanel);
		functionPane.addTab("Control", null, controlpanel, null);
		controlpanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		screenpanel = new JPanel();
		splitPane.setLeftComponent(screenpanel);
		
		imgScreen = new JLabel("");
		imgScreen.setHorizontalAlignment(SwingConstants.CENTER);
		imgScreen.addMouseListener(actTouchListener);
		screenpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		screenpanel.add(imgScreen);
		
		
		JPanel FunctionArea = new JPanel();
		controlpanel.add(FunctionArea);
		
		btnPower = new JButton("POWER");
		btnPower.setName("Power");
		btnPower.addActionListener(actListener);
		FunctionArea.setLayout(new GridLayout(0, 3, 0, 0));
		FunctionArea.add(btnPower);
		
		btnHome = new JButton("HOME");
		btnHome.setName("Home");
		btnHome.addActionListener(actListener);
		FunctionArea.add(btnHome);
		
		btnMenu = new JButton("MENU");
		btnMenu.setName("Menu");
		btnMenu.addActionListener(actListener);
		FunctionArea.add(btnMenu);
		
		btnBack = new JButton("BACK");
		btnBack.setName("Back");
		btnBack.addActionListener(actListener);
		FunctionArea.add(btnBack);
		
		btnUnlock = new JButton("UNLOCK");
		btnUnlock.setName("Unlock");
		btnUnlock.addActionListener(actListener);
		FunctionArea.add(btnUnlock);
		
		
		btnSetting = new JButton("SETTING");
		btnSetting.setName("Setting");
		btnSetting.addActionListener(actListener);
		FunctionArea.add(btnSetting);
		

		JPanel DpadArea = new JPanel();
		controlpanel.add(DpadArea);
		
		JPanel PhoneArea = new JPanel();
		controlpanel.add(PhoneArea);
		PhoneArea.setLayout(new MigLayout("", "[433px]", "[68px][68px]"));
				
		
		CallArea = new JPanel();
		PhoneArea.add(CallArea, "cell 0 0,grow");
				
		JPanel MovingArea = new JPanel();
		MovingArea.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel panel = new JPanel();
		MovingArea.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		btnUp = new JButton("");
		btnUp.setToolTipText("Dpad-UP");
		btnUp.setName("Up");
		btnUp.setIcon(CMD.ICON_UP);
		panel.add(btnUp);
		btnUp.addActionListener(actListener);
		
		JPanel panel_1 = new JPanel();
		MovingArea.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 3, 0, 0));
		
		btnLeft = new JButton("");
		btnLeft.setToolTipText("Dpad-LEFT");
		btnLeft.setName("Left");
		btnLeft.setIcon(CMD.ICON_LEFT);
		panel_1.add(btnLeft);
		btnLeft.addActionListener(actListener);
		
		btnCenter = new JButton("CENTER");
		btnCenter.setToolTipText("Dpad-CENTER");
		btnCenter.setName("Center");
		panel_1.add(btnCenter);
		
		btnRight = new JButton("");
		btnRight.setToolTipText("Dpad-DOWN");
		btnRight.setName("Right");
		btnRight.setIcon(CMD.ICON_RIGHT);
		panel_1.add(btnRight);
		
		btnRight.addActionListener(actListener);
		
		btnCenter.addActionListener(actListener);
		
		JPanel panel_6 = new JPanel();
		MovingArea.add(panel_6);
		
		btnDown = new JButton("");
		btnDown.setToolTipText("Dpad-DOWN");
		btnDown.setName("Down");
		btnDown.setIcon(CMD.ICON_DOWN);
		btnDown.addActionListener(actListener);
		DpadArea.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
		panel_6.setLayout(new BorderLayout(0, 0));
		panel_6.add(btnDown);
		DpadArea.add(MovingArea);
		
		JPanel TypingArea = new JPanel();
		FlowLayout flowLayout = (FlowLayout) TypingArea.getLayout();
		flowLayout.setVgap(0);
		PhoneArea.add(TypingArea, "cell 0 1,grow");
		
		_text = new JTextField();
		_text.setToolTipText("Input text");
		_text.setFont(new Font("Dialog", Font.PLAIN, 18));
		TypingArea.add(_text);
		_text.setColumns(15);
		
		btnInput = new JButton("Input");
		btnInput.setName("Input");
		btnInput.addActionListener(actListener);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		TypingArea.add(btnInput);
		FlowLayout fl_CallArea = new FlowLayout(FlowLayout.CENTER, 5, 0);
		CallArea.setLayout(fl_CallArea);
		
		dialNumber = new JTextField();
		dialNumber.setToolTipText("Phone number");
		dialNumber.setFont(new Font("Dialog", Font.PLAIN, 18));
		CallArea.add(dialNumber);
		dialNumber.setColumns(15);
		
		btnCall = new JButton("");
		btnCall.setToolTipText("Call");
		btnCall.setName("Call");
		btnCall.setIcon(CMD.ICON_CALL);
		btnCall.addActionListener(actListener);
		CallArea.add(btnCall);
		
		btnEndcall = new JButton("");
		btnEndcall.setToolTipText("Endcall");
		btnEndcall.setName("Endcall");
		btnEndcall.setIcon(CMD.ICON_CALLEND);
		btnEndcall.addActionListener(actListener);
		CallArea.add(btnEndcall);
		frame.getContentPane().add(splitPane);
		
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		frame.getContentPane().add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 20));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		
		IconStatus = new JLabel("");
		statusPanel.add(IconStatus);
		
		lbAdbStatus = new JLabel("...");
		lbAdbStatus.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(lbAdbStatus);
		
		toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		btnLRotate = new JButton("");
		btnLRotate.addActionListener(ToolBtnListener);
		btnLRotate.setIcon(CMD.ICON_LROTATE);
		btnLRotate.setName("L-Rotate");
		btnLRotate.setToolTipText(btnLRotate.getName());
		toolBar.add(btnLRotate);
		
		btnRRotate = new JButton();
		btnRRotate.addActionListener(ToolBtnListener);
		btnRRotate.setIcon(CMD.ICON_RROTATE);
		btnRRotate.setName("R-Rotate");
		btnRRotate.setToolTipText(btnRRotate.getName());
		toolBar.add(btnRRotate);
		
		btnSnapshot = new JButton("");
		toolBar.add(btnSnapshot);
		btnSnapshot.addActionListener(ToolBtnListener);
		btnSnapshot.setIcon(CMD.ICON_SNAPSHOT);
		btnSnapshot.setName("Snapshot");
		btnSnapshot.setToolTipText(btnSnapshot.getName());
		
		btnInstallAPK = new JButton("");
		btnInstallAPK.addActionListener(ToolBtnListener);
		btnInstallAPK.setIcon(CMD.ICON_PACKAGE);
		btnInstallAPK.setName("Install_APK");
		btnInstallAPK.setToolTipText("Install APK");
		toolBar.add(btnInstallAPK);

	}
	
	private ActionListener ToolBtnListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			String act = ((JButton)e.getSource()).getName();

			if(device==null) {return;}
			
			if(act.equals(btnLRotate.getName())){
				//L-Rotate
				mAngle -= 90;
				mAngle = (mAngle<0? 270:mAngle);
			}
			else if(act.equals(btnRRotate.getName())){
				//R-Rotate
				mAngle += 90;
				mAngle = (mAngle>270? 0:mAngle);
			}
			else if(act.equals(btnInstallAPK.getName())){
				
				String path 	= null;
				String result 	= null;
				String apk 		= null;
				String splitStr = null;
				
				try{
					
					if( (path = Dialog.OpenDialog(frame, CMD.STRING_DIALOG_TITLE_INSTALL_APK, "apk")) != null)
			        {
						result = Controller.InstallPackage(device, path, true, "");
						
						//path type (windows/linux)
						if(path.contains("\\"))
							splitStr = "\\";	//windows
						else
							splitStr = "/";		//linux
						
						
						//obtain apk name
						for(String str : path.split(splitStr))
							apk = str;
						
						LOG(String.format(CMD.STRING_INSTALLAPK, apk, result));
			        }
					
				}catch(Exception ex){ DebugLOG(ex.getMessage()); }
				
				
			}
			else if(act.equals(btnSnapshot.getName())){
				
				String path;
				
				try{
						
					BufferedImage snapshot = Scrshot.getSnapshot();
					
					_RefreshScreenThread.suspend();
					
			        if( (path = Dialog.SaveDialog(frame, CMD.STRING_DIALOG_TITLE_SAVE_SNAPSHOT, CMD.FILENAME_SNAPSHOT)) != null)
			        {
						Scrshot.SaveSnapshot(snapshot, path);
						LOG(String.format(CMD.STRING_SAVESNAPSHOT, path));
			        }
			        
				}catch(Exception ex){ DebugLOG(ex.getMessage()); }
				
		        _RefreshScreenThread.resume();
		        
			}
		}

		

	};


	//private void control(String btn)
	private ActionListener actListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) 
		{
			//String act = e.getActionCommand();
			String act = ((JButton)e.getSource()).getName();
			
			if(act.equals(btnUp.getName()))
				Controller.SendKEY_DPAD_UP();
			else if(act.equals(btnDown.getName()))
				Controller.SendKEY_DPAD_DOWN();
			else if(act.equals(btnLeft.getName()))
				Controller.SendKEY_DPAD_LEFT();
			else if(act.equals(btnRight.getName()))
				Controller.SendKEY_DPAD_RIGHT();
			else if(act.equals(btnCenter.getName()))
				Controller.SendKEY_DPAD_CENTER();
			else if(act.equals(btnHome.getName()))
				Controller.SendKEY_HOME();
			else if(act.equals(btnPower.getName()))
				Controller.SendKEY_POWER();
			else if(act.equals(btnMenu.getName()))
				Controller.SendKEY_MENU();
			else if(act.equals(btnInput.getName()))
				Controller.SendKEY_ENTER(_text.getText());
			else if(act.equals(btnBack.getName()))
				Controller.SendKEY_BACK();
			else if(act.equals(btnSetting.getName()))
				Controller.SendSetting();
			else if(act.equals(btnUnlock.getName()))
				Controller.SendKEY_MENU();
			else if(act.equals(btnCall.getName()))
			{
				LOG(String.format(CMD.STRING_MAKECALL, dialNumber.getText()));
				Controller.SendCall(dialNumber.getText());
			}
			else if(act.equals(btnEndcall.getName()))
			{
				LOG(String.format(CMD.STRING_ENDCALL));
				Controller.SendEndCall();
			}
		}


	};
	
	
	private MouseListener actTouchListener = new MouseListener() {

		int mouseX = 0;
		int mouseY = 0;
		
		int mouseReleaseX = 0;
		int mouseReleaseY = 0;
		
		Point PressPoint, ReleasePoint;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		
		}


		@Override
		public void mousePressed(MouseEvent e) {
			
			mouseX = e.getX();
			mouseY = e.getY();
			

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
			mouseReleaseX = e.getX();
			mouseReleaseY = e.getY();
			
			//Calculate real points
			PressPoint 	= new Point((int)(mouseX /ratio), (int)(mouseY /ratio));
			ReleasePoint 	= new Point((int)(mouseReleaseX /ratio), (int)(mouseReleaseY /ratio));

			if(mouseReleaseX <= mouseX+10 && mouseReleaseX >= mouseX-10 &&
				mouseReleaseY <= mouseY+10 && mouseReleaseY >= mouseY-10){
				//Touch case
				LOG(String.format(CMD.STRING_CLICK, ratio, PressPoint.x, PressPoint.y));
				Controller.SendTouch(PressPoint.x, PressPoint.y);
			}else{
				//Swipe case
				LOG(String.format(CMD.STRING_SWIPE, ratio, PressPoint.x, PressPoint.y, ReleasePoint.x, ReleasePoint.y));
				Controller.SendSwipe(PressPoint.x, PressPoint.y, ReleasePoint.x, ReleasePoint.y);
			}


		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); 
			e.getComponent().setCursor(cursor);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			Cursor cursor = Cursor.getDefaultCursor();
			e.getComponent().setCursor(cursor);
		}
	};

	private JLabel IconStatus;
	private JTabbedPane functionPane;
	private JTextField dialNumber;
	private JButton btnCall;
	private JButton btnEndcall;
	private JToolBar toolBar;
	private JButton btnRRotate;
	private JButton btnLRotate;
	private JButton btnSnapshot;
	private JMenuItem mntmUfyahoocomtw;
	private JButton btnInstallAPK;
	
	private void RefreshScreen() {
		
		BufferedImage img = null;
		
		img = ScreenCapture(img);
		
		if(img!=null)	 ReszieImage(img);

	}
	
	private void ReszieImage(BufferedImage img) {
		
		int MaxWidth  = CMD.IMG_MAXSIZE;
		int MaxHeight = CMD.IMG_MAXSIZE;
		int ScrWidth  = screenpanel.getWidth();
		int ScrHeight = screenpanel.getHeight();
		int ImgWidth  = img.getWidth();
		int ImgHeight = img.getHeight();
		
		
		if(ImgWidth>0)
			MaxWidth = (ScrWidth>ImgWidth? ImgWidth:ScrWidth);
			
		if(ImgHeight>0)
			MaxHeight = (ScrHeight>ImgHeight? ImgHeight:ScrHeight);
		
		DebugLOG(String.format("Image(%d, %d), screenpanel(%d, %d) Max(%d, %d)", 
				ImgWidth, ImgHeight, ScrWidth, ScrHeight,	MaxWidth, MaxHeight));
		
		try{
			//Set screen to image
			imgScreen.setIcon(new ImageIcon(Scrshot.resize(MaxWidth, MaxHeight, img)));
			ratio = Scrshot.getRatio();

		}catch(Exception e){
			DebugLOG("Resize screen exception.");	
		}
	}

	private BufferedImage ScreenCapture(BufferedImage img) {
		
		Scrshot = new screenshot();
		
		//Obtain screen capture
		try {
			if(Controller.isConnected()){
				String file = Controller.Get_Snapshot(CMD.STRING_FILE_SCREENCAPTURE);
				img = Scrshot.screencapture(file, mAngle);
			}else{
				DebugLOG("Disconnected.");
				init();
			}
		} catch (TimeoutException e1) {
			DebugLOG("Screen capture timeout exception.");
			init();
		} catch (AdbCommandRejectedException e1) {
			LOG("ADB rejected exception.");
			init();
		} catch (IOException e1) {
			DebugLOG("Screen capture IOException.");
			init();
		}

		return img;
		
	}
	
	private class ThreadRefreshScreen implements Runnable {

		private int frequency;

		ThreadRefreshScreen(int freq) {
			this.frequency = freq;
		}

		public void run() {
			synchronized (this) {
				while (true) {
					
					DebugLOG("Device="+device);
					
					if(device!=null)
						RefreshScreen();
					else
						init();
						
					Sleep(this.frequency);
				}
			}
		}
	}
	
	public void Sleep(int millis)
	{
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init() {

		String state = "";
		boolean init_result = false;
		
		DebugLOG("init");
		
		device = null;
		imgScreen.setIcon(null);
		
		//Request new device
		Controller.Query_devices();
		device = Controller.getFirstDevice();
		
		
		//if(device == null || device.isOffline() || device.getSerialNumber().startsWith("??")){
		if(device != null && device.isOnline())
		{
			//ADB connected
			state = String.format(CMD.STRING_CONNECTED,device);
			
			System.out.println(String.format(CMD.STRING_DEVICEINFO,device));
			lbAdbStatus.setText(state);
			IconStatus.setIcon(CMD.ICON_GREEN);
			
			//Init remote environment
			if(Controller.init_environment())
				init_result = true;
			
			if(init_result)
				LOG("<<<<< Init done >>>>>");
			
		}else{
			//ADB disconnected
			state = String.format(CMD.STRING_DISCONNECTED,device);
			
			LOG(state);
			lbAdbStatus.setText(state);
			IconStatus.setIcon(CMD.ICON_RED);
		}
	}
	
	
	public void StartCapture()
	{
		_RefreshScreenThread = new Thread(new ThreadRefreshScreen(freq));
		_RefreshScreenThread.start();
		
	}
	

	private void getRatio() {
		
		while(ratio==0.0 || ratio ==1.0)
		{
			ratio = Scrshot.getRatio();
			Sleep(10);
			//System.out.println(ratio);
		}
	}
	
	
	private void LOG(String str){
		System.out.println("LOG   >> "+ str);
	}
	
	private void DebugLOG(String str){
		if(debug)
			System.out.println("DEBUG >> "+str);
	}
	

}
