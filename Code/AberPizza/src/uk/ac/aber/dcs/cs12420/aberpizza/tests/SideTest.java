package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Side;
import uk.ac.aber.dcs.cs12420.aberpizza.data.SideType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Size;


/**
 * JUnit test case class for methods and operations in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Side} class.
 * 
 * @author Connor Goddard (clg11)
 */
public class SideTest {
	
	/** The Side test object */
	private Side testSide;

	/**
	 * Performs generic operations that are required before every test case method.
	 */
	@Before
	public void setUp() {
		
		//Initialise Drink test object
		testSide = new Side();
	}

	/**
	 * Test case method for constructor in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Side} class.
	 * Tests that the constructor for Side class allows the correct data to be passed as parameters and sorted.
	 */
	@Test
	public void testSideConstructor() {
		
		//Create test Side object that using test parameters.
		testSide = new Side("Test Side", Size.LARGE, SideType.FRIES, new BigDecimal("0.60"));
		
		//Check that each stored parameter in the Side class matches the parameters that were given.
		assertEquals("Description should be 'Test Side'", "Test Side", testSide.getDescription());
		assertEquals("Size should be 'LARGE'", Size.LARGE, testSide.getSize());
		assertEquals("Side type should be 'LEMONADE'", SideType.FRIES, testSide.getType());
		assertEquals("Price should be £0.60", "0.60", testSide.getPrice().toString());
		
	}

	/**
	 * Test case method for <code>getType()</code> & <code>setType()</code> 
	 * methods in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Side} class.
	 * Tests that methods can successfully set a {@link uk.ac.aber.dcs.cs12420.aberpizza.data.SideType} enum, 
	 * and return the same value.
	 */
	@Test
	public void testSetAndGetType() {
		
		//Set the side type with specific SideType enum value
		testSide.setType(SideType.GARLIC_BREAD);
		
		//Check that set type matches entered value
		assertEquals("Side type should be 'GARLIC_BREAD'", SideType.GARLIC_BREAD, testSide.getType());
	}


}
