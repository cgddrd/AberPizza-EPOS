package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents an individual order stored within 'orderArray' inside {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} .
 * Contains an array list of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem} representing all the Pizza/Drink/Sides in an order. 
 * 
 * @author Connor Goddard (clg11)
 */
public class Order {

	/** The current date. */
	private Date today = new Date();

	/** The customer name that the order is for. */
	private String customerName;

	/** The array list of items in the order. */
	private ArrayList<OrderItem> items;

	/** The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Discount} class used to calculate discounts for the order. */
	private Discount discountObject = new Discount(this);

	/** The new item template object used to add an item to an order */
	private OrderItem newItem;

	/** The total discount amount applied to the order. */
	private BigDecimal discount = new BigDecimal("0.00");

	/** The total amount tendered for the order by the customer. */
	private BigDecimal amountPaid = new BigDecimal("0.00");

	/** The change given to the customer (if applicable). */
	private BigDecimal changeGiven = new BigDecimal("0.00");



	/**
	 * Instantiates a new order.
	 * Creates a new array list of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem} used to store all items
	 * added to order.
	 */
	public Order() {
		items = new ArrayList<OrderItem>();
	}


	/**
	 * Gets the {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Discount} class used to calculate discounts for the order.
	 *
	 * @return the {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Discount} class used to calculate discounts for the order.
	 */
	public Discount getDiscountObject() {
		return discountObject;
	}

	/**
	 * Sets the {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Discount} class used to calculate discounts for the order.
	 *
	 * @param discountObject The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Discount} class used to calculate discounts for the order.
	 */
	public void setDiscountObject(Discount discountObject) {
		this.discountObject = discountObject;
	}

	/**
	 * Sets the array list of items in the order (used for loading a Till)
	 *
	 * @param itemArray The new array list of items.
	 */
	public void setItemArray(ArrayList<OrderItem> itemArray) {
		this.items = itemArray;
	}


	/**
	 * Updates the amount paid the customer has paid for the order.
	 *
	 * @param amountPaid The new amount of money paid for the order.
	 */
	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	/**
	 * Gets the amount the customer has paid for the order.
	 *
	 * @return The total amount paid for the order by the customer.
	 */
	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	/**
	 * Gets the change given to the customer (Default = 0.00).
	 *
	 * @return The change given (Default = 0.00)
	 */
	public BigDecimal getChangeGiven() {
		return changeGiven;
	}

	/**
	 * Sets the change given to the customer (Calculated automatically).
	 *
	 * @param changeGiven The amount of change that is required.
	 */
	public void setChangeGiven(BigDecimal changeGiven) {
		this.changeGiven = changeGiven;
	}

	/**
	 * Gets the array list of items stored in the order.
	 *
	 * @return The array list of {@link uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem}
	 */
	public ArrayList<OrderItem> getItemArray() {
		return items;
	}

	/**
	 * Gets the date the order was processed.
	 *
	 * @return Today's date.
	 */
	public Date getDate() {
		return today;
	}

	/**
	 * Sets the date that the order was processed (normally current date).
	 *
	 * @param date The order process date.
	 */
	public void setDate(Date date) {
		this.today = date;
	}

	/**
	 * Gets the total discount applied to the order.
	 *
	 * @return The total discount value.
	 */
	public BigDecimal getDiscount() {
		return discount;
	}

	/**
	 * Updates total discount applied to the order (Calculated automatically)
	 *
	 * @param discount The total discount applied to the order.
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = this.discount.add(discount);
	}

	/**
	 * Gets the new item 'template' object.
	 *
	 * @return The new item 'template' object.
	 */
	public OrderItem getNewItem() {
		return newItem;
	}

	/**
	 * Sets the customer name that the order is for.
	 *
	 * @param name The new customer name.
	 */
	public void setCustomerName(String name) {
		this.customerName = name;
	}

	/**
	 * Gets the customer name that the order is for.
	 *
	 * @return The customer name that the order is for.
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Updates the new 'template' item with another 'template' item.
	 *
	 * @param newItem The new item 'template' object.
	 */
	public void setNewItem(OrderItem newItem) {
		this.newItem = newItem;
	}

	/**
	 * Creates a new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza} object using data retrieved from the GUI. 
	 * This new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza} is then added to the 'itemArray' array list.
	 *
	 * @param size The selected size of the new pizza - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Size}
	 * @param type The selected type of the new pizza- Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType}
	 * @param quantity The selected quantity of the new pizza item
	 */
	public void createNewPizza(Size size, PizzaType type, int quantity) {

		//Create a new pizza object
		Pizza newPizza = new Pizza();

		//Used to multiply the item price by the quantity required
		BigDecimal quantityValue = new BigDecimal(quantity);

		//Set the new pizza description (automatically done)
		newPizza.setDescription("Pizza");

		//Set the new pizza size using the selected size from GUI
		newPizza.setSize(size);

		//Check the selected size of the pizza to determine price
		if (size.equals(Size.SMALL)) {

			//If size is 'SMALL', set the price to (£5.00 x the quantity) 
			newPizza.setPrice(new BigDecimal("5.00").multiply(quantityValue));

		} else if (size.equals(Size.MEDIUM)) {

			//If size is 'MEDIUM', set the price to (£10.00 x the quantity)
			newPizza.setPrice(new BigDecimal("10.00").multiply(quantityValue));

		} else {

			//If size is 'LARGE', set the price to (£15.00 x the quantity)
			newPizza.setPrice(new BigDecimal("15.00").multiply(quantityValue));

		}

		//Set the new pizza type using the selected type from GUI
		newPizza.setType(type);

		//Create a new OrderItem object using pizza object and quantity and add to 'itemArray'
		addItem(newPizza, quantity);
	}

	/**
	 * Creates a new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Drink} object using data retrieved from the GUI. 
	 * This new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Drink} is then added to the 'itemArray' array list.
	 *
	 * @param size The selected size of the new drink - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Size}
	 * @param type The selected type of the new drink- Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.DrinkType}
	 * @param quantity The selected quantity of the new drink item
	 */
	public void createNewDrink(Size size, DrinkType type, int quantity) {

		Drink newDrink = new Drink();

		BigDecimal quantityValue = new BigDecimal(quantity);

		newDrink.setDescription("Drink");
		newDrink.setSize(size);

		if (size.equals(Size.SMALL)) {
			newDrink.setPrice(new BigDecimal("2.00").multiply(quantityValue));
		} else if (size.equals(Size.MEDIUM)) {
			newDrink.setPrice(new BigDecimal("4.00").multiply(quantityValue));
		} else {
			newDrink.setPrice(new BigDecimal("6.00").multiply(quantityValue));
		}

		newDrink.setType(type);

		addItem(newDrink, quantity);
	}

	/**
	 * Creates a new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Side} object using data retrieved from the GUI. 
	 * This new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Side} is then added to the 'itemArray' array list.
	 *
	 * @param size The selected size of the new side - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Size}
	 * @param type The selected type of the new side- Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.SideType}
	 * @param quantity The selected quantity of the new side item
	 */
	public void createNewSide(Size size, SideType type, int quantity) {

		Side newSide = new Side();

		BigDecimal quantityValue = new BigDecimal(quantity);

		newSide.setDescription("Side");
		newSide.setSize(size);

		if (size.equals(Size.SMALL)) {
			newSide.setPrice(new BigDecimal("2.00").multiply(quantityValue));
		} else if (size.equals(Size.MEDIUM)) {
			newSide.setPrice(new BigDecimal("3.00").multiply(quantityValue));
		} else {
			newSide.setPrice(new BigDecimal("5.00").multiply(quantityValue));
		}

		newSide.setType(type);

		addItem(newSide, quantity);
	}

	/**
	 * Creates a new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem} object using the Pizza/Drink/Side object created, before adding it to the 
	 * 'itemArray' array list. Calls the {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Discount} class to check and apply any discounts now valid.
	 *
	 * @param item The item created previously (either Pizza, Drink or Side).
	 * @param quantity The quantity of that new item.
	 */
	public void addItem(Item item, int quantity) {

		//Create a new OrderItem using the parameter data
		setNewItem(new OrderItem(item, quantity));

		//Add this new OrderItem object to 'itemArray'
		items.add(newItem);

		//Call Discount class and check the quantities of items
		discountObject.checkQuantities();

		//Check if any new discounts should be applied using the new item as criteria
		discountObject.checkDiscount(newItem);		
	}

	/**
	 * Gets the quantity of a specific item in 'itemArray'
	 *
	 * @param i The index of 'itemArray' where the specified item is located
	 * @return The quantity of a specified item
	 */
	public int getItemQuantity (int i) {
		return items.get(i).getQuantity();
	}

	/**
	 * Loops through all items in 'itemArray' to calculate and return the sub total of the entire order.
	 *
	 * @return The order sub total
	 */
	public BigDecimal getSubTotal() {

		//Create a new sub total value (Default = 0.00)
		BigDecimal subTotal = new BigDecimal("0.00");

		//Loop through every item in 'itemArray' (every item added to the order)
		for (int i = 0; i < getItemArray().size(); i++) {

			//Add the price of the selected item to the existing order sub total
			subTotal = subTotal.add(getItemArray().get(i).getItem().getPrice());

		}

		return subTotal;

	}

	/**
	 * Locates and accesses a specified item that requires a quantity change in 'itemArray', before changing the quantity of that item,
	 * and re-calculating the price of that item using the new quantity value.
	 *
	 * @param item The selected item that requires a quantity change.
	 * @param quantity The new quantity value that is to be set.
	 */
	public void updateItemQuantity(Item item, int quantity) { 

		//Create new BigDecimal quantity value to be used to re-calculate item price.
		BigDecimal quantityValue = new BigDecimal(quantity);

		//Loop through all items in itemArray
		for (int i = 0; i < items.size(); i++) {

			//Create variable for current item in array
			Item selectedItem = items.get(i).getItem();

			//Compare the current item in array, with the selected item by user
			if (selectedItem.equals(item)) {

				//If they match..

				//Set the quantity of the item to the newly specified quantity
				items.get(i).setQuantity(quantity);

				//Get the size of the item
				Size size = selectedItem.getSize();

				//Re-calculate the price of the item using the item size and new quantity

				//If the item is a Pizza, and is SMALL
				if (selectedItem instanceof Pizza && size.equals(Size.SMALL)) {

					//Set the price of the item to (individual item price x new quantity value)
					items.get(i).getItem().setPrice(new BigDecimal("5.00").multiply(quantityValue));

				} else if (selectedItem instanceof Pizza && size.equals(Size.MEDIUM)) {
					items.get(i).getItem().setPrice(new BigDecimal("10.00").multiply(quantityValue));
				} else if (selectedItem instanceof Pizza && size.equals(Size.LARGE)) {
					items.get(i).getItem().setPrice(new BigDecimal("15.00").multiply(quantityValue));
				} else if (selectedItem instanceof Drink && size.equals(Size.SMALL)) {
					items.get(i).getItem().setPrice(new BigDecimal("2.00").multiply(quantityValue));
				} else if (selectedItem instanceof Drink && size.equals(Size.MEDIUM)) {
					items.get(i).getItem().setPrice(new BigDecimal("4.00").multiply(quantityValue));
				} else if (selectedItem instanceof Drink && size.equals(Size.LARGE)) {
					items.get(i).getItem().setPrice(new BigDecimal("6.00").multiply(quantityValue));
				} else if (selectedItem instanceof Side && size.equals(Size.SMALL)) {
					items.get(i).getItem().setPrice(new BigDecimal("2.00").multiply(quantityValue));
				} else if (selectedItem instanceof Side && size.equals(Size.MEDIUM)) {
					items.get(i).getItem().setPrice(new BigDecimal("3.00").multiply(quantityValue));
				} else if (selectedItem instanceof Side && size.equals(Size.LARGE)) {
					items.get(i).getItem().setPrice(new BigDecimal("5.00").multiply(quantityValue));
				} 
			}
		}
	}
}