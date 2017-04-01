import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetworkCheck {
	
	public boolean isNetAvailable() {                                                                                                                                                                                                 
	    try {                                                                                                                                                                                                                                 
	        final URL url = new URL("http://www.google.com");                                                                                                                                                                                 
	        final URLConnection conn = url.openConnection();                                                                                                                                                                                  
	        conn.connect();                                                                                                                                                                                                                   
	        return true;                                                                                                                                                                                                                      
	    } catch (MalformedURLException e) {                                                                                                                                                                                                   
	        System.out.println(e);                                                                                                                                                                                                  
	    } catch (IOException e) {                                                                                                                                                                                                             
	        return false;                                                                                                                                                                                                                     
	    } 
	    return false;
	}  

	public static void main(String[] args)
	{
		NetworkCheck netChecker=new NetworkCheck();
		System.out.println(netChecker.isNetAvailable());
	}
}
