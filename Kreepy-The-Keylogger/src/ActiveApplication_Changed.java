import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

public class ActiveApplication_Changed {
    static final int MAX_TITLE_LENGTH = 1024;
    static String windowName = "";

    public boolean ActiveApplicationChecker(){
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
        String buffer_windowName = Native.toString(buffer);

        if(buffer_windowName.equals(windowName)==false){
            windowName=buffer_windowName;
            return true;
        }
        else return false;
    }
}
