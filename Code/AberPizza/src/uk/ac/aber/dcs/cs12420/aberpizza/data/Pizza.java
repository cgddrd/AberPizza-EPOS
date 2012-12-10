package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

/**
 * Represents the 'Pizza' product available in the Till system.
 * 
 * @author Connor Goddard (clg11)
 */
public class Pizza extends Product {

	/** The type of pizza - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType} */
	private PizzaType type;

	/**
	 * Instantiates a new pizza. Used by the 'createNewPizza' method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}
	 *
	 * @param des The description of the pizza - Automatically set to "Pizza"
	 * @param size The size of the pizza - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Size}
	 * @param type The type of pizza - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType}
	 * @param price The price of the pizza - Automatically set depending on size
	 */
	public Pizza(String des, Size size, PizzaType type, BigDecimal price) {
		this.type = type;
		this.setDescription(des);
		this.setSize(size);
		this.setPrice(price);
	}

	/**
	 * Default constructor for a new pizza
	 */
	public Pizza() {

	}

	/**
	 * Gets the pizza type.
	 *
	 * @return The type of pizza - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType}
	 */
	public PizzaType getType() {
		return type;
	}

	/**
	 * Sets the pizza type.
	 *
	 * @param type The type of pizza - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType}
	 */
	public void setType(PizzaType type) {
		this.type = type;
	}


}


