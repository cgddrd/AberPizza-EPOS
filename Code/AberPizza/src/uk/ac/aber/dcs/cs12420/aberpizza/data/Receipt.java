package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Loops through all orders stored in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} to produce bespoke HTML receipts for each order using
 * data from {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}. 
 * 
 * Outputs this information using a pre-defined HTML template to '.html' files.
 * 
 * @author Connor Goddard (clg11)
 */
public class Receipt {

	/** The array of Orders obtained from Till */
	private ArrayList<Order> receiptArray;

	/** The PrintWriter object to output the receipt content to file */
	private PrintWriter out; 

	/** Formats the date into specific format for output */
	private Format dateFormatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");


	/**
	 * Instantiates a new receipt and retrieves the array list of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}
	 * from {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till}
	 *
	 * @param orderArray The array list of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}
	 */
	public Receipt(ArrayList<Order> orderArray) {
		receiptArray = orderArray;
	}

	/**
	 * Loops through all stored orders and outputs all item and financial
	 * information to a '.html' file using a pre-defined HTML template.
	 * Sends the newly-created file to the printer using a dedicated thread.
	 * 
	 * @see uk.ac.aber.dcs.cs12420.aberpizza.data.Print
	 */
	public void exportReceipt(String dirPath, boolean printReceipt) {

		//Loop through all stored orders.
		for (int i = 0; i < receiptArray.size(); i++) {

			try {

				//Create a new file using individual customer's name that order relates to.
				out = new PrintWriter(new FileWriter(dirPath + "\\AberPizza Receipt - " + receiptArray.get(i).getCustomerName() + ".html"));


				//Output HTML DOCTYPE information
				out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
				out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">");

				//Output <head> tag and bespoke page title
				out.println("<head>");
				out.println("<title>AberPizza | Receipt for " + receiptArray.get(i).getCustomerName() + "</title>");


				//Output CSS style code
				out.println("<style type=\"text/css\">");
				out.println("<!--");

				out.println("#receipt {");
				out.println("height: 100%;");
				out.println("width: 300px;");
				out.println("text-align: center;");
				out.println("padding-bottom: 10px;");
				out.println("margin: 0 auto;");
				out.println("font-family:\"Trebuchet MS\", Helvetica, sans-serif;");
				out.println("border: solid;");
				out.println("border-width: 1px;");
				out.println("}");

				out.println("#receipt h1 {");
				out.println("font-size: 1.5em;");
				out.println("}");

				out.println("#receipt h2 {");
				out.println("font-size: 0.8em;");
				out.println("}");

				out.println("#receipt h3 {");
				out.println("font-size: 0.8em;");
				out.println("text-align: right;");
				out.println("padding-right: 25px;");
				out.println("}");

				out.println("#receipt h4 {");
				out.println("font-size: 1em;");
				out.println("margin-top: -5px;");
				out.println("margin-bottom: -10px;");
				out.println("}");

				out.println("#receipt p {");
				out.println("font-size: 0.8em;");
				out.println("}");

				out.println("#receipt table.producttable {");
				out.println("font-family: verdana,arial,sans-serif;");
				out.println("font-size:11px;");
				out.println("color:#333333;");
				out.println("margin: 0 auto;");
				out.println("}");

				out.println("#receipt table.producttable th {");
				out.println("padding: 8px;");
				out.println("background-color: #dedede;");
				out.println("text-align:left;");
				out.println("}");

				out.println("#receipt table.producttable td {");
				out.println("padding: 8px;");
				out.println("text-align:center;");
				out.println("}");

				out.println("-->");
				out.println("</style>");
				out.println("</head>");

				//Output generic receipt header
				out.println("<body>");

				out.println("<div id=\"receipt\">");
				out.println("<h1>AberPizza Pizza House</h1>");
				out.println("<p>38 Food Street, Aberystwyth, SY23 3GE</p>");
				out.println("<p>Tel: 01970 453645</p>");

				//Output order process date and customer name
				out.println("<h2>" + dateFormatter.format(receiptArray.get(i).getDate()) + "</h2>");
				out.println("<h4>*************************************</h4>");
				out.println("<h2>Customer Name: " + receiptArray.get(i).getCustomerName() + "</h2>");

				//Create HTML table to display order items breakdown
				out.println("<table class=\"producttable\">");
				out.println("<tr><th>Qty</th><th>Item</th><th>Price</th></tr>");

				//Loop through all the items stored in the selected Order
				for (int y = 0; y < receiptArray.get(i).getItemArray().size(); y++) {

					//Create local variables to selected OrderItem and Item
					OrderItem selectedOrderItem = receiptArray.get(i).getItemArray().get(y);
					Item selectedItem = receiptArray.get(i).getItemArray().get(y).getItem();

					//Create temporary local variable to contain item type
					String prodType;

					//Determine item type..

					//If the item is of type 'Pizza'
					if (selectedItem instanceof Pizza) {

						//Create temporary Pizza object to represent item (used to access 'getType()' method)
						Pizza pizzaObject = (Pizza) receiptArray.get(i).getItemArray().get(y).getItem();

						//Set the prodType string to the type of pizza
						prodType = pizzaObject.getType().toString();

					} else if (receiptArray.get(i).getItemArray().get(y).getItem() instanceof Drink) {
						Drink drinkObject = (Drink) receiptArray.get(i).getItemArray().get(y).getItem();
						prodType = drinkObject.getType().toString();
					} else {
						Side sideObject = (Side) receiptArray.get(i).getItemArray().get(y).getItem();
						prodType = sideObject.getType().toString();
					}

					//Output the item quantity, description, size, type and price in new HTML table row
					out.println("<tr><td>" + selectedOrderItem.getQuantity() + "</td><td>" + selectedItem.getDescription() + 
							" (" + selectedItem.getSize() + ", " + prodType + ")</td><td>&pound;" + selectedItem.getPrice().toString()+ "</td></tr>");

				}

				//Output order subtotal, discount applied, amount paid, and change given values using defined layout
				out.println("</table>");
				out.println("<h4>*************************************</h4>");
				out.println("<h3>Subtotal: &pound;" + receiptArray.get(i).getSubTotal() + "</h3>");
				out.println("<h3>Discount: &pound;" + receiptArray.get(i).getDiscount() + "</h3>");
				out.println("<h3>Payment: &pound;" + receiptArray.get(i).getAmountPaid() + "</h3>");
				out.println("<h3>Change: &pound;" + receiptArray.get(i).getChangeGiven() + "</h3>");
				out.println("<h4>*************************************</h4>");

				//Output receipt footer
				out.println("<h2>Thankyou for your Custom</h2>");

				//Close open HTML tags
				out.println("</div>");
				out.println("</body>");
				out.println("</html>");

				//Save file and close PrintWriter
				out.close();

				if (printReceipt == true) {

					//Creates a new instance of the Print class and passes the file path to be printed
					Print printObject = new Print(new File(dirPath + "\\AberPizza Receipt - " + receiptArray.get(i).getCustomerName() + ".html"));

					//Create a new thread of the Print object
					Thread printThread = new Thread(printObject);

					//Set the thread name
					printThread.setName("AberPizza Printing Thread");

					//Run the thread
					printThread.start();

				}


			} catch (FileNotFoundException nF) {
				System.out.println("File not found");
			} catch (NullPointerException nP) {
				System.out.println("ArrayList not found - NP");
			} catch (IndexOutOfBoundsException iA) {
				System.out.println("ArrayList not found - IA");
			} catch (IOException iO) {
				System.out.println("IO error");
			}
		}
	}
}

