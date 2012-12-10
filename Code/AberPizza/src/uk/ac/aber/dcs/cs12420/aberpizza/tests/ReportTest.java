package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.DrinkType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Order;
import uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Report;
import uk.ac.aber.dcs.cs12420.aberpizza.data.SideType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Size;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

/**
 * JUnit test case class for methods and operations in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Report} class.
 * 
 * @author Connor Goddard (clg11)
 */
public class ReportTest {

	/** The Till test object. */
	private Till testTill;

	/** The current date */
	private Date today;

	/** The date formatter for file names */
	private Format fileNameFormatter;

	/** The Report test object */
	private Report testReport;

	/**
	 * Performs generic operations that are required before every test case method.
	 */
	@Before
	public void setUp() {

		//Initialise new test Till object
		testTill = new Till();

		//Initialise new date object set to current date
		today = new Date();

		//Initialise new date formatter for generating file names
		fileNameFormatter = new SimpleDateFormat("dd-MM-yy");

		/* Create test Order objects populated with test items before
		adding them to the test Till object */
		testTill.createNewOrder("Connor Goddard");
		testTill.getNewOrder().createNewPizza(Size.LARGE, PizzaType.HAM_CHEESE, 1);
		testTill.getNewOrder().createNewDrink(Size.SMALL, DrinkType.LEMONADE, 2);
		testTill.getNewOrder().setAmountPaid(new BigDecimal("1000.00"));
		testTill.addOrder(testTill.getNewOrder());
		testTill.closeOrder();

		testTill.createNewOrder("Helen Harman");
		testTill.getNewOrder().createNewPizza(Size.SMALL, PizzaType.PEPPERONI, 34);
		testTill.getNewOrder().createNewDrink(Size.SMALL, DrinkType.LEMONADE, 2);
		testTill.getNewOrder().createNewDrink(Size.MEDIUM, DrinkType.COLA, 1);
		testTill.getNewOrder().createNewSide(Size.LARGE, SideType.COLESLAW, 5);
		testTill.getNewOrder().setAmountPaid(new BigDecimal("430.00"));
		testTill.addOrder(testTill.getNewOrder());
		testTill.closeOrder();

		//Initialise test Report object using data obtained from the newly-populated test Till object
		testReport = new Report(testTill.getOrderArray(), testTill.getTotalForDay(), testTill.getTotalDiscountForDay(), testTill.getTotalNoOfOrders());
	}

	/**
	 * Test case method for constructor in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Report} class.
	 * Tests that the constructor allows the correct data to be passed as parameters and sorted.
	 */
	@Test
	public void testReport() {

		/* Create test Item & Order objects that represent some of the data passed to
		 the test Report class as parameters */
		Item testItem = testReport.getReportArray().get(0).getItemArray().get(0).getItem();
		Order testOrder = testReport.getReportArray().get(0);
		Order testOrder2 = testReport.getReportArray().get(1);

		//Checks that the data passed as parameters to the test Report object matches what is stored in the test object
		assertEquals("First order customer name should be 'Connor Goddard'", "Connor Goddard", testOrder.getCustomerName());
		assertEquals("First item in first order should have size 'LARGE'", Size.LARGE, testItem.getSize());
		assertEquals("Fourth item in second order should have quantity 5", 5, testOrder2.getItemArray().get(3).getQuantity());
		assertEquals("Total taken should be '222.00", "222.00", testReport.getTotalForDay().toString());
		assertEquals("Total discount applied should be '0.00", "0.00", testReport.getTotalDiscountForDay().toString());
		assertEquals("Total no of orders should be '2", 2, testReport.getNoOfOrders());

	}

	/**
	 * Test case method for <code>exportReport()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Report} class.
	 * Tests that the method can successfully generate and export a bespoke 'end of day' report 
	 * using data from a test {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} object.
	 */
	@Test
	public void testExportReport() {

		//Generate and export the bespoke 'end of day' report
		try {
			testTill.exportReport("C:\\TestReport.html", new URI("file:///C:/TestReport.html"), false);
		} catch (IOException e) {
			fail("IO Exception Triggered");
		} catch (URISyntaxException e) {
			fail ("URI Syntax Exception Triggered");
		}

		//Create variable that represents the expected HTML report file
		File testReportFile = new File("C:\\TestReport.html");

		//Check that the expected file exists, confirming that the export has been successful
		assertTrue("Receipt export file should exist in directory: 'C:\\TestReport.html", testReportFile.exists());

		//Create a search term
		String searchTerm = "Connor";

		//Create a boolean to set if the search term has been found or not
		Boolean searchTermFound = false;

		//Search through exported receipt for the search term
		try {

			/* This portion of code has been modified from code taken from a Stack Overflow post.
			 * Available at: stackoverflow.com/3697833/find-all-string-the-in-in-txt-file.html
			 */

			BufferedReader bf = new BufferedReader(new FileReader("C:\\TestReport.html"));

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
