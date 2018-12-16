/**
 * 
 * @author Rams
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class Board extends JPanel
{	
	static boolean is_your_turn = false;

	static int totalbox = 12;
	static MButton[] box = new MButton[144];

	static int sameBox = 4;
	static int satisfiedBoxs[] = new int[4];
	static int belong_chk = 1;

	public Board() {
		//super(new FlowLayout(FlowLayout.CENTER,0,0));
		super();
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		for (int i=0; i < totalbox; i++ )
		{
			//System.out.println("\n\n");
			for (int j=0; j < totalbox; j++ )
			{
				int pos = i * totalbox + j;
				int y = totalbox * MButton.width - i * MButton.width;
				int x = totalbox * MButton.height - j * MButton.height;
				//System.out.println("Pos: "+pos+" (X, Y): ("+x+","+y);
				box[pos] = new MButton(pos,i,j);
				box[pos].setPos(x-23,y-23);
				this.add(box[pos]);
				//set Position also here
			}
		}
	}

	//Create 16x16 MButton

	//Static Function to check the eligibility of a Button to set Icon.
	static boolean checkEligibility(int index) {
		if (box[index].iconset == true)
		{
			return false;
		}
		if (index < totalbox  )
		{
			return true;
		}
		else
		{
			if (box[index-totalbox].iconset == true)
			{
				return true;
			}
			return false;
		}
	}

	static int[] getBoxes(int index, int[] xAxis, int[] yAxis, int i, int j) {
		
		//get Box Position
		int[] sboxes = new int[sameBox];
		sboxes[0] = index;

		int m = box[index].m + xAxis[i];
		int n = box[index].n + yAxis[i];

		int k = 1;

		for ( ; k < sameBox; k++)
		{
			int nextBox = m * totalbox + n;

			if (box[nextBox].belongs != belong_chk)
			{
				break;
			}

			sboxes[k] = nextBox;

			m = m + xAxis[i];
			n = n + yAxis[i];
		}

		if (j != -1)
		{
			m = box[index].m + xAxis[j];
			n = box[index].n + yAxis[j];
			for ( ; k < sameBox; k++)
			{
				int nextBox = m * totalbox + n;

				if (box[nextBox].belongs != belong_chk)
				{
					break;
				}

				sboxes[k] = nextBox;

				m = m + xAxis[j];
				n = n + yAxis[j];
			}
		}

		return sboxes;

	}
	
	//static function to check the same type of boxes
	static int getNumberOFBoxes(int index, int x, int y) {
		//get Box Position
		int count = 0;
		int m = box[index].m+x;
		int n = box[index].n+y;
		
		while(true)
		{
			if (m > -1 && n > -1 && m < totalbox && n < totalbox)
			{
				int nextBox = m * totalbox + n;
				if (box[nextBox].belongs != belong_chk)
				{
					break;
				}
				count++;
			}
			else
			{
				break;
			}
			m = m + x;
			n = n + y;
		}
		
		return count;

	}

	//Static Function to check the game done
	static boolean isGameDone(int index) {
	/*
		0(1, 1)		N/A			1(1,-1)
		2(0, 1)		(X)			3(0, -1)
		4(-1, 1)	5(-1,0)		6(-1,-1)
	*/	
		boolean isgamedone = false;
		int xAxis[] = {1, 1, 0, 0, -1, -1, -1};
		int yAxis[] = {1, -1, 1, -1, 1, 0, -1};
		int count[] = {0, 0, 0, 0, 0, 0, 0};

		for (int i=0; i < xAxis.length; i++)
		{
			count[i] = getNumberOFBoxes(index, xAxis[i], yAxis[i]);
			if (count[i] >= sameBox - 1 )
			{
				satisfiedBoxs = getBoxes(index, xAxis, yAxis, i, -1);
				isgamedone = true;
				break;
			}
		}

		if (isgamedone)
		{
			return true;
		}

		// 0 X 6 : Cross
		if (count[0] + count[6] >= sameBox - 1)
		{
			satisfiedBoxs = getBoxes(index, xAxis, yAxis, 0, 6);
			return true;
		}

		// 4 X 1 : Cross
		if (count[4] + count[1] >= sameBox - 1)
		{
			satisfiedBoxs = getBoxes(index, xAxis, yAxis, 4, 1);
			return true;
		}

		// 2 X 3 : Width
		if (count[2] + count[3] >= sameBox - 1)
		{
			satisfiedBoxs = getBoxes(index, xAxis, yAxis, 2, 3);
			return true;
		}
		
		return isgamedone;
	}

	//Static Function to reset the game
	static void resetGame() {
		
		MButton.recent = -1;

		for (int i=0; i < totalbox; i++ )
		{
			for (int j=0; j < totalbox; j++ )
			{
				int pos = i * totalbox + j;
				box[pos].belongs = 0;
				box[pos].setIcon2();
			}
		}
	}

}
