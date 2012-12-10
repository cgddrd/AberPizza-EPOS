package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

/**
 * "Wrapper" class used to represent an individual item and it's quantity in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}
 * @author Connor Goddard (clg11)
 */
public class OrderItem {

	/** The item quantity */
	private int quantity;

	/** The particular item (Pizza, Drink or Side) */
	private Item item;

	/**
	 * Instantiates a new OrderItem. Default constructor required for XML Serialisation.
	 * @see uk.ac.aber.dcs.cs12420.aberpizza.data.XML
	 */
	public OrderItem() {

	}

	/**
	 * Instantiates a new order item. Used by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} to create new "item entry" in 'itemArray'.
	 *
	 * @param item The new item (Pizza, Drink or Side) to be stored.
	 * @param quantity The quantity value the new item.
	 */
	public OrderItem(Item item, int quantity) {
		this.quantity = quantity;
		this.item = item;
	}

	/**
	 * Updates the item stored in the OrderItem object.
	 *
	 * @param item The replacement item to update existing item.
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * Gets the related quantity for the stored item.
	 *
	 * @return The item quantity value.
	 */
	public int getQuantity() {
		return this.quantity;
	}

	/**
	 * Updates the related quantity for the stored item.
	 *
	 * @param quantity The new quantity value to update the existing quantity.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the stored item (Pizza, Drink or Side).
	 *
	 * @return The stored item.
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * Gets the total price of the item stored in OrderItem object.
	 *
	 * @return The stored item price.
	 */
	public BigDecimal getOrderItemTotal() {
		return item.getPrice();
	}

}
