import java.io.File;
import java.io.IOException;

public class FilePermissions {
 
    public static void main(String[] args)
    {
        FilePermissions fp=new FilePermissions();
        File f=new File("lib/Docs/password.txt");
		fp.GrantFilePermissions(f);
		fp.RevokeFilePermissions(f);
        //System.out.println("Current directory: "+System.getProperty("user.dir"));
    }
    
    public void setFileHidden(File f) 
    {
    	String filePath=f.getPath();
    	String directory=System.getProperty("user.dir")+"\\"+filePath;
    	int index=directory.lastIndexOf('\\')+1;
		String fileName=directory.substring(index);
		//System.out.println(fileName);
		directory=directory.substring(0, index-1);
        // System.out.println(directory);
    	ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd "+directory+"&& attrib +h +r +s "+fileName);
            builder.redirectErrorStream(true);
 				Process p = null;
				try {
					p = builder.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	
    }

    public void setFileUnhidden(File f)
    {
    	String filePath=f.getPath();
    	String directory=System.getProperty("user.dir")+"\\"+filePath;
    	int index=directory.lastIndexOf('\\')+1;
		String fileName=directory.substring(index);
		//System.out.println(fileName);
		directory=directory.substring(0, index-1);
        //System.out.println(directory);

    	ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd "+directory+"&& attrib -h -r -s "+fileName);
            builder.redirectErrorStream(true);
        		Process p = null;
				try {
					p = builder.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    }
    
    public void setFileWritable(File f)
    {
    	f.setWritable(true);
    }
    
    public void setFileNonWritable(File f)
    {
    	f.setWritable(false);
    }
    
    public void setFileReadable(File f)
    {
    	f.setReadable(true);
    }
    
    public void setFileUnreadable(File f)
    {
    	f.setReadable(false);
    }
    
    public void GrantFilePermissions(File f)
    {
    	setFileUnhidden(f);
    	setFileReadable(f);
    	setFileWritable(f);
    }
    
    public void RevokeFilePermissions(File f)
    {
    	setFileUnreadable(f);
    	setFileNonWritable(f);
    	setFileHidden(f);
    }
}