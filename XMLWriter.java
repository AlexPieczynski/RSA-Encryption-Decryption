import java.io.*;

/* 1. Create instance of XMLWriter
 * 2. call makePublicKey or makePrivateKey with e and n values as paramter
 * 3. You're done
 */

public class XMLWriter
{  
  private String e;
  private String n;
  
  public XMLWriter()
  {}
  
  public void makePublicKey(String e, String n)
  {
    this.e = e;
    this.n = n;
    this.makeKey(false);
  }
  
  public void makePrivateKey(String e, String n)
  {
    this.e = e;
    this.n = n;
    this.makeKey(true);
  }
  
  private void makeKey(boolean isPrivate)
  {
    String fname;
    if (isPrivate)
      fname = "prikey.xml";
    else
      fname = "pubkey.xml";
    
    try{
    File file = new File(fname);
    FileWriter fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);
    
    bw.write("<rsakey>");
    bw.newLine();
    
    bw.write("    <evalue>");
    bw.write(e); //put your number as a string here
    bw.write("</evalue>");
    bw.newLine();
    
    bw.write("    <nvalue>");
    bw.write(n); //put your number as a string here
    bw.write("</nvalue>");
    bw.newLine();
    
    bw.write("</rsakey");
    }
    catch(IOException exp){
      exp.printStackTrace();
    }
  }
}