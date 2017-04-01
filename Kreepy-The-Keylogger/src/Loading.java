import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Loading {

    public Loading(int sleepTime) {
        ShowLoading(sleepTime);
    }

    public void ShowLoading(int seconds) {
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

        JDialog dlg = new JDialog();
        dlg.setTitle("Kreepy::The KeyLogger");
        JProgressBar dpb = new JProgressBar(0, 500);

        dlg.add(BorderLayout.CENTER, dpb);
        //dlg.add(dpb, BorderLayout.CENTER);
        //dlg.add()
        dlg.add(BorderLayout.NORTH, new JLabel("Please Wait..."));
        dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dlg.setSize(300, 75);
        dlg.setLocation(500, 200);
        dlg.setVisible(true);
    /*Thread t = new Thread(new Runnable() {
      public void run() {
       // dlg.setVisible(true);
      }
    });
    t.start();*/
        for (int i = 0; i <= 500; i = i + 1) {
            dpb.setValue(i);
            try {
                Thread.sleep(seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dlg.setVisible(false);
        dlg.dispose();
    }
}