import javax.swing.*;
import java.awt.*;

public class Window extends JFrame
{

	public Window(String title,int xcord,int ycord,int width,int height,String path)
	{
		setTitle(title);
		setSize(width,height);
		setLocation(xcord,ycord);
		setIconImage(new ImageIcon(path).getImage());
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}
