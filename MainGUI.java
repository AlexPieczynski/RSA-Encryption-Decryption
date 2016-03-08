/* user interacts with program using this GUI */

import javax.swing.*;

public class MainGUI extends JFrame
{  
  RSAHandler rsa;
  
  public MainGUI()
  {
    super("RSA Message Encryption/Decryption");
    JMenu menu;
  }
  
  
  public static void main (String[] args)
  {
    MainGUI window = new MainGUI();
    window.setVisible(true);
  }
}