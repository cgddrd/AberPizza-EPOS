package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Drink;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Side;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

/**
 * Displays a list of all items currently in an order in a list box. Also allows 
 * the user to change the quantity of an item added to the order using the list box component.
 * 
 * Contained within {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.DisplayBasePanel}.
 * 
 * @author Connor Goddard (clg11)
 */
public class OrderDisplayPanel extends JPanel implements MouseListener {

	/** The list box used to display items in the order. */
	private JList orderDisplay;

	/** The model required for the JList component. */
	private DefaultListModel listOrder;

	/** The scrollpane window to 'wrap' the list box. */
	private JScrollPane scrollOrder;

	/** The label to display the customer name. */
	private JLabel custName;

	/** The Till data object used to control and access the application data model */
	private Till tillObject;

	/** Link to the base panel for this class - used to update GUI elements */
	private DisplayBasePanel guiDisplay;

	/**
	 * Instantiates a new Order Display Panel. Creates 'link' objects to {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data object
	 * and {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.DisplayBasePanel}.
	 *
	 * @param tillObject The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data object passed down from {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.TillFrame}
	 * @param displayPan The object representing the 'base' panel for this panel
	 */
	public OrderDisplayPanel(Till tillObject, DisplayBasePanel displayPan) {

		//Set the size of the panel
		this.setPreferredSize(new Dimension(385, 270));

		//Create a titled border around the panel
		this.setBorder(BorderFactory.createTitledBorder("Current Order"));

		//Link parameter objects to local variables
		this.tillObject = tillObject;
		this.guiDisplay = displayPan;

		//Initialise all panel components
		this.initComponents();
	}

	/**
	 * Initialises the panel components (including linking components to listeners where required) before
	 * adding the components to the panel.
	 */
	public void initComponents() {

		//Initialise the list model for the list box
		listOrder = new DefaultListModel();

		//Create a new list box component that utilises the new list model
		orderDisplay = new JList(listOrder);

		//Add a mouse interaction listener to the list box 
		orderDisplay.addMouseListener(this);

		//Wrap the list box with a new scroll pane object
		scrollOrder = new JScrollPane(orderDisplay);

		//Set the size of the scroll pane (list box)
		scrollOrder.setPreferredSize(new Dimension(350, 200));

		//Create bevelled border around scroll pane
		scrollOrder.setBorder(BorderFactory.createLoweredBevelBorder());

		//Initialise 'customer name' display label with specified text
		custName = new JLabel("Customer Name: N/A");

		//Set the size and type of font for label
		custName.setFont(new Font("Arial", Font.BOLD, 15));

		//Add components to panel
		this.add(custName, BorderLayout.NORTH);
		this.add(scrollOrder, BorderLayout.SOUTH);
	}

	/**
	 * Updates the list box display to reflect any changes to 
	 * the items in the order (e.g. new item added/removed) by re-looping 
	 * through updated item array list and populating 
	 * list box with updated information.
	 */
	public void updateList() {
		
		if (listOrder.size() > 3) {
			
			//Remove all current items from list box (keeping the header information)
			listOrder.removeRange(2, listOrder.size() - 1);	
			
		} else if (listOrder.size() == 3){
			//Remove all current items from list box (keeping the header information)
			listOrder.remove(2);
		
		} 
		
		//Loop through all items in 'itemArray' contain in new order
		for (int i = 0; i < tillObject.getNewOrder().getItemArray().size(); i++) {

			//Create local variable of the current item in loop
			Item selectedItem = tillObject.getNewOrder().getItemArray().get(i).getItem();

			//Create string to represent the product type
			String prodType;

			//If the item is of type Pizza
			if (selectedItem instanceof Pizza) { 

				//Create local Pizza object
				Pizza pizzaObject = (Pizza) selectedItem;

				//Set the prodType string to the pizza type
				prodType = pizzaObject.getType().toString();

			} else if (selectedItem instanceof Drink) {
				Drink drinkObject = (Drink) selectedItem;
				prodType = drinkObject.getType().toString();
			} else {
				Side sideObject = (Side) selectedItem;
				prodType = sideObject.getType().toString();
			}

			//Add the item to the list box using in set format
			listOrder.add(listOrder.size(), "Item: " + selectedItem.getDescription() + " (" + selectedItem.getSize() + ", " + prodType + ") x " + tillObject.getNewOrder().getItemArray().get(i).getQuantity());	
		}
	}


	/**
	 * Sets the customer name display to the name of the customer that holds the order.
	 * Creates and adds a pre-defined header to the order list box display.
	 *
	 * @param customerName The name of the customer that the order is for/
	 */
	public void setListTitle(String customerName) {

		//Set the customer name display to contain new customer name
		this.custName.setText("Customer Name: " + customerName);

		//Add bespoke header to the list box display
		listOrder.add(0, "New Order for: " + customerName);
		listOrder.add(1, "****************");
		listOrder.add(2, "");

	}

	/**
	 * Resets the components in the panel ready for a new order.
	 */
	public void reset() {

		//Reset the customer name display
		custName.setText("Customer Name: N/A");

		//Clear the entire list box
		listOrder.clear();
	}


	/** Listener for mouse actions that interact with the list box display. Used to change the quantity of an item.
	 *  @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * @param evt - ActionEvent called from the list box that require an action to be performed.
	 */
	@Override
	public void mouseClicked(MouseEvent evt) {

		//Used to store the inputed quantity value
		int quantity = 0;

		//Create new JList object that links to action event source (order list box display)
		JList list = (JList)evt.getSource();

		//If the user double clicks on an item in the list box
		if (evt.getClickCount() == 2) {


			Item selectItem = null;
			
			try {

				//Create local variable that uses the selected list box item in the 'itemArray' array list
				selectItem = (Item) tillObject.getNewOrder().getItemArray().get(list.locationToIndex(evt.getPoint()) - 2).getItem();

				//Display input dialog to allow user to enter new quantity
				quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter new Quantity: ", "AberPizza | Item Quantity", 1));

			} catch (NumberFormatException nF) {
				JOptionPane.showMessageDialog(new JFrame(), "Please enter a number value", "AberPizza | Item Quantity", JOptionPane.ERROR_MESSAGE);
				return;
			} catch (ArrayIndexOutOfBoundsException aI) {
				JOptionPane.showMessageDialog(new JFrame(), "Selection is not an item stored in Order", "AberPizza | Item Quantity", JOptionPane.ERROR_MESSAGE);
				return;
			}

			//If the quantity has been set to '0'
			if (quantity == 0) {

				//Display question dialog asking user if they want to remove the item form the order
				int exit = JOptionPane.YES_OPTION;
				exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this item?", "AberPizza | Remove Item", JOptionPane.YES_NO_OPTION);

				//If user selects "yes"
				if (exit == JOptionPane.YES_OPTION) {

					//Remove the selcted item from the 'itemArray' array list in the order
					OrderItem removeItem = tillObject.getNewOrder().getItemArray().get(list.locationToIndex(evt.getPoint()) - 2);
					tillObject.getNewOrder().getItemArray().remove(removeItem);

				}

				//Otherwise, if the user enters a quantity value > 100	
			} else if (quantity > 100) {

				//Display an error message box
				JOptionPane.showMessageDialog(new JFrame(), "Quantity cannot exceed 100", "AberPizza | Item Quantity", JOptionPane.ERROR_MESSAGE);

				//Otherwise..
			} else {

				//Update the selected item's quantity value with the entered value
				tillObject.getNewOrder().updateItemQuantity(selectItem, quantity);	

			}

			//Update the order list box display to reflect changes
			updateList();

			//Update the other GUI components to reflect changes
			guiDisplay.getCostPanel().setAmountDue(tillObject.getNewOrder().getSubTotal());
			guiDisplay.getTillPanel().updateList();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
