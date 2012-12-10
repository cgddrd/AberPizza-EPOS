package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Order;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Receipt;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Size;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

/**
 * JUnit test case class for methods and operations in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Receipt} class.
 * 
 * @author Connor Goddard (clg11)
 */
public class ReceiptTest {

	/** The Till test object. */
	private Till testTill;

	/** The Order test object. */
	private Order testOrder;

	/**
	 * Performs generic operations that are required before every test case method.
	 */
	@Before
	public void setUp() {

		//Initialise the test Till and Order objects
		testTill = new Till();
		testOrder = new Order();
	}


	/**
	 * Test case method for <code>exportReceipt()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Receipt} class.
	 * Tests that the method can successfully generate and export bespoke receipts for  
	 * {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} objects retrieved 
	 * from the {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} class.
	 */
	@Test
	public void testExportReceipt() {

		//Create a test order (that contains a test item), and add it to the test Till object
		testOrder.addItem(new Pizza("Test Pizza", Size.LARGE, PizzaType.CHEESE, new BigDecimal("2.40")), 1);
		testOrder.setCustomerName("Connor Goddard");
		testTill.addOrder(testOrder);

		//Initialise a new test Receipt object passing the new order array list as a parameter
		Receipt testReceipt = new Receipt(testTill.getOrderArray());

		//Run the operations to export a bespoke receipt for each order (1)
		testReceipt.exportReceipt("C:\\", false);

		//Create variable that represents the expected HTML receipt file
		File testReceiptFile = new File("C:\\AberPizza Receipt - Connor Goddard.html");

		//Check that the expected file exists, confirming that the export has been successful
		assertTrue("Receipt export file should exist in directory: 'C:\\AberPizza Receipt - <Customer-Name>.html'", testReceiptFile.exists());

		//Create a search term
		String searchTerm = "Connor";

		//Create a boolean to set if the search term has been found or not
		Boolean searchTermFound = false;

		//Search through exported receipt for the search term
		try {
			
			/* This portion of code has been modified from code taken from a Stack Overflow post.
			 * Available at: stackoverflow.com/3697833/find-all-string-the-in-in-txt-file.html
			 */

			BufferedReader bf = new BufferedReader(new FileReader("C:\\AberPizza Receipt - Connor Goddard.html"));

			String line;

			while ((line = bf.readLine()) != null) {

				int indexFound = line.indexOf(searchTerm);

				//If the search term is found in the file
				if (indexFound > -1) {

					//Update the search found boolean
					searchTermFound = true;
				}
				
				//***End of modified code**

			}

		} catch (FileNotFoundException e) {
			fail();
		} catch (IOException e) {
			fail();
		}

		//Check that the search term has been found in the file
		assertTrue("Search term should have been found", searchTermFound);
	}

}
