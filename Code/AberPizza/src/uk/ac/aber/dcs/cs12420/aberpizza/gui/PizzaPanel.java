package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaType;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Size;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

/**
 * Panel that contains components that allow user to create and add a new 
 * {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza} item to the current order.
 * 
 * Contained within {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.InputBasePanel}.
 * 
 * @author Connor Goddard (clg11)
 */
public class PizzaPanel extends JPanel implements ActionListener {
	
	/** The component title labels. */
	private JLabel sizeLabel, typeLabel, quantLabel;
	
	/** The pizza size and type combo boxes. */
	private JComboBox sizeCombo, typeCombo;
	
	/** The button to add the selected pizza to the order */
	private JButton addPizza;
	
	/** The number model for the quantity spinner component. */
	private SpinnerNumberModel quantModel;
	
	/** The spinner to select the quantity of the drink. */
	private JSpinner quantSpinner;
	
	/** The layout manager used by the panel. */
	private SpringLayout layout = new SpringLayout();
	
	/** The Till data object used to control and access the application data model. */
	private Till tillObject;
	
	/** Link to the display base panel - used to update GUI display. */
	private DisplayBasePanel guiDisplay;

	/**
	 * Instantiates a new Pizza panel. Creates 'link' objects to {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data object
	 * and {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.DisplayBasePanel}.
	 *
	 * @param tillObject The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data object passed down from {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.TillFrame}
	 * @param displayPan The object representing the base panel for the 'display' sub-panels
	 */
	public PizzaPanel(Till tillObject, DisplayBasePanel displayPan) {
		
		//Set the size of the panel
		this.setPreferredSize(new Dimension(360, 130));
		
		//Create a titled border around the panel
		this.setBorder(BorderFactory.createTitledBorder("Pizzas"));
		
		//Set the layout manager
		this.setLayout(layout);
		
		//Link parameter objects to local variables
		this.tillObject = tillObject;
		this.guiDisplay = displayPan;
		
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
		
		//Create new instances of JLabel with specified display text
		sizeLabel = new JLabel("Size:");
		typeLabel = new JLabel("Topping:");
		quantLabel = new JLabel("Quantity:");
		
		//Create new instances of JComboBox and populate with Size and PizzaType enum values
		sizeCombo = new JComboBox(Size.values());
		typeCombo = new JComboBox(PizzaType.values());
		
		//Set the quantity spinner model (Min Value: 1, Max Value: 100)
		quantModel = new SpinnerNumberModel(1, 1, 100, 1);
		
		//Create new instance of JSpinner that uses newly created spinner model
		quantSpinner = new JSpinner(quantModel);
		
		//Create new instance of JButton to with specified button text
		addPizza = new JButton("Add Pizza to Order");
		
		//Add link to local action listener
		addPizza.addActionListener(this);
		
		//Add components to panel
		this.add(sizeLabel);
		this.add(typeLabel);
		this.add(quantLabel);
		this.add(sizeCombo);
		this.add(typeCombo);
		this.add(quantSpinner);
		this.add(addPizza);	
	}
	
	/**
	 * Sets up the 'SpringLayout' layout manager to organise all components on the panel.
	 */
	public void setUpLayout() {
		
		layout.putConstraint(SpringLayout.NORTH,sizeLabel,10,SpringLayout.NORTH,this);
		layout.putConstraint(SpringLayout.WEST,sizeLabel,40,SpringLayout.WEST,this);
		
		layout.putConstraint(SpringLayout.NORTH,sizeCombo,10,SpringLayout.SOUTH,sizeLabel);
		layout.putConstraint(SpringLayout.WEST,sizeCombo,40,SpringLayout.WEST,this);
		
		layout.putConstraint(SpringLayout.NORTH,typeLabel,10,SpringLayout.NORTH,this);
		layout.putConstraint(SpringLayout.WEST,typeLabel,20,SpringLayout.EAST, sizeCombo);
		
		layout.putConstraint(SpringLayout.NORTH,typeCombo,10,SpringLayout.SOUTH,typeLabel);
		layout.putConstraint(SpringLayout.WEST,typeCombo,20,SpringLayout.EAST, sizeCombo);
		
		layout.putConstraint(SpringLayout.NORTH,quantLabel,10,SpringLayout.NORTH,this);
		layout.putConstraint(SpringLayout.WEST,quantLabel,20,SpringLayout.EAST, typeCombo);
		
		layout.putConstraint(SpringLayout.NORTH,quantSpinner,10,SpringLayout.SOUTH,typeLabel);
		layout.putConstraint(SpringLayout.WEST,quantSpinner,20,SpringLayout.EAST, typeCombo);
		
		layout.putConstraint(SpringLayout.NORTH,addPizza,10,SpringLayout.SOUTH,sizeCombo);
		layout.putConstraint(SpringLayout.WEST,addPizza,-20,SpringLayout.EAST,sizeCombo);
	}

	/** Listener for actions on panel to allow interactive components (such as Buttons) to be used.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * @param evt - ActionEvent called from components on the panel that require an action to be performed
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		String actionCommand = evt.getActionCommand();
		
		//If the "Add Pizza to Order" button is clicked
		if (actionCommand.equals("Add Pizza to Order")) {
			
			try {
				
				/* Create a new Pizza object inside new order using selected options from combo boxes and spinner,
				 * before adding to the array list of items inside order.
				 */
				tillObject.getNewOrder().createNewPizza((Size)sizeCombo.getSelectedItem(), (PizzaType)typeCombo.getSelectedItem(), Integer.parseInt((String) quantSpinner.getValue().toString()));
				
				//Update all GUI display components to reflect new item added.
				guiDisplay.getCostPanel().setAmountDue(tillObject.getNewOrder().getSubTotal());
				guiDisplay.getCostPanel().setDiscount(tillObject.getNewOrder().getDiscount());
				guiDisplay.getOrderPanel().updateList();
				guiDisplay.getTillPanel().updateList();
;
				
			} catch (NullPointerException nP) {
				//If no order is in progress, display error message box
				JOptionPane.showMessageDialog(new JFrame(), "No order created - Create a new order to continue.", "AberPizza | Add to Order", JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException nFP) {
				//If incorrect number format is used, display error message box
				JOptionPane.showMessageDialog(new JFrame(), "Please enter a quantity value", "AberPizza | Add to Order", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
