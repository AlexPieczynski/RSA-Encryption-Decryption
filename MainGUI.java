import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

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
    key.addActionListener( new KeyHandler() );
    block = new JMenuItem("Block A File");
    block.addActionListener( new BlockHandler() );
    unblock = new JMenuItem("Unblock a file");
    unblock.addActionListener( new UnblockHandler() );
    ende = new JMenuItem("Encrypt/Decrypt");
    ende.addActionListener( new EndeHandler() );
    
    menuBar.add(menu);
    menu.add(key);
    menu.add(block);
    menu.add(unblock);
    menu.add(ende);
    
    this.setJMenuBar(menuBar);
    this.setSize(500,500);
    
    rsa = new RSAHandler();
  }
  
  
  //Key menu item. Generates keys and saves them to a file
  private class KeyHandler implements ActionListener {
      public void actionPerformed( ActionEvent event )
      {
        HugePrime p,q;
        String input;
        int opt = JOptionPane.showConfirmDialog(null, "Would you like to enter a prime? A random prime will be chosen otherwise.",
                                      "Prime Number Generation", JOptionPane.YES_NO_OPTION);
        
        if (opt == 0){ //user said yes, they enter a prime number
          do{
            input = JOptionPane.showInputDialog("Please enter in a prime number p");
            p = new HugePrime(input);
          }
          while (!p.isPrime());
          
          do{
            input = JOptionPane.showInputDialog("Please enter in a prime number q");
            q = new HugePrime(input);
          }
          while (!q.isPrime());
        }
        else{ //choose prime randomly
          p = new HugePrime();
          q = new HugePrime();
        }
        
        rsa.calcValues(p,q);
        rsa.generateKeys();
      }
   }
  
  
  private class BlockHandler implements ActionListener {
      public void actionPerformed( ActionEvent event )
      {
        rsa = new RSAHandler();
        String fname = JOptionPane.showInputDialog("Please enter the name of the file to be blocked");
        String blockSize = JOptionPane.showInputDialog("Please enter in a block size");
        try{
          rsa.blockFile(Integer.parseInt(blockSize), fname);
        }
        catch(FileNotFoundException fnfe){
          System.out.println("FILE NOT FOUND");
        }
        catch(IOException e){
          System.out.println("catch IOException");
        }
      }
  }
  
  
  private class UnblockHandler implements ActionListener {
      public void actionPerformed( ActionEvent event )
      {
        String fname = JOptionPane.showInputDialog("Please enter the name of the file to be unblocked");
        String blockSize = JOptionPane.showInputDialog("Please enter in the block size");
        try{
          rsa.unblockFile(Integer.parseInt(blockSize), fname);
        }
        catch(FileNotFoundException fnfe){
          System.out.println("FILE NOT FOUND");
        }
        catch(IOException e){
          System.out.println("catch IOException");
        }
      }
  }
  
  
  private class EndeHandler implements ActionListener {
      public void actionPerformed( ActionEvent event )
      {
        String inFile = JOptionPane.showInputDialog("Please enter the name of the file to be encrypted/decrypted");
        String keyFile = JOptionPane.showInputDialog("Please enter the name of the RSA key file");
        String outFile = JOptionPane.showInputDialog("Please enter the name output file");
        
        try{
          rsa.encrypt(inFile, keyFile, outFile);
        }
        catch(FileNotFoundException fnfe){
          System.out.println("FILE NOT FOUND");
        }
        catch(IOException e){
          System.out.println("catch IOException");
        }
      }
  }
  
  

  public static void main (String[] args)
  {
    MainGUI window = new MainGUI();
    window.setVisible(true);
  }
}
