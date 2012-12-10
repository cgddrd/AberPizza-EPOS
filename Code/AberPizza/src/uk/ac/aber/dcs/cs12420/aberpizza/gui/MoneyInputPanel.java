package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

/**
 * Calculates payment information and contains components that allow user to
 * enter payment information for an order before closing and saving the order 
 * once payment is completed.
 * 
 * Contained within {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.InputBasePanel}.
 * 
 * @author Connor Goddard (clg11)
 */
public class MoneyInputPanel extends JPanel implements ActionListener {

	/** Buttons that represent monetary values and calculate operation */
	private JButton buttonZero, buttonOne, buttonTwo, buttonThree, buttonFour, 
	buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine, buttonPoint,
	buttonDZero, button10Cash, button20Cash, button50Cash, buttonOK;

	/** The amount title. */
	private JLabel cashTitle, changeTitle, amountTitle;

	/** The component title labels. */
	private JTextField textAmount;

	/** The layout manager used by the panel. */
	private SpringLayout layout;

	/** The Till data object used to control and access the application data model. */
	private Till tillObject;

	/** Link to the display base panel - used to update GUI display. */
	private DisplayBasePanel guiDisplay;

	/** Value used to keep track of the amount of money paid by customer. */
	private BigDecimal amountPaid;

	/** FileChooser dialog object used to select XML load files */
	private JFileChooser fc = new JFileChooser();

	/**
	 * Instantiates a new Money Input Panel. Creates 'link' objects to {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data object
	 * and {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.DisplayBasePanel}.
	 *
	 * @param tillObject The {@link uk.ac.aber.dcs.cs12420.aberpizza.data.Till} data object passed down from {@link uk.ac.aber.dcs.cs12420.aberpizza.gui.TillFrame}
	 * @param displayPan The object representing the base panel for the 'display' sub-panels
	 */
	public MoneyInputPanel(Till tillObject, DisplayBasePanel displayPan) {

		//Set the layout manager
		layout = new SpringLayout();
		this.setLayout(layout);	

		//Set the size of the panel
		this.setPreferredSize(new Dimension(360, 335));

		//Create a tiled border around the panel
		this.setBorder(BorderFactory.createTitledBorder("Order Payment"));

		//Set amount paid value to initial '0.00' value
		this.amountPaid = new BigDecimal("0.00");

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

		//Create new instance of JLabel with specified display text
		cashTitle = new JLabel("Cash:");
		changeTitle = new JLabel("Change:");
		amountTitle = new JLabel("Amount Entered:");

		//Set maximum size of amount text box to 15 characters
		textAmount = new JTextField(15);

		//Create new instance of JButton with specified button text
		buttonZero = new JButton("  0  ");

		//Set the font type and size of the button
		buttonZero.setFont(new Font("Arial", Font.BOLD, 20));

		//Add link to local action listener to the button
		buttonZero.addActionListener(this);

		buttonOne = new JButton("  1  ");
		buttonOne.setFont(new Font("Arial", Font.BOLD, 20));
		buttonOne.addActionListener(this);

		buttonTwo = new JButton("  2  ");
		buttonTwo.setFont(new Font("Arial", Font.BOLD, 20));
		buttonTwo.addActionListener(this);

		buttonThree = new JButton("  3  ");
		buttonThree.setFont(new Font("Arial", Font.BOLD, 20));
		buttonThree.addActionListener(this);

		buttonFour = new JButton("  4  ");
		buttonFour.setFont(new Font("Arial", Font.BOLD, 20));
		buttonFour.addActionListener(this);

		buttonFive = new JButton("  5  ");
		buttonFive.setFont(new Font("Arial", Font.BOLD, 20));
		buttonFive.addActionListener(this);

		buttonSix = new JButton("  6  ");
		buttonSix.setFont(new Font("Arial", Font.BOLD, 20));
		buttonSix.addActionListener(this);

		buttonSeven = new JButton("  7  ");
		buttonSeven.setFont(new Font("Arial", Font.BOLD, 20));
		buttonSeven.addActionListener(this);

		buttonEight = new JButton("  8  ");
		buttonEight.setFont(new Font("Arial", Font.BOLD, 20));
		buttonEight.addActionListener(this);

		buttonNine = new JButton("  9  ");
		buttonNine.setFont(new Font("Arial", Font.BOLD, 20));
		buttonNine.addActionListener(this);

		buttonPoint = new JButton("  .  ");
		buttonPoint.setFont(new Font("Arial", Font.BOLD, 20));
		buttonPoint.addActionListener(this);

		buttonDZero = new JButton(" 00 ");
		buttonDZero.setFont(new Font("Arial", Font.BOLD, 20));
		buttonDZero.addActionListener(this);

		button10Cash = new JButton("£10.00");
		button10Cash.setFont(new Font("Arial", Font.BOLD, 20));
		button10Cash.addActionListener(this);

		button20Cash = new JButton("£20.00");
		button20Cash.setFont(new Font("Arial", Font.BOLD, 20));
		button20Cash.addActionListener(this);

		button50Cash = new JButton("£50.00");
		button50Cash.setFont(new Font("Arial", Font.BOLD, 20));
		button50Cash.addActionListener(this);

		buttonOK = new JButton("OK");
		buttonOK.addActionListener(this);

		//Add the components to the panel
		this.add(cashTitle);
		this.add(changeTitle);
		this.add(amountTitle);
		this.add(textAmount);
		this.add(buttonZero);
		this.add(buttonOne);
		this.add(buttonTwo);
		this.add(buttonThree);
		this.add(buttonFour);
		this.add(buttonFive);
		this.add(buttonSix);
		this.add(buttonSeven);
		this.add(buttonEight);
		this.add(buttonNine);
		this.add(buttonDZero);
		this.add(buttonPoint);
		this.add(button10Cash);
		this.add(button20Cash);
		this.add(button50Cash);
		this.add(buttonOK);

	}

	/**
	 * Sets up the 'SpringLayout' layout manager to organise all components on the panel.
	 */
	public void setUpLayout() {

		//Set NORTH edge of 'newClass' label, 10 pixels down from NORTH edge of panel 
		layout.putConstraint(SpringLayout.NORTH,amountTitle,10,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,amountTitle,10,SpringLayout.WEST, this); 

		layout.putConstraint(SpringLayout.NORTH,textAmount,10,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,textAmount,10,SpringLayout.EAST, amountTitle); 

		layout.putConstraint(SpringLayout.NORTH,buttonOK,8,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,buttonOK,10,SpringLayout.EAST, textAmount); 

		layout.putConstraint(SpringLayout.NORTH,cashTitle,10,SpringLayout.SOUTH, textAmount);
		layout.putConstraint(SpringLayout.WEST,cashTitle,10,SpringLayout.WEST, this); 

		layout.putConstraint(SpringLayout.NORTH,button10Cash,10,SpringLayout.SOUTH, cashTitle);
		layout.putConstraint(SpringLayout.WEST,button10Cash,22,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,button20Cash,10,SpringLayout.SOUTH, cashTitle);
		layout.putConstraint(SpringLayout.WEST,button20Cash,10,SpringLayout.EAST, button10Cash);

		layout.putConstraint(SpringLayout.NORTH,button50Cash,10,SpringLayout.SOUTH, cashTitle);
		layout.putConstraint(SpringLayout.WEST,button50Cash,10,SpringLayout.EAST, button20Cash);

		layout.putConstraint(SpringLayout.NORTH,changeTitle,10,SpringLayout.SOUTH, button10Cash);
		layout.putConstraint(SpringLayout.WEST,changeTitle,10,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,buttonSeven,10,SpringLayout.SOUTH, changeTitle);
		layout.putConstraint(SpringLayout.WEST,buttonSeven,60,SpringLayout.WEST, this); 

		layout.putConstraint(SpringLayout.NORTH,buttonEight,10,SpringLayout.SOUTH, changeTitle);
		layout.putConstraint(SpringLayout.WEST,buttonEight,10,SpringLayout.EAST, buttonSeven); 

		layout.putConstraint(SpringLayout.NORTH,buttonNine,10,SpringLayout.SOUTH, changeTitle);
		layout.putConstraint(SpringLayout.WEST,buttonNine,10,SpringLayout.EAST, buttonEight);

		layout.putConstraint(SpringLayout.NORTH,buttonFour,10,SpringLayout.SOUTH, buttonEight);
		layout.putConstraint(SpringLayout.WEST,buttonFour,60,SpringLayout.WEST, this); 

		layout.putConstraint(SpringLayout.NORTH,buttonFive,10,SpringLayout.SOUTH, buttonEight);
		layout.putConstraint(SpringLayout.WEST,buttonFive,10,SpringLayout.EAST, buttonFour); 

		layout.putConstraint(SpringLayout.NORTH,buttonSix,10,SpringLayout.SOUTH, buttonEight);
		layout.putConstraint(SpringLayout.WEST,buttonSix,10,SpringLayout.EAST, buttonFive); 

		layout.putConstraint(SpringLayout.NORTH,buttonOne,10,SpringLayout.SOUTH, buttonFour);
		layout.putConstraint(SpringLayout.WEST,buttonOne,60,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,buttonTwo,10,SpringLayout.SOUTH, buttonFour);
		layout.putConstraint(SpringLayout.WEST,buttonTwo,10,SpringLayout.EAST, buttonOne);

		layout.putConstraint(SpringLayout.NORTH,buttonThree,10,SpringLayout.SOUTH, buttonFour);
		layout.putConstraint(SpringLayout.WEST,buttonThree,10,SpringLayout.EAST, buttonTwo); 

		layout.putConstraint(SpringLayout.NORTH,buttonZero,10,SpringLayout.SOUTH, buttonOne);
		layout.putConstraint(SpringLayout.WEST,buttonZero,60,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,buttonPoint,10,SpringLayout.SOUTH, buttonOne);
		layout.putConstraint(SpringLayout.WEST,buttonPoint,12,SpringLayout.EAST, buttonZero);

		layout.putConstraint(SpringLayout.NORTH,buttonDZero,10,SpringLayout.SOUTH, buttonOne);
		layout.putConstraint(SpringLayout.WEST,buttonDZero,15,SpringLayout.EAST, buttonPoint);

	}

	/**
	 * Using amount due and amount paid values, determines whether an order has been
	 * paid for or not and calculates payment still due/change due accordingly.
	 */
	public void calculatePayment() {

		//Retrieve the payment entered by user
		BigDecimal newAmountPaid = new BigDecimal(textAmount.getText());

		//Add the additional payment value to the existing amount paid value
		amountPaid = amountPaid.add(newAmountPaid);

		//Update the 'amount paid' GUI display
		guiDisplay.getCostPanel().setAmountPaid(amountPaid);

		//Update the amount paid value in the current order object
		tillObject.getNewOrder().setAmountPaid(amountPaid);

		//Determine if the amount of money still due exceeds the new payment value entered by user
		if (guiDisplay.getCostPanel().getAmountDue().intValue() > newAmountPaid.intValue()) {

			//If it does, create new local variable containing amount of money still due
			BigDecimal moneyToPay = guiDisplay.getCostPanel().getAmountDue().subtract(newAmountPaid);

			//Display messgae box informing user of money left to pay
			JOptionPane.showMessageDialog(new JFrame(), "Money still to pay: £" + moneyToPay, "AberPizza | Payment", JOptionPane.INFORMATION_MESSAGE);

			//Update the amount due display
			guiDisplay.getCostPanel().setAmountDue(moneyToPay);

			//Otherwise if the amount of money paid by customer exceeds the amount due	
		} else {

			//Create new local variable containing the difference between the two values
			BigDecimal moneyOwed = newAmountPaid.subtract(guiDisplay.getCostPanel().getAmountDue());

			//If no change is due
			if (moneyOwed.toString().equals("0.00")) {

				//Display message box confirming order has been successfully paid for
				JOptionPane.showMessageDialog(new JFrame(), "Transaction Completed - Receipt Printing", "AberPizza | Payment", JOptionPane.INFORMATION_MESSAGE);
			} else {

				//Otherwise, display message bopx informing user of how much change is due to the customer
				JOptionPane.showMessageDialog(new JFrame(), "Change Due: £" + moneyOwed, "AberPizza | Payment", JOptionPane.INFORMATION_MESSAGE);

				//Update the Order object with how much change was given (for receipt)
				tillObject.getNewOrder().setChangeGiven(moneyOwed);
			}

			//Add the now completed order to the array list of orders
			tillObject.addOrder(tillObject.getNewOrder());

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

			}
			
			//Display confirmation dialog once completed
			JOptionPane.showMessageDialog(new JFrame(), "Receipt Exported Successfully", "AberPizza | Receipt Export", JOptionPane.INFORMATION_MESSAGE);

			//Close the order
			tillObject.closeOrder();

			//Reset GUI display
			resetTill();
		}

	}

	/**
	 * Resets all display panels within GUI ready for a new order to be created. 
	 */
	public void resetTill() {
		guiDisplay.getCostPanel().reset();
		guiDisplay.getOrderPanel().reset();
		guiDisplay.getTillPanel().reset();
		textAmount.setText("");
	}

	/** Listener for actions on panel to allow interactive components (such as Buttons) to be used.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * @param evt - ActionEvent called from components on the panel that require an action to be performed
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		String actionCommand = evt.getActionCommand();

		//If the "OK" button is clicked
		if (actionCommand.equals("OK")) {

			try {

				//Check there is an order in progress
				if (tillObject.isOrderActive() == true) {

					//If there is, calculate the payment
					calculatePayment();	

					//Reset the money value input textbox
					textAmount.setText("");
				} else {
					//Otheriwse if there is no order in progress, display error message box
					JOptionPane.showMessageDialog(new JFrame(), "No order open. Please open an order to continue.", "AberPizza | Calculate Price", JOptionPane.ERROR_MESSAGE);
				}

			} catch (NullPointerException nP) {
				JOptionPane.showMessageDialog(new JFrame(), "No price to calculate", "AberPizza | Calculate Price", JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException nFP) {
				JOptionPane.showMessageDialog(new JFrame(), "Currency format not acceptable", "AberPizza | Calculate Price", JOptionPane.ERROR_MESSAGE);
			}

			//If a number panel button is pressed
		} else if (actionCommand.equals("  0  ")) {

			//Add this value to the money input textbox
			textAmount.setText(textAmount.getText() + "0");

		} else if (actionCommand.equals(" 00 ")) {
			textAmount.setText(textAmount.getText() + "00");
		} else if (actionCommand.equals("  1  ")) {
			textAmount.setText(textAmount.getText() + "1");
		} else if (actionCommand.equals("  2  ")) {
			textAmount.setText(textAmount.getText() + "2");
		} else if (actionCommand.equals("  3  ")) {
			textAmount.setText(textAmount.getText() + "3");
		} else if (actionCommand.equals("  4  ")) {
			textAmount.setText(textAmount.getText() + "4");
		} else if (actionCommand.equals("  5  ")) {
			textAmount.setText(textAmount.getText() + "5");
		} else if (actionCommand.equals("  6  ")) {
			textAmount.setText(textAmount.getText() + "6");
		} else if (actionCommand.equals("  7  ")) {
			textAmount.setText(textAmount.getText() + "7");
		} else if (actionCommand.equals("  8  ")) {
			textAmount.setText(textAmount.getText() + "8");
		} else if (actionCommand.equals("  9  ")) {
			textAmount.setText(textAmount.getText() + "9");
		} else if (actionCommand.equals("  .  ")) {
			textAmount.setText(textAmount.getText() + ".");
		} else if (actionCommand.equals("£10.00")) {
			textAmount.setText("10.00");
		} else if (actionCommand.equals("£20.00")) {
			textAmount.setText("20.00");
		} else if (actionCommand.equals("£50.00")) {
			textAmount.setText("50.00");
		}
	}
}
