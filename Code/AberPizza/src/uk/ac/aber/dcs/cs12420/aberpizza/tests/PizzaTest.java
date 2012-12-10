package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Size;


/**
 * JUnit test case class for methods and operations in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza} class.
 * 
 * @author Connor Goddard (clg11)
 */
public class PizzaTest {

	/** The Drink test object */
	private Pizza testPizza;

	/**
	 * Performs generic operations that are required before every test case method.
	 */
	@Before
	public void setUp() {
		
		//Initialise Drink test object
		testPizza = new Pizza();
		
	}

	/**
	 * Test case method for constructor in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza} class.
	 * Tests that the constructor for Pizza class allows the correct data to be passed as parameters and sorted.
	 */
	@Test
	public void testPizzaConstructor() {
		
		//Create test Pizza object that using test parameters.
		testPizza = new Pizza("Test Pizza", Size.LARGE, PizzaType.HAM_CHEESE, new BigDecimal("3.60"));
		
		//Check that each stored parameter in the Drink class matches the parameters that were given.
		assertEquals("Description should be 'Test Pizza'", "Test Pizza", testPizza.getDescription());
		assertEquals("Size should be 'LARGE'", Size.LARGE, testPizza.getSize());
		assertEquals("Pizza type should be 'LEMONADE'", PizzaType.HAM_CHEESE, testPizza.getType());
		assertEquals("Price should be £3.60", "3.60", testPizza.getPrice().toString());
		
	}

	/**
	 * Test case method for <code>getType()</code> & <code>setType()</code> 
	 * methods in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza} class.
	 * Tests that methods can successfully set a {@link uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType} enum, 
	 * and return the same value.
	 */
	@Test
	public void testSetAndGetType() {
		
		//Set the pizza type with specific PizzaType enum value
		testPizza.setType(PizzaType.PEPPERONI);
		
		//Check that set type matches entered value
		assertEquals("Pizza type should be 'PEPPERONI'", PizzaType.PEPPERONI, testPizza.getType());
	}

}
