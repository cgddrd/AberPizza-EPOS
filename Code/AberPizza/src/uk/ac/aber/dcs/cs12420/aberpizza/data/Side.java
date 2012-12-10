package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

/**
 * Represents the 'Side' product available in the Till system.
 * 
 * @author Connor Goddard (clg11)
 */
public class Side extends Product {

	/** The type of drink - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.SideType} */
	private SideType type;
	
	
	/**
	 * Instantiates a new side. Used by the 'createNewDrink' method in {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}
	 *
	 * @param des The description of the side - Automatically set to "Side"
	 * @param size The size of the side - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Size}
	 * @param type The type of side - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.SideType}
	 * @param price The price of the side - Automatically set depending on size
	 */
	public Side(String des, Size size, SideType type, BigDecimal price) {
		this.type = type;
		this.setDescription(des);
		this.setSize(size);
		this.setPrice(price);
	}
	
	/**
	 * Default constructor for a new side.
	 */
	public Side() {
		
	}
	
	/**
	 * Gets the side type.
	 *
	 * @return The type of side - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.SideType}
	 */
	public SideType getType() {
		return type;
	}

	/**
	 * Sets the side type.
	 *
	 * @param type The type of side - Represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.SideType}
	 */
	public void setType(SideType type) {
		this.type = type;
	}
	

}
