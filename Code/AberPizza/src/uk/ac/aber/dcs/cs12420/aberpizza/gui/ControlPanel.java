package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;


/**
 * Allows user to perform system-wide operations within the application including creating a new order,
 * printing receipts and generating 'end of the day' reports.
 * 
 * Contained within {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.DisplayBasePanel}.
 * 
 * @author Connor Goddard (clg11)
 */
public class ControlPanel extends JPanel implements ActionListener {

	/** Buttons that represent system-wide operations */
	private JButton newOrder, saveTill, loadTill, printReceipt, exitTill;

	/** The layout manager used by the panel */
	private SpringLayout layout = new SpringLayout();
	
	/** The current date */
	private Date today = new Date();
	
	/** Formats the date into specific format for outputting the report file name */
	private Format fileNameFormatter = new SimpleDateFormat("dd-MM-yy");

	/** The Till data object used to control and access the application data model */
	private Till tillObject;

	/** Link to the base panel for this class - used to update GUI elements */
	private DisplayBasePanel guiDisplay;

	/** FileChooser dialog object used to select XML load files */
	private JFileChooser fc = new JFileChooser();

	/**
	 * Instantiates a new Control Panel. Creates 'link' objects to {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data object
	 * and {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.DisplayBasePanel}.
	 *
	 * @param tillObject The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data object passed down from {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.TillFrame}
	 * @param displayPan The object representing the 'base' panel for this panel
	 */
	public ControlPanel(Till tillObject, DisplayBasePanel displayPan) {

		//Set the size of the panel
		this.setPreferredSize(new Dimension(385, 100));

		//Create a titled border around the panel
		this.setBorder(BorderFactory.createTitledBorder("System"));

		//Set the layout manager
		this.setLayout(layout);

		//Link parameter objects to local variables
		this.guiDisplay = displayPan;
		this.tillObject = tillObject;

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

		//Create new instance of JButton with specified button text
		newOrder = new JButton("New Order");

		//Add link to local action listener to the button
		newOrder.addActionListener(this);

		saveTill = new JButton("Export Till Data");
		saveTill.addActionListener(this);
		loadTill = new JButton("Load Till Data");
		loadTill.addActionListener(this);
		printReceipt = new JButton("Print Receipt");
		printReceipt.addActionListener(this);
		exitTill = new JButton("Exit System");
		exitTill.addActionListener(this);

		//Add the button to the panel
		this.add(newOrder);

		this.add(saveTill);
		this.add(loadTill);
		this.add(printReceipt);
		this.add(exitTill);

	}

	/**
	 * Sets up the 'SpringLayout' layout manager to organise all components on the panel.
	 */
	public void setUpLayout() {

		//Set the NORTH edge of the button to be 5 pixels down from the NORTH edge of the panel
		layout.putConstraint(SpringLayout.NORTH,newOrder,5,SpringLayout.NORTH,this);

		//Set the WEST edge of the button to be 80 pixels to the right of the WEST edge of the panel
		layout.putConstraint(SpringLayout.WEST,newOrder,80,SpringLayout.WEST,this);

		layout.putConstraint(SpringLayout.NORTH,printReceipt,5,SpringLayout.NORTH,this);
		layout.putConstraint(SpringLayout.WEST,printReceipt,10,SpringLayout.EAST,newOrder);

		layout.putConstraint(SpringLayout.NORTH,saveTill,10,SpringLayout.SOUTH,newOrder);
		layout.putConstraint(SpringLayout.WEST,saveTill,10,SpringLayout.WEST,this);

		layout.putConstraint(SpringLayout.NORTH,loadTill,10,SpringLayout.SOUTH,newOrder);
		layout.putConstraint(SpringLayout.WEST,loadTill,10,SpringLayout.EAST,saveTill);

		layout.putConstraint(SpringLayout.NORTH,exitTill,10,SpringLayout.SOUTH,newOrder);
		layout.putConstraint(SpringLayout.WEST,exitTill,10,SpringLayout.EAST,loadTill);


	}

	/** Listener for actions on panel to allow interactive components (such as Buttons) to be used.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * @param evt - ActionEvent called from components on the panel that require an action to be performed
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		String actionCommand = evt.getActionCommand();

		//If the "New Order" button is clicked
		if (actionCommand.equals("New Order")) {

			//Check if an order is already in progress
			if (tillObject.isOrderActive() == true) {

				//If so, display error message box
				JOptionPane.showMessageDialog(new JFrame(), "Order already in progress - Close order to create new order", "AberPizza | New Order", JOptionPane.ERROR_MESSAGE);
			
			} else {

				//Otherwise.. 
				try {

					//Display input dialog to allow user to enter customer name
					String custName = JOptionPane.showInputDialog(null, "Enter Customer Name:", "AberPizza | New Order", JOptionPane.QUESTION_MESSAGE);

					//Prevent the user entering nothing for the customer name
					while (custName.equals("")) {
						custName = JOptionPane.showInputDialog(null, "Enter Customer Name:", "AberPizza | New Order", JOptionPane.QUESTION_MESSAGE);
					}

					//Create a new Order object ('newOrder') using the entered customer name
					tillObject.createNewOrder(custName);

					//Setup the header information for both GUI listboxes
					guiDisplay.getOrderPanel().setListTitle(custName);
					guiDisplay.getTillPanel().setListTitle();

				} catch (NullPointerException nP) {
					//If input dialog was closed pre-maturely, display error message box.
					JOptionPane.showMessageDialog(new JFrame(), "No name specified, please enter a name to continue", "AberPizza | New Order", JOptionPane.ERROR_MESSAGE);
				}
			}	


		} else if (actionCommand.equals("Export Till Data")) {
			
			//Set up file chooser dialog to allows only files to be selected and set '.xml' file filter
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.addChoosableFileFilter(new FileNameExtensionFilter("XML files", "xml"));
			
			//Set the default file name
			fc.setSelectedFile(new File("AberPizza_Till_Export-" + fileNameFormatter.format(today) + ".xml"));

			//Display file chooser dialog
			int returnVal = fc.showSaveDialog(null);

			//Once the user has selected the file and clicked the 'Open' button
			if (returnVal == JFileChooser.APPROVE_OPTION) {

			//Run the 'save' method in Till to export the Till to XML
			tillObject.save(fc.getSelectedFile().toString());

			//Display confirmation dialog once completed
			JOptionPane.showMessageDialog(new JFrame(), "Till Exported Successfully", "AberPizza | Till Export", JOptionPane.INFORMATION_MESSAGE);
			
			}

		} else if (actionCommand.equals("Load Till Data")) {

			//Set up file chooser dialog to allows only files to be selected and set '.xml' file filter
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.addChoosableFileFilter(new FileNameExtensionFilter("XML files", "xml"));

			//Display file chooser dialog
			int returnVal = fc.showOpenDialog(null);

			//Once the user has selected the file and clicked the 'Open' button
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				try {

					//Update the current Till object to the newly loaded Till object created from the XML data
					tillObject = Till.load(fc.getSelectedFile().toString());

					//Display confirmation dialog once completed
					JOptionPane.showMessageDialog(new JFrame(), "Till Imported Successfully", "AberPizza | Till Import", JOptionPane.INFORMATION_MESSAGE);

					//If there is a problem with the file and/or it's data, display error message box
				} catch (ArrayIndexOutOfBoundsException aOB) {

					JOptionPane.showMessageDialog(new JFrame(), "Error loading till - File not compatible", "AberPizza | Till Import", JOptionPane.ERROR_MESSAGE);

				} catch (NoSuchElementException sE) {

					JOptionPane.showMessageDialog(new JFrame(), "Error loading till - File not compatible", "AberPizza | Till Import", JOptionPane.ERROR_MESSAGE);

				} catch (IOException e) {

					JOptionPane.showMessageDialog(new JFrame(), "Error loading till - " + e, "AberPizza | Till Import", JOptionPane.ERROR_MESSAGE);

				}

			}

		} else if (actionCommand.equals("Print Receipt")) {

			//If an order is currently in progress but there are no orders saved in the Till
			if (tillObject.isOrderActive() == true && tillObject.getNewOrder().getItemArray().size() == 0) {

				//Display an error message box
				JOptionPane.showMessageDialog(new JFrame(), "Cannot export - No items in order to export", "AberPizza | Save Receipt", JOptionPane.ERROR_MESSAGE);

				//Otherwise, if there are orders stored in the till
			} else if (tillObject.getOrderArray().size() > 0) {

				//Display save dialog
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				//Display the file chooser dialog
				int returnVal = fc.showSaveDialog(null);

				//Check if user has approved save
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
					//Display question dialog
					int shouldPrint = JOptionPane.YES_OPTION;
					shouldPrint = JOptionPane.showConfirmDialog(null, "Would you like to send the receipts to print?", "AberPizza | Print Receipt", JOptionPane.YES_NO_OPTION);

					//If user selects "yes"
					if (shouldPrint == JOptionPane.YES_OPTION) {

						//Run the method in Till to generate and export the receipts for them
						tillObject.exportReceipt(fc.getSelectedFile().toString(), true);

					} else {
						
						//Run the method in Till to generate and export the receipts for them
						tillObject.exportReceipt(fc.getSelectedFile().toString(), false);
						
					}

					//Display confirmation dialog once completed
					JOptionPane.showMessageDialog(new JFrame(), "Receipt Saved Successfully", "AberPizza | Save Receipt", JOptionPane.INFORMATION_MESSAGE);
				}

			//Otherwise..
			} else {

				JOptionPane.showMessageDialog(new JFrame(), "Cannot export - No orders available to export", "AberPizza | Save Receipt", JOptionPane.ERROR_MESSAGE);

			}


		} else if (actionCommand.equals("Exit System")) {

			//Display question dialog
			int exit = JOptionPane.YES_OPTION;
			exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "AberPizza | Exit", JOptionPane.YES_NO_OPTION);

			//If user selects "yes"
			if (exit == JOptionPane.YES_OPTION) {

				//Terminate the program
				System.exit(0);

			}
		}
	}
}
