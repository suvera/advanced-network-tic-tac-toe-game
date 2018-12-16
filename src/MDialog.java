/*
*
* MDialog.java
* @author Rana
*/
import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.regex.*;

class MDialog extends JDialog implements ActionListener
{

    private String _ipaddress;
    private JLabel label1,label2,label3;
    private JTextField ip,serverPort,clientPort;
    private JButton ok;
    private JButton cancel;
    private Container container;
    private JRadioButton vs_computer;
    private JRadioButton vs_human;
    private int game_type;

    public MDialog(JFrame jframe)
    {
        super(jframe, "Select Game Type", true);
        _ipaddress = null;
        game_type = 0;
        initDialogBox(jframe);
    }

    private void initDialogBox(JFrame jframe)
    {
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        container = getContentPane();
        container.setLayout(null);
        
        int x = 10;
        int y = 10;
        vs_computer = new JRadioButton("Vs. Computer");
        vs_computer.setMnemonic(KeyEvent.VK_C);
        vs_computer.setActionCommand("vs_computer");
        vs_computer.setSelected(true);
        vs_computer.setBounds(x, y, 250, 20);
        vs_human = new JRadioButton("Vs. Human (Must connect to LAN)");
        vs_human.setMnemonic(KeyEvent.VK_H);
        vs_human.setActionCommand("vs_human");
        y = y+25;
        vs_human.setBounds(x, y, 250, 20);
        ButtonGroup group = new ButtonGroup();
        group.add(vs_computer);
        group.add(vs_human);

        
        y = y+25;
        label1 = new JLabel("IP Address:");
		label1.setBounds(x+30, y, 115, 20);
        ip = new JTextField();
        ip.setBounds(115, y, 100, 20);
        ip.setEnabled(false);
		
		label2 = new JLabel("Server Port:");
		y = y+20;
		label2.setBounds(x+30, y, 115, 20);
        serverPort = new JTextField("4876");
		serverPort.setBounds(115, y, 60, 20);
		serverPort.setEnabled(false);
		
		y = y+20;
		label3 = new JLabel("Client Port:");
		label3.setBounds(x+30, y, 115, 20);
        clientPort = new JTextField("4876");
		clientPort.setBounds(115, y, 60, 20);
		clientPort.setEnabled(false);
        ok = new JButton("OK");
        cancel = new JButton("Cancel");
        y = y+40;
		ok.setBounds(30, y, 70, 20);
        cancel.setBounds(110, y, 80, 20);
        
        container.add(vs_computer);
        container.add(vs_human);
        container.add(label1);
		container.add(label2);
		container.add(serverPort);
		container.add(label3);
		container.add(clientPort);
        container.add(ip);
        container.add(ok);
        container.add(cancel);
		
        vs_computer.addActionListener(this);
        vs_human.addActionListener(this);
        ok.addActionListener(this);
        cancel.addActionListener(this);

        //setResizable(false);
		setSize(240, 240);
        setLocationRelativeTo(jframe);
		//pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
    	if(actionevent.getSource() == vs_computer)
        {
    		ip.setEnabled(false);
    		serverPort.setEnabled(false);
    		clientPort.setEnabled(false);
    		game_type = 0;
    		return;
        }
    	if(actionevent.getSource() == vs_human)
        {
    		ip.setEnabled(true);
    		serverPort.setEnabled(true);
    		clientPort.setEnabled(true);
    		game_type = 1;
    		return;
        }
    	
        if( game_type == 1 && (actionevent.getSource() == ok || actionevent.getSource() == ip ))
        {
            _ipaddress = ip.getText();
            if(_ipaddress.length() == 0)
            {
		 	  
                JOptionPane.showMessageDialog(this, "Please enter IP", "Error", 2);
                return;
            }
			else
			{
				Pattern pattern;
				Matcher matcher;
				try
				{
					pattern = Pattern.compile("^([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})$");
					matcher = pattern.matcher(_ipaddress);
					if (_ipaddress.matches("^[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}$"))
					{
						 //JOptionPane.showMessageDialog(this, "Please enter Valid IP", "Error", 2);
		                 //return;
					}
				}
				catch (Exception e)	{}
			}
        } else if(actionevent.getSource() == cancel)
		{
			System.exit(0);
		}
        setVisible(false);
    }

    protected String getIP()
    {
        return _ipaddress;
    }

	protected int getServerPort()
    {
        return Integer.parseInt(serverPort.getText());
    }

	protected int getClientPort()
    {
        return Integer.parseInt(clientPort.getText());
    }
	protected int getGameType()
    {
        return game_type;
    }
}
