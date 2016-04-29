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

	public void dealDealer()
		{
			ImageIcon card;
			card = deck.getCard();
			table.setCard(card, xDealer, 100);
			xDealer += 70;
			dealerScore = dealerScore + deck.cardValue;
			table.dealerScore.setText(Integer.toString(dealerScore));


		}

		public void dealPlayer()
		{
			ImageIcon card;
			card = deck.getCard();
			table.setCard(card, xPlayer, 300);
			xPlayer += 70;
			playerScore = playerScore + deck.cardValue;
			table.playerScore.setText(Integer.toString(playerScore));
		}

		public void dealDealerCardBack()
		{
			ImageIcon card;
			card = deck.cardBack();
			table.setCard(card, xDealer, 100);
		}

		public void playerDealOut()
		{
			dealPlayer();
			if(playerScore > 21)
			{
				endGame(dealerScore, playerScore);
			}

		}

		public void dealerDealOut()
		{
			dealDealer();
			table.dealerScore.setVisible(true);
			if((dealerScore > 17) && (dealerScore < 22))
			{
				endGame(dealerScore, playerScore);
			}
			else
			if(dealerScore < 17)
			{
				do
				{
					dealDealer();
					if((dealerScore > 17) && (dealerScore < 22))
					{
						endGame(dealerScore, playerScore);
					}
				}while(dealerScore <= 17);
			}

		}

		public void endGame(int dlr, int plyr)
		{
			table.draw.setEnabled(false);
			table.stand.setEnabled(false);
			table.beginGame.setEnabled(true);
			if((dlr <= 21) && (plyr <= 21))
			{
				if(dlr > plyr)
				{
					table.statusLabel.setVisible(true);
					table.statusLabel.setIcon(table.loseImage);
					table.bankTotal = table.bankTotal - table.totalBet;
					table.bankLabel.setText(Integer.toString(table.bankTotal));
					table.totalField.setText("");
					table.betNumber = 0;
					table.totalBet = 0;
				}
				if(plyr > dlr)
				{
					table.statusLabel.setVisible(true);
					table.statusLabel.setIcon(table.winImage);
					table.bankTotal = table.bankTotal + table.totalBet;
					table.bankLabel.setText(Integer.toString(table.bankTotal));
					table.totalField.setText("");
					table.betNumber = 0;
					table.totalBet = 0;
				}
				if(plyr == dlr)
				{
					table.statusLabel.setVisible(true);
					table.statusLabel.setIcon(table.loseImage);
					table.bankTotal = table.bankTotal - table.totalBet;
					table.bankLabel.setText(Integer.toString(table.bankTotal));
					table.totalField.setText("");
					table.betNumber = 0;
					table.totalBet = 0;
				}
			}

			else
			{
				if(dlr > 21)
				{
					table.statusLabel.setVisible(true);
					table.statusLabel.setIcon(table.winImage);
					table.bankTotal = table.bankTotal + table.totalBet;
					table.bankLabel.setText(Integer.toString(table.bankTotal));
					table.totalField.setText("");
					table.betNumber = 0;
					table.totalBet = 0;
				}

				if(plyr > 21)
				{
					table.statusLabel.setVisible(true);
					table.statusLabel.setIcon(table.loseImage);
					table.bankTotal = table.bankTotal - table.totalBet;
					table.bankLabel.setText(Integer.toString(table.bankTotal));
					table.totalField.setText("");
					table.betNumber = 0;
					table.totalBet = 0;

				}
			}
		}

}
