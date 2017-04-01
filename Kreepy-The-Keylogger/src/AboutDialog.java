import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.*;

public class AboutDialog 
{
    
    public static final String projectName = "Kreepy::The Key Logger";
    
    public static final String authorText = "(C) 2016 SE TEAM";
    
    public static final String copyrightText =
	"This software system will be a Key Logging cum \"User Activity Control\" software. This system will be designed to track the activities which a particular user performs on his/her computer. These be recorded and saved for later to be monitored and inspected by the super user who has installed the software on the user system."
	+ "It will provide tools to capture key stokes, screenshots of screen (in special cases: such as on opening new applications). "
	+ "\nBy keeping the track of the activities performed by the user, the super user could have control over the actions performed by the user and will enable a surveillance to ensure that only work/actions specified by the super user are performed on the system."
	+ "\n\nKreepy is provided to you free of charge.";
	
    
    public static void displayAboutBox()
    {

		Window frame = new Window("About Us", 100, 100, 300, 250, "lib/images/keyboard.png");

		// create text area
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Comic sans MS", Font.ITALIC, 12));
		textArea.setText(copyrightText);
		textArea.setEditable(false);

		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setLocation(10, 70);
		scroller.setSize(230, 100);
		frame.add(scroller);
		frame.setResizable(false);
		textArea.setCaretPosition(0);
		frame.setVisible(true);
    }
}
