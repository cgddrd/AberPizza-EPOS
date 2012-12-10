package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Drink;
import uk.ac.aber.dcs.cs12420.aberpizza.data.DrinkType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Size;

/**
 * JUnit test case class for methods and operations in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem} class.
 * 
 * @author Connor Goddard (clg11)
 */
public class OrderItemTest {

	/** The OrderItem test object. */
	private OrderItem testOrderItem;
	
	/**
	 * Performs generic operations that are required before every test case method.
	 */
	@Before
	public void setUp() {
		
		//Initialise OrderItem test object
		testOrderItem = new OrderItem();
	}

	/**
	 * Test case method for <code>getItem()</code> & <code>setItem()</code> 
	 * methods in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem} class.
	 * Tests that methods can successfully set a specified Item, and return the same Item.
	 */
	@Test
	public void testSetAndGetItem() {
		
		//Create test Item object using test parameters
		Item testItem = new Pizza("Test Pizza", Size.MEDIUM, PizzaType.HAM_CHEESE, new BigDecimal("2.30"));
		
		//Set the Item in OrderItem test object
		testOrderItem.setItem(testItem);
		
		//Check that returned itm information matches the entered values
		assertEquals("Item description should match 'Test Pizza'", "Test Pizza", testOrderItem.getItem().getDescription());
		assertEquals("Item price should match £2.30", "2.30", testOrderItem.getItem().getPrice().toString());

	}

	/**
	 * Test case method for <code>getQuantity()</code> & <code>setQuantity()</code> 
	 * methods in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem} class.
	 * Tests that methods can successfully set a specified quantity value, and return the same value.
	 */
	@Test
	public void testSetAndGetQuantity() {
		
		//Create test quantity value
		int testQuantity = 0;
		
		//Automatically test range of quantity values
		while (testQuantity < 101) {
			
			//Set the quantity in test OrderItem object to test quantity value
			testOrderItem.setQuantity(testQuantity);
			
			//Check that the OrderItem quantity matches the entered value
			assertEquals("Quantity should match " + testQuantity, testQuantity, testOrderItem.getQuantity());
			
			//Increase the quantity range by 25
			testQuantity = testQuantity + 25;
		}
		
	}
	
	/**
	 * Test case method for OrderItem class constructor in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem} class.
	 * Tests that the constructor allows the correct data to be passed as parameters and sorted.
	 */
	@Test
	public void testOrderItemConstructor() {
		
		//Create test Item object using test parameters
		Item testItem = new Drink("Test Drink", Size.SMALL, DrinkType.COLA, new BigDecimal("0.80"));
		
		//Create test quantity value
		int testQuantity = 3;
		
		//Create new test OrderItem object using constructor
		testOrderItem = new OrderItem(testItem, testQuantity);
		
		//Check that each stored parameter in the ObjectItem matches the parameters that were given.
		assertEquals("Item description should match 'Test Drink", "Test Drink", testOrderItem.getItem().getDescription());
		assertEquals("Item quantity should match '3'", 3, testOrderItem.getQuantity());
	}


}
