package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;

/**
 * Displays the financial information related to a particular {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Order}.
 * 
 * Contained within {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.DisplayBasePanel}.
 * 
 * @author Connor Goddard (clg11)
 */
public class CostDisplayPanel extends JPanel {
	
	/** The labels for displaying financial information */
	private JLabel discount, amountDue, amountPaid;
	
	/** The layout manager used by the panel */
	private SpringLayout layout;
	 
	/**
	 * Instantiates a new Cost Display Panel.
	 */
	public CostDisplayPanel() {
		
		//Set the layout manager
		layout = new SpringLayout();
		this.setLayout(layout);
		
		//Set the size of the panel
		this.setPreferredSize(new Dimension(385, 85));
		
		//Create an etched border around the panel
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		//Initialise all panel components
		this.initComponents();
		
		//Define panel layout settings for layout manager
		this.setUpLayout();
		
	}
	
	/**
	 * Initialises the panel components (including linking components to listeners where required) before
	 * adding the components to the panel.
	 */
	public void initComponents() {
		
		//Create new instance of JLabel with specified display text
		discount = new JLabel("Discount Applied: N/A");
		
		//Set the font type and size of the label
		discount.setFont(new Font("Arial", Font.BOLD, 14));
		
		amountDue = new JLabel("Amount Due: N/A");
		amountDue.setFont(new Font("Arial", Font.BOLD, 14));
		amountPaid = new JLabel("Amount Paid: N/A");
		amountPaid.setFont(new Font("Arial", Font.BOLD,14));
		
		//Add the label to the panel
		this.add(discount);
		
		this.add(amountDue);
		this.add(amountPaid);
		
	}
	
	/**
	 * Sets up the 'SpringLayout' layout manager to organise all components on the panel.
	 */
	public void setUpLayout() {
		
		layout.putConstraint(SpringLayout.NORTH,amountDue,15,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,amountDue,35,SpringLayout.WEST, this); 
		
		layout.putConstraint(SpringLayout.NORTH,amountPaid,15,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,amountPaid,15,SpringLayout.EAST, amountDue);
		layout.putConstraint(SpringLayout.EAST,amountPaid,-15,SpringLayout.EAST, this); 
		
		layout.putConstraint(SpringLayout.NORTH,discount,10,SpringLayout.SOUTH, amountDue);
		layout.putConstraint(SpringLayout.WEST,discount,60,SpringLayout.WEST, amountDue);
		layout.putConstraint(SpringLayout.EAST,discount,-60,SpringLayout.EAST, this); 
	
	}
	
	/**
	 * Sets the Amount Due display label to a specified amount
	 *
	 * @param amountDue The new amount due value
	 */
	public void setAmountDue(BigDecimal amountDue) {
		this.amountDue.setText("Amount Due: £" + amountDue.toString());
	}
	
	/**
	 * Sets the Amount Paid display label to a specified amount
	 *
	 * @param amountPaid The new amount paid value
	 */
	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid.setText("Amount Paid: £" + amountPaid.toString());
	}
	
	/**
	 * Sets the Discount Applied display label to a specified amount
	 *
	 * @param discount The new discount applied value
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount.setText("Discount Applied: £" + discount.toString());
	}
	
	/**
	 * Gets the value of the Amount Due display label
	 *
	 * @return The value of the Amount Due display label
	 */
	public BigDecimal getAmountDue() {
		return new BigDecimal(amountDue.getText().substring(13));
	}
	
	/**
	 * Gets the value of the Amount Paid display label
	 *
	 * @return The value of the Amount Paid display label
	 */
	public BigDecimal getAmountPaid() {
		return new BigDecimal(amountPaid.getText().substring(15));
	}
	
	/**
	 * Gets the value of the Discount Applied display label
	 *
	 * @return The value of the Discount Applied display label
	 */
	public BigDecimal getDiscount() {
		return new BigDecimal(discount.getText().substring(20));
	}
	
	/**
	 * Resets the order finance display labels.
	 */
	public void reset() {
		discount.setText("Discount Applied: N/A");
		amountDue.setText("Amount Due: N/A");
		amountPaid.setText("Amount Paid: N/A");
	}

}
