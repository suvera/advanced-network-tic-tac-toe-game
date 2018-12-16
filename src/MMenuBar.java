/**
 * 
 * @author Rams
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class MMenuBar extends JMenuBar implements ActionListener
{
	JMenuItem mi_rand, mi_exit, mi_help, mi_author;
	JRadioButtonMenuItem mi_decorations[] = new JRadioButtonMenuItem[10];
	String lookandfeel[] = new String[10];
	
	public MMenuBar() {
		//Build the first menu.
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription("File Menu");
        
		ButtonGroup group = new ButtonGroup();

		UIManager.LookAndFeelInfo[] laf =  UIManager.getInstalledLookAndFeels();
		for (int i = 0; i < laf.length; i++) {
		    mi_decorations[i] = new JRadioButtonMenuItem(laf[i].getName());
			lookandfeel[i] = laf[i].getClassName();
			if(UIManager.getLookAndFeel().getName() == laf[i].getName()) 
				mi_decorations[i].setSelected(true);
			//mi_decorations[i].setMnemonic(KeyEvent.VK_D);
			group.add(mi_decorations[i]);
			mi_decorations[i].addActionListener(this);
			menu.add(mi_decorations[i]);
        }

        menu.addSeparator();

		mi_exit = new JMenuItem("Exit", KeyEvent.VK_X);
        mi_exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
        mi_exit.getAccessibleContext().setAccessibleDescription("Exit Menu Item");
        mi_exit.addActionListener(this);
        menu.add(mi_exit);

		//Build second menu in the menu bar.
        JMenu menu_help = new JMenu("Help");
        menu_help.setMnemonic(KeyEvent.VK_N);
        menu_help.getAccessibleContext().setAccessibleDescription("Help Menu");

		mi_help = new JMenuItem("How To Play", KeyEvent.VK_H);
        mi_help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
        mi_help.getAccessibleContext().setAccessibleDescription("How To Play Menu Item");
        mi_help.addActionListener(this);
        menu_help.add(mi_help);

		mi_author = new JMenuItem("About Author", KeyEvent.VK_T);
        mi_author.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK));
        mi_author.getAccessibleContext().setAccessibleDescription("About Author Menu Item");
        mi_author.addActionListener(this);
        menu_help.add(mi_author);
		
		this.add(menu);
		this.add(menu_help);
	}

	public void actionPerformed(ActionEvent ae)
	{
		String source = getClassName(ae.getSource());
		//System.out.println(" Clicked on "+getClassName(ae.getSource()));
		if(ae.getSource() == mi_exit)
		{
			System.exit(0);
		}
		else if(ae.getSource() == mi_help)
		{
			//System.out.println("How To Play");
			JOptionPane.showMessageDialog(Game.frame,"Please make sure 4 of your boxes on one line (cross also)","Help",JOptionPane.INFORMATION_MESSAGE,null);
		}
		else if(ae.getSource() == mi_author)
		{
			//System.out.println("How To Play");
			JLabel auth_msg = new JLabel("<html><font color=red><em>N Ramanarayana</em></font><br>Software Engineer<br>Cybage Software Ltd.<br>Pune, India<br><br>Mail: <a href='mailto:rana_912@yahoo.com'><i>rana_912@yahoo.com</i></a><br>Phone: <i>+91-9890917155</i></html>");
			JOptionPane.showMessageDialog(Game.frame,auth_msg,"About Author",JOptionPane.INFORMATION_MESSAGE,null);
		}
		else if(source.equals("JRadioButtonMenuItem"))
		{
			//System.out.println(" Clicked on Look And Feel");
			for (int i = 0; i < mi_decorations.length; i++) {
				if(ae.getSource() == mi_decorations[i])
				{
					//System.out.println(" Selected :"+lookandfeel[i]);
					try {
					   UIManager.setLookAndFeel(lookandfeel[i]);
					   SwingUtilities.updateComponentTreeUI(Game.frame);
					   // call myFrame.pack() 
					   // to resize frame for laf
					 } catch (IllegalAccessException e) {
					   // insert code to handle this exception
					   System.out.println("IllegalAccessException: "+e);
					 } catch (UnsupportedLookAndFeelException e) {
					   // insert code to handle this exception
					   System.out.println("UnsupportedLookAndFeelException: "+e);
					 } catch (InstantiationException e) {
					   // insert code to handle this exception
					   System.out.println("InstantiationException: "+e);
					 } catch (ClassNotFoundException e) {
					   // insert code to handle this exception
					   System.out.println("ClassNotFoundException: "+e);
					 }
				}
			}
		}
		else
		{
			
		}  //else
	}  // action performed ends.

	protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }
}
