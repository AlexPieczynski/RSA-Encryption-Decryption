/* Class to write public/private keys to files
 *   -also contains methods to read keys from XML files
 */

import java.io.*;
import java.util.Scanner;

public class XMLWriter
{  
  private String e;
  private String n;
  
  public XMLWriter()
  {}
  
  public void makePublicKey(String filePrefix, String e, String n)
  {
    this.e = e;
    this.n = n;
    this.makeKey(filePrefix, false);
  }
  
  public void makePrivateKey(String filePrefix, String e, String n)
  {
    this.e = e;
    this.n = n;
    this.makeKey(filePrefix, true);
  }
  
  private void makeKey(String filePrefix, boolean isPrivate)
  {
    String fname;
    if (isPrivate)
      fname = "prikey.xml";
    else
      fname = "pubkey.xml";
    fname = filePrefix+fname;
    
    try{
    File file = new File(fname);
    file.createNewFile();
    FileWriter fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);
    
    bw.write("<rsakey>");
    bw.newLine();
    if(isPrivate)
    {
      bw.write("    <dvalue>");
      bw.write(e); //put your number as a string here
      bw.write("</dvalue>");
      bw.newLine();
    }
    else{
      bw.write("    <evalue>");
      bw.write(e); //put your number as a string here
      bw.write("</evalue>");
      bw.newLine();
    }
    bw.write("    <nvalue>");
    bw.write(n); //put your number as a string here
    bw.write("</nvalue>");
    bw.newLine();
    
    bw.write("</rsakey");
    bw.close();
    }
    catch(IOException exp){
      exp.printStackTrace();
    }
  }
  
  
  //gets private key values d and n from an XML file
  //stores them in the parameter RSAHandler's data members
  public void getPrivateKey( String filePrefix, RSAHandler rsa )
  {
    try{
      File file = new File("prikey.xml");
      Scanner sc = new Scanner(file);
      
      sc.nextLine(); //skip first line
      String d = sc.next();
      int stopAt = d.lastIndexOf("</dvalue>");
      d = d.substring(8, stopAt);
      
      String n = sc.next();
      stopAt = n.lastIndexOf("</nvalue>");
      n = n.substring(8, stopAt);
      
      HugeUnsignedInt dVal = new HugeUnsignedInt(d);
      HugeUnsignedInt nVal = new HugeUnsignedInt(n);
      rsa.setPriKeyValues(dVal, nVal);
    }
    catch (FileNotFoundException fnfe){
      System.out.println("FILE NOT FOUND");
    }
  }
  
  //gets public key values e and n from an XML file
  //stores them in the parameter RSAHandler's data members
  public void getPublicKey( String filePrefix, RSAHandler rsa )
  {
    try{
      File file = new File("pubkey.xml");
      Scanner sc = new Scanner(file);
      
      sc.nextLine(); //skip first line
      String e = sc.next();
      int stopAt = e.lastIndexOf("</evalue>");
      e = e.substring(8, stopAt);
      
      String n = sc.next();
      stopAt = n.lastIndexOf("</nvalue>");
      n = n.substring(8, stopAt);
      
      HugeUnsignedInt eVal = new HugeUnsignedInt(e);
      HugeUnsignedInt nVal = new HugeUnsignedInt(n);
      rsa.setPriKeyValues(eVal, nVal);
    }
    catch (FileNotFoundException fnfe){
      System.out.println("FILE NOT FOUND");
    }
  }
  
  
  //test XMLWriter
  public static void main(String[] args)
  {
    XMLWriter xw = new XMLWriter();
    xw.makePrivateKey("test1","123","456");
    xw.makePublicKey("test1","123","456");  
    
    xw.getPrivateKey("test1", new RSAHandler());
    xw.getPublicKey("test1", new RSAHandler());
  }
}
