package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Drink;
import uk.ac.aber.dcs.cs12420.aberpizza.data.DrinkType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Order;
import uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Side;
import uk.ac.aber.dcs.cs12420.aberpizza.data.SideType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Size;

/**
 * JUnit test case class for methods and operations in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} class.
 * 
 * @author Connor Goddard (clg11)
 */
public class OrderTest {
	
	/** The Order test object. */
	private Order testOrder;
	
	/**
	 * Performs generic operations that are required before every test case method.
	 */
	@Before
	public void setUp() {
		
		//Initialise Order test object
		testOrder = new Order();
		
	}

	/**
	 * Test case method for <code>getItemArray()</code> & <code>setItemArray()</code> 
	 * methods in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} class.
	 * Tests that methods can successfully set an array list of Items, and then return that same array with all its contents.
	 */
	@Test
	public void testSetAndGetItemArray() {
		
		//Create new test array list of OrderItems
		ArrayList<OrderItem> testItemArray = new ArrayList<OrderItem>();
		
		//Add a new test item to the test item array list
		testItemArray.add(new OrderItem((Item) new Pizza("Pizza", Size.LARGE, PizzaType.HAM_CHEESE, new BigDecimal("2.99")), 1));
		
		//Set the item array list in Order as the test item array list just created
		testOrder.setItemArray(testItemArray);
		
		//Check that the first item in the Order item array list matches the first item in the test item array
		assertEquals("Description should match", "Pizza", testOrder.getItemArray().get(0).getItem().getDescription());
	}

	/**
	 * Test case method for <code>getDate()</code> & <code>setDate()</code> 
	 * methods in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} class.
	 * Tests that methods can successfully set an order date, and return the same date.
	 */
	@Test
	public void testSetAndGetDate() {
		
		//Create a new date set to the current date.
		Date testDate = new Date();
		
		//Set the date in the test Order object to the created date.
		testOrder.setDate(testDate);
		
		//Check that the set date in Order matches the created date.
		assertEquals("Dates should match", new Date(), testOrder.getDate());
	}

	/**
	 * Test case method for <code>getDiscount()</code> & <code>setDiscount()</code> 
	 * methods in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} class.
	 * Tests that methods can successfully set an applied discount value in the Order, and return the same value.
	 */
	@Test
	public void testSetAndGetDiscount() {
		
		//Create a test discount applied value
		BigDecimal testDiscount = new BigDecimal("1.20");
		
		//Set the discount applied value in Order to the test value
		testOrder.setDiscount(testDiscount);
		
		//Check that the discount applied value in the test Order object matches the test discount value
		assertEquals("Discount price should be £1.20", "1.20", testOrder.getDiscount().toString());
	}

	/**
	 * Test case method for <code>getNewItem()</code> & <code>setNewItem()</code> 
	 * methods in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} class.
	 * Tests that methods can successfully set the new item object in Order, and return the same item object.
	 */
	@Test
	public void testSetAndGetNewItem() {
	
		//Create test OrderItem object with test parameters
		OrderItem testOrderItem = new OrderItem(new Drink("Test Drink", Size.MEDIUM, DrinkType.LEMONADE, new BigDecimal("2.30")), 1);
		
		//Set the 'newItem' object in Order to the created test OrderItem object.
		testOrder.setNewItem(testOrderItem);
		
		//Check that the 'newItem' object in the test Order object matches the test OrderItem object.
		assertEquals("New item description should match 'TestDrink'", "Test Drink", testOrder.getNewItem().getItem().getDescription());
		
	}

	/**
	 * Test case method for <code>getCustomerName()</code> & <code>setCustomerName()</code> 
	 * methods in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} class.
	 * Tests that methods can successfully set a new customer name, and return the same name.
	 */
	@Test
	public void testSetAndGetCustomerName() {
		
		//Set the customer name in test Order object as random customer name
		testOrder.setCustomerName("Connor Goddard");
		
		//Check that the customer name value in test Order object matches the entered name value
		assertEquals("Customer names should match", "Connor Goddard", testOrder.getCustomerName());
	}


	/**
	 * Test case method for <code>addItem()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} class.
	 * Tests that method can successfully add a specified item to the array list of OrderItems in Order object. 
	 */
	@Test
	public void testAddItem() {
	
		//Run 'addItem' method with test item as a parameter
		testOrder.addItem(new Pizza("Test Pizza", Size.SMALL, PizzaType.PEPPERONI, new BigDecimal("4.30")), 1);
		
		//Check that the first item in the array list of items inside test Order object matches the entered OrderItem
		assertEquals("Description should match 'Test Pizza'", "Test Pizza", testOrder.getItemArray().get(0).getItem().getDescription());
	}

	/**
	 * Test case method for <code>getItemQuantity()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} class.
	 * Tests that method can successfully return the quantity of a specified item store in item array list in Order object.
	 */
	@Test
	public void testGetItemQuantity() {
		
		//Create test items that using test parameters
		Item testItem1 = new Side("Test Side", Size.MEDIUM, SideType.FRIES, new BigDecimal("1.20"));
		Item testItem2 = new Pizza("Test Pizza", Size.MEDIUM, PizzaType.HAM_CHEESE, new BigDecimal("4.40"));
		
		//Add test OrderItems (with test quantity values) to item arrray list inside test Order object
		testOrder.addItem(testItem1, 3);
		testOrder.addItem(testItem2, 5);
		
		//Check that the quantities of both items stored in test Order obejct match the quantities of the test OrderItem objects created
		assertEquals("'0' position in item array should have description 'Test Side'","Test Side", testOrder.getItemArray().get(0).getItem().getDescription());
		assertEquals("'0' position in item array should have quantity of 3",3, testOrder.getItemArray().get(0).getQuantity());
		
		assertEquals("'1' position in item array should have description 'Test Pizza'","Test Pizza", testOrder.getItemArray().get(1).getItem().getDescription());
		assertEquals("'1' position in item array should have quantity of 5",5, testOrder.getItemArray().get(1).getQuantity());
		
		
		
	}

	/**
	 * Test case method for <code>getSubTotal()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} class.
	 * Tests that method can successfully calculate and return the sub total of all items stored in the Order object.
	 */
	@Test
	public void testGetSubTotal() {
		
		//Create test items (with specified prices) 
		Item testItem1 = new Side("Test Side", Size.MEDIUM, SideType.FRIES, new BigDecimal("1.20").multiply(new BigDecimal(2)));
		Item testItem2 = new Pizza("Test Pizza", Size.MEDIUM, PizzaType.HAM_CHEESE, new BigDecimal("4.40").multiply(new BigDecimal(5)));
		Item testItem3 = new Side("Test Drink", Size.MEDIUM, SideType.FRIES, new BigDecimal("2.25").multiply(new BigDecimal(1)));

		//Add test items to item array list in test Order class
		testOrder.addItem(testItem1, 2);
		testOrder.addItem(testItem2, 5);
		testOrder.addItem(testItem3, 1);
		
		//Check that the returned sub total value matches the sum of all the test item prices
		assertEquals("Sub total should match £26.65", "26.65", testOrder.getSubTotal().toString());
	}
	
	/**
	 * Test case method for <code>createNewPizza()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} class.
	 * Tests that method can successfully create and add new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza} item
	 * to the Order using specified parameters.
	 */
	@Test
	public void testCreateNewPizza() {
		
		//Run 'createNewPizza' method using test parameters
		testOrder.createNewPizza(Size.MEDIUM, PizzaType.HAM_CHEESE, 4);
		
		//Create test object that represents the item created by the 'createNewPizza' method 
		Pizza testPizza = (Pizza) testOrder.getItemArray().get(0).getItem();
		
		//Check that the Pizza created/added by the 'createNewPizza' method matches the
		//parameters entered
		assertEquals("Item description should match 'Pizza'", "Pizza", testPizza.getDescription());
		assertEquals("Pizza type should match 'HAM_CHEESE'", PizzaType.HAM_CHEESE, testPizza.getType());
		assertEquals("Pizza type should match 'MEDIUM'", Size.MEDIUM, testPizza.getSize());
		assertEquals("Pizza price should match £40.00", "40.00", testOrder.getItemArray().get(0).getOrderItemTotal().toString());
		assertEquals("Item quantity should match 4", 4, testOrder.getItemArray().get(0).getQuantity());
		
	}
	
	/**
	 * Test case method for <code>createNewDrink()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} class.
	 * Tests that method can successfully create and add new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Drink} item
	 * to the Order using specified parameters.
	 */
	@Test
	public void testCreateNewDrink() {
		
		//Run 'createNewDrink' method using test parameters
		testOrder.createNewDrink(Size.LARGE, DrinkType.COLA, 1);
		
		//Create test object that represents the item created by the 'createNewDrink' method 
		Drink testDrink = (Drink) testOrder.getItemArray().get(0).getItem();
		
		//Check that the Drink created/added by the 'createNewPizza' method matches the
		//parameters entered
		assertEquals("Item description should match 'Drink'", "Drink", testDrink.getDescription());
		assertEquals("Drink type should match 'COLA'", DrinkType.COLA, testDrink.getType());
		assertEquals("Drink type should match 'MEDIUM'", Size.LARGE, testDrink.getSize());
		assertEquals("Drink price should match £6.00", "6.00", testOrder.getItemArray().get(0).getOrderItemTotal().toString());
		assertEquals("Item quantity should match 1", 1, testOrder.getItemArray().get(0).getQuantity());
		
	}
	
	/**
	 * Test case method for </code>createNewSide()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} class.
	 * Tests that method can successfully create and add new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Side} item
	 * to the Order using specified parameters.
	 */
	@Test
	public void testCreateNewSide() {
		
		//Run 'createNewSide' method using test parameters
		testOrder.createNewSide(Size.SMALL, SideType.COLESLAW, 27);
		
		//Create test object that represents the item created by the 'createNewSide' method 
		Side testSide = (Side) testOrder.getItemArray().get(0).getItem();
		
		//Check that the Side created/added by the 'createNewSide' method matches the
		//parameters entered
		assertEquals("Item description should match 'Side'", "Side", testSide.getDescription());
		assertEquals("Side type should match 'COLESLAW'", SideType.COLESLAW, testSide.getType());
		assertEquals("Side type should match 'SMALL'", Size.SMALL, testSide.getSize());
		assertEquals("Side price should match £54.00", "54.00", testOrder.getItemArray().get(0).getOrderItemTotal().toString());
		assertEquals("Item quantity should match 27", 27, testOrder.getItemArray().get(0).getQuantity());
		
	}
	
	/**
	 * Test case method for <code>updateItemQuantity()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} class.
	 * Tests that method can successfully retrieve a stored item in Order object, update the quantity value of that item and 
	 * finally re-calculate the price of that item using the new quantity value.
	 */
	@Test
	public void testUpdateItemQuantity() {
		
		//Run 'createnewPizza' method with test parameters 
		testOrder.createNewPizza(Size.MEDIUM, PizzaType.HAM_CHEESE, 1);
		
		//Check that the first item stored in the test Order object matches the entered values
		assertEquals("Item quantity should be 1", 1, testOrder.getItemArray().get(0).getQuantity());
		assertEquals("Pizza price should be normal price: '10.00", "10.00", testOrder.getItemArray().get(0).getItem().getPrice().toString());
		
		//Apply the 'updateItemQuantity' method to the tets item just added
		testOrder.updateItemQuantity(testOrder.getItemArray().get(0).getItem(), 34);
		
		//Check that the method has succecesfully updated the quantity, and as a result re-calculated and updated the price
		assertEquals("Item quantity should now be 34", 34, testOrder.getItemArray().get(0).getQuantity());
		assertEquals("Pizza price should now be '10.00 x 34 = 340.00'", "340.00", testOrder.getItemArray().get(0).getItem().getPrice().toString());
	}

}
