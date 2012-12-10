package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.NoSuchElementException;


/**
 * Exports/imports entire {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} object
 * including array list of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} objects to/from an XML file.
 * 
 * @author Connor Goddard (clg11)
 */
public class XML {

	/** The XMLEncoder object for exporting to XML */
	private XMLEncoder xmlEncoder;

	/** The XMLDecoder object for importing from XML */
	private XMLDecoder xmlDecoder;

	/**
	 * Exports the entire {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} object (taken as a parameter)
	 * to XML before saving it to the file system.
	 *
	 * @param till The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} object that is to be exported to XML.
	 * @param pathName The destination path name of the exported XML file.
	 */
	public void saveTill(Till till, String pathName) {

		try {

			//Create new XMLEncoder and set destination to specified path name
			xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(pathName)));

			//Create new PersistanceDelegate to allow BigDecimal objects to be exported corectly.
			PersistenceDelegate pd = xmlEncoder.getPersistenceDelegate(Integer.class);
			xmlEncoder.setPersistenceDelegate(BigDecimal.class, pd);

			//Write the whole Till object to XML
			xmlEncoder.writeObject(till);

			//Clear the encoder to ensure all data has been successfully encoded
			xmlEncoder.flush();

			//Save the XML file and finally close the encoder
			xmlEncoder.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Reads/loads Till data from XML file to new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} object 
	 * using selected file path. If XML file cannot be parsed, returns a new Till object instead.
	 *
	 * @param pathName The specified source file path
	 * @return New {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} object (either from XML file or new)
	 * @throws ArrayIndexOutOfBoundsException Could not access the Order array list (Data not acceptable) - Caught by GUI
	 * @throws NoSuchElementException Could not find any Till object (Data not acceptable) - Caught by GUI
	 */
	public Till loadTill(String pathName) throws ArrayIndexOutOfBoundsException, NoSuchElementException {

		//Create new File object using specified source file path
		File tillLoad = new File(pathName);

		try {

			//Create new XMLDecoder using streams set to file path selected by user
			xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(tillLoad)));

			//Create new Till object using data from XML file
			Till obj = (Till) xmlDecoder.readObject();
			
			//Return this new till object
			return obj;
			
		} catch (FileNotFoundException fNF) {
			
			//If the XML file cannot be found, return a completely new Till object
			return new Till();
			
		} catch (NoSuchElementException nSE) {
			
			return new Till();
			
		} catch (Exception e) {
			
			return new Till();
			
		}

	}

}
