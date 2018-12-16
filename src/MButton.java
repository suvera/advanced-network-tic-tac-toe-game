/**
 * 
 * @author Rams
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

class  MButton extends JButton implements ActionListener
{
	static int width = 25;
	static int height = 25;
	boolean iconset;
	static int recent = -1;
	int belongs = 0;
	int mindex;
	int m,n;

	public MButton(int i,int m,int n) {
		super(createImageIcon("button.gif"));
		//super(""+i);
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.addActionListener(this);
		this.iconset = false;
		this.mindex = i;
		this.m = m;
		this.n = n;
	}

	void setPos(int x,int y) {
		this.setBounds(x,y,width,height);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if (Board.is_your_turn == true)
		{
			//do something here
			//Check for eligibility of this Button (from parent,call parent Static method(mindex)  )
			if (Board.checkEligibility(this.mindex))
			{
				//this.belongs = 1;
				this.belongs = 1;
				//setIcon
				this.setIcon2();
				
				if(recent != -1 && Game.game_type == 1) {
					Board.box[recent].setIcon(createImageIcon("logo2.gif")); 
					recent = -1;
				}
				
				//Check for GameDone
				if (Board.isGameDone(this.mindex))
				{
					//send info game done
					if(Game.game_type == 1)
						Game.client.send("DONE:"+this.m+","+this.n);
					
					this.showDone();
					new Alert().plain("You won the Game","Success");
					Board.resetGame();
					Board.is_your_turn = true;
					
					Status.status1.setText("<html><font color='#FF0000'>Your Turn...</font></html>");
					return;
					//System.exit(1);
				} else {
					//send Info to player2
					if(Game.game_type == 1)
						Game.client.send("MOV:"+this.m+","+this.n);
				}
				
				//unlock
				Board.is_your_turn = false;
				
				if(Game.game_type == 0) {
					Status.status1.setText("Computer's Turn...");
					Game.computer.actionPerformed(this.mindex);
				} else {
					Status.status1.setText("Other Player Turn...");
				}
			}
		}
		else
		{
			//set status 'not your turn' (set Parent Static JLabel2 = 'not your turn')
		}
	}

	public void setIcon2() {
		//System.out.println("Box:"+this.mindex+" Set Icon Type:"+this.belongs);
		
		this.iconset = true;
		if (this.belongs == 1)
		{
			this.setIcon(MButton.createImageIcon("logo1.gif"));
		}
		else if (this.belongs == 2)
		{
			this.setIcon(MButton.createImageIcon("logo21.gif"));
			recent = this.mindex;
		}
		else 
		{
			this.setIcon(MButton.createImageIcon("button.gif"));
			this.iconset = false;
		}
	}

	void showDone() {
		for (int i=0; i < Board.satisfiedBoxs.length; i++ )
		{
			Board.box[Board.satisfiedBoxs[i]].setIcon(MButton.createImageIcon("logo3.gif"));
		}
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MButton.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
