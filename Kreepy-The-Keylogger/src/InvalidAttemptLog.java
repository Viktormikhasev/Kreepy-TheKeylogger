import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

public class InvalidAttemptLog extends JPanel implements ActionListener {

    private JList list;
    FilePermissions fp = new FilePermissions();

    public InvalidAttemptLog() throws NumberFormatException, IOException {
        setLayout(new GridLayout(0, 1, 1, 1));

        BufferedReader br = new BufferedReader(new FileReader("lib/docs/webcamshot_serialno.txt"));
        int ctr = Integer.parseInt(br.readLine());
        br.close();
        System.out.println(ctr);

        Object[] items = new Login_item[ctr];
        File file = new File("lib/docs/logs.txt");
        fp.GrantFilePermissions(file);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String info;
        if (ctr != 0) {
            br = new BufferedReader(new FileReader(file));

            for (int i = 0; i < ctr; i++) {
                info = br.readLine();
                items[i] = new Login_item(info, "lib/webcam_pics_failedAttempts/webcampic_" + (i + 1) + ".png");
            }
            br.close();

            fp.RevokeFilePermissions(file);

            list = new JList(items);

            list.setCellRenderer(new PanelRenderer());
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    clickButtonAt(me.getPoint());
                }
            });

            JScrollPane scroller = new JScrollPane(list);
            JScrollBar scrollBar = scroller.getVerticalScrollBar();
            add(scroller);
        }
        else{
            JLabel display = new JLabel("No invalid attempts yet...");
            add(display);
        }
    }

    private void clickButtonAt(Point point) {
        int index = list.locationToIndex(point);
        Login_item item = (Login_item) list.getModel().getElementAt(index);
        item.getButton().doClick();
    }


    class PanelRenderer extends JButton implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            // TODO Auto-generated method stub
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setText(value.toString());

            return this;
        }

    }


    class Login_item {
        private JButton button;

        public Login_item(String name, String value) {
            button = new JButton(name);
            button.setRequestFocusEnabled(true);
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // TODO Auto-generated method stub
                    Window frame = new Window("Photo of the Trespasser", 200, 200, 500, 300, null);
                    JLabel img = new JLabel(new ImageIcon(value));
                    frame.add(img);
                    frame.setVisible(true);
                    frame.setResizable(true);
                    System.out.println("Trespasser Photo :" + value);


                }

            });
        }

        public JButton getButton() {
            return button;
        }

        public String toString() {
            return button.getText();
        }
    }


    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

    }

	
    /*public static void main(String args[])throws IOException{
        JFrame viewLog = new InvalidAttemptLog();
        viewLog.setVisible(true);
        viewLog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewLog.pack();
    }*/
}
