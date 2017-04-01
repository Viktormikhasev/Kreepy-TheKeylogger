import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Security
{
	private static String passwordHash;
	boolean securityStatus;
	private emailHelper email=new emailHelper();
	public FilePermissions fp;
	public Main cmain;

	public Security()
	{
		/**
		 * Whenever an object of class security will be created
		 * the passwordHash will be checked in textfile if hash will be
		 * present then it will be initialized
		 */
		fp=new FilePermissions();
		try {
			passwordHash=getFileData();
			securityStatus=true;
		} catch (IOException e) {

			String password="";
			int flag=1;
			while(!goodPassword(password))
			{
				if(flag==0)
				{
					JOptionPane.showMessageDialog(null, "Your entered password is weak.Please follow required"
							+ " password guidelines.");
				}
				AuthenticationUI auth=new AuthenticationUI();
				auth.FirstTimePassword();

				password=auth.pass;
				flag=0;
				//alert dialog specifying password specifications
				//code to call ui for password generation the ui must return a password
			}
			setPassword(password);

			try {
				writeFileData(passwordHash);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//Storing newly created password hash in file
			securityStatus=true;
		}
	}

	public boolean isEmailServiceStarted() { return new File("lib/docs/emailService.txt").isFile(); }

	public static boolean goodPassword(String password)
	{
		//checking whether the password is null
		if(password.length()<8)
			return false;
		if (!password.matches(".*[A-Z].*"))
			return false;
		if (!password.matches(".*[a-z].*"))
			return false;
		if (!password.matches(".*\\d.*"))
			return false;
		if (!password.matches(".*[$&+,:;=?@#|<>.-^*'()%!].*"))
			return false;

		return true;
	}

	private String getFileData() throws IOException
	{
		String filePath="lib/docs/password.txt";
		File file=new File(filePath);
		fp.GrantFilePermissions(file);
		FileReader fr = new FileReader(file);
		BufferedReader bf=new BufferedReader(fr);
		String a;
		a=bf.readLine();// reads the content to the array
		fr.close();

		fp.RevokeFilePermissions(file);
		return a;
	}


	public void writeFileData(String password)throws IOException
	{
		File file = new File("lib/docs/password.txt");
		fp.GrantFilePermissions(file);
		//Loading load=new Loading(6);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(file.getPath());
		FileWriter writer = new FileWriter(file,false);
		// Writes the content to the file
		writer.write(password);
		writer.flush();
		writer.close();
		fp.RevokeFilePermissions(file);
	}
	//create a function for checking password

	public void setPassword(String password)
	{
		try
		{
			passwordHash=hash(password);
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}

	public static String hash(String password)throws NoSuchAlgorithmException
	{
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		StringBuilder buffer = new StringBuilder();
		byte[] passBytes = password.getBytes();
		byte[] passHash = sha256.digest(passBytes);
		for(byte b : passHash)
			buffer.append(b);
		return buffer.toString();
	}

	public boolean authorizeAction()
	{
		AuthenticationUI auth=new AuthenticationUI();
		auth.Authorization();
		if(!goodPassword(auth.pass) || !checkEnteredPassword(auth.pass))
		{
			if(cmain.webcam_detector) {
				try {
					new Webcam_activator();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			loginTracker("UNSUCCESSFUL LOGIN ATTEMPT");
			return false;
		}
		return checkEnteredPassword(auth.pass);
	}


	public void changePassword()
	{
		JDialog d=new JDialog();
		d.setLayout(new FlowLayout());
		d.setSize(300,150);
		d.setLocation(500,200);
		d.setResizable(false);
		d.setTitle("Change the Password");
		JLabel oldPass,newPass,confirmPass;
		oldPass=new JLabel("Enter old password:");
		newPass=new JLabel("Enter new password:");
		confirmPass=new JLabel("Confirm New Password:");

		JPasswordField oldPasswordField,newPasswordField,confirmPassField;

		oldPasswordField=new JPasswordField(20);
		newPasswordField=new JPasswordField(20);
		confirmPassField=new JPasswordField(20);

		d.add(oldPass);
		d.add(oldPasswordField);
		d.add(newPass);
		d.add(newPasswordField);
		d.add(confirmPass);
		d.add(confirmPassField);

		JButton change_BUTTON,cancel_BUTTON;
		change_BUTTON=new JButton("Change Password");
		d.add(change_BUTTON);
		cancel_BUTTON=new JButton("Cancel");
		d.add(cancel_BUTTON);

		d.setVisible(true);
		change_BUTTON.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==change_BUTTON)
				{
					String enteredPassword=new String(oldPasswordField.getPassword());
					String enteredHash;
					try
					{
						enteredHash=hash(enteredPassword);
						if(enteredHash.equals(passwordHash))
						{
							String newPass=new String(newPasswordField.getPassword());
							String confirmNewPass=new String(confirmPassField.getPassword());
							if(newPass.equals(confirmNewPass))
							{
								int flag=1;
								if(!goodPassword(newPass))
								{
									JOptionPane.showMessageDialog(null, "Your entered password is weak.Please follow required"
											+ " password guidelines.");
								}
								else
								{
									setPassword(newPass);
									try {
										
										writeFileData(passwordHash);
										
										d.dispose();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Old entered password do not match.");
								oldPasswordField.setText("");
								newPasswordField.setText("");
								confirmPassField.setText("");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Newly entered password do not match.");
							oldPasswordField.setText("");
							newPasswordField.setText("");
							confirmPassField.setText("");

						}

					}
					catch(NoSuchAlgorithmException e1)
					{
						e1.printStackTrace();
					}
				}

			}

		});
		cancel_BUTTON.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==cancel_BUTTON)
				{
					d.dispose();
				}
			}
		});
	}




	public boolean checkEnteredPassword(String enteredPassword)
	{
		String enteredHash;
		try
		{
			enteredHash=hash(enteredPassword);
			if(enteredHash.equals(passwordHash))
				return true;
			else
			{
				//Undesired/abnormal login attempt to be reported
				//loginTracker("UNSUCCESSFUL LOGIN ATTEMPT");
				return false;
			}

		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public void loginTracker(String status)
	{
		//Getting login date and time
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String login_time=dateFormat.format(date);
		File file=new File("lib/docs/logs.txt");
		fp.GrantFilePermissions(file);
		try {
			String line;
			FileWriter fw=new FileWriter(file,true);
			BufferedWriter bw=new BufferedWriter(fw);
			PrintWriter pw=new PrintWriter(bw);
			line=login_time+"    "+status;
			pw.println(line);
			pw.close();
			bw.close();
			fw.close();

			if(isEmailServiceStarted()) new scheduleTimer(1,line);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fp.RevokeFilePermissions(file);
	}
}