import javax.swing.ImageIcon;


public class Computer {
	//Game.server2
	//Game.client2
	
	int best_box;
	static int recent = -1;
	
	public Computer() {
	}
	
	public int get_best_box_index(int index) {
		//this.best_box = index + 12;
		AI ai = new AI();
		
		this.best_box = ai.getNextBestBox(index);
		System.out.println("AI Best Box:"+this.best_box);
		
		return this.best_box;
	}
	
	public void actionPerformed(int index) {
		Board.belong_chk = 2;
		this.get_best_box_index(index);
				
		if (Board.is_your_turn == false)
		{
			if (Board.isGameDone(this.best_box))
			{
				new Alert().plain("Losser!!! Computer won the Game","Loss");
				Board.box[this.best_box].showDone();
				Board.resetGame();
				return;
				//System.exit(1);
			} else {
				//System.out.println("Best Box:"+this.best_box);
				
				Board.box[this.best_box].belongs = 2;
				Board.box[this.best_box].setIcon2();
			}
		}
		Board.belong_chk = 1;
		Board.is_your_turn = true;
		Status.status1.setText("<html><font color='#FF0000'>Your Turn...</font></html>");
	}
		
}
