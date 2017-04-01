import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class saveScreenshot {

    static boolean enabled = Main.enabled_ss;
    static int number=0;
    public saveScreenshot() throws IOException {
        enabled = Main.enabled_ss;
        System.out.println("Status of enable : "+enabled);
        if(enabled) {
            try {
                FileReader fr = new FileReader("lib/docs/screenshot_serialno.txt");
                BufferedReader br = new BufferedReader(fr);
                //String textLine = br.readLine();
                number = Integer.parseInt(br.readLine().toString());
                br.close();

                Thread.sleep(500);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                Rectangle screenRectangle = new Rectangle(screenSize);
                Robot robot = new Robot();
                BufferedImage image = robot.createScreenCapture(screenRectangle);

                number++;
                String fileName = "lib/screenshots/screen_" + number + ".png";
                ImageIO.write(image, "png", new File(fileName));
                System.out.println("saved.." + fileName);

                BufferedWriter bw = new BufferedWriter(new FileWriter("lib/docs/screenshot_serialno.txt", false));
                PrintWriter pw = new PrintWriter(bw);
                pw.println(number);

                pw.close();
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else System.out.println("Screenshot not being saved as the feature has been disabled...");
    }
}