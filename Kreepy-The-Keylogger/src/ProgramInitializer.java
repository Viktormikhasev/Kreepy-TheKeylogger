import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ProgramInitializer 
{
	public static void main(String[] args) throws IOException
	{
		setFilesUnHidden();
		deleteFiles();
		deletepics();
		
	}
	public static void setFilesUnHidden()
	{
    	ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd \"C:\\Users\\Prakhar\\mainWorkspace\\Project\\lib\\Docs\"&& attrib -h -r -s *.*");
            builder.redirectErrorStream(true);
 				Process p = null;
				try {
					p = builder.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
	}
	
	public static void deleteFiles()
	{
		File f=new File("C:\\Users\\Prakhar\\mainWorkspace\\Project\\lib\\Docs\\password.txt");
		f.delete();
		f=new File("C:\\Users\\Prakhar\\mainWorkspace\\Project\\lib\\Docs\\Logger_Keys.txt");
		f.delete();
		f=new File("C:\\Users\\Prakhar\\mainWorkspace\\Project\\lib\\Docs\\Logs.txt");
		f.delete();
		f=new File("C:\\Users\\Prakhar\\mainWorkspace\\Project\\lib\\Docs\\emailService.txt");
		f.delete();
	}
	
	public static void deletepics() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("lib/docs/screenshot_serialno.txt"));
        int ctr = Integer.parseInt(br.readLine());
        br.close();

        BufferedWriter bw =  new BufferedWriter(new FileWriter("lib/docs/screenshot_serialno.txt",false));
        PrintWriter pw = new PrintWriter(bw);
        pw.println("0");
        pw.close();
        bw.close();

        for(int i=1;i<=ctr;i++){
            String filename = "lib/screenshots/screen_"+i+".png";
            File f1 = new File(filename);
            f1.delete();
        }
        
        
        
        br = new BufferedReader(new FileReader("lib/docs/webcamshot_serialno.txt"));
        ctr = Integer.parseInt(br.readLine());
        br.close();

        bw =  new BufferedWriter(new FileWriter("lib/docs/webcamshot_serialno.txt",false));
        pw = new PrintWriter(bw);
        pw.println("0");
        pw.close();
        bw.close();

        for(int i=1;i<=ctr;i++){
            String filename = "lib/webcam_pics_failedAttempts/webcampic_"+i+".png";
            File f1 = new File(filename);
            f1.delete();
        }
	}
}
