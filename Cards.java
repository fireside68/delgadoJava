/*	Name:			Cedric Johnson
	Date:			07 April 2016
	Program Name:	Cards.java
	Class:			CMIN 257
	Purpose:		Cards class of Blackjack program
*/

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;

public class Cards
{

	BufferedImage [] buffImage = new BufferedImage[52];
	int [] usedCards = new int[52];
	Random rand = new Random();
	int cardValue;
	Boolean used;

		public Cards()
		{

			for(int j=0; j<52;j++)
			{
				usedCards[j] = 0;
			}

		try
		{
			for(int i=0;i<52;i++)
			{

				buffImage[i] = ImageIO.read(new File(Integer.toString((i+1))+".gif"));
			}
		}
			catch (IOException io)
			{
				JOptionPane.showMessageDialog(null,"Error" ,"Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		public ImageIcon getCard()
		{
			//randomize
			int count = 0;
			int x = rand.nextInt(51) + 1;
			used = beenUsed(usedCards, x);
			while(used)
			{
				x = rand.nextInt(51) + 1;
			}
			cardValue = getCardValue(x);
			ImageIcon imahj = new ImageIcon(buffImage[x]);
			usedCards[count] = x;
			count++;

			return imahj;
		}

		// returns the face down card for the dealer hand
		public ImageIcon cardBack()
		{
			ImageIcon back = new ImageIcon("back_1.jpg");
			return back;

		}

		public int getCardValue(int x)
		{
			int rtrn;
			x++;

			if((x % 13) == 0)
			{
				rtrn = 10;
			}
			else if((x % 13) > 9)
			{
				rtrn = 10;
			}
			else
				if((x % 13) == 1)
				{
					rtrn = 1;
				}
			else
				rtrn = (x % 13);

			return rtrn;		}

		public Boolean beenUsed(int[] uc, int x)
		{
			Boolean hey = false;

			for(int d = 0; d < 52; d++)
			{
				if(uc[d] == x)
				{
					hey = true;
				}

			}

			return hey;

		}
	}

