package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

/**
 * Called by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} every time an Item is added to
 * determine if any discounts should be applied to an order and calculates the prices accordingly.
 * 
 * @author Connor Goddard (clg11)
 */
public class Discount {

	/** The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order that is currently open */
	private Order thisOrder;

	/** The no of items in an order & limit of pizzas to set a discount */
	private int pizzaNo, drinkNo, sideNo, pizzaLimit;

	/** Prevents a discount being applied more than once per order. */
	private boolean applyOnce;

	/**
	 * Instantiates a new discount and sets pizzaLimit value to 2.
	 *
	 * @param order The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order} that is currently open
	 */
	public Discount (Order order) {
		thisOrder = order;
		pizzaLimit = 2;
		applyOnce = false;
	}

	/**
	 * Gets the number of large currently pizzas in the order
	 *
	 * @return Number of large pizzas in the order
	 */
	public int getPizzaNo() {
		return pizzaNo;
	}

	/**
	 * Gets the number of drinks currently in the order
	 *
	 * @return Number of drinks in the order
	 */
	public int getDrinkNo() {
		return drinkNo;
	}

	/**
	 * Gets the number of sides currently in the order
	 *
	 * @return Number of sides in the order
	 */
	public int getSideNo() {
		return sideNo;
	}

	/**
	 * Gets the current limit set for the number of large pizzas that are required for a discount to be applied
	 *
	 * @return the limit of large pizzas before a discount is applied
	 */
	public int getPizzaLimit() {
		return pizzaLimit;
	}

	/**
	 * Checks if a discount has already been applied to the order
	 *
	 * @return True, if a discount has already been applied to the order
	 */
	public boolean isApplyOnce() {
		return applyOnce;
	}

	/**
	 * Determines how many of each product (Pizza/Drink/Side) are currently contained within the 'itemArray' arraylist
	 * inside the {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}.
	 */
	public void checkQuantities() {

		Item currentItem = thisOrder.getNewItem().getItem();
		int currentItemQuantity = thisOrder.getNewItem().getQuantity();

		//Checks if the current item is a LARGE PIZZA
		if (currentItem.getDescription().equals("Pizza") && currentItem.getSize().equals(Size.LARGE)) {

			//If it is, add the quantity of the current item to pizzaNo
			for (int i = 0; i < currentItemQuantity; i++) {
				pizzaNo++;
			}

			//Otherwise, if the current item is ANY DRINK
		} else if (currentItem.getDescription().equals("Drink")) {
			for (int i = 0; i < currentItemQuantity; i++) {
				drinkNo++;
			}
		} else if (currentItem.getDescription().equals("Side")) {
			for (int i = 0; i < currentItemQuantity; i++) {
				sideNo++;
			}
		}

	}

	/**
	 * Uses the number of items gathered from checkDiscount() to determine if a discount should be applied,
	 * and applies the discounts accordingly.
	 *
	 * @param orderItem The current {@link uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem} being accessed inside the 'itemArray' array list
	 */
	public void checkDiscount(OrderItem orderItem) {

		//Check to see if the pizza number threshold has been reached and that the new item added is of type 'LARGE'
		if (pizzaNo > pizzaLimit && orderItem.getItem().getSize().equals(Size.LARGE)) {

			//If it is apply the discount..
			applyDiscount1(orderItem);

			//...and increase the threshold by three so it can be used again
			pizzaLimit = pizzaLimit + 3;

			//Otherwise if there is at least one LARGE pizza, one drink and one side, and the discount has not already been applied..
		} else if (pizzaNo >= 1 && drinkNo >= 1 && sideNo >= 1 && isApplyOnce() != true) {

			//Apply the other discount
			applyDiscount2();

			//Do not allow the discount to be applied again for this order
			applyOnce = true;
		} 

	}

	/**
	 * Applies the "3 x Large Pizza" discount.
	 * Takes the price of the latest large pizza to be added and sets its price to '£0.00'
	 *
	 * @param orderItem The current {@link uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem} being accessed inside the 'itemArray' array list
	 */
	public void applyDiscount1(OrderItem orderItem) {

		//Get the price of the individual item (if the quantity of item > 1, divide by the quantity to get the individual price of one item)		
		BigDecimal itemPrice = orderItem.getItem().getPrice().divide(new BigDecimal(orderItem.getQuantity()));

		//Set the amount of money that is going to be discounted
		BigDecimal discountPrice = orderItem.getItem().getPrice().subtract(itemPrice);

		//Set the item price to the newly calculated discounted price
		orderItem.getItem().setPrice(discountPrice);

		//Set the discount amount to be applied to GUI and receipts/report
		thisOrder.setDiscount(itemPrice);

	}

	/**
	 * Applies the "Half price" discount.
	 * Halves the subtotal of the order.
	 */
	public void applyDiscount2() {

		//For every item currently in the order
		for (int i = 0; i < thisOrder.getItemArray().size(); i++) {

			Item loopItem = thisOrder.getItemArray().get(i).getItem();

			//Divide the item price by two
			BigDecimal discountPrice = loopItem.getPrice().divide(new BigDecimal("2"));

			//Set the item at the newly discounted price
			loopItem.setPrice(discountPrice);

			//Set the discount amount to be applied to GUI and receipts/report
			thisOrder.setDiscount(discountPrice);

		}
	}

}
