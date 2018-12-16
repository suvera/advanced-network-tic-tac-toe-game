/**
 * Creates server socket, listens to connections and
 * runs server thread for further communication 
 * for each new connection.
 * 
 * @author Rams
 *
 */

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;


public class Server implements Runnable,Constants
{
	private ServerSocket serverSocket;
	Socket clientSocket = null;
	private BufferedReader in = null;
	int serverPort;
	Command cmd;
	Alert alert;
	
	public Server(Command g,int port)
	{
		this.cmd = g;
		this.alert = new Alert();
		if (port <= 0)
		{
			this.serverPort = Constants.port;
		}
		else
		{
			this.serverPort = port;
		}
		
	}
	
	/**
	 * Server's loop. It accepts new connections and creates
	 * server thread for new communication for each connection.
	 */
	public void run()
	{
		Socket sock = null;
		try 
		{
			serverSocket = new ServerSocket(this.serverPort);
			System.err.println("Server Socket Started. Port:"+ this.serverPort);
		} catch(Exception e) {
			this.alert.error("Could not listen on port: "+Constants.port,"Error");
			System.exit(1);
		}

        try 
		{
            clientSocket = serverSocket.accept();
            System.err.println("Server Started/Accepted. Port:"+ this.serverPort);
        } catch (Exception e) {
            this.alert.error("Accept failed.","Error");
            System.exit(1);
        }
		
		try 
		{
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (Exception e) {
            //this.alert.error("BufferedReader failed.","Error");
            //System.exit(1);
			System.err.println(""+e);
        }
		String inputLine;
		while(true) 
		{
			try 
			{
				if((inputLine = in.readLine()) != null) {
					cmd.processCommand(inputLine);
					cmd.doCommand();
					inputLine = null;
				}
			} catch(Exception e) {
				this.alert.error("Server Disconnected","Disconnected");
				System.exit(1);
				//System.err.println(""+e);
			}
		}
	} // Function ends

	void stopServer() {
		try
		{
			in.close();
			clientSocket.close();
			serverSocket.close();
		} catch (Exception e) {
			System.err.println("Server Cls:"+e);
		}
	}
}
