import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI extends JFrame
{  
  RSAHandler rsa;
  
  public MainGUI()
  {
    super("RSA Message Encryption/Decryption");
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem key;
    JMenuItem block;
    JMenuItem unblock;
    JMenuItem ende;
    
    menuBar = new JMenuBar();
    menu = new JMenu("RSA");
    key = new JMenuItem("Key Creation");
    key.addActionListener( new MenuHandler() );
    block = new JMenuItem("Block A File");
    block.addActionListener( new MenuHandler() );
    unblock = new JMenuItem("Unblock a file");
    unblock.addActionListener( new MenuHandler() );
    ende = new JMenuItem("Encrypt/Decrypt");
    ende.addActionListener( new MenuHandler() );
    
    menuBar.add(menu);
    menu.add(key);
    menu.add(block);
    menu.add(unblock);
    menu.add(ende);
    
    this.setJMenuBar(menuBar);
    this.setSize(500,500);
  }
  
  private class MenuHandler implements ActionListener {

      // just seeing if the handler works
      public void actionPerformed( ActionEvent event )
      {
        JMenuItem temp = (JMenuItem) event.getSource();
         JOptionPane.showMessageDialog( MainGUI.this,
            "You pressed: " + event.getActionCommand() + " " + temp.getText() );
      }

   }
  public static void main (String[] args)
  {
    MainGUI window = new MainGUI();
    window.setVisible(true);
  }
}
