/*
*
*	@author: Rams
*
*/

public class AI
{

	int[] accessibleBoxes;
	static int totalbox = 12;
	static int sameBox = 4;

	int[] prevAccessibleBoxes;
	
	//7 Directions
	int xAxis[] = {1, 1, 0, 0, -1, -1, -1};
	int yAxis[] = {1, -1, 1, -1, 1, 0, -1};
	
	//Box count all sides, Box Number X Player X 7 Possibilities
	int count[][][];
	
	//   Box Number X Player X 7 Possibilities X Box Indexes
	int sameTypeBoxs[][][][];

	int playerCurrentIndex;
	int bestBox;
	
	int human;
	int computer;

	//Constructor
	public AI() {

		//
		this.accessibleBoxes = new int[totalbox];

		this.prevAccessibleBoxes = new int[totalbox];

		this.playerCurrentIndex = -1;

		this.bestBox = -1;

		this.sameTypeBoxs = new int[totalbox*totalbox][3][7][sameBox];

		this.count = new int[totalbox*totalbox][3][7];
		
		this.human = 1;
		this.computer = 2;
	}
	
	//
	void getAccessibleBoxes() {

		this.prevAccessibleBoxes = this.accessibleBoxes;

		for (int i=0; i < totalbox; i++)
		{
			this.accessibleBoxes[i] = this.getNextAvailableTopBox(i);
			System.out.print(this.accessibleBoxes[i]+",");
		}
		System.out.print("\n");
	}
	
	//
	int getTopBox(int index) {

		if (index < totalbox * totalbox - totalbox)
		{
			return index+totalbox;
		}
		return -1;
	}
	
	//
	int getNextAvailableTopBox(int index) {

		if (Board.box[index].belongs == 0)
		{
			return index;
		}
		else
		{
			int nextTopBox = this.getTopBox(index);

			while (nextTopBox != -1 && Board.box[nextTopBox].belongs != 0) {

				nextTopBox = this.getTopBox(nextTopBox);
			}

			return nextTopBox;
		}
	}

	//set User selected Box
	void setCurrentBox(int index) {

		this.playerCurrentIndex = index;
	}

	//get best Box
	int getBestBox() {

		return this.bestBox;

	}
	
	//get next best Box
	//input: user selected box
	int getNextBestBox(int index) {

		this.setCurrentBox(index);

		this.getAccessibleBoxes();

		this.getAllPlayerPossibilities(index);

		this.processPossibilities(index);
		
		if(this.bestBox == -1) {
			for (int i=0; i < this.accessibleBoxes.length; i++)
			{
				if (this.accessibleBoxes[i] != -1)
				{
					return this.accessibleBoxes[i];
				}
			}
		}
				
		if(this.bestBox == -1) {
			System.err.println("Error: Unable to find best box From AI");
		}
		return this.bestBox;
	}

	//get Process Players Possibilities. All side counts from one box 
	void processPossibilities(int index) {
		//int[][] temp = new int[this.accessibleBoxes.length][this.xAxis.length];
		
		int player = this.human;
		try {
		for (int k=sameBox - 1; k > 0; k--)
		{
			for (int i=0; i < this.accessibleBoxes.length; i++)
			{
				if (this.accessibleBoxes[i] != -1)
				{
					int idx = this.accessibleBoxes[i];
					System.out.print("For Box:"+idx+" = ");
					
					for (int j=0; j < this.xAxis.length; j++)
					{
						System.out.print(this.count[idx][player][j]);
						if(this.count[idx][player][j] >= k)
						{
							this.bestBox = idx;
							return;
						}
						
						if(this.count[idx][this.computer][j] >= k)
						{
							this.bestBox = idx;
							return;
						}
						
						if (this.count[idx][player][0] + this.count[idx][player][6] >= k 
							|| 
							this.count[idx][this.computer][0] + this.count[idx][this.computer][6] >= k
							||
							this.count[idx][player][4] + this.count[idx][player][1] >= k 
							|| 
							this.count[idx][this.computer][4] + this.count[idx][this.computer][1] >= k
							||
							this.count[idx][player][2] + this.count[idx][player][3] >= k 
							|| 
							this.count[idx][this.computer][2] + this.count[idx][this.computer][3] >= k
							)
						{
							this.bestBox = idx;
							return;
						}
					}
					System.out.println("");
				}
			}
		}
		} catch(Exception e) {}
	}

	//get All Players Possibilities. All side counts from one box 
	void getAllPlayerPossibilities(int index) {

		for (int i=0; i < this.accessibleBoxes.length; i++)
		{
			if (this.accessibleBoxes[i] != -1)
			{
				this.getPlayerPossibilities(this.accessibleBoxes[i], 1);
				this.getPlayerPossibilities(this.accessibleBoxes[i], 2);
			}
		}
	}

	//get Player all Possibilities. All side counts from one box 
	void getPlayerPossibilities(int index, int player) {

		for (int i=0; i < this.xAxis.length; i++)
		{
			this.count[index][player][i] = this.getNumberOFSameBoxes(index, i, player);
			this.sameTypeBoxs[index][player][i] = this.getBoxes(index, i, player);
		}
	}

	//function to get the same type of boxes
	int[] getBoxes(int index, int i, int player) {
		
		//get Box Position
		int[] sboxes = new int[sameBox];

		int m = Board.box[index].m + this.xAxis[i];
		int n = Board.box[index].n + this.yAxis[i];

		int k = 1;

		for ( ; k < sameBox; k++)
		{
			int nextBox = m * totalbox + n;
			
			try {
				if (nextBox >= totalbox * totalbox || Board.box[nextBox].belongs != player)
					break;
			} catch(Exception e) {
			}

			sboxes[k] = nextBox;

			m = m + this.xAxis[i];
			n = n + this.yAxis[i];
		}

		return sboxes;

	}
	
	//function to get the number of same type boxes
	public int getNumberOFSameBoxes(int index, int i, int player) {
		//get Box Position
		int m = Board.box[index].m + this.xAxis[i];
		int n = Board.box[index].n + this.yAxis[i];
		int count = 0;

		while(true)
		{
			if (m > -1 && n > -1 && m < totalbox && n < totalbox)
			{
				int nextBox = m * totalbox + n;
				if (Board.box[nextBox].belongs != player)
				{
					break;
				}
				count++;
			}
			else
			{
				break;
			}
			m = m + this.xAxis[i];
			n = n + this.yAxis[i];
		}
		
		return count;

	}


	
}

