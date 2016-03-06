/* user interacts with program using this GUI */

import javax.swing.*;

public class MainGUI extends JFrame
{
  private JMenu menu;
  
  public MainGUI()
  {
    super("RSA Message Encryption/Decryption");
  }
  
  
  public static void main (String[] args)
  {
    MainGUI window = new MainGUI();
    window.setVisible(true);
  }
}