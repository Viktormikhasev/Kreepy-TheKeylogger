import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Screenshot_Log extends JPanel implements ActionListener{

    private JList list;
    public Screenshot_Log() throws IOException {

        setLayout(new GridLayout(0, 1, 1, 1));

        BufferedReader br = new BufferedReader(new FileReader("lib/docs/screenshot_serialno.txt"));
        int ctr = Integer.parseInt(br.readLine()); //stores that 'ctr - 1' images have been stored in it
        br.close();

        if(ctr!=0) {
            Object[] items = new Screenshot_item[ctr];
            for (int i = 0; i < ctr; i++)
                items[i] = new Screenshot_item("Screenshot number : " + (i + 1), "lib/screenshots/screen_" + (i + 1) + ".png");

            list = new JList(items);
            list.setCellRenderer(new PanelRenderer());
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    //super.mouseClicked(me);
                    clickButtonAt(me.getPoint());
                }
            });
            JScrollPane scroller = new JScrollPane(list);
            JScrollBar scrollBar = scroller.getVerticalScrollBar();
            add(scroller);
        }
        else {
            JLabel display = new JLabel("No screenshots present currently...");
            add(display);
        }
    }

    private void clickButtonAt(Point point){
        int index = list.locationToIndex(point);
        Screenshot_item item = (Screenshot_item) list.getModel().getElementAt(index);
        item.getButton().doClick();
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    class PanelRenderer extends JButton implements ListCellRenderer {
        /*
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JButton renderer = (JButton) value;
            renderer.setBackground(isSelected ? Color.red : list.getBackground());
            return renderer;
        }*/

        public Component getListCellRendererComponent(JList comp, Object value, int index,
                                                      boolean isSelected, boolean hasFocus)
        {
            setEnabled(comp.isEnabled());
            setFont(comp.getFont());
            setText(value.toString());

            return this;
        }
    }

    class Screenshot_item{
        private JButton button;
        public Screenshot_item(String name, String value){
            this.button = new JButton();
            button.setText(name);
            button.setRequestFocusEnabled(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Window frame = new Window("Screenshot Record", 200,200,500,300,null);
                    JLabel img = new JLabel(new ImageIcon(value));
                    frame.add(img);
                    frame.setVisible(true);
                    frame.setResizable(true);
                    System.out.println("Screenshot info : "+value);
                }
            });
        }

        public JButton getButton(){return button;}

        public String toString(){return button.getText();}
    }
}
