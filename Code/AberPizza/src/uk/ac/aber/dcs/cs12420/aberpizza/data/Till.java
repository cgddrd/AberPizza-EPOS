package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;


/**
 * Main data class in the application that is used by GUI elements to control the Till. Used to represent the Till as a whole, and contains the array list of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}s 
 * that represent each customer order. Contains "system-wide" methods including creating a new order, generating reports and printing order receipts.
 * 
 * @author Connor Goddard (clg11)
 */
public class Till {

	/** The array list of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}s. */
	private ArrayList<Order> orders = new ArrayList<Order>();

	/** Template Order object that is populated when a new order is create4d. */
	private Order newOrder = null;


	/**
	 * Instantiates a new till. Default constructor required for XML Serialisation
	 */
	public Till() {

	}

	/**
	 * Gets the array list of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}s stored.
	 *
	 * @return The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} array list.
	 */
	public ArrayList<Order> getOrderArray() {
		return orders;
	}

	/**
	 * Sets the array list of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}s stored.
	 *
	 * @param orderArray the new order array
	 */
	public void setOrderArray(ArrayList<Order> orderArray) {
		this.orders = orderArray;
	}

	/**
	 * Updates the newOrder object with a new order object.
	 *
	 * @param newOrder The updated newOrder object.
	 */
	public void setNewOrder(Order newOrder) {
		this.newOrder = newOrder;
	}

	/**
	 * Creates a new customer order by initialising the 'newOrder' "template" object 
	 * as a new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}. 
	 * Sets the 'newOrder' customer name field to the string entered by the user via GUI.
	 *
	 *
	 * @param custName The customer name that the new order is for.
	 */
	public void createNewOrder(String custName) {

		//Set the 'newOrder' template object to a new Order object.
		newOrder = new Order();

		//Set the 'customer name' field to the string enetered by the user.
		newOrder.setCustomerName(custName);

	}

	/**
	 * Gets the 'newOrder' object.
	 *
	 * @return The 'newOrder' object that represents the newly created order.
	 */
	public Order getNewOrder() {
		return newOrder;
	}

	/**
	 * Typically adds the 'newOrder' object to the 'orderArray' once the order has been finished.
	 * However any {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} object can be added to the 'orderArray' using this method.
	 *
	 * @param order The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} object to be added to 'orderArray'
	 */
	public void addOrder(Order order) {
		orders.add(order);	
	}

	/**
	 * Calculates the total money taken for the day.
	 *
	 * @return The total money taken for the day.
	 */
	public BigDecimal getTotalForDay() {

		//Create new local variable to hold total money taken value
		BigDecimal total = new BigDecimal("0.00");

		//Loop through every order stored in 'orderArray'
		for (Order loopOrder : getOrderArray()) {

			//Add the sub total of the current order in loop to the existing total value
			total = total.add(loopOrder.getSubTotal());
		}

		//Return the total of ALL order subtotals
		return total;
	}

	/**
	 * Calculates the total discount applied to all the orders for the day.
	 *
	 * @return The total discount applied for the day.
	 */
	public BigDecimal getTotalDiscountForDay() {

		//Create new local variable to hold total money taken value
		BigDecimal totalDiscount = new BigDecimal("0.00");

		//Loop through every order stored in 'orderArray'
		for (Order loopOrder : getOrderArray()) {
			
			//Add the discount applied value of the current order in loop to the existing total discount applied value
			totalDiscount = totalDiscount.add(loopOrder.getDiscount());
		}
		
		//Return the total discount applied of ALL orders
		return totalDiscount;
	}

	/**
	 * Calculates the total number of orders taken for the day.
	 *
	 * @return The total number of orders taken for the day.
	 */
	public int getTotalNoOfOrders() {

		//Create new local variable to total oders taken value
		int noOfOrders = 0;

		//Loop through every order stored in 'orderArray'
		for (Order loopOrder : getOrderArray()) {
			
			//For every order that is looped through, increment the total orders value by 1
			noOfOrders++;
		}
		
		//Return the total number of orders value
		return noOfOrders;
	}

	/**
	 * Creates and calls the {@link uk.ac.aber.dcs.cs12420.aberpizza.data.XML} class to export the entire Till (this class) to XML 
	 * before saving it to the file system.
	 */
	public void save(String filePath) {
		
		//Create new instance of XML class
		XML xml = new XML();
		
		//Run the 'saveOrders' method inside XML passing this object, and a pathname as parameters.
		xml.saveTill(this, filePath);
	}


	/**
	 * Static method that allows a new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} object to be created containing data (such as an array of
	 * {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}s) from an XML file.
	 * The method is static so that it can be accessed without a new instance of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} having to 
	 * be created first.
	 *
	 * @param pathName The path name at which an acceptable XML file can be accessed
	 * @return The new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} object that contains data from XML file.
	 * @throws IOException Signals that an I/O exception has occurred - Caught by GUI.
	 */
	public static Till load(String pathName) throws IOException {
		
		//Create new instance of XML class
		XML xml = new XML();
		
		//Return a Till object that is the result of running the 'loadOrders' method in XML class.
		return xml.loadTill(pathName);
	}

	/**
	 * Creates a new instance of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Receipt} class and passes the array list of 
	 * {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} objects to export the bespoke order receipts.
	 * 
	 * @param dirPath The file path of the new file that is to be saved/printed.
	 * @param printReceipt Boolean defining if the new file is to be printed or not.
	 */
	public void exportReceipt(String dirPath, boolean printReceipt) {
		
		//Create a new Receipt object (Pass the array list of Orders as parameter)
		Receipt receipt = new Receipt(getOrderArray());
		
		//Run method to generate and export receipts.
		receipt.exportReceipt(dirPath, printReceipt);
	}

	/**
	 * Creates a new instance of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Report} class and passes the array list of 
	 * {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} objects, and the calculated financial data to generate the 'end of day' till report.
	 * 
	 * @param filePath The file path of the new file that is to be saved/printed.
	 * @param pathURI The URI of the new fine (used to display in web browser)
	 * @param printReport Boolean defining if the new file is to be printed or not.
	 */
	public void exportReport(String filePath, URI pathURI, boolean printReport) throws IOException {
		
		//Create a new Report object (Pass the array list of Orders and till financial data as parameters)
		Report report = new Report(getOrderArray(), getTotalForDay(), getTotalDiscountForDay(), getTotalNoOfOrders());
		
		//Run method to generate and export the report.
		report.exportReport(filePath, pathURI, printReport);
	}

	/**
	 * Closes the current order by setting the 'newOrder' "template" object to null.
	 */
	public void closeOrder() {
		newOrder = null;
	}

	/**
	 * Determines whether a new order has been created, or an order is currently being edited by
	 * checking if the 'newOrder' object is currently null or not.
	 *
	 * @return Whether 'newOrder' is currently active or not.
	 */
	public Boolean isOrderActive() {
		if (newOrder == null) {
			return false;
		} else {
			return true;
		}
	}

}
