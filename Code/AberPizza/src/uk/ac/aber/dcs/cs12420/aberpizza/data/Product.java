package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;


/**
 * Abstract class used to contain methods for shared information between Pizza, Drink and Side products.
 * Implements {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Item} interface. 
 * Extended by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza}, 
 * {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Drink} & {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Side}
 */
public abstract class Product implements Item {
	
	/** The price of the item. */
	private BigDecimal price;
	
	/** The item description. */
	private String description;
	
	/** The item size. */
	private Size size;

	/** 
	 * Returns the price of the item. 
	 * @see uk.ac.aber.dcs.cs12420.aberpizza.data.Item#getPrice()
	 * 
	 * @return The item price.
	 */
	@Override
	public BigDecimal getPrice() {
		return price;	
	}
	
	/** 
	 * Sets the price of the item. 
	 * @see uk.ac.aber.dcs.cs12420.aberpizza.data.Item#setPrice()
	 * 
	 * @param price The item price to be set.
	 */
	@Override
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/** 
	 * Returns the size of the item, represented by {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Size}
	 * See: @see uk.ac.aber.dcs.cs12420.aberpizza.data.Item#getSize()
	 * 
	 * @return The item size.
	 */
	public Size getSize() {
		return size;
	}

	/** 
	 * Sets the size of the item. 
	 * @see uk.ac.aber.dcs.cs12420.aberpizza.data.Item#setSize()
	 * 
	 * @param size The new size of the item.
	 */
	public void setSize(Size size) {
		this.size = size;
	}

	/** 
	 * Returns the description of the item.
	 * @see uk.ac.aber.dcs.cs12420.aberpizza.data.Item#getDescription()
	 * 
	 * @return The description of the item.
	 */
	@Override
	public String getDescription() {
		return description;	
	}

	/** 
	 * Sets the size of the item. 
	 * @see uk.ac.aber.dcs.cs12420.aberpizza.data.Item#setDescription()
	 * 
	 * @param description The new description of the item.
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
}
