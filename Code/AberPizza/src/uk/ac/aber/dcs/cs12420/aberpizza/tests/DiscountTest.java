package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Discount;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Drink;
import uk.ac.aber.dcs.cs12420.aberpizza.data.DrinkType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Order;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Side;
import uk.ac.aber.dcs.cs12420.aberpizza.data.SideType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Size;

/**
 * JUnit test case class for methods and operations in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Discount} class.
 * 
 * @author Connor Goddard (clg11)
 */
public class DiscountTest {
	
	/** The Order test object */
	private Order testOrder;
	
	/** The Discount test object */
	private Discount testDiscount;
	
	/**
	 * Performs generic operations that are required before every test case method.
	 */
	@Before
	public void setUp() {
		
		//Initialise Order test object
		testOrder = new Order();
		
		//Set customer name for test object
		testOrder.setCustomerName("Connor Goddard");
		
		//Initialise Discount test object that utilises test Order object
		testDiscount = new Discount(testOrder);
		testOrder.setDiscountObject(testDiscount);
		
	}
	
	/**
	 * Test case method for <code>checkQuantities()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Discount} class.
	 * Tests that method can successfully track number of items in an order using set criteria.
	 */
	@Test
	public void testCheckQuantities() {
		
		//Create test Item objects that contain test information.
		Pizza testPizza1 = new Pizza("Pizza", Size.LARGE, PizzaType.HAM_CHEESE, new BigDecimal("10.00"));
		Pizza testPizza2 = new Pizza("Pizza", Size.MEDIUM, PizzaType.HAM_CHEESE, new BigDecimal("5.00"));
		Drink testDrink1 = new Drink("Drink", Size.SMALL, DrinkType.ORANGE_JUICE, new BigDecimal("1.60"));
		Drink testDrink2 = new Drink("Drink", Size.MEDIUM, DrinkType.COLA, new BigDecimal("1.90"));
		Side testSide1 = new Side("Side", Size.MEDIUM, SideType.FRIES, new BigDecimal("1.20"));
		Side testSide2 = new Side("Side", Size.LARGE, SideType.GARLIC_BREAD, new BigDecimal("3.76"));
		
		//Add test items to test Order object
		testOrder.addItem(testPizza1, 1);
		testOrder.addItem(testPizza2, 1);
		testOrder.addItem(testDrink1, 5);
		testOrder.addItem(testDrink2, 2);
		testOrder.addItem(testSide1, 1);
		testOrder.addItem(testSide2, 1);
		
		//Check that there has only been one pizza detected in the order
		assertEquals("Pizza no should be 1 (as only large pizzas are counted)", 1 , testOrder.getDiscountObject().getPizzaNo());
		
		//Check that there have been seven drinks detected in the order
		assertEquals("Drink no should be 7", 7 , testOrder.getDiscountObject().getDrinkNo());
		
		//Check that there have been two sides detected in the order
		assertEquals("Side no should be 2", 2 , testOrder.getDiscountObject().getSideNo());
		
	}

	/**
	 * Test case method for <code>checkDiscount()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Discount} class.
	 * Tests that method can determine whether any discounts are valid and applies them accordingly.
	 */
	@Test
	public void testCheckDiscount() {
		
		//Create test Item objects that contain test information.
		Pizza testPizza1 = new Pizza("Pizza", Size.LARGE, PizzaType.HAM_CHEESE, new BigDecimal("14.00"));
		Pizza testPizza2 = new Pizza("Pizza", Size.LARGE, PizzaType.CHEESE, new BigDecimal("23.00"));
		Pizza testPizza3 = new Pizza("Pizza", Size.LARGE, PizzaType.PEPPERONI, new BigDecimal("10.00"));
		
		//Add test items to test Order object
		testOrder.addItem(testPizza1, 1);
		testOrder.addItem(testPizza2, 1);
		testOrder.addItem(testPizza3, 1);
		
		//Check that the pizza limit value has been increased by three as the discount should have been triggered and applied
		assertEquals("pizzaLimit should have increased by 3, from 2 -> 5", 5 , testOrder.getDiscountObject().getPizzaLimit());
		
		//Create and add some extra test items to the order
		Drink testDrink1 = new Drink("Drink", Size.SMALL, DrinkType.ORANGE_JUICE, new BigDecimal("1.60"));
		Side testSide1 = new Side("Side", Size.MEDIUM, SideType.FRIES, new BigDecimal("1.20"));
		
		testOrder.addItem(testDrink1, 1);
		testOrder.addItem(testSide1, 1);
		
		//Check that the apply once boolean has been changed from FALSE to TRUE as the discount should have been triggered and applied
		assertTrue("applyOnce boolean should be set to TRUE", testOrder.getDiscountObject().isApplyOnce());
	}

	/**
	 * Test case method for <code>applyDiscount1()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Discount} class.
	 * Tests that method can successfully apply re-price the third large pizza added to the order.
	 */
	@Test
	public void testApplyDiscount1() {
		
		//Create test Item objects that contain test information.
		Pizza testPizza1 = new Pizza("Pizza", Size.LARGE, PizzaType.HAM_CHEESE, new BigDecimal("14.00"));
		Pizza testPizza2 = new Pizza("Pizza", Size.LARGE, PizzaType.CHEESE, new BigDecimal("23.00"));
		Pizza testPizza3 = new Pizza("Pizza", Size.LARGE, PizzaType.PEPPERONI, new BigDecimal("10.00"));
		
		//Add test items to test Order object
		testOrder.addItem(testPizza1, 1);
		testOrder.addItem(testPizza2, 1);
		testOrder.addItem(testPizza3, 1);
		
		//Check that the third pizza has been successfully re-priced to '0.00', and so that the discount was applied correctly
		assertEquals("testPizza3 should have been re-priced as free (0.00)", "0.00" , testOrder.getItemArray().get(2).getItem().getPrice().toString());
		
	}
	
	/**
	 * Test case method for <code>applyDiscount2()</code> method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Discount} class.
	 * Tests that method can successfully apply half the price of the order sub total.
	 */
	@Test
	public void testApplyDiscount2() {
		
		//Create test Item objects that contain test information.
		Pizza testPizza1 = new Pizza("Pizza", Size.LARGE, PizzaType.HAM_CHEESE, new BigDecimal("14.00"));
		Drink testDrink1 = new Drink("Drink", Size.SMALL, DrinkType.ORANGE_JUICE, new BigDecimal("1.60"));
		Side testSide1 = new Side("Side", Size.MEDIUM, SideType.FRIES, new BigDecimal("1.20"));
		
		//Add test items to test Order object
		testOrder.addItem(testPizza1, 1);
		testOrder.addItem(testDrink1, 1);
		testOrder.addItem(testSide1, 1);

		//Check that the order sub total has been halved, and so the discount was applied correctly
		assertEquals("Subtotal should have been halved (16.80 / 2 = 8.40)", "8.40" , testOrder.getSubTotal().toString());
	}

}
