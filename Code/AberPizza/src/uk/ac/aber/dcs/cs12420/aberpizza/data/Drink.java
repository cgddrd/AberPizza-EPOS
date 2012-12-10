package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

/**
 * Represents the 'Drink' product available in the Till system.
 * 
 * @author Connor Goddard (clg11)
 */
public class Drink extends Product {
	
	/** The type of drink - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.DrinkType} */
	private DrinkType type;

	/**
	 * Instantiates a new drink. Used by the 'createNewDrink' method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}
	 *
	 * @param des The description of the drink - Automatically set to "Drink"
	 * @param size The size of the drink - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Size}
	 * @param type The type of drink - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.DrinkType}
	 * @param price The price of the drink - Automatically set depending on size
	 */
	public Drink(String des, Size size, DrinkType type, BigDecimal price) {
		this.type = type;
		this.setDescription(des);
		this.setSize(size);
		this.setPrice(price);
	}
	
	/**
	 * Default constructor for a new drink.
	 */
	public Drink() {
		
	}

	/**
	 * Gets the drink type.
	 *
	 * @return The type of drink - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.DrinkType}
	 */
	public DrinkType getType() {
		return type;
	}

	/**
	 * Sets the drink type.
	 *
	 * @param type The type of drink - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.DrinkType}
	 */
	public void setType(DrinkType type) {
		this.type = type;
	}

	
}
