package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.Dimension;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

/**
 * Displays a list of all items currently in an order in a list box with their 
 * associated price information.
 * 
 * Contained within {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.DisplayBasePanel}.
 * 
 * @author Connor Goddard (clg11)
 */ 
public class TillDisplayPanel extends JPanel {

	/** The list box used to display items in the order. */
	private JList tillDisplay;

	/** The model required for the JList component. */
	private DefaultListModel listTill;

	/** The scrollpane window to 'wrap' the list box. */
	private JScrollPane scrollTill;

	/** The Till data object used to control and access the application data model */
	private Till tillObject;

	/**
	 * Instantiates a new till display panel.Creates 'link' object 
	 * to {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data object.
	 *
	 * @param tillObject The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data object passed down from {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.TillFrame}
	 */
	public TillDisplayPanel(Till tillObject) {

		//Set the size of the panel
		this.setPreferredSize(new Dimension(385, 270));

		//Create a titled border around the panel
		this.setBorder(BorderFactory.createTitledBorder("Current Transaction"));

		//Link parameter object to local variable
		this.tillObject = tillObject;

		//Initialise all panel components
		this.initComponents();

	}


	/**
	 * Initialises the panel components (including linking components to listeners where required) before
	 * adding the components to the panel.
	 */
	public void initComponents() {

		//Initialise the list model for the list box
		listTill = new DefaultListModel();

		//Create a new list box component that utilises the new list model
		tillDisplay = new JList(listTill);

		//Wrap the list box with a new scroll pane object
		scrollTill= new JScrollPane(tillDisplay);

		//Set the size of the scroll pane (list box)
		scrollTill.setPreferredSize(new Dimension(350, 230));

		//Create bevelled border around scroll pane
		scrollTill.setBorder(BorderFactory.createLoweredBevelBorder());

		//Add scroll pane (list box) to panel
		this.add(scrollTill);

	}

	/**
	 * Updates the list box display to reflect any changes to 
	 * the items in the order (e.g. new item added/removed) by re-looping 
	 * through updated item array list and populating 
	 * list box with updated information.
	 */
	public void updateList() {

		
		if (listTill.size() > 3) {

			//Remove all current items from list box (keeping the header information)
			listTill.removeRange(2, listTill.size() - 1);	

		} else if (listTill.size() == 3){
			listTill.remove(2);

		}

		//Loop through all items in 'itemArray' contain in new order
		for (int i = 0; i < tillObject.getNewOrder().getItemArray().size(); i++) {

			//Create local variable of the current item in loop
			Item selectedItem = tillObject.getNewOrder().getItemArray().get(i).getItem();

			//Add the item to the list box using in set format
			listTill.add(listTill.size(), selectedItem.getDescription() + " x " + tillObject.getNewOrder().getItemArray().get(i).getQuantity() + " - £" + selectedItem.getPrice());	
		}
	}



	/**
	 * Creates and adds a pre-defined header to the order list box display.
	 */
	public void setListTitle() {

		//Create new instance of date formatter set to a specvific format
		Format dateFormatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

		//Create a new date object that represents the current date
		Date today = new Date();

		//Add bespoke header to the list box display
		listTill.add(0, "New Transaction - Date : " + dateFormatter.format(today));
		listTill.add(1, "****************");
		listTill.add(2, "");
	}

	/**
	 * Resets the components in the panel ready for a new order.
	 */
	public void reset() {
		listTill.clear();
	}


}
