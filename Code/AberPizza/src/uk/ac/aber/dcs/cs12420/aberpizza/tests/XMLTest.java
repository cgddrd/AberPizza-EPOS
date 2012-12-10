package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.DrinkType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Size;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;
import uk.ac.aber.dcs.cs12420.aberpizza.data.XML;

/**
 * JUnit test case class for methods and operations in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Report} class.
 * 
 * @author Connor Goddard (clg11)
 */
public class XMLTest {
	
	/** The XML test object. */
	private XML testXML;
	
	/** The Till test object. */
	private Till testTill;

	/**
	 * Performs generic operations that are required before every test case method.
	 */
	@Before
	public void setUp() {
		
		//Initialise the test XML and Till objects
		testXML = new XML();
		testTill = new Till();
	}

	/**
	 * Test case method for <code>saveTill()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.XML} class.
	 * Tests that the method can export an entire {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} object to XML 
	 * before saving the file to the file system.
	 */
	@Test
	public void testSaveTill() {
		
		//Create a test order (that contains a test item), and add it to the test Till object
		testTill.createNewOrder("Connor Goddard");
		testTill.getNewOrder().createNewPizza(Size.LARGE, PizzaType.HAM_CHEESE, 1);
		testTill.getNewOrder().createNewDrink(Size.SMALL, DrinkType.LEMONADE, 2);
		testTill.addOrder(testTill.getNewOrder());
		
		/* Run the operations to export the entire test Till object to XML and save the
		new XML file to a specified file path */
		testXML.saveTill(testTill, "C:\\JUnitTestXML.xml");
		
		//Create variable that represents the expected XML file
		File testXML = new File("C:\\JUnitTestXML.xml");
		
		//Check that the expected file exists, confirming that the export has been successful
		assertTrue("Receipt export file should exist in directory: 'C:\\JUnitTestXML.xml", testXML.exists());
		
	}

	/**
	 * Test case method for <code>loadTill()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.XML} class.
	 * Tests that the method can create a new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} object that contains data retrieved 
	 * from a saved XML file.
	 */
	@Test
	public void testLoadTill() {
		
		//Create new test Till object that will be used to test loading data from XML
		Till testTill2 = null;
		
		try {
			
			//Attempt to initialise the test Till object using data from the specified XML file
			testTill2 = Till.load("C:\\JUnitTestXML.xml");
			
		} catch (IOException e) {
			
			//If an exception occurs, fail the test
			fail("Failed to load the XML file");
			
		}
		
		//Check the new Till object contains an order with a specified customer name, confirming the load was successful
		assertEquals("Customer name in new Till object should match 'Connor Goddard'", "Connor Goddard", testTill2.getOrderArray().get(0).getCustomerName());
		
	}
	
	/**
	 * Test case method for <code>loadTill()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.XML} class.
	 * Tests that the method can create a brand new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} object when an XML
	 * file cannot be found or accessed.
	 */
	@Test 
	public void testLoadOrdersNoData() throws IOException {
		
		//Create new test Till object 
		Till testTill2 = null;
		
		try {
			
			//Attempt to initialise the test Till object using a non-existant XML file
			testTill2 = Till.load("C:\\NoFile.xml");
			
		} catch (FileNotFoundException fNP) {
			
			//If this cannot be done, create a new Till object
			testTill2 = new Till();
			
		} catch (IOException e) {
			//If neither works, fail the test
			fail("Failed to load the XML file or create a new Till object");
		}
		
		//Check that a new Till has been created by ensuring that there is no data store din the test Till object
		assertTrue("Order array in Till object should have a size of 0", testTill2.getOrderArray().size() == 0);
		
	}

}
