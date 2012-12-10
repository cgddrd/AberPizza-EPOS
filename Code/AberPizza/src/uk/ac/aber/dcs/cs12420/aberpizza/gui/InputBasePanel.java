package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

/**
 * "Base" panel designed to hold all sub-panels that are used to input data into the system by the user
 * via the GUI.
 * 
 * Also distributes the {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data created in 
 * {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.TillFrame} to the sub-panels.
 *  
 *  @author Connor Goddard (clg11)
 */
public class InputBasePanel extends JPanel {
	
	/** The Pizza Panel for creating and adding new Pizza objects to order. */
	private PizzaPanel pizzaPane;
	
	/** The Drink Panel for creating and adding new Drink objects to order. */
	private DrinkPanel drinkPane;
	
	/** The Side Panel for creating and adding new Side objects to order. */
	private SidePanel sidePane;
	
	/** The Money Input Panel for entering order payment values. */
	private MoneyInputPanel moneyPane;
	
	/** The layout manager used by the panel */
	private SpringLayout layout;
	
	/**
	 * Instantiates a new Input Base Panel. Creates and instantiates the four sub-panels contained within
	 * this panel ({@link uk.ac.aber.dcs.cs12420.aberpizza.gui.PizzaPanel}, {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.DrinkPanel},
	 * {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.SidePanel} & {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.MoneyInputPanel}).

	 *
	 * @param tillObject The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data object passed down from {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.TillFrame}
	 */
	public InputBasePanel(Till tillObject, DisplayBasePanel displayPan) {
		
		//Set the layout manager
		layout = new SpringLayout();
		this.setLayout(layout);
		
		//Set the size of the panel
		this.setPreferredSize(new Dimension(380, 765));
		
		/* Create the four sub-panels 
		(passing the Till object and this DisplayBasePanel as parameters) */
		pizzaPane = new PizzaPanel(tillObject, displayPan);
		drinkPane = new DrinkPanel(tillObject, displayPan);
		sidePane = new SidePanel(tillObject, displayPan);
		moneyPane = new MoneyInputPanel(tillObject, displayPan);
		
		//Add the sub-panels to the panel
		this.add(pizzaPane);
		this.add(drinkPane);
		this.add(sidePane);
		this.add(moneyPane);
		
		//Define panel layout settings for layout manager
		this.setUpLayout();
	}
	
	/**
	 * Sets up the 'SpringLayout' layout manager to organise all components on the panel.
	 */
	public void setUpLayout() {
		
		layout.putConstraint(SpringLayout.NORTH,pizzaPane,5,SpringLayout.NORTH,this);
		layout.putConstraint(SpringLayout.NORTH,drinkPane,10,SpringLayout.SOUTH,pizzaPane);
		layout.putConstraint(SpringLayout.NORTH,sidePane,10,SpringLayout.SOUTH,drinkPane);
		layout.putConstraint(SpringLayout.NORTH,moneyPane,10,SpringLayout.SOUTH,sidePane); 
		
	}
	
	/**
	 * Gets the MoneyInputPanel to allow GUI to be reset.
	 *
	 * @return The current MoneryInputPanel.
	 */
	public MoneyInputPanel getMoneyInputPanel() {
		return moneyPane;
	}

}
