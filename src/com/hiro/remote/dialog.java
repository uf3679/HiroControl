/**
 * 
 */
package com.hiro.remote;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * @author hiro
 *
 */
public class dialog {

	/**
	 * 
	 */
	public dialog() {
		// TODO Auto-generated constructor stub
	}

	public String SaveDialog(Component parent, String dialogTitle, String defaultName) {
		
		String 			path 		  = null;
		String 			fileExtension = null;
		JFileChooser 	chooser		  = null;

		//Obatin file extension name
		for(String str : defaultName.split("\\."))
			fileExtension = str;
		
		// create file dialog
		chooser = new JFileChooser();
		chooser.setFileFilter(new FilesFilter(fileExtension));
		chooser.setSelectedFile(new File(defaultName));
		chooser.setDialogTitle(dialogTitle);
		
		int option = chooser.showSaveDialog(parent);
		
		if (option == JFileChooser.APPROVE_OPTION) 
		{
			if((chooser.getSelectedFile()!=null))
				path = chooser.getCurrentDirectory()+"/"+chooser.getSelectedFile().getName();
		}
		
		return path;
	}
	
	
	public String OpenDialog(Component parent, String dialogTitle, String extensionName) {
		
		String 			path 		  = null;
		String 			fileExtension = null;
		JFileChooser 	chooser		  = null;

		//Obatin file extension name
		fileExtension = extensionName;
		
		// create file dialog
		chooser = new JFileChooser();
		chooser.setFileFilter(new FilesFilter(fileExtension));
		chooser.setDialogTitle(dialogTitle);
		
		int option = chooser.showOpenDialog(parent);
		
		if (option == JFileChooser.APPROVE_OPTION) 
		{
			if((chooser.getSelectedFile()!=null))
				path = chooser.getCurrentDirectory()+"/"+chooser.getSelectedFile().getName();
		}
		
		return path;
	}
	
	
	class FilesFilter extends FileFilter {

		String FileExtension;
		
		public FilesFilter(String fileExtension)
		{
			FileExtension = fileExtension;
		}
		
		public boolean accept(File f) {
		    if (f.isDirectory())
		      return true;
		    String s = f.getName();
		    int i = s.lastIndexOf('.');

		    if (i > 0 && i < s.length() - 1)
		      if (s.substring(i + 1).toLowerCase().equals(FileExtension))
		        return true;

		    return false;
		}

		public String getDescription() {
			return "*."+FileExtension;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
