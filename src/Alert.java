/*
*
*	Used to show alert messages
*	@author: Rams
*   @file: Alert.java
*
*/

import javax.swing.JOptionPane;
import javax.swing.JFrame;

class Alert extends JOptionPane 
{
	JFrame frame;

	Alert(JFrame f) {
		this.frame = f;
	}

	Alert() {
		this.frame = null;
	}

	void error(String msg, String title) {
		this.showMessageDialog(this.frame,msg,title,JOptionPane.ERROR_MESSAGE);
	}

	void warning(String msg, String title) {
		this.showMessageDialog(this.frame,msg,title,JOptionPane.WARNING_MESSAGE);
	}

	void plain(String msg, String title) {
		this.showMessageDialog(this.frame,msg,title,JOptionPane.PLAIN_MESSAGE);
	}

	void info(String msg, String title) {
		this.showMessageDialog(this.frame,msg,title,JOptionPane.INFORMATION_MESSAGE);
	}

	void question(String msg, String title) {
		this.showMessageDialog(this.frame,msg,title,JOptionPane.QUESTION_MESSAGE);
	}
}
