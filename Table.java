/*	Name:			Cedric Johnson
	Date:			07 April 2016
	Program Name:	Table.java
	Class:			CMIN 257
	Purpose:		Table class of Blackjack program
*/

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class Table extends JFrame
{
	Image [] imageArray = new Image[200];
	int size = 0;
	int x=10, y=100;
	// Canvas for cards
	DrawOn canvas = new DrawOn();
	Cards cards = new Cards();
	Dealer daemon;
	Player playa;
		//Fonts
		Font labelFont = new Font("Times New Roman", Font.BOLD, 24);
		Font scoreFont = new Font("Courier", Font.ITALIC, 24);

		// Draw Panel with buttons and the like
		Panel bar = new Panel(new GridLayout(1,3));

			Panel buttonPanel1 = new Panel(new GridLayout(1,3));		//buttons
				Button draw = new Button("Hit");
				Button stand = new Button("Stand");
				Button beginGame = new Button("New Game");

			Panel buttonPanel2 = new Panel(new GridLayout(1,2));		//Bet and Total
				// bet
				Panel betPanel = new Panel();
					JButton bet = new JButton("Bet");
					JTextField betField = new JTextField(6);
				// total
				Panel totalPanel = new Panel();
					Label total = new Label("Total: ");
					JTextField totalField = new JTextField(6);

			Panel buttonPanel3 = new Panel();		//Bank
				Label bank = new Label("Bank: ");
				Label bankLabel = new Label("");

		// Score Panel, which will be out east
		Panel scorePanel = new Panel(new GridLayout(2,1,2,2));

			//Dealer Score Panel
			Panel dlrScorePanel = new Panel(new GridLayout(2,1));
				Label dealerLabel = new Label("DEALER");
				Label dealerScore = new Label("");
			//Player Score Panel
			Panel plyrScorePanel = new Panel(new GridLayout(2,1));
				Label playerLabel = new Label("PLAYER");
				Label playerScore = new Label("");

		// Win/Lose/Draw Panel, which will be south
		JPanel statusPanel = new JPanel();
		JLabel statusLabel = new JLabel();
		ImageIcon winImage, loseImage;

	int[] xPos = new int[200];
	int[] yPos = new int[200];
	public int betNumber;// numerical bet
 	public String betText;// string bet
 	int totalBet = 0;//total of the bet, integer
 	public int bankTotal= 1000; // total in bank

	public Table()
	{
		setSize(new Dimension(1000,700));
				canvas.setBackground(Color.green);
				setLayout(new BorderLayout());
				setLocationRelativeTo(null);

				// Draw components onto bar

				// Panel1
				buttonPanel1.add(draw);
					draw.addActionListener(canvas);
					draw.setEnabled(false);
				buttonPanel1.add(stand);
					stand.addActionListener(canvas);
					stand.setEnabled(false);
				buttonPanel1.add(beginGame);
					beginGame.addActionListener(canvas);
				bar.add(buttonPanel1); //add buttonPanel1 to bar

				//Panel2

					//betPanel
					betPanel.add(bet);
						bet.addActionListener(canvas);
					betPanel.add(betField);
				buttonPanel2.add(betPanel);

					//totalPanel
					totalPanel.add(total);
					totalPanel.add(totalField);
					totalField.setEditable(false);
				buttonPanel2.add(totalPanel);
				bar.add(buttonPanel2);

				//Panel3
				buttonPanel3.add(bank);
				buttonPanel3.add(bankLabel);
				bar.add(buttonPanel3);

				// Draw components onto scorePanel

				//Dealer Panel
					dlrScorePanel.add(dealerLabel);
						dealerLabel.setFont(labelFont);
					dlrScorePanel.add(dealerScore);
						dealerScore.setFont(scoreFont);
						dealerScore.setVisible(false);
				scorePanel.add(dlrScorePanel);

				//Player Panel
					plyrScorePanel.add(playerLabel);
						playerLabel.setFont(labelFont);
					plyrScorePanel.add(playerScore);
						playerScore.setFont(scoreFont);
				scorePanel.add(plyrScorePanel);

				// Win Status Panel
				winImage = new ImageIcon("winbanner.jpg");
				loseImage = new ImageIcon("losebanner.jpg");
				statusPanel.add(statusLabel);
					statusLabel.setVisible(false);


				add(bar, "North");
				add(canvas, "Center");
				add(scorePanel,"East");
				add(statusPanel, "South");

	}

public void setDealer(Dealer daemon)
	{
		this.daemon = daemon;
		//repaint();
	}

	public void setPlayer(Player playa)
	{
		this.playa = playa;
	}



	public void setCard(ImageIcon newCard, int x, int y)
	{
		xPos[size] = x;
		yPos[size] = y;
		Image img = newCard.getImage();
		imageArray[size] = img;
		size++;
		setVisible(true);

	}

	class DrawOn extends Canvas implements ActionListener
	{
		public DrawOn()
		{
			setBackground(Color.green);
		}

		public void paint(Graphics g)
		{
			for(int i = 0; i < size; i++)
			{

				g.drawImage(imageArray[i], xPos[i], yPos[i], null);
				//g.fillRect(0, 0, getWidth(), getHeight());
				x += 100;
			}
		}

		public void actionPerformed(ActionEvent e)
		{
			String which = e.getActionCommand();
			if(which == "Hit")
			{
				daemon.playerDealOut();
				repaint();

			}

			if(which == "Stand")
			{
				draw.setEnabled(false);
				stand.setEnabled(false);
				bet.setEnabled(false);
				beginGame.setEnabled(true);
				daemon.dealerDealOut();
				repaint();
			}

			if(which == "Bet")
			{
				draw.setEnabled(true);
				stand.setEnabled(true);
				beginGame.setEnabled(false);
				betText = betField.getText();
				boolean isInteger = isInteger(betText);
				betNumber = Integer.parseInt(betText);
				while((betNumber < 0) || (betNumber > bankTotal))
				{
					if(betNumber < 0)
					{
						betText = JOptionPane.showInputDialog("Enter a number greater than 0: ");
						isInteger = isInteger(betText);
						betNumber = Integer.parseInt(betText);
					}
					if(betNumber > bankTotal)
					{
						betText = JOptionPane.showInputDialog("Enter an amount you actually have: ");
						isInteger = isInteger(betText);
						betNumber = Integer.parseInt(betText);
					}
				}
				betField.setText("");
				totalBet = totalBet + betNumber;
				totalField.setText(Integer.toString(totalBet));
			}

			if(which == "New Game")
			{
				clear();
				daemon.beginGame();

			}
		}

		 public boolean isInteger(String s) {
		      boolean isValidInteger = false;
		      try
		      {
		         Integer.parseInt(s);

		         // s is a valid integer

		         isValidInteger = true;
		      }
		      catch (NumberFormatException ex)
		      {
		         betText = JOptionPane.showInputDialog("Enter a valid number please ");
		      }

		      return isValidInteger;
   }

		protected void clear()
		{
			draw.setEnabled(false);
			stand.setEnabled(false);
			bet.setEnabled(true);
			dealerScore.setVisible(false);
			statusLabel.setVisible(false);
			canvas.repaint();
			size = 0;
			daemon.xDealer = 30;
			daemon.xPlayer = 30;
			daemon.dealerScore = 0;
			daemon.playerScore = 0;
			for(int j=0; j<52;j++)
			{
				cards.usedCards[j] = 0;
			}


		}


	}
}