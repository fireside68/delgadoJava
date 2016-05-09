/*/
		Name:			Cedric Johnson
		Date:			04 May 2016
		Class:			CMIN 232
		Program Name:	Fibonacci.java
		Purpose:		Final for CMIN232
*/

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.text.*;

public class Fibonacci extends JFrame implements ActionListener
{
	// north panel; this will house a prompt label to instruct the user
	JPanel northPanel;
	JLabel northPromptLabel;

	// west panel; this will house buttons and fields necessary for user input
	JPanel westPanel;
	JLabel westStartLabel, westEndLabel;
	JTextField westStartField, westEndField;
	JButton westGoButton;

	// center panel; this will house the text area onto which results will be displayed
	JPanel centerPanel;
	JTextArea centerTextArea;
	JScrollPane centerScrollPane;

	// integer variables and array(s)
	int[] fibArray = new int[50];
	int[] highArray = new int[50]; // array to help search for high end
	int low, high; // sentinel values to be used
	int userLow, userHigh, rangeLow, rangeHigh;

	// string values for user input; will be converted to integer format
	public String userLowString, userHighString;

	// StringBuilder
	StringBuilder sb = new StringBuilder();

	public Fibonacci()
	{
		setLayout(new BorderLayout());
		setSize(new Dimension(600, 600));

		// build north panel
		northPanel = new JPanel();
			northPromptLabel = new JLabel();
			String prompt = "Enter two values.  The program will return Fibonacci numbers between those two values.";
			northPromptLabel.setText(prompt);
		northPanel.add(northPromptLabel);

		// build west panel
		westPanel = new JPanel(new GridLayout(5,1));
			westStartLabel = new JLabel("Start");
			westStartField = new JTextField(10);
			westEndLabel = new JLabel("End");
			westEndField = new JTextField(10);
			westGoButton = new JButton("Go");
				westGoButton.addActionListener(this);
		westPanel.add(westStartLabel);
		westPanel.add(westStartField);
		westPanel.add(westEndLabel);
		westPanel.add(westEndField);
		westPanel.add(westGoButton);

		// build center panel
		centerPanel = new JPanel();
		centerTextArea = new JTextArea();
			centerTextArea.setFont(new Font("Courier", Font.BOLD, 14));
			centerTextArea.setLineWrap(true);
			centerTextArea.setWrapStyleWord(true);
		//centerScrollPane = new JScrollPane(centerTextArea);
			centerTextArea.setBounds(250, 250, 300, 300);
		centerPanel.add(centerTextArea);


		// add panels to frame
		add(northPanel, "North");
		add(westPanel, "West");
		add(centerPanel, "Center");

	}// end Fibonacci() constructor

	public static void main(String[] args)
	{
		Fibonacci seq = new Fibonacci();
		seq.setVisible(true);
		seq.pack();
	}// end main()

	public void actionPerformed(ActionEvent e)
	{
		userLowString = westStartField.getText();
		userHighString = westEndField.getText();
		// input validation; will determine if user input is an integer
		boolean isLow = isLowInteger(userLowString);
		boolean isHigh = isHighInteger(userHighString);

		// once user input has been determined to be an integer, it will then be parsed to
		// integer format from string
		userLow = Integer.parseInt(userLowString);
		userHigh = Integer.parseInt(userHighString);

		// further validation of user input; ensures user enters positive integers
		while((userLow < 0) || (userHigh < 0))
				{
					if(userLow < 0)
					{
						userLowString = JOptionPane.showInputDialog("Please enter a positive low integer.");
						userLow = Integer.parseInt(userLowString);
					}

					if(userHigh < 0)
					{
						userHighString = JOptionPane.showInputDialog("Please enter a positive high integer.");
						userHigh = Integer.parseInt(userHighString);
					}
		}// end while for negative numbers

		// validates user low score is below user high score
		if(userHigh < userLow)
		{
			userHighString = JOptionPane.showInputDialog("Please enter an integer larger than " + userLow + ".");
			boolean newCheck = isHighInteger(userHighString);
			userHigh = Integer.parseInt(userHighString);
			while(userHigh < 0)
			{
				userHighString = JOptionPane.showInputDialog("Please enter a positive high integer.");
				userHigh = Integer.parseInt(userHighString);
			}
		}

		// load array with numbers in the Fibonacci sequence
		fibArray[0] = 0;
		fibArray[1] = 1;
		fibArray[2] = 1;
		fillFibArray(fibArray);

		low = getLowFibonacci(userLow, fibArray);
		high = getHighFibonacci(userHigh, fibArray);

		// displays the user's low end number, the Fibonacci numbers within the specified range,
		// and the user's high end number; output to textarea in center panel

		centerTextArea.append(Integer.toString(userLow) + ", ");
		for(int g = low; g <= high; g++)
		{
			centerTextArea.append(Integer.toString(fibArray[g]) + ", ");
		}
		centerTextArea.append(Integer.toString(userHigh));

	}// end actionPerformed()

	 // this method validates integer input for user low integer
	 public boolean isLowInteger(String s)
	 {
	      boolean isValidInteger = false;
	      try
	      {
	         Integer.parseInt(s);

	         // s is a valid integer

	         isValidInteger = true;
	      }
	      catch (NumberFormatException ex)
	      {
	         // s is not an integer

	         userLowString = JOptionPane.showInputDialog("Please enter a positive low integer.");
	      }

	      return isValidInteger;
	   }// end isLowInteger()

		 // this method validates input for user high number
	   	 public boolean isHighInteger(String s)
	   	 {
	   	      boolean isValidInteger = false;
	   	      try
	   	      {
	   	         Integer.parseInt(s);

	   	         // s is a valid integer

	   	         isValidInteger = true;
	   	      }
	   	      catch (NumberFormatException ex)
	   	      {
	   	         // s is not an integer

	   	         userHighString = JOptionPane.showInputDialog("Please enter a positive high integer.");
	   	      }

	   	      return isValidInteger;
	   	}// end isHighInteger

		// this method fills an array with 46 numbers of the Fibonacci sequence (0, 1, and 1 already preloaded)
	   	public int[] fillFibArray(int[] sequence)
	   	{
			for(int i = 3; i < 50; i++)
			{
				sequence[i] = sequence[i - 2] + sequence[i - 1];	//algorithn for Fibonacci sequence
			}

			return sequence;
		}

		public int getLowFibonacci(int userLow, int[] fibArray)
		{
			int[] lowArray = new int[50]; // array to help search for low end
			int z = 0;
			do
			{
				lowArray[z] = fibArray[z];
				z++;
			}while(fibArray[z] < userLow);
			return z;
		}// end getLowFibonacci()

		/*
			getHighFibonacci() will take the passed values of userHigh and the contents of fibArray[].
			A new array, local to the method, will populate with numbers from the sequence until the
			index of the number higher than the user's high end is reached; the method will return the index
			number one prior to the user's input
		*/

		public int getHighFibonacci(int userHigh, int[] fibArray)
		{
			int[] highArray = new int[50]; // array to help search for low end
			int q = 0;
			do
			{
				highArray[q] = fibArray[q];
				q++;
			}while(fibArray[q] < userHigh);
			return q - 1;
		}// end getHighFibonacci()

	}// end class Fibonacci()