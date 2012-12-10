package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 * Sends a file (passed as a parameter) to the host system's printer spooler allowing the file to be printed.
 * 
 * Wrapped in a dedicated thread to enforce robustness and prevent "locking up" of the GUI.
 */
public class Print implements Runnable {

	/** The file to be sent to the printer */
	File fileToPrint;
	
	/** The the host system environment */
	Desktop printDesktop = Desktop.getDesktop();
	
	/**
	 * Instantiates a new Print object, and defines the path name of the file that is to be printed.
	 *
	 * @param fileToPrint The pathname of the file that is to be sent to the printer.
	 */
	public Print(File fileToPrint) {
		
		//Set the path name of the file that is to be printed
		this.fileToPrint = fileToPrint;
		
	}
	
	/** 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
	
		try {
			
			//Send the specified file
			printDesktop.print(fileToPrint);
		} catch (IOException e) {	
			//Printout error - CANNOT SEEM TO THROW THIS DUE TO AN ISSUE WITH 'run()' ??
		}
	}

}
