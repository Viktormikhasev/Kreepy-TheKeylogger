import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.*;

public class AuthenticationUI extends JFrame implements ActionListener {
    JDialog d1;
    public JButton button_OK1, button_OK2, button_RESET1, button_RESET2, button_CANCEL;
    public JPasswordField password, repassword;
    String pass, repass;

    public AuthenticationUI() {
        pass = "";
        repass = "";
        System.out.println("Entering the constructor OF AUI...");
    }

    public void Authorization() {

        JLabel prompter;
        int passwordLength = 20;

        JDialog.setDefaultLookAndFeelDecorated(true);
        d1 = new JDialog(this, "Authenticate", true);

        d1.setSize(500, 100);
        d1.setLocation(100, 100);
        d1.setLayout(new FlowLayout());

        prompter = new JLabel("Enter Password : ");
        d1.add(prompter);

        password = new JPasswordField(passwordLength) {
            public void addNotify() {
                super.addNotify();
                requestFocus();
            }
        };
        d1.add(password);

        button_OK2 = new JButton("OK");
        button_OK2.addActionListener(this);
        password.addKeyListener(new CustomKeyListener());
        d1.add(button_OK2);

        button_RESET2 = new JButton("RESET");
        button_RESET2.addActionListener(this);
        d1.add(button_RESET2);
        d1.setVisible(true);
    }

    public void FirstTimePassword() {
        JLabel prompter, reprompter;
        int passwordLength = 20;

        JDialog.setDefaultLookAndFeelDecorated(true);
        d1 = new JDialog(this, "Kreepy::Authentication Channel", true);

        d1.setSize(400, 160);
        d1.setLocation(300, 200);
        d1.setLayout(new FlowLayout());

        prompter = new JLabel("Enter Password : ");
        prompter.setRequestFocusEnabled(true);
        prompter.setToolTipText("Password should have at max " + passwordLength + " characters...\nAnd it should be alpha-numeric!!");
        d1.add(prompter);

        password = new JPasswordField(passwordLength);
        d1.add(password);

        reprompter = new JLabel("Re-enter Password : ");
        reprompter.setToolTipText("Password should be same as that entered in the previous field...");
        d1.add(reprompter);

        repassword = new JPasswordField(passwordLength);
        d1.add(repassword);

        button_OK1 = new JButton("OK");
        button_OK1.addActionListener(this);
        d1.add(button_OK1);

        button_RESET1 = new JButton("RESET");
        button_RESET1.addActionListener(this);
        d1.add(button_RESET1);

        button_CANCEL = new JButton("CANCEL");
        button_CANCEL.addActionListener(this);
        d1.add(button_CANCEL);

        d1.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button_OK1) {
            pass = new String(password.getPassword());
            repass = new String(repassword.getPassword());
            if (pass.equals(repass))
                d1.dispose();
            else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "The two passwords do not match please re-enter them.");

                password.setText("");
                repassword.setText("");
            }
        } else if (e.getSource() == button_CANCEL) {
            System.exit(0);
        } else if (e.getSource() == button_OK2) {
            pass = new String(password.getPassword());
            d1.dispose();
        } else if (e.getSource() == button_RESET1) {
            password.setText("");
            repassword.setText("");
        } else if (e.getSource() == button_RESET2) {
            password.setText("");
        }
    }

    class CustomKeyListener implements KeyListener {
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                pass = new String(password.getPassword());
                d1.dispose();
            }
        }

        public void keyReleased(KeyEvent e) {
        }
    }
}