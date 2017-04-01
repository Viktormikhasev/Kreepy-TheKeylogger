import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


public class Webcam_activator {

    public Webcam_activator() throws IOException, InterruptedException {
        
        FileReader fr = new FileReader("lib/docs/webcamshot_serialno.txt");
        BufferedReader br = new BufferedReader(fr);
        int number = Integer.parseInt(br.readLine().toString());
        Webcam webcam = Webcam.getDefault();
        webcam.open();

        number++;
        BufferedImage image = webcam.getImage();
        String fileName = "lib/webcam_pics_failedAttempts/webcampic_"+number+".png";
        ImageIO.write(image,"png",new File(fileName));
        webcam.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter("lib/docs/webcamshot_serialno.txt",false));
        PrintWriter pw = new PrintWriter(bw);
        pw.println(number);

        pw.close();
        bw.close();
        br.close();
    }
}
