import java.util.Timer;
import java.util.TimerTask;
import java.net.*;

/**
 * Simple demo that uses java.util.Timer to schedule a task 
 * to execute once 5 seconds have passed.
 */

public class Reminder implements Constants {
    Timer timer;
	static int attempts = 0;

    public Reminder() {
        timer = new Timer();
        //timer.schedule(new RemindTask(), Constants.WAIT_SECONDS);
		timer.scheduleAtFixedRate(new RemindTask(),0,Constants.WAIT_SECONDS);
	}

    class RemindTask extends TimerTask {
        public void run() {
            System.out.format("Time's up!");
			try
			{ 
				if (Game.client.openSocket())
				{
					if (Game.client.openWriter())
					{
						Status.status2.setText("Connected.");
						Reminder.attempts = 0;
						String client_hash=null,server_hash=null;

						try
						{
							client_hash = Game.client.ip + Game.client.Clientport;

							InetAddress localHost = InetAddress.getLocalHost();
							//System.out.println(localHost.getHostName());
							String other = localHost.getHostAddress();
							server_hash = other + Game.serverPort;
						}
						catch (Exception e)
						{
						}

						if ( server_hash.compareTo(client_hash) >= 0 || Game.game_type == 0)
						{
							Board.is_your_turn = true;
							Status.status1.setText("<html><font color='#FF0000'>Your Turn...</font></html>");
						}  else {
							Status.status1.setText("Other player Turn...");
						}
						timer.cancel(); //Terminate the timer thread
					}
					else
					{
						Status.status1.setText("Re-Connecting... "+Constants.ClientIP);
						if (Reminder.attempts == 20)
						{
							timer.cancel(); //Terminate the timer thread
							System.exit(0);
						}
						Reminder.attempts++;
						Status.status2.setText(Reminder.attempts+" attempts");
					}
				}
				else
				{
					Status.status1.setText("Re-Connecting... "+ Game.client.ip);
					if (Reminder.attempts == 20)
					{
						timer.cancel(); //Terminate the timer thread
						System.exit(0);
					}
					Reminder.attempts++;
					Status.status2.setText(Reminder.attempts+" attempts");
				}
			}
			catch(Exception e) { System.out.println(e+" timer");} 
        }
    }

    //public static void main(String args[]) {
        //new Reminder(5);
        //System.out.format("Task scheduled.%n");
    //}
}