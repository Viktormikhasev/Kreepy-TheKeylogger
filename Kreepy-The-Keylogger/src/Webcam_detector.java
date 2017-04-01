import com.github.sarxos.webcam.Webcam;


public class Webcam_detector {
    public boolean Webcam_detector(){
        Webcam webcam = Webcam.getDefault();
        if(webcam!=null){
            System.out.println("Webcam has been detected on this system...");
            return true;
        }
        else  return false;
    }
    public static void main(String args[]){
        System.out.println("Webcam detected : "+new Webcam_detector().Webcam_detector());
    }
}
