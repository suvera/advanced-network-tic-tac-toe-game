/**
 * 
 * @author Rams
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

class Chat extends JPanel implements ActionListener
{	
	static JEditorPane chatpane;
	private JButton send;
	private JTextField text;
	static SimpleAttributeSet styles;

	public Chat() {
		super(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		chatpane = new JEditorPane("text/html","<html><b><font color='green'>Chat Window</font></b><br></html>");
		chatpane.setContentType("text/html");
		this.send = new JButton("Send");
		this.text = new JTextField();
		//getRootPane().setDefaultButton(this.send);

		chatpane.setEditable(false);
		JScrollPane editorScrollPane = new JScrollPane(chatpane);
		editorScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		editorScrollPane.setPreferredSize(new Dimension(Board.totalbox*MButton.width+10, 60));
		editorScrollPane.setMinimumSize(new Dimension(10, 10));

		this.add(editorScrollPane, BorderLayout.PAGE_START);
		this.add(this.text, BorderLayout.CENTER);
		this.add(this.send, BorderLayout.LINE_END);
		this.send.addActionListener(this);

		styles = new SimpleAttributeSet();
	}

	public void actionPerformed(ActionEvent e) {
        String msg = this.text.getText();
		if (msg != null && msg.length() > 0)
		{
			if (Game.client.kkSocket != null)
			{
				appendText("<You>: "+msg+"\n",1);
				Game.client.send("MSG:"+msg);
			}			
		}
		this.text.setText("");
    }

	public static void appendText(String DAT, int who)	// method to append text in JEditorPane
	{

		try {
			if (who == 2 )
			{
				StyleConstants.setFontFamily(styles, "courier new");
				StyleConstants.setForeground(styles, new Color(0,0,255));
				StyleConstants.setItalic(styles, true);
			} else
			{
				StyleConstants.setFontFamily(styles, "courier new");
				StyleConstants.setForeground(styles, new Color(0,0,0));
				StyleConstants.setItalic(styles, false);
			}
			chatpane.getDocument().insertString(chatpane.getDocument().getLength(), DAT, styles);
			chatpane.setCaretPosition(chatpane.getDocument().getLength());
		}
		catch (BadLocationException ex) {}
	}


}
