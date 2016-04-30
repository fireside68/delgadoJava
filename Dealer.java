/*	Name:			Cedric Johnson
	Date:			07 April 2016
	Program Name:	Dealer.java
	Class:			CMIN 257
	Purpose:		Dealer class of Blackjack program
*/

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class Dealer
{
	Table table;
	Player player1;
	Cards deck;
	int xDealer, yDealer, xPlayer, yPlayer;
	int dealerScore = 0;
	int playerScore = 0;



	public Dealer(Table table)
	{

		this.table=table;
		player1 = new Player();
		deck = new Cards();
		table.setDealer(this);
		xDealer = xPlayer = 30;
		beginGame();

	}

	public void setPlayer(Player player1)
	{
		this.player1 = player1;
	}


	public void beginGame()
		{
			dealPlayer();
			dealDealer();
			dealPlayer();
			dealDealerCardBack();
			table.bankLabel.setText(Integer.toString(table.bankTotal));

	}

	// method to dispense cards to dealer
	public void dealDealer()
		{
			ImageIcon card;
			card = deck.getCard();
			table.setCard(card, xDealer, 100);
			xDealer += 70;
			dealerScore = dealerScore + deck.cardValue;
			table.dealerScore.setText(Integer.toString(dealerScore));


		}// end dealDealer


	// method to dispense cards to player
		public void dealPlayer()
		{
			ImageIcon card;
			card = deck.getCard();
			table.setCard(card, xPlayer, 300);
			xPlayer += 70;
			playerScore = playerScore + deck.cardValue;
			table.playerScore.setText(Integer.toString(playerScore));
		}// end dealPlayer

		// method to dispense face down card to dealer
		public void dealDealerCardBack()
		{
			ImageIcon card;
			card = deck.cardBack();
			table.setCard(card, xDealer, 100);
		}// end dealDealerCardBack

		// method to control player game play
		public void playerDealOut()
		{
			dealPlayer();
			if(playerScore > 21)
			{
				endGame(dealerScore, playerScore);
			}

		}// end playerDealOut

		// method to deal dealer after player stand
		public void dealerDealOut()
		{
			// user can now see dealer score
			table.dealerScore.setVisible(true);

			dealDealer();
			// keeps dealer from hitting on score higher than 17
			if((dealerScore > 17) && (dealerScore < 22))
			{
				endGame(dealerScore, playerScore);
			}
			else
			// ensures regular dealer play
			if(dealerScore <= 17)
			{
				do
				{
					dealDealer();
					if((dealerScore > 17) && (dealerScore < 22))
					{
						endGame(dealerScore, playerScore);
					}
					if(dealerScore >=22)
					{
						endGame(dealerScore, playerScore);
					}
				}while(dealerScore <= 17);
			}

		}

		public void endGame(int dlr, int plyr)
		{
			/*
				This method performs functions relative to end of game; if a bust occurs, if the player
				wins, etc.
			*/

			// disable game function buttons
			table.draw.setEnabled(false);
			table.stand.setEnabled(false);

			// reenable New Game button
			table.beginGame.setEnabled(true);

			// ensure either score is within valid range
			if((dlr <= 21) && (plyr <= 21))
			{
				// if dealer wins
				if(dlr > plyr)
				{
					table.statusLabel.setVisible(true);
					table.statusLabel.setText("YOU LOSE!!!");
					table.bankTotal = table.bankTotal - table.totalBet;
					table.bankLabel.setText(Integer.toString(table.bankTotal));
					table.totalField.setText("");
					table.betNumber = 0;
					table.totalBet = 0;
					checkBank(table.bankTotal);
				}// end dealer win

				// if player wins
				if(plyr > dlr)
				{
					table.statusLabel.setVisible(true);
					table.statusLabel.setText("YOU WIN!!");
					table.bankTotal = table.bankTotal + table.totalBet;
					table.bankLabel.setText(Integer.toString(table.bankTotal));
					table.totalField.setText("");
					table.betNumber = 0;
					table.totalBet = 0;
					checkBank(table.bankTotal);
				}// end player win

				// if push
				if(plyr == dlr)
				{
					table.statusLabel.setVisible(true);
					table.statusLabel.setText("PUSH. YOU LOSE!!!");
					table.bankTotal = table.bankTotal - table.totalBet;
					table.bankLabel.setText(Integer.toString(table.bankTotal));
					table.totalField.setText("");
					table.betNumber = 0;
					table.totalBet = 0;
					checkBank(table.bankTotal);
				}// end push
			}// end valid scores

			// handle scores above 21
			else
			{
				// dealer bust
				if(dlr > 21)
				{
					table.statusLabel.setVisible(true);
					table.statusLabel.setText("Dealer bust. YOU WIN!!!");
					table.bankTotal = table.bankTotal + table.totalBet;
					table.bankLabel.setText(Integer.toString(table.bankTotal));
					table.totalField.setText("");
					table.betNumber = 0;
					table.totalBet = 0;
					checkBank(table.bankTotal);
				}// end dealer bust

				if(plyr > 21)
				{
					// player bust
					table.statusLabel.setVisible(true);
					table.statusLabel.setText("Player bust. YOU LOSE!!!");
					table.bankTotal = table.bankTotal - table.totalBet;
					table.bankLabel.setText(Integer.toString(table.bankTotal));
					table.totalField.setText("");
					table.betNumber = 0;
					table.totalBet = 0;
					checkBank(table.bankTotal);

				}// end player bust
			}// end dealing with bust
		}// end endGame

		/*
			This method will check the bank after each round.  If the bank is ever at zero, the game will
			automatically end and thank the user for playing.
		*/
		public void checkBank(int over)
		{
			if(over == 0)
			{
				JOptionPane.showMessageDialog(null,"Your bank is empty.  Thanks for playing!", "BANK EMPTY", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}

		}

}
