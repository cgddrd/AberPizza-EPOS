package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Generates an "end-of-day" report detailing useful till information including total money taken, total number of orders and a detailed breakdown of all orders stored inside
 * {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}.
 * Outputs and prints this report using a pre-defined HTML template to a '.html' file.
 *
 * @author Connor Goddard (clg11)
 */
public class Report {

	/** The array of Orders obtained from Till */
	private ArrayList<Order> reportArray;

	/** The total amount of money taken and total discount applied for day. */
	private BigDecimal totalForDay, totalDiscountForDay;

	/** The total number of orders taken for day */
	private int noOfOrders;

	/** The PrintWriter object to output the report to file */
	private PrintWriter out;

	/** The current date */
	private Date today = new Date();

	/** Formats the date into specific format for generating in report */
	private Format dateFormatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");


	/**
	 * Gets the array of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} that is used by 
	 * this class to output till data.
	 *
	 * @return The array of Orders used by this class
	 */
	public ArrayList<Order> getReportArray() {
		return reportArray;
	}

	/**
	 * Gets the total money taken for the day variable used by this class.
	 *
	 * @return The total taken for the day
	 */
	public BigDecimal getTotalForDay() {
		return totalForDay;
	}

	/**
	 * Gets the total discount applied for the day variable used by this class.
	 *
	 * @return The total discount for the day
	 */
	public BigDecimal getTotalDiscountForDay() {
		return totalDiscountForDay;
	}

	/**
	 * Gets the total number of orders variable used by this class.
	 *
	 * @return The total number of orders
	 */
	public int getNoOfOrders() {
		return noOfOrders;
	}

	/**
	 * Instantiates a new report. Retrieves the array list of Orders from Till as well as the 
	 * the total amount of money taken, total discount applied, and total number of orders calculated by
	 * {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till}
	 *
	 * @param orderArray The array list of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}
	 * @param totalForDay The total amount of money taken (Calculated by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till#getTotalForDay()})
	 * @param totalDiscountForDay The total discount applied (Calculated by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till#getTotalDiscountForDay()})
	 * @param totalOrders The total number of orders taken (Calculated by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till#getTotalNoOfOrders()})
	 */
	public Report(ArrayList<Order> orderArray, BigDecimal totalForDay, BigDecimal totalDiscountForDay, int totalOrders) {

		this.reportArray = orderArray;
		this.totalForDay = totalForDay;
		this.totalDiscountForDay = totalDiscountForDay;
		this.noOfOrders = totalOrders;

	}

	/**
	 * Outputs all till data obtained from {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till}, including financial data and
	 * a breakdown of all orders into a pre-defined HTML report, before loading the newly generated report into the system default web browser.
	 * 
	 * Sends the newly-created file to the printer using a dedicated thread.
	 * 
	 * @see uk.ac.aber.dcs.cs12420.aberpizza.data.Print
	 */
	public void exportReport(String filePath, URI fileURI, boolean printReport) throws IOException { 

		//Check to see if there are any orders in the till..
		if (reportArray.size() == 0) {

			//If there are none, through a new exception to the GUI
			throw new IndexOutOfBoundsException("No orders to export");

		} else {

			//Otherwise if there are orders to export

			//Create a new file using the current date in the file name.
			out = new PrintWriter(new FileWriter(filePath));

			//Output HTML DOCTYPE information
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
			out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">");

			//Output <head> tag and bespoke page title
			out.println("<head>");
			out.println("<title>AberPizza | End of Day Report</title>");

			//Output CSS style code
			out.println("<style type=\"text/css\">");
			out.println("<!--");

			out.println("#report {");
			out.println("height: 100%;");
			out.println("width: 100%;");
			out.println("text-align: left;");
			out.println("padding-bottom: 10px;");
			out.println("font-family:\"Trebuchet MS\", Helvetica, sans-serif;");
			out.println("border: solid;");
			out.println("border-width: 1px;");
			out.println("}");

			out.println("#report h1 {");
			out.println("font-size: 1.5em;");
			out.println("padding-left: 10px;");
			out.println("}");

			out.println("#report h2 {");
			out.println("font-size: 0.8em;");
			out.println("padding-left: 10px;");
			out.println("}");

			out.println("#report h3 {");
			out.println("font-size: 1.2em;");
			out.println("text-align: center;");
			out.println("padding-left: 10px;");
			out.println("}");

			out.println("#report h4 {");
			out.println("font-size: 1.2em;");
			out.println("padding-left: 10px;");
			out.println("}");

			out.println("#report table.producttable {");
			out.println("font-family: verdana,arial,sans-serif;");
			out.println("color:#333333;");
			out.println("margin: 0 auto;");
			out.println("border-width: 1px;");
			out.println("border-color: #666666;");
			out.println("border-collapse: collapse;");
			out.println("}");

			out.println("#report table.producttable th {");
			out.println("font-size:14px;");
			out.println("padding: 8px;");
			out.println("background-color: #dedede;");
			out.println("text-align:left;");
			out.println("border-width: 1px;");
			out.println("border-color: #666666;");
			out.println("border-style: solid;");

			out.println("}");

			out.println("#report table.producttable td {");
			out.println("font-size:12px;");
			out.println("padding: 8px;");
			out.println("text-align:center;");
			out.println("border-width: 1px;");
			out.println("border-color: #666666;");
			out.println("border-style: solid;");
			out.println("}");

			out.println("-->");
			out.println("</style>");
			out.println("</head>");

			//Output report header
			out.println("<body>");

			out.println("<div id=\"report\">");
			out.println("<h1>AberPizza - End of Day Report</h1>");

			//Output the current date (report generation date)
			out.println("<h2>Generated: " + dateFormatter.format(today) + "</h2>");
			out.println("<hr></hr>");

			//Output financial information
			out.println("<h3>Total Taken: &pound;" + totalForDay + " |  Total Orders: " + noOfOrders + " |  Total Discounts : &pound;" + totalDiscountForDay + "</h3>");
			out.println("<hr></hr>");

			//Output order breakdown table headers
			out.println("<h4>Orders Breakdown:</h4>");

			out.println("<table class=\"producttable\">");
			out.println("<tr><th>Order No</th><th>Date</th><th>Customer Name</th><th>Items</th><th>Total Due</th><th>Discount Applied</th><th>Amount Paid</th><th>Change Given</th></tr>");

			//Order number iterator
			int orderNo = 1;

			//Loop through all orders stored
			for (int i = 0; i < reportArray.size(); i++) {

				//Create string to list all items in a particular order
				String itemList = "";

				//Loop through all items in the selected order
				for (int y = 0; y < reportArray.get(i).getItemArray().size(); y++) {

					//Create local variables to selected OrderItem and Item
					Item loopItem =  reportArray.get(i).getItemArray().get(y).getItem();
					OrderItem loopOrderItem = reportArray.get(i).getItemArray().get(y);

					//Create temporary local variable to contain item type
					String prodType;

					//Determine item type..

					//If the item is of type 'Pizza'
					if (loopItem instanceof Pizza) { 

						//Create temporary Pizza object to represent item (used to access 'getType()' method)
						Pizza pizzaObject = (Pizza) reportArray.get(i).getItemArray().get(y).getItem();

						//Set the prodType string to the type of pizza
						prodType = pizzaObject.getType().toString();


					} else if (reportArray.get(i).getItemArray().get(y).getItem() instanceof Drink) {
						Drink drinkObject = (Drink) reportArray.get(i).getItemArray().get(y).getItem();
						prodType = drinkObject.getType().toString();
					} else {
						Side sideObject = (Side) reportArray.get(i).getItemArray().get(y).getItem();
						prodType = sideObject.getType().toString();
					}

					//Add to item list with current item description, size, type, quantity and price
					itemList = itemList + loopItem.getDescription() + " (" + loopItem.getSize() + ", " + prodType + ") x " + loopOrderItem.getQuantity() + " - &pound;" + loopItem.getPrice() + "<br></br>";

				}

				//Output the current order breakdown in new HTML table row
				out.println("<tr><td>" + orderNo + "</td><td>" + dateFormatter.format(reportArray.get(i).getDate()) + "</td><td>" + reportArray.get(i).getCustomerName() + "</td><td>" + itemList + "</td><td>&pound;" + reportArray.get(i).getSubTotal() + "</td><td>&pound;" + reportArray.get(i).getDiscount() + 
						"</td><td>&pound;" + reportArray.get(i).getAmountPaid() + "</td><td>&pound;" + reportArray.get(i).getChangeGiven() + "</td></tr>");

				//Increment the order no
				orderNo++;
			}

			//Close open HTML tags
			out.println("</table>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");

			//Save file and close PrintWriter
			out.close();

			if (printReport == true) {

				//Get the host system environment
				Desktop desktop = Desktop.getDesktop();

				//Open the system default web browser
				desktop.browse(fileURI);

				//Creates a new instance of the Print class and passes the file path to be printed
				Print printObject = new Print(new File(filePath));

				//Create a new thread of the Print object
				Thread printThread = new Thread(printObject);

				//Set the thread name
				printThread.setName("AberPizza Printing Thread");

				//Run the thread
				printThread.start();


			}

		}
	}
}





