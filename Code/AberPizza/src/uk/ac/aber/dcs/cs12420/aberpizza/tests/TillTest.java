package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.DrinkType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Order;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType;

import uk.ac.aber.dcs.cs12420.aberpizza.data.SideType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Size;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

/**
 * JUnit test case class for methods and operations in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} class.
 * 
 * @author Connor Goddard (clg11)
 */
public class TillTest {
	
	/** The Till test object. */
	private Till till;
	
	/** The current date */
	private Date today;
	
	/** The date formatter for file names */
	private Format fileNameFormatter;

	/**
	 * Performs generic operations that are required before every test case method.
	 */
	@Before
	public void setUp() {
		
		//Initialise new test Till object
		till = new Till();
		
		//Initialise new date object set to current date
		today = new Date();
		
		//Initialise new date formatter for generating file names
		fileNameFormatter = new SimpleDateFormat("dd-MM-yy");
	}

	@Test
	public void testCreateNewOrderWithName() {
		
		//Run 'createNewOrder' method with a test name
		till.createNewOrder("Connor Goddard");
		
		//Check that the 'newOrder' object returns the same name that was entered.
		assertEquals("Should return inputted name","Connor Goddard", till.getNewOrder().getCustomerName());
	}
	
	/**
	 * Test case method for <code>createNewOrder()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} class.
	 * Tests that method can initialise the 'newOrder' object without a specified customer name.
	 */
	@Test
	public void testCreateNewOrderWithoutName() {
		
		//Initialise the 'newOrder' object without any name
		till.createNewOrder(null);
		
		//Check that the returned name value is null
		assertNull("Should return null for customer name", till.getNewOrder().getCustomerName());
	}


	/**
	 * Test case method for <code>addOrder()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} class.
	 * Tests that the method can successfully add a test {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} object
	 * to the array list of Orders stored with Till.
	 */
	@Test
	public void testAddOrder() {
		
		//Create a new Order object and set a specified name
		Order testOrder = new Order();
		testOrder.setCustomerName("Test User");
		
		//Create a new Pizza item and add it to the item array list inside the test Order object
		Pizza testPizza = new Pizza();
		testPizza.setDescription("Test Pizza");
		testPizza.setPrice(new BigDecimal("5.99"));
		testPizza.setSize(Size.LARGE);
		testPizza.setType(PizzaType.HAM_CHEESE);
		
		testOrder.addItem(testPizza, 1);
		
		//Add the test order object to the array list of Orders (orderArray)
		till.addOrder(testOrder);
		
		//Ensure that the size of the order array list inside the Till test object is > 0
		assertTrue("Size should be greater than 0", till.getOrderArray().size() > 0);
	}

	/**
	 * Test case method for <code>getTotalForDay()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} class.
	 * Tests that the method can successfully calculate and return the sum of the sub totals of all the stored {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} 
	 * objects inside the Till object.
	 */
	@Test
	public void testGetTotalForDay() {
		
		//Create first test order
		till.createNewOrder("Connor Goddard");
		
		//Add a selection of test items to that test order
		till.getNewOrder().createNewPizza(Size.SMALL, PizzaType.HAM_CHEESE, 1);
		till.getNewOrder().createNewDrink(Size.MEDIUM, DrinkType.LEMONADE, 1);
		till.getNewOrder().createNewSide(Size.LARGE, SideType.COLESLAW, 1);
		
		//Add the first test order to the array list of Orders stored in the test Till object
		till.addOrder(till.getNewOrder());
		
		//Close the first order
		till.closeOrder();
		
		//Create second test order
		till.createNewOrder("Helen Harman");
		
		//Add a selection of test items to that test order
		till.getNewOrder().createNewPizza(Size.MEDIUM, PizzaType.HAM_CHEESE, 1);
		till.getNewOrder().createNewDrink(Size.MEDIUM, DrinkType.LEMONADE, 1);
		till.getNewOrder().createNewSide(Size.SMALL, SideType.COLESLAW, 1);
		till.getNewOrder().createNewSide(Size.LARGE, SideType.FRIES, 1);
		
		//Add the second test order to the array list of Orders stored in the test Till object
		till.addOrder(till.getNewOrder());
	
		//Check that the sum of the sub totals of both the test order object equals '35.00'
		assertEquals("Should return the total of all orders", "35.00", till.getTotalForDay().toString());
		
	}

	/**
	 * Test case method for <code>save()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} class.
	 * Tests that the method can successfully export the Till object to XML before saving the file.
	 */
	@Test
	public void testSave() {
		
		//Create a test Order object and populate it with a selection of test items
		till.createNewOrder("Connor Goddard");
		
		till.getNewOrder().createNewPizza(Size.LARGE, PizzaType.HAM_CHEESE, 1);
		till.getNewOrder().createNewDrink(Size.MEDIUM, DrinkType.LEMONADE, 1);
		till.getNewOrder().createNewSide(Size.SMALL, SideType.COLESLAW, 1);
		
		//Add the test Order object to order array list in the test Till object
		till.addOrder(till.getNewOrder());
		
		//Run the operations to export and save the Till object as XML
		till.save("C:\\Test.xml");
		
		//Create variable that represents the expected XML file
		File testXML = new File("C:\\Test.xml");
		
		//Check that the expected file exists, confirming that the export has been successful
		assertTrue("XML export file should exist in directory: 'C:\\Test.xml", testXML.exists());
		
	}

	/**
	 * Test case method for <code>load()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} class.
	 * Tests that the method can successfully create a new Till object from XML, or if this is not possible, creates 
	 * a new Till object.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testLoad() throws IOException {
		
		//Create a test Order object and populate it with a selection of test items
		till.createNewOrder("Connor Goddard");
		
		till.getNewOrder().createNewPizza(Size.LARGE, PizzaType.HAM_CHEESE, 1);
		till.getNewOrder().createNewDrink(Size.MEDIUM, DrinkType.LEMONADE, 1);
		till.getNewOrder().createNewSide(Size.SMALL, SideType.COLESLAW, 1);
		
		//Add the test Order object to order array list in the test Till object
		till.addOrder(till.getNewOrder());
		
		//Export the Till object to XML
		till.save("C:\\Test.xml");
		
		//Create a new Till object using data loaded from he newly-saved XML file.
		Till testTill = Till.load("C:\\Test.xml");
		
		//Check that the array list of orders in the new Till object is > 0, confirming that data must have been loaded from XML
		assertTrue("OrderArray should have data loaded from XML (Size should be > 0)", testTill.getOrderArray().size() > 0);
	}

	/**
	 * Test case method for <code>getOrderArray()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} class.
	 * Tests that the method can successfully return the array list of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} objects 
	 * stored in the Till object.
	 */
	@Test
	public void testGetOrderArray() {
		
		//Create two test order objects and populate them with test items before adding them to the array list of orders
		till.createNewOrder("Connor Goddard");
		
		till.getNewOrder().createNewPizza(Size.LARGE, PizzaType.HAM_CHEESE, 1);
		till.getNewOrder().createNewDrink(Size.MEDIUM, DrinkType.LEMONADE, 1);
		till.getNewOrder().createNewSide(Size.SMALL, SideType.COLESLAW, 1);
		
		till.addOrder(till.getNewOrder());
		
		till.closeOrder();
		
		till.createNewOrder("Helen Harman");
		
		till.getNewOrder().createNewPizza(Size.LARGE, PizzaType.HAM_CHEESE, 1);
		till.getNewOrder().createNewDrink(Size.MEDIUM, DrinkType.LEMONADE, 1);
		till.getNewOrder().createNewSide(Size.SMALL, SideType.COLESLAW, 1);
		till.getNewOrder().createNewSide(Size.LARGE, SideType.FRIES, 1);
		
		till.addOrder(till.getNewOrder());
		
		//Create new order array list that represents the array list returned from the 'getOrderArray' method
		ArrayList<Order> testOrderArray = till.getOrderArray();
		
		//Check that the test array list object size > 0, confirming that the order array list form the test Till object has
		//been successfully returned
		assertTrue("Test ArrayList should have a size > 0", testOrderArray.size() > 0);
		
	}

	/**
	 * Test case method for <code>printReceipt()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} class.
	 * Tests that the method can successfully generate and export bespoke order receipts and before saving them as individual HTML files.
	 */
	@Test
	public void testPrintReceipt() {
		
		//Create a test order object and populate it with a selection of test items
		till.createNewOrder("Connor Goddard");
		
		till.getNewOrder().createNewPizza(Size.LARGE, PizzaType.HAM_CHEESE, 1);
		till.getNewOrder().createNewDrink(Size.MEDIUM, DrinkType.LEMONADE, 1);
		till.getNewOrder().createNewSide(Size.SMALL, SideType.COLESLAW, 1);
		
		//Add the test order object to the array list of orders stored in the test Till object
		till.addOrder(till.getNewOrder());
		
		//Run the operations to generate and export the HTML receipt for the test order
		till.exportReceipt("C:\\", false);
		
		//Create variable that represents the expected HTML file
		File testReceipt = new File("C:\\AberPizza Receipt - Connor Goddard.html");
		
		//Check that the expected file exists, confirming that the export has been successful
		assertTrue("Receipt file should exist in directory: 'C:\\AberPizza Receipt - <Customer-Name>.html'", testReceipt.exists());
	}
	
	/**
	 * Test case method for <code>generateReport()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} class.
	 * Tests that the method can successfully generate and export a bespoke 'end of day' till report using data obtained from
	 * the Till class.
	 */
	@Test
	public void testGenerateReport() {
		
		//Create a test order object and populate it with a selection of test items
		till.createNewOrder("Connor Goddard");
		
		till.getNewOrder().createNewPizza(Size.MEDIUM, PizzaType.HAM_CHEESE, 5);
		till.getNewOrder().createNewDrink(Size.MEDIUM, DrinkType.LEMONADE, 2);
		till.getNewOrder().createNewSide(Size.SMALL, SideType.COLESLAW, 1);
		
		//Add the test order object to the array list of orders stored in the test Till object
		till.addOrder(till.getNewOrder());
		
		//Run the operations to generate and export the HTML 'end of day' report
		try {
			till.exportReport("C:\\TestReport.html", new URI("file:///C:/TestReport.html"), false);
		} catch (IOException e) {
			fail("IO Exception Triggered");
		} catch (URISyntaxException e) {
			fail ("URI Syntax Exception Triggered");
			e.printStackTrace();
		}
		
		//Create variable that represents the expected HTML report file
		File testReport = new File("C:\\TestReport.html");
		
		//Check that the expected file exists, confirming that the export has been successful
		assertTrue("Receipt file should exist in directory: 'C:\\TestReport.html'", testReport.exists());
	}

}
