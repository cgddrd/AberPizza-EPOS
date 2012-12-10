package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

/**
 * Interface used to represent a particular "item" or "product" able to be added to an {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}. 
 * Implemented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Product}.
 * 
 * @author Connor Goddard (clg11)
 */
public interface Item {
	
	/**
	 * Gets the item price. (No return method in interface)
	 */
	public BigDecimal getPrice();
	
	/**
	 * Sets the item price. (No set method in interface)
	 */
	public void setPrice(BigDecimal price); 
	
	/**
	 * Gets the item description. (No return method in interface)
	 */
	public String getDescription();
	
	/**
	 * Sets the item description. (No set method in interface)
	 */
	public void setDescription(String description);

	/**
	 * Gets the item size. (No return method in interface)
	 */
	public Size getSize();


}
