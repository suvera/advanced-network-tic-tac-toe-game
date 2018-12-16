/*
*
*	@author: Rams
*   @file: Game.java
*
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.InetAddress;

class Game extends JFrame implements Constants
{
	public Command cmd;
	public Board board;
	public static Computer computer;
	public Status status;
	public static Server server, server2;
	public static Client client, client2;
	public static int serverPort;
	public static int clientPort;
	public static String clientIP;
	public static Timer timer;
	public static Game frame;
	public static int game_type;

	// Main Method
	public static void main(String args[]) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createAndShowGUI();
				}
			});
	}

	public Game(String title) {
		super(title);
	}
	
	// Creates and shows GUI
	private static void createAndShowGUI()
	{
		frame = new Game("A Game - By RAMS");
		//JFrame.setDefaultLookAndFeelDecorated(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
		//Create and set up the window.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLayout(new BorderLayout()); //new GridLayout(4,4));
		frame.setResizable(false);
		frame.setIconImage(createImageIcon("logo.jpg").getImage());

        frame.setJMenuBar(new MMenuBar());
		frame.board = new Board();
		frame.status = new Status();
		frame.status.add(new Chat(), BorderLayout.PAGE_END);
		frame.getContentPane().add(frame.board,BorderLayout.CENTER);
		frame.getContentPane().add(frame.status,BorderLayout.PAGE_END);
		
		//Display the window.
		frame.setLocation(screenSize.width/2-200, screenSize.height/2-200);
		frame.setSize(Board.totalbox*MButton.width+10, Board.totalbox*MButton.height+170); // Or frame.pack();
		//frame.pack();
		frame.setVisible(true);

		// Show Dialog that gets IP Address
		MDialog mdialog = new MDialog(frame);
//System.out.println(mdialog.getClientPort());
		game_type = mdialog.getGameType();
		frame.cmd = new Command();
		
		if(game_type == 0) { //Game Type is Vs. Computer, so start AI instance.
			//new Alert().info("Vs. Computer.", "Vs. COmputer");
			//System.exit(0);
			computer = new Computer();
			
			Board.is_your_turn = true;
			Status.status1.setText("<html><font color='#FF0000'>Your Turn...</font></html>");
		} else { //Game Type is Vs. Human, so start Server and Client instances
			serverPort = mdialog.getServerPort();
			clientPort = mdialog.getClientPort();
			clientIP = mdialog.getIP();
			
			client = new Client(clientIP, clientPort);
			new Reminder();
		
			try
			{
				server = new Server(frame.cmd, serverPort);
				new Thread(server).start();
			} catch (Exception e) {
				System.err.println("Server:"+e);
				server.stopServer();
			}
		}
		
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Game.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
