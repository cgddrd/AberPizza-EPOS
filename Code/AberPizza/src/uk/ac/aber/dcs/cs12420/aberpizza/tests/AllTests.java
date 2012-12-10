package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * JUnit Test Suite that allows all JUnit tests to be run simultaneously.
 * 
 * @author Connor Goddard (clg11)
 */
@RunWith(Suite.class)
@SuiteClasses({ DiscountTest.class, DrinkTest.class, OrderItemTest.class,
		OrderTest.class, PizzaTest.class, ReceiptTest.class, ReportTest.class,
		SideTest.class, TillTest.class, XMLTest.class })
public class AllTests {

}
