package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

/**
 * Listener for menu bar in {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.TillFrame} that performs specific 
 * functions when menu items are clicked.
 * 
 * @author Connor Goddard (clg11)
 *
 * @see MenuEvent
 */
public class MenuListener implements ActionListener {

	/** The Till data object used to control and access the application data model */
	private Till tillObject;

	/** The current date */
	private Date today = new Date();

	/** Link to the display base panel - used to update GUI display. */
	private DisplayBasePanel displayPan;

	/** Link to the input base panel - used to retrieve input from user via GUI. */
	private InputBasePanel inputPan;

	/** FileChooser dialog object used to select XML load files */
	private JFileChooser fc = new JFileChooser();

	/** Formats the date into specific format for outputting the report file name */
	private Format fileNameFormatter = new SimpleDateFormat("dd-MM-yy");

	/**
	 * Instantiates a new menu listener. Creates 'link' objects to {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data object,
	 * {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.InputBasePanel} and {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.DisplayBasePanel}
	 *
	 * @param tillObject The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data object passed down from {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.TillFrame}
	 * @param displayPan The object representing the base panel for the 'display' sub-panels
	 * @param inputPan The object representing the base panel for the 'input' sub-panels
	 */
	public MenuListener(Till tillObject, DisplayBasePanel displayPan, InputBasePanel inputPan) {

		this.tillObject = tillObject;
		this.displayPan = displayPan;
		this.inputPan = inputPan;

	}

	/** Listener for actions from menu bar, to allow operations to be run when menu items are clicked.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * @param evt - ActionEvent called from menu items in the menu bar that require an action to be performed.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		String actionCommand = evt.getActionCommand();

		//**See ControlPanel for more information on this code
		if (actionCommand.equals("New Order")) {

			if (!(tillObject.getNewOrder() == null)) {
				JOptionPane.showMessageDialog(new JFrame(), "Order already in progress - Close order to create new order", "AberPizza | New Order", JOptionPane.ERROR_MESSAGE);
			} else {
				String custName = JOptionPane.showInputDialog(null, "Enter Customer Name:", "AberPizza | New Order", JOptionPane.QUESTION_MESSAGE);
				tillObject.createNewOrder(custName);
				displayPan.getOrderPanel().setListTitle(custName);
				displayPan.getTillPanel().setListTitle();
			}

			//If the 'Cancel Order' menu item is clicked	
		} else if (actionCommand.equals("Cancel Order")) {

			//Check if an order is already in progress
			if (tillObject.getNewOrder() == null) {

				//If there is no order in progress, display error message box
				JOptionPane.showMessageDialog(new JFrame(), "Unable to cancel order - No order in progress.", "AberPizza | Cancel Order", JOptionPane.ERROR_MESSAGE);

				//Otherwise..
			} else {

				//Display question dialog confirming cancellation
				int cancel = JOptionPane.YES_OPTION;
				cancel = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel the order?", "AberPizza | Cancel Order", JOptionPane.YES_NO_OPTION);

				//If user selects "yes"
				if (cancel == JOptionPane.YES_OPTION) {

					//Close order and set 'newOrder' component in Till to null
					tillObject.closeOrder();

					//Reset the GUI
					inputPan.getMoneyInputPanel().resetTill();

					//Display confirmation dialog once completed
					JOptionPane.showMessageDialog(new JFrame(), "Order successfully cancelled.", "AberPizza | Cancel Order", JOptionPane.INFORMATION_MESSAGE);
				}
			}

		} else if (actionCommand.equals("Save Till")) {

			//Display save dialog
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.addChoosableFileFilter(new FileNameExtensionFilter("XML files", "xml"));

			//Set the default file name
			fc.setSelectedFile(new File("AberPizza_Till_Export-" + fileNameFormatter.format(today) + ".xml"));

			//Display the file chooser dialog
			int returnVal = fc.showSaveDialog(null);

			//Check if user has approved save
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				//Run the 'save' method in Till to export the Till to XML
				tillObject.save(fc.getSelectedFile().toString());

				//Display confirmation dialog once completed
				JOptionPane.showMessageDialog(new JFrame(), "Till Exported Successfully", "AberPizza | Till Export", JOptionPane.INFORMATION_MESSAGE);

			}

		} else if (actionCommand.equals("Load Till")) {

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

					JOptionPane.showMessageDialog(new JFrame(), "Error loading till - File not compatable", "AberPizza | Till Import", JOptionPane.ERROR_MESSAGE);

				} catch (IOException e) {

					JOptionPane.showMessageDialog(new JFrame(), "Error loading till - " + e, "AberPizza | Till Import", JOptionPane.ERROR_MESSAGE);

				}

			}

		} else if (actionCommand.equals("Exit")) {

			//Display question dialog
			int exit = JOptionPane.YES_OPTION;
			exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "AberPizza | Exit", JOptionPane.YES_NO_OPTION);

			//If user selects "yes"
			if (exit == JOptionPane.YES_OPTION) {

				//Terminate the program
				System.exit(0);
			} 

		} else if (actionCommand.equals("Generate Till Report")) {

			//If there are no objects to export
			if (tillObject.getOrderArray().size() == 0) {
				
				//Display error message box
				JOptionPane.showMessageDialog(new JFrame(), "Error exporting report - No orders in Till", "AberPizza | Report Export", JOptionPane.ERROR_MESSAGE);
			
			//Otherwise...
			} else {

				//Display save dialog
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.addChoosableFileFilter(new FileNameExtensionFilter("HTML files", "html"));

				//Set the default file name
				fc.setSelectedFile(new File("AberPizza Report-" + fileNameFormatter.format(today) + ".html"));

				//Display the file chooser dialog
				int returnVal = fc.showSaveDialog(null);

				//Check if user has approved save
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					//Display question dialog
					int shouldPrint = JOptionPane.YES_OPTION;
					shouldPrint = JOptionPane.showConfirmDialog(null, "Would you like to send the report to print?", "AberPizza | Print Report", JOptionPane.YES_NO_OPTION);


					try {

						//If user selects "yes"
						if (shouldPrint == JOptionPane.YES_OPTION) {

							//Run the method in Till to generate and export the 'end of day' report
							tillObject.exportReport(fc.getSelectedFile().toString(), fc.getSelectedFile().toURI(), true);

						} else {

							//Run the method in Till to generate and export the 'end of day' report
							tillObject.exportReport(fc.getSelectedFile().toString(), fc.getSelectedFile().toURI(), false);

						}

						//Display confirmation dialog once completed
						JOptionPane.showMessageDialog(new JFrame(), "Report Generated Successfully", "AberPizza | Till Report", JOptionPane.INFORMATION_MESSAGE);


					} catch (IndexOutOfBoundsException iAO) {

						//If there are no orders able to be exported, display error message box
						JOptionPane.showMessageDialog(new JFrame(), "Export Cancelled - No orders stored", "AberPizza | Till Report", JOptionPane.ERROR_MESSAGE);

					} catch (IOException iO) {

						JOptionPane.showMessageDialog(new JFrame(), "Export Cancelled - IO Error", "AberPizza | Till Report", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		} else if (actionCommand.equals("About")) {

			//Display 'About' dialog box
			JOptionPane.showMessageDialog(new JFrame(), "Created by: Connor Luke Goddard (clg11) | CS124 Individual Project 2012", "AberPizza | About", JOptionPane.PLAIN_MESSAGE);
		}
	}
}

