import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.*;
import java.util.Scanner;

/* class to create a public and private RSA key and save them to a file
 *   -also contains methods to encrypt/decrypt messages using the keys
 */

public class RSAHandler
{
  private HugePrime p;   
  private HugePrime q;
  private HugeUnsignedInt n;
  private HugeUnsignedInt l;
  private HugeUnsignedInt e;
  private HugeUnsignedInt d;

 
  public RSAHandler ()
  {}
  
  public void setPriKeyValues(HugeUnsignedInt d, HugeUnsignedInt n){
    this.d = d;
    this.n = n;
  }
  
  public void setPubKeyValues(HugeUnsignedInt e, HugeUnsignedInt n){
    this.e = e;
    this.n = n;
  }
  
  //calculate e,d, n values, store as data members
  public void calcValues(HugePrime p, HugePrime q)
  {
    this.p = p;
    this.q = q;    
    this.n = p.multiply(q);
    
    
    HugeUnsignedInt p1 = p.subtract(1);
    HugeUnsignedInt q1 = q.subtract(1);
    l = p1.multiply(q1);
   
    //how should the user enter the random values for e and d?
    //also have to use euclidean to find e. do we have to implement in HUI?
    //or 3 for basic codes and 65537 (which is 216 + 1) for more secure codes. <-- this sounds like a good lazy way I do this
    //actually sounds like a bad idea
   
    e = new HugeUnsignedInt(7);
    HugeUnsignedInt k= new HugeUnsignedInt("351812273291173183");
    /**
    HugeUnsignedInt loving =  ((l.multiply(k)).add(1)).modulus(e);
    while (loving.equals(0) != true)
    {
      k.add(1);
      loving =  ((l.multiply(k)).add(1)).modulus(e);
    }
    **/
    d = k;
  }
 
  //generates a public-private key set and saves each to a separate file
  public void generateKeys(String filePrefix)
  {
    //look up how to make pretty XML files. then public is e and n, private is d and n
    XMLWriter writer = new XMLWriter();
    writer.makePublicKey(filePrefix, e.toString(),n.toString());
    writer.makePrivateKey(filePrefix, d.toString(),n.toString());
  }
 
 
 public void blockFile(int blockSize, String fileName, String outputFile) throws IOException, FileNotFoundException 
  {
    /* First open the file and give it to a HUI
     * mod the HUI by the blocksize to see how many 00 we need to add
     * then use for loop till blocksize taking the thing
     * to do this backwards have a for loop inside with the size of the number?
     * then add to another file?
     * 
     **/
    
   File file = new File(fileName+".txt");
   File blockedFile = new File(outputFile+".txt");
   blockedFile.createNewFile();
   
   //read whole line file into one string
   Scanner sc = new Scanner(file);
   String wholeFile = sc.useDelimiter("\\Z").next();
   String ascii = "";
   
   for (char c : wholeFile.toCharArray()){
     if (((int) c) == 11)
       ascii = "01"+ascii;
     else{
     switch (c){
       case '\0':
         ascii = "00"+ascii;
         break;
       case '\t':
         ascii = "02"+ascii;
         break;
       case '\n':
         ascii = "03"+ascii;
         break;
       case '\r':
         ascii = "04"+ascii;
         break;
       default:
         int tmp = ((int) c) - 27;
         if (tmp < 10)
           ascii = "0"+String.valueOf(tmp)+ascii;
         else
           ascii = tmp+ascii;
     }
     }
   }
   
   int numOfNull = 0; //numToBeBlocked.modulus(blockSize);
   PrintWriter pw = new PrintWriter(blockedFile);
   
   //add leading 00 if the blockSize is too large
   int numLeadingZero = (ascii.length())%(blockSize*2);
   String zero = "";
   if(numLeadingZero!=0)
   {
     //int zeros = blockSize - numLeadingZero;
    
     while(numLeadingZero != 0)
     {
       zero = zero + "0";
       numLeadingZero--;
     }
   }
   String split = ascii;
   split = zero + split;
  
   int start = split.length()- (blockSize*2);
   int end = split.length() ;
   
   
   for(int i = 0;i < (split.length() / (blockSize*2)) ; i++)
   {
     String temp = "";
     temp = split.substring(start,end);
     start = start - (blockSize*2);
     end = end -  (blockSize*2);
     pw.println(temp);
   }
   pw.close();
  }
 
  public void unblockFile(int blockSize, String fileName,String output)throws IOException, FileNotFoundException 
  {
    /* When we read the line, the number is backwards. How to reverse it?
     * -just put the line into a string then keep adding it then make a HUI
     * WHAT IF WE HAVE LEADING 0 IN THE HUI ERROR
     * 
     * */
   File file = new File(fileName+".txt");
   File blockedFile = new File(output+".txt");
   String line;
   String unblocked="";
   String finalMessage = "";
   BufferedReader bufferedReader =  new BufferedReader(new FileReader(file));
   PrintWriter pw = new PrintWriter(blockedFile);
   
   while((line = bufferedReader.readLine()) != null) 
   {
     finalMessage =line+ finalMessage;
   }   
   HugeUnsignedInt temp1 = new HugeUnsignedInt(finalMessage);
   finalMessage = temp1.toString();
   //pw.println(finalMessage);
   int start =0;
   int end = 2;
   String realMessage = "";
   for(int i = 0; i < finalMessage.length();i=i+2)
   {
     String temp = finalMessage.substring(start,end);
     
     int to = Integer.parseInt(temp);
     
     start+=2;
     end+=2;
     to+=27;
     
     char c = (char) to; 
     realMessage = c + realMessage;
     
   }
   System.out.println(finalMessage);
   System.out.println(realMessage);
   pw.println(realMessage);
   
   // Always close files.
   bufferedReader.close(); 
   pw.close();
   
  }
 
 public void encrypt(int opt, String blockedFile, String keyFile, String outputFile)throws IOException, FileNotFoundException
  {
    //take the e and n value from the key file
    //take in lines from file to be the input
    //output number = (input)^e mod n
   XMLWriter xw = new XMLWriter();
   if (opt == 0)
     xw.getPublicKey(keyFile,this);
   else
     xw.getPrivateKey(keyFile,this);
     
    File file = new File(blockedFile+".txt");
    File out = new File(outputFile+".txt");
    BufferedReader bufferedReader =  new BufferedReader(new FileReader(file));
    PrintWriter pw = new PrintWriter(out);
    String line;
    
    HugeUnsignedInt tem = new HugeUnsignedInt("7");
    while((line = bufferedReader.readLine()) != null) 
    {
      boolean wasOdd = false;
      HugeUnsignedInt temp = new HugeUnsignedInt(line);
      HugeUnsignedInt t1 = temp;
      HugeUnsignedInt t2 = temp;
      if(!(tem.modulus(2).equals( 0)))
      {
        tem = tem.subtract(1);
        wasOdd = true;
      }
      while(!(tem.equals(0)))
      {
        //System.out.println("pls");
        //tem.printNum();
        t1 = t1.multiply(t1);
        tem = tem.divide(2);
        t1=t1.modulus(n);
             // System.out.println(t1);
      }
      if(wasOdd)
      {
        t1 = t1.multiply(t2);
        t1 = t1.modulus(n);
        
      }
      else
      {
        t1 = t1.modulus(n);
      }
      //t1.printNum();
            pw.println(t1.toString());
    }
    
    pw.close();
    bufferedReader.close();
  
  }

  public static void main(String args[]) throws IOException, FileNotFoundException
  {
    HugePrime p = new HugePrime("2");
    HugePrime q = new HugePrime("3");
    
    RSAHandler n = new RSAHandler();
        
    //blockFile(int blockSize, String fileName, String outputFile)
   
         n.blockFile(8,"tobeblocked","hatelife4");
         n.unblockFile(8,"hatelife4","lovelife4");

     
  }
}
