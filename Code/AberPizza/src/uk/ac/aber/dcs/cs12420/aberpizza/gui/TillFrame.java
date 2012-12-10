package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.NoSuchElementException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

/**
 * Main JFrame for displaying program GUI. Responsible for initialising the 
 * main data model class {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till}, as well as
 * the two GUI base panels {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.DisplayBasePanel} & 
 * {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.InputBasePanel}. Passes the new Till object to the base panels
 * as parameters to allow access to the data model from GUI sub-panels.
 * 
 * Also creates and initialises menu bar and it's associated listener {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.MenuListener}.
 * 
 * @author Connor Goddard (clg11)
 *
 */
public class TillFrame extends JFrame {

	/** The new InputBasePanel component */
	private InputBasePanel inputPan;

	/** The new DisplayBasePanel component */
	private DisplayBasePanel displayPan;

	/** The new Till data object used to control and access the application data model */
	private Till tillObject;

	/** The new listener for the menu bar */
	private MenuListener mL;

	/** The menu bar used to select additional and advanced operations. */
	private JMenuBar mb = new JMenuBar();

	/** The menu bar menus and menu items. */
	private JMenu fileMenu = new JMenu("File");
	private JMenu adminMenu = new JMenu("Admin");
	private JMenu helpMenu = new JMenu("Help");
	private JMenuItem newOrder = new JMenuItem("New Order");
	private JMenuItem cancelOrder = new JMenuItem("Cancel Order");
	private JMenuItem saveTill = new JMenuItem("Save Till");
	private JMenuItem loadTill = new JMenuItem("Load Till");
	private JMenuItem exitProg = new JMenuItem("Exit");
	private JMenuItem genOverview= new JMenuItem("Generate Till Report");
	private JMenuItem about = new JMenuItem("About");

	/** FileChooser dialog object used to select XML load files */
	private JFileChooser fc = new JFileChooser();


	/**
	 * Instantiates a new Till Frame. Creates a new {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} object
	 * by attempting to load data from an XML file, or if that is to available creates a brand new object.
	 * 
	 * Creates and initialises both 'base' panels to allow the GUI sub-panels to be created.
	 */
	public TillFrame() {

		//Initialise and set up frame
		this.setTitle("AberPizza Till - By Connor Goddard (clg11)");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		//Display question dialog
		int exit = JOptionPane.YES_OPTION;
		exit = JOptionPane.showConfirmDialog(null, "Would you like to import Till from file?", "AberPizza | Start-Up", JOptionPane.YES_NO_OPTION);

		//If user selects "yes"
		if (exit == JOptionPane.YES_OPTION) {

			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.addChoosableFileFilter(new FileNameExtensionFilter("XML files", "xml"));

			//Display the file chooser dialog
			int returnVal = fc.showOpenDialog(null);

			//Check if user has approved save
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				//Attempt to create the new Till object from XML data
				try {

					tillObject = Till.load(fc.getSelectedFile().toString());

					//Ensure all orders are closed
					tillObject.closeOrder();

					//If the Till cannot be loaded from XML, create a new instance of Till
				} catch (IOException e) {
					tillObject = new Till();
				} catch (NoSuchElementException nSE) {
					tillObject = new Till();
				}
			
			} else {
				//If user closes the file chooser dialog prematurely, create a new Till object
				tillObject = new Till();
			}

		} else {

			//Otherwise, if they select no, create new Till object
			tillObject = new Till();

		}

		//Initialise the two 'base' panels passing the new Till data object as parameters
		displayPan = new DisplayBasePanel(tillObject);
		inputPan = new InputBasePanel(tillObject, displayPan);

		//Add the two base panels to the frame
		this.add(inputPan, BorderLayout.EAST);
		this.add(displayPan, BorderLayout.WEST);

		//Create a new menu listener passing the data object and base panels as parameters
		mL = new MenuListener(tillObject, displayPan, inputPan);

		//Create and initialise keystroke shortcut commands for menu bar
		KeyStroke keyNewOrder = KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		KeyStroke keyLoadTill = KeyStroke.getKeyStroke(KeyEvent.VK_L, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		KeyStroke keySaveTill= KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		KeyStroke keyExportReport = KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		KeyStroke keyQuit = KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());

		//Initialise menus and menu items and add link to menu listener
		newOrder.addActionListener(mL);
		newOrder.setAccelerator(keyNewOrder);
		fileMenu.add(newOrder);
		cancelOrder.addActionListener(mL);
		fileMenu.add(cancelOrder);
		saveTill.addActionListener(mL);
		saveTill.setAccelerator(keySaveTill);
		fileMenu.add(saveTill);
		loadTill.addActionListener(mL);
		loadTill.setAccelerator(keyLoadTill);
		fileMenu.add(loadTill);
		exitProg.addActionListener(mL);
		exitProg.setAccelerator(keyQuit);
		fileMenu.add(exitProg);


		genOverview.addActionListener(mL);
		genOverview.setAccelerator(keyExportReport);
		adminMenu.add(genOverview);

		about.addActionListener(mL);
		helpMenu.add(about);

		//Add menus to menu bar
		mb.add(fileMenu);
		mb.add(adminMenu);
		mb.add(helpMenu);

		//Add menu bar to frame
		this.add(mb, BorderLayout.NORTH);

		//Fit frame to ensure all panels/components are visible
		this.pack();

		//Determine centre of user's screen and position frame accordingly
		Toolkit k=Toolkit.getDefaultToolkit();
		Dimension d=k.getScreenSize();
		this.setLocation(d.width/2-this.getWidth()/2,d.height/2-this.getHeight()/2);

		//Display frame on screen
		this.setVisible(true);

	}

}
