import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DisplayTrayIcon {
    private static Main cMain;
    static TrayIcon trayIcon;
    static boolean mode = true; //this is for the Activate / Deactivate mode

    public DisplayTrayIcon() {
        ShowTrayIcon();
    }

    public static void ShowTrayIcon() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        if (!SystemTray.isSupported()) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,"The tray icon is not supported!!!");
            System.exit(0);
        }
        Image img = Toolkit.getDefaultToolkit().getImage("lib/images/kreepy.png");
        final TrayIcon trayIcon = new TrayIcon(img, "Kreepy::The KeyLogger");

        final PopupMenu popupMenu = new PopupMenu();
        popupMenu.add("About");
        popupMenu.add("Activate/Deactivate");
        popupMenu.add("Settings");
        popupMenu.addSeparator();
        popupMenu.add("Exit");

        popupMenu.addActionListener(
                event -> {
                    if (event.getActionCommand().equals("About")) {
                        popupMenu.setEnabled(false);
                            AboutDialog.displayAboutBox();
                        popupMenu.setEnabled(true);
                    } else if (event.getActionCommand().equals("Exit")) {
                        popupMenu.setEnabled(false);
                        if (cMain.secure.authorizeAction()) {
                            trayIcon.displayMessage("Kreepy::The KeyLogger", "Program Terminated....", TrayIcon.MessageType.INFO);
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            GlobalScreen.unregisterNativeHook();
                            System.exit(0);
                        }
                        else {
                            Toolkit.getDefaultToolkit().beep();
                            JOptionPane.showMessageDialog(null, "You entered wrong credentials. This incident will be reported.");
                            popupMenu.setEnabled(true);
                        }
                    } else if (event.getActionCommand().equals("Activate/Deactivate")) {
                        popupMenu.setEnabled(false);
                        if (cMain.secure.authorizeAction()) {
                            mode = !mode;
                            //System.out.println("Setting the mode : " + mode);
                            try {
                                new KeyLogger_main(mode);
                            } catch (NativeHookException e) {
                                System.out.println(e);
                            }
                        }
                        else {
                            Toolkit.getDefaultToolkit().beep();
                            JOptionPane.showMessageDialog(null, "You entered wrong credentials. This incident will be reported.");
                            popupMenu.setEnabled(true);
                        }
                        popupMenu.setEnabled(true);
                    } else if (event.getActionCommand().equals("Settings")) {
                        popupMenu.setEnabled(false);
                        //if (cMain.secure.authorizeAction()) new MainDialog();
                        if (cMain.secure.authorizeAction()) new MainDialog();
                        else {
                            Toolkit.getDefaultToolkit().beep();
                            JOptionPane.showMessageDialog(null, "You entered wrong credentials. This incident will be reported.");
                        }
                        popupMenu.setEnabled(true);
                    }
                }
        );

        trayIcon.setPopupMenu(popupMenu);
        trayIcon.setImageAutoSize(true);
        // add icon to the tray
        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {

        }
    }
}
