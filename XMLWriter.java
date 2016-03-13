import java.io.*;

public class XMLWriter
{
  public XMLWriter( String e, String n )
  {
    try{
    File file = new File("pubkey.xml");
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