import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class scheduleTimer 
{
	Timer timer,timer1;
	private emailHelper email=new emailHelper();
	String timeInterval;
	String info;
	int count;
	int failed_report_ctr;
	NetworkCheck internet;
	int hours;
	public scheduleTimer(int mins,String info)
	{
		count=0;
		failed_report_ctr=0;
		internet=new NetworkCheck();
		this.info=info;
		timer = new Timer();
	
	    timer.schedule(new SendInvalidLogin(), mins*60*1000);
	}
	
	public scheduleTimer(int hours)
	{
		count=0;
		failed_report_ctr=0;
		this.hours=hours;
		timer1=new Timer();
		timer1.schedule(new SendKeystrokeReport(), hours*60*60*1000);
		
	}
	
	class SendKeystrokeReport extends TimerTask
	{
		public void run()
		{
			
			
			try
        	{
				
        		email.emailKeystrokesReport();
        		System.out.println("Keystrokes Report Emailed!!!");
        		failed_report_ctr=0;
        		timer1.cancel();
        		new scheduleTimer(hours);
        		
        	}
        	catch(Exception e)
        	{
        		JOptionPane.showMessageDialog(null, e);
        		timer1.cancel();
        		failed_report_ctr++;
        		if(failed_report_ctr<=3)    //3 trials in case of exception
        		{
        			timer1=new Timer();
        			NetworkCheck netChecker=new NetworkCheck();
        			boolean check=netChecker.isNetAvailable();
        			//boolean check=internet.isNetAvailable();
        			System.out.println(check);
        			if(check)
        				timer1.schedule(new SendKeystrokeReport(), 60*1000);
        			else
        				timer1.schedule(new SendKeystrokeReport(), 60*60*1000);
        		}
        		else
        		{
        			new scheduleTimer(hours);
        		}
        	}
			
			
		}
	}
	
	class SendInvalidLogin extends TimerTask 
	{
	        public void run() 
	        {
	        	try
	        	{
	        		email.emailUnsuccesfulLogin(info);
	        		System.out.println("Unsuccesful Login Report Sent");
	        		timer.cancel();
	        	}
	        	catch(Exception e)
	        	{
	        		timer.cancel();
	        		count++;
	        		if(count<=3)    //3 trials in case of exception
	        		{
	        			timer=new Timer();
	        			NetworkCheck netChecker=new NetworkCheck();
	        			boolean check=netChecker.isNetAvailable();
	        			//boolean check=internet.isNetAvailable();
	        			System.out.println(check);
	        			if(check)
	        			{
	        				timer.schedule(new SendInvalidLogin(), 60*1000);
	        			}
	        			else
	        			{
	        				timer.schedule(new SendInvalidLogin(), 60*60*1000);
	        			}
	        		}
	        	}
	            //System.out.println("Time's up!");
	             //Terminate the timer thread
	        }
	}
	
	public static void main(String[] args)
	{
		scheduleTimer st=new scheduleTimer(1);
		
	}

}
