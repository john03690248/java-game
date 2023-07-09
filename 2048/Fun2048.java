import java.awt.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
public class Fun2048 extends JFrame 
{ 
  public Fun2048()
  {
    setTitle("2048 Game");
    setSize(1000, 1000);
    setLocation(450, 25);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setLayout(new GridLayout(6, 6, 2, 2));
    new Operation(this);
    this.setVisible(true);
  }
 
  public static void main(String args[])
  {
    try
    {
      UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel");
    }
    catch (ClassNotFoundException | InstantiationException| IllegalAccessException | UnsupportedLookAndFeelException e)
    {
      e.printStackTrace();
    }
    JFrame.setDefaultLookAndFeelDecorated(true);
    new Fun2048();
  }
 
}