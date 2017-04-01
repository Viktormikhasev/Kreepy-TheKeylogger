import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class emailDialog extends JFrame implements ActionListener, KeyListener
{
	JDialog d;
	private String recipentEmail;
	public String delayTime;
	JTextField recipent,delay;

	JLabel recipentLabel,delayLabel;
	JButton button_OK,button_CANCEL, button_CANCEL2;
	JLabel dialogSubject;
	String pathName="lib/docs/emailService.txt";
	FilePermissions fp;
	Main cmain=new Main();

	public emailDialog(String dialogSubject)
	{
		recipentEmail="";
		delayTime="";

		fp=new FilePermissions();

		StringBuilder sb=new StringBuilder();
		sb.append("<html>");
		sb.append("<h1>");
		sb.append("<u>");
		sb.append("<b>");
		sb.append("<i>");
		sb.append(dialogSubject);
		sb.append("</i>");
		sb.append("</b>");
		sb.append("</u>");
		sb.append("</h1>");
		sb.append("</html>");
		this.dialogSubject=new JLabel(sb.toString());
	}

	public boolean checkForm()
	{
		if(recipentEmail.length()<12 || delayTime.length()<1)
			return false;
		else
			return true;
	}

	public void CreateUI()
	{
		try
		{
			fp=new FilePermissions();
			fp.GrantFilePermissions(new File(pathName));

			FileReader fr= new FileReader(new File(pathName));
			BufferedReader br=new BufferedReader(fr);
			String line;
			this.recipentEmail=br.readLine();
			this.delayTime=br.readLine();
			br.close();
			fr.close();

			fp.RevokeFilePermissions(new File(pathName));
		}
		catch(IOException e)
		{
			JDialog.setDefaultLookAndFeelDecorated(true);
			d=new JDialog(this,"Kreepy::Emailer Service",true);

			d.setSize(400,240);
			d.setLocation(200,200);
			d.setLayout(new FlowLayout());

			recipent=new JTextField(20);
			delay=new JTextField(20);

			recipentLabel=new JLabel("Recipent's Email:");
			delayLabel=new JLabel("Delay(in hours):");

			button_OK=new JButton("Save Details");
			button_CANCEL=new JButton("Cancel");

			button_OK.addActionListener(this);
			button_CANCEL.addActionListener(this);

			button_OK.setToolTipText("Get Ready For Emails :)");
			button_CANCEL.setToolTipText("Wanna leave :(");

			button_CANCEL.addKeyListener(this);
			button_OK.addKeyListener(this);
			recipent.setForeground(Color.blue);

			d.add(dialogSubject);
			d.add(recipentLabel);
			d.add(recipent);
			d.add(delayLabel);
			d.add(delay);
			d.add(button_OK);
			d.add(button_CANCEL);

			d.setVisible(true);
		}
	}

	public void createUI_newEmail(){
		d=new JDialog();
		d.setLayout(new FlowLayout());
		d.setSize(200,150);
		d.setLocation(500,200);
		d.setResizable(false);
		d.setTitle("Change the E-MAIL");

		try {
			fp=new FilePermissions();
			fp.GrantFilePermissions(new File(pathName));
			BufferedReader br = new BufferedReader(new FileReader(pathName));
			br.readLine();
			delayTime=br.readLine();
			br.close();
			fp.RevokeFilePermissions(new File(pathName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		recipentLabel = new JLabel("Enter the new email ID : ");
		d.add(recipentLabel);
		recipent = new JTextField(20);
		d.add(recipent);
		recipent.setForeground(Color.blue);

		delayLabel=new JLabel("Delay(in hours):");
		d.add(delayLabel);
		delay=new JTextField(20);
		d.add(delay);

		button_OK=new JButton("Save Details");
		button_OK.addActionListener(this);
		d.add(button_OK);

		button_CANCEL2 = new JButton("CANCEL");
		button_CANCEL2.addActionListener(this);
		d.add(button_CANCEL2);

		d.setVisible(true);
	}

	public void store_details()
	{
		this.recipentEmail=recipent.getText();
		System.out.println("recipent email : "+this.recipentEmail);
		System.out.println("delay : "+this.delayTime);
		this.delayTime = delay.getText();

		try {
			fp=new FilePermissions();
			fp.GrantFilePermissions(new File(pathName));
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			FileWriter fw=new FileWriter(new File(pathName));
			BufferedWriter bw=new BufferedWriter(fw);
			PrintWriter pw=new PrintWriter(bw);
			pw.println(this.recipentEmail);
			pw.println(this.delayTime);
			pw.close();
			bw.close();
			fw.close();
			fp.RevokeFilePermissions(new File(pathName));
			System.out.println("Wrote the data in file for emailDialog class");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}

		if(checkForm())
		{
			JOptionPane.showMessageDialog(null,"The details are successfully stored."
					+ "You will start receiving emails shortly.\nThank You \n@Kreepy Team");

			System.out.println("Details of emailer service stored.");
			d.dispose();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Please enter all the details");
		}
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		if(e.getSource()==button_OK)
		{
			store_details();
			if(!cmain.emailServiceExists)
			{
				new scheduleTimer(Integer.parseInt(delayTime));
				cmain.emailServiceExists=true;
			}
		}
		else if(e.getSource()==button_CANCEL)
		{
			d.dispose();
			JOptionPane.showMessageDialog(null, "You can add/change the credentials"
					+ "in future via settings panel");
		}
		else if(e.getSource()==button_CANCEL2){
			d.dispose();
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{

			d.dispose();
			JOptionPane.showMessageDialog(null, "You can add/change the credentials"
					+ "in future via settings panel");
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			store_details();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}