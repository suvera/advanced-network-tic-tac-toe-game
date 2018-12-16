/**
 * 
 * @author Rams
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

class Status extends JPanel
{	
	static JLabel status1,status2;

	public Status() {
		super(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		//Status.status1.setText("");
		status1 = new JLabel("Welcome");
		status2 = new JLabel(" ");
		this.add(status1, BorderLayout.CENTER);
		this.add(status2, BorderLayout.LINE_END);
	}
}
