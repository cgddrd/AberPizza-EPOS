package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Drink;
import uk.ac.aber.dcs.cs12420.aberpizza.data.DrinkType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Size;

/**
 * JUnit test case class for methods and operations in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Drink} class.
 * 
 * @author Connor Goddard (clg11)
 */
public class DrinkTest {
	
	/** The Drink test object */
	private Drink testDrink;

	
	/**
	 * Performs generic operations that are required before every test case method.
	 */
	@Before
	public void setUp() {
		
		//Initialise Drink test object
		testDrink = new Drink();
		
	}

	/**
	 * Test case method for constructor in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Drink} class.
	 * Tests that the constructor for Drink class allows the correct data to be passed as parameters and sorted.
	 */
	@Test
	public void testDrinkConstructor() {
		
		//Create test Drink object that using test parameters.
		testDrink = new Drink("Test Drink", Size.LARGE, DrinkType.LEMONADE, new BigDecimal("1.50"));
		
		//Check that each stored parameter in the Drink class matches the parameters that were given.
		assertEquals("Description should be 'Test Drink'", "Test Drink", testDrink.getDescription());
		assertEquals("Size should be 'LARGE'", Size.LARGE, testDrink.getSize());
		assertEquals("Drink type should be 'LEMONADE'", DrinkType.LEMONADE, testDrink.getType());
		assertEquals("Price should be £1.50", "1.50", testDrink.getPrice().toString());
		
	}

	/**
	 * Test case method for <code>getType()</code> & <code>setType()</code> 
	 * methods in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Drink} class.
	 * Tests that methods can successfully set a {@link uk.ac.aber.dcs.cs12420.aberpizza.data.DrinkType} enum, and return the same value.
	 */
	@Test
	public void testSetAndGetType() {
		
		//Set the drink type with specific DrinkType enum value
		testDrink.setType(DrinkType.COLA);
		
		//Check that set type matches entered value
		assertEquals("Drink type should be 'COLA'", DrinkType.COLA, testDrink.getType());
	}

}
