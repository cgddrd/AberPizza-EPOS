package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

/**
 * "Base" panel designed to hold all sub-panels that are used to display information to the user
 * via the GUI (With the exception of {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.ControlPanel}).
 * 
 * Also distributes the {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data created in 
 * {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.TillFrame} to the sub-panels.
 *  
 * @author Connor Goddard (clg11)
 */
public class DisplayBasePanel extends JPanel {
	
	/** The Order Display Panel for displaying order information */
	private OrderDisplayPanel orderPanel;
	
	/** The Till Display Panel for displaying transaction information */
	private TillDisplayPanel tillPanel;
	
	/** The Control Panel for system-wide controls */
	private ControlPanel contPanel;
	
	/** The Cost Display Panel for displaying order finance information */
	private CostDisplayPanel costPanel;

	
	/**
	 * Instantiates a new Display Base Panel. Creates and instantiates the four sub-panels contained within
	 * this panel ({@link uk.ac.aber.dcs.cs12420.aberpizza.gui.OrderDisplayPanel}, {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.TillDisplayPanel},
	 * {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.CostDisplayPanel} & {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.ControlPanel}).

	 *
	 * @param tillObject The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data object passed down from {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.TillFrame}
	 */
	public DisplayBasePanel(Till tillObject) {
		
		
		/* Create the four sub-panels 
		(passing the Till object and this panel as parameters as required) */
		costPanel = new CostDisplayPanel();
		tillPanel = new TillDisplayPanel(tillObject);
		orderPanel = new OrderDisplayPanel(tillObject, this);
		contPanel = new ControlPanel(tillObject, this);
		
		//Position and add the sub-panels to the panel 
		this.add(contPanel, BorderLayout.NORTH);
		this.add(orderPanel, BorderLayout.CENTER);
		this.add(tillPanel, BorderLayout.CENTER);
		this.add(costPanel, BorderLayout.SOUTH);
		
		//Set the size of the panel
		this.setPreferredSize(new Dimension(400, 765));

	}

	/**
	 * Gets the current 'OrderDisplay' sub-panel
	 *
	 * @return The 'OrderDisplay' sub-panel
	 */
	public OrderDisplayPanel getOrderPanel() {
		return orderPanel;
	}

	/**
	 * Gets the current 'TillDisplay' sub-panel
	 *
	 * @return The 'TillDisplay' sub-panel
	 */
	public TillDisplayPanel getTillPanel() {
		return tillPanel;
	}

	/**
	 * Gets the current 'ControlPanel' sub-panel
	 *
	 * @return The 'ControlPanel' sub-panel
	 */
	public ControlPanel getContPanel() {
		return contPanel;
	}

	/**
	 * Gets the current 'CostDisplay' sub-panel
	 *
	 * @return The 'CostDisplay' sub-panel
	 */
	public CostDisplayPanel getCostPanel() {
		return costPanel;
	}
	
	

}
