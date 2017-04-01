import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class View_Log extends JPanel implements ActionListener{

    static String button_name="";
    static String button_info="";
    private JList list;
    public View_Log() throws IOException {

        setLayout(new GridLayout(0, 1, 1, 1));

        BufferedReader br = new BufferedReader(new FileReader("lib/docs/Logger_Keys.txt"));
        String textLine = br.readLine();
        textLine=br.readLine(); //shifting to the 2nd line of the logging file as the 1st line is a blank

        int ctr=0;
        while(textLine!=null){
            ctr++;
            if(textLine.startsWith("cntrl_lognote")){
                //System.out.println("textLine from if : "+textLine);
                button_name+=textLine.substring(textLine.indexOf(' ')+1) + ",,";
                if(ctr!=1)
                    button_info+=",,";
                textLine=br.readLine();
            }
            else{
                //System.out.println("textLine from else : "+textLine);
                while(textLine!=null && textLine.startsWith("cntrl_lognote")==false) {
                    button_info+=textLine+"\n";
                    textLine=br.readLine();
                }
            }
        }

        Pattern p = Pattern.compile(",,");
        //String button_name_array[] = button_name.split(",,");
        String button_name_array[] = p.split(button_name);
        //String button_info_array[] = button_info.split(",,");
        String button_info_array[] = p.split(button_info);
        ctr=0;

        for(int i=0;i<button_name_array.length;i++){
            //System.out.println("Button name : "+button_name_array[i]);
            //System.out.println("Button info : "+button_info_array[i]);
            ctr++;
        }
        ctr=button_info_array.length;

        Object[] items = new ButtonItem[ctr];
        for (int i = 0; i < ctr; i++)  items[i]= new ButtonItem(button_name_array[i], button_info_array[i]);

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

    private void clickButtonAt(Point point){
        int index = list.locationToIndex(point);
        ButtonItem item = (ButtonItem) list.getModel().getElementAt(index);
        item.getButton().doClick();
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    class PanelRenderer extends JButton implements ListCellRenderer {
        public Component getListCellRendererComponent(JList comp, Object value, int index,
                                                      boolean isSelected, boolean hasFocus)
        {
            setEnabled(comp.isEnabled());
            setFont(comp.getFont());
            setText(value.toString());
            return this;
        }
    }

    class ButtonItem{
        private JButton button;
        public ButtonItem(String name, String value){
            this.button = new JButton(name);
            button.setText(name);
            button.setRequestFocusEnabled(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Window frame = new Window("Logging Record", 200,200,500,300,null);
                    JTextArea textArea = new JTextArea();
                    textArea.setLineWrap(true);
                    textArea.setText(value);
                    textArea.setEditable(false);

                    JScrollPane scroller = new JScrollPane(textArea);
                    scroller.setLocation(10, 70);
                    scroller.setSize(230, 100);
                    frame.add(scroller);
                    frame.setVisible(true);
                    System.out.println("Button info : "+value);
                }
            });
        }

        public JButton getButton(){return button;}

        public String toString(){return button.getText();}
    }
}
