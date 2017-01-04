package com.hiro.remote;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.TimeoutException;

public class screenshot {
	
	private boolean 		mLandscape=false;
	private Dimension 		imgSize;
	private double 			ratio;
	private BufferedImage	mScreenImage;

	
	public screenshot() {
		imgSize 		= new Dimension();
	}

	
	public double getRatio()
	{
		return ratio;
	}
	
	public BufferedImage getSnapshot()
	{
		// 5 round, if image == null;
		for(int i=0; i<5; i++)
		{
			if(mScreenImage!=null) break;
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return mScreenImage;
	}
	
	public void SaveSnapshot(BufferedImage image, String filepath)
	{
		try {
			ImageIO.write((RenderedImage)image, "PNG", new File(filepath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
	}
	
	public BufferedImage ReadImageFromFile(String file)
	{
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(file));
		} catch (IOException e) {
		}
		
		return img;
	}
	
	public boolean isLandscape(BufferedImage image, int angle)
	{
		boolean landscape =  false;
		
		if(image != null){
			if(image.getHeight() > image.getWidth())
				landscape = false;	//Portrait
			else
				landscape = true;	//Landscape
			
		}
		
		if(landscape && (angle == 0 || angle == 180))
			landscape = true;
		else if(landscape && (angle == 90 || angle == 270))
			landscape = false;
		else if(!landscape && (angle == 0 || angle == 180))
			landscape = false;
		else if(!landscape && (angle == 90 || angle == 270))
			landscape = true;

		return landscape;
		
	}
	
	public BufferedImage screencapture(String file) throws TimeoutException, AdbCommandRejectedException, IOException
	{
		return screencapture(file, 0);
	}
	
	public BufferedImage screencapture(String file, int angle ) throws TimeoutException, AdbCommandRejectedException, IOException
	{
		BufferedImage rawImage = null;

		int indexInc;	//RGB pixel index
		int index;		//Output image index
		
		//rawImage = device.getScreenshot();
		rawImage = ReadImageFromFile(file); 
		
		if(rawImage != null){  
	
			mLandscape = isLandscape(rawImage, angle);

			if(mLandscape){
				imgSize.width   = Math.max(rawImage.getWidth(), rawImage.getHeight());  
				imgSize.height  = Math.min(rawImage.getWidth(), rawImage.getHeight()); 
			}else{
				imgSize.width   = Math.min(rawImage.getWidth(), rawImage.getHeight());    
				imgSize.height  = Math.max(rawImage.getWidth(), rawImage.getHeight());   
			}
			
			//System.out.println("Angle="+ angle +", mLandscape="+ mLandscape +", RawW="+ rawImage.width +", RawH="+ rawImage.height +", ImgW="+imgSize.width+", ImgH="+imgSize.height);
			
			AffineTransform transform = new AffineTransform();
		    transform.rotate(Math.toRadians(angle), rawImage.getWidth()/2, rawImage.getHeight()/2);
		    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		    rawImage = op.filter(rawImage, null);
		}
		
		mScreenImage = rawImage;
		
		return rawImage;
	}

/*  //Capture screen shot via ADB getScreenshot()

	public boolean isLandscape(RawImage raw, int angle)
	{
		boolean landscape =  false;
		
		if(raw != null){
			if(raw.height > raw.width)
				landscape = false;	//Portrait
			else
				landscape = true;	//Landscape
			
		}
		
		if(landscape && (angle == 0 || angle == 180))
			landscape = true;
		else if(landscape && (angle == 90 || angle == 270))
			landscape = false;
		else if(!landscape && (angle == 0 || angle == 180))
			landscape = false;
		else if(!landscape && (angle == 90 || angle == 270))
			landscape = true;

		return landscape;
		
	}
	
	public BufferedImage screencapture(IDevice device) throws TimeoutException, AdbCommandRejectedException, IOException
	{
		return screencapture(device, 0);
	}
	
	public BufferedImage screencapture(IDevice device, int angle ) throws TimeoutException, AdbCommandRejectedException, IOException
	{
		RawImage rawImage = null;
		BufferedImage image = null;
		int indexInc;	//RGB pixel index
		int index;		//Output image index
		
		rawImage = device.getScreenshot();
		
		if(rawImage != null){  
	
			mLandscape = isLandscape(rawImage, angle);

			if(mLandscape){
				imgSize.width   = Math.max(rawImage.width, rawImage.height);  
				imgSize.height  = Math.min(rawImage.width, rawImage.height);  
			}else{
				imgSize.width   = Math.min(rawImage.width, rawImage.height);  
				imgSize.height  = Math.max(rawImage.width, rawImage.height);  
			}
			
			//System.out.println("Angle="+ angle +", mLandscape="+ mLandscape +", RawW="+ rawImage.width +", RawH="+ rawImage.height +", ImgW="+imgSize.width+", ImgH="+imgSize.height);
			
			image = new BufferedImage(imgSize.width, imgSize.height, BufferedImage.TYPE_INT_RGB);  


		    index 		= 0;  
		    indexInc 	= rawImage.bpp >> 3;  
		    
		    for (int y = 0; y < rawImage.height; y++) {
		    	for (int x = 0; x < rawImage.width; x++, index += indexInc) {
		    		
		    		int xPixel=0, yPixel=0;
		    		
		    		//Ratation
		    		switch(angle)
		    		{
		    		case 0:
		    			xPixel = x;
		    			yPixel = y;
		    			break;
		    		case 90:
		    			xPixel = imgSize.width  - y -1;
		    			yPixel = x;
		    			break;
		    		case 180:
		    			xPixel = imgSize.width   - x -1;
		    			yPixel = imgSize.height  - y -1;
		    			break;
		    		case 270:
		    			xPixel = y;
		    			yPixel = imgSize.height - x -1;
		    			break;
		    		}
			    	
		    		//System.out.println("xPixel="+ xPixel +", yPixel="+ yPixel+", x="+ x +", y="+ y);
		    		
		    		int value = rawImage.getARGB(index);  
		    		image.setRGB(xPixel, yPixel, value);
		    		
		        }  
		    }   
		    
		    //img = resize(image);
		    //img = image;
		    
		    //ImageIO.write((RenderedImage)image,"PNG",new File("D:/temp.jpg"));  
		    
		    mScreenImage = image;
		}
		return image;
	}
*/
	
	public Image resize(double MaxWidth, double MaxHeight, BufferedImage image)
	{
		Image img = null;

		if(image.getWidth()>image.getHeight()){
			//Landscape
			if(image.getWidth()>MaxWidth)
				ratio = (MaxWidth / image.getWidth());
			else
				ratio = (MaxWidth / image.getHeight());
		}
		else{
			//Portrait
			if(image.getHeight()>MaxHeight)
				ratio = (MaxHeight / image.getHeight());
			else
				ratio = (MaxHeight / image.getWidth());
		}
	    
		if(ratio > 1)
			ratio = 1;
		
		imgSize.width  = (int) (image.getWidth()  *ratio);  
		imgSize.height = (int) (image.getHeight() *ratio);
	    //System.out.println("width="+imgSize.width+", height"+ imgSize.height);

		//System.out.println("ratio="+ratio);
	    img = image.getScaledInstance(imgSize.width, imgSize.height,Image.SCALE_DEFAULT);
	    
	    return img;
		
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		//screenshot scrshot = new screenshot();
//	}

}
