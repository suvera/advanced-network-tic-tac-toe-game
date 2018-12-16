/*
*
*	@author: Rams
*   @file: Command.java
*
*/
class Command 
{
	String type;
	int row;
	int col;
	String msg = null;

	void processCommand(String cmd) {
		String args[] = cmd.split(":");
		
		if(args.length >= 2 ) {
			this.type = args[0];
			if(this.type.equals("MOV")) {
				String mov[] = args[1].split(",");
				if(mov.length >= 2) {
					this.row = Integer.parseInt(mov[0]);
					this.col = Integer.parseInt(mov[1]);
				} else {
					this.row = -1;
					this.col = -1;
				}
			} else if(this.type.equals("OK")) {
				this.row = -1;
				this.col = -1;	
			} else if(this.type.equals("MSG")) {
				this.msg = args[1];
		    } else if(this.type.equals("DONE")) {
				String mov[] = args[1].split(",");
				if(mov.length >= 2) {
					this.row = Integer.parseInt(mov[0]);
					this.col = Integer.parseInt(mov[1]);
				} else {
					this.row = -1;
					this.col = -1;
				}
			} else {
				this.type = null;
				this.row = -1;
				this.col = -1;
			}
		} else {
			this.type = null;
			this.row = -1;
			this.col = -1;
		}
	} // processCommand Ends

	void doCommand() {
		if(this.type.equals("MOV")) 
		{	
			if (this.row >= 0 && this.col >= 0 && ( this.row * Board.totalbox + this.col ) < Board.totalbox * Board.totalbox  )
			{
				Board.box[this.row * Board.totalbox + this.col].belongs = 2;
				Board.box[this.row * Board.totalbox + this.col].setIcon2();
				Board.is_your_turn = true;
				Status.status1.setText("<html><font color='#FF0000'>Your Turn...</font></html>");

				Game.frame.setState(javax.swing.JFrame.NORMAL);
				Game.frame.setVisible(true);
				// Request input focus
				Game.frame.requestFocus();
			}
		} 
		else if(this.type.equals("DONE")) 
		{
			if (this.row >= 0 && this.col >= 0 && ( this.row * Board.totalbox + this.col ) < Board.totalbox * Board.totalbox  )
			{
				Board.box[this.row * Board.totalbox + this.col].belongs = 2;
				Board.box[this.row * Board.totalbox + this.col].setIcon2();
				//Board.is_your_turn = true;
				Board.box[this.row * Board.totalbox + this.col].showDone();
				new Alert().plain("You Loss the game. Other Player Won the Game","Loss");
				Board.resetGame();
				//System.exit(1);
			}
		}
		else if(this.type.equals("MSG")) 
		{
			if (this.msg != null && this.msg.length() > 0 )
			{
				Chat.appendText("<Other>: "+this.msg+"\n",2);

				Game.frame.setState(javax.swing.JFrame.NORMAL);
				Game.frame.setVisible(true);
				// Request input focus
				Game.frame.requestFocus();
			}
		}
		else if(this.type.equals("OK")) 
		{
		}else 
		{
		}
	}
}
