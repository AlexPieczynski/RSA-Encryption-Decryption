import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.*;

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
  
  //calculate e,d, n values
  public void calcValues(HugePrime p, HugePrime q)
  {
    this.p = p;
    this.q = q;
    n = p.multiply(q);
   
    //Should we have subtract be able to enter ints instead of a HUI? FIXED - ALEX
    //Will making a HUI with a "1" be too much?
   
    HugeUnsignedInt p1 = p.subtract(1);
    HugeUnsignedInt q1 = q.subtract(1);
    l = p1.multiply(q1);
   
    //how should the user enter the random values for e and d?
    //also have to use euclidean to find e. do we have to implement in HUI?
    //or 3 for basic codes and 65537 (which is 216 + 1) for more secure codes. <-- this sounds like a good lazy way I do this
    //actually sounds like a bad idea
   
    e = new HugeUnsignedInt(65537);
    HugeUnsignedInt k= new HugeUnsignedInt(1);
    
    HugeUnsignedInt loving =  ((l.multiply(k)).add(1)).modulus(e);
    while (loving.equals(0) != true)
    {
      k.add(1);
      loving =  ((l.multiply(k)).add(1)).modulus(e);
    }
    d = loving;
  }
 
  //generates a public-private key set and saves each to a separate file
  public void generateKeys()
  {
    //look up how to make pretty XML files. then public is e and n, private is d and n
    XMLWriter writer = new XMLWriter();
    writer.makePublicKey(e.toString(),n.toString());
    writer.makePrivateKey(d.toString(),n.toString());
  }
 
 
  public void blockFile(int blockSize, String fileName) throws IOException, FileNotFoundException 
  {
    /* First open the file and give it to a HUI
     * mod the HUI by the blocksize to see how many 00 we need to add
     * then use for loop till blocksize taking the thing
     * to do this backwards have a for loop inside with the size of the number?
     * then add to another file?
     * 
     **/
    
   File file = new File(fileName);
   File blockedFile = new File(outputFile);
   BufferedReader br = new BufferedReader(new FileReader(file));
   //File should have one line which is the large number
   String number = br.readLine();
   br.close()
   HugeUnsignedInt numToBeBlocked = new HugeUnsignedInt(number);
   int numOfNull = 0; //numToBeBlocked.modulus(blockSize);
   PrintWriter pw = new PrintWriter(blockedFile);
   
   /**
   for(int i = (numToBeBlocked.size);i > 0;i= (i-blockSize) )
   {
    //Need some way of accessing the numbers to split it up. Or is there some better way?
    //REDO THIS
    String split = numToBeBlocked.toString();
    for(int j = 0; j<blockSize;j=j+2){
      split.substring(i-2, i);
      pw.println(split);
    }
    //add leading 00 if the blockSize is too large
    
   }
   **/
   int numLeadingZero = ((numToBeBlocked.size)%(blockSize*2));
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
   String split = numToBeBlocked.toString();
   split = zero + split;
   
   int start = split.length()- (blockSize*2);
   int end = split.length() ;
   int derpd = (split.length() % (blockSize*2));
  
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
 
  public void unblockFile(int blockSize, String fileName)throws IOException, FileNotFoundException 
  {
    /* When we read the line, the number is backwards. How to reverse it?
     * -just put the line into a string then keep adding it then make a HUI
     * WHAT IF WE HAVE LEADING 0 IN THE HUI ERROR
     * 
     * */
   File file = new File(fileName);
   String line;
   String unblocked="";
   String finalMessage = "";
   BufferedReader bufferedReader =  new BufferedReader(new FileReader(file));
   
   while((line = bufferedReader.readLine()) != null) 
   {
     //THIS DOES NOT WORK
     for(int i = 0; i < blockSize; i= i+2)
     {
       //get the ascii value?
       String temp = line.substring(i,i+2);
       int value = Integer.parseInt(temp);
       unblocked = unblocked + Character.toString((char)value);
     }
     finalMessage = finalMessage + unblocked;
   }   
   
   // Always close files.
   bufferedReader.close(); 
   
  }
 
 public void encrypt(String blockedFile, String keyFile, String outputFile)throws IOException, FileNotFoundException
  {
    //take the e and n value from the key file
    //take in lines from file to be the input
    //output number = (input)^e mod n
    File file = new File(blockedFile);
    File out = new File(outputFile);
    BufferedReader bufferedReader =  new BufferedReader(new FileReader(file));
    PrintWriter pw = new PrintWriter(out);
    String line;
    while((line = bufferedReader.readLine()) != null) 
    {
      HugeUnsignedInt temp = new HugeUnsignedInt(line);
      //temp =temp.expo(this.e);
      //temp = temp.mod(n);
      pw.println(temp.toString());
    }
    pw.close();
    bufferedReader.close();
  }
 
  public void decrypt(String encryptFile, String keyFile,String outputFile)throws IOException, FileNotFoundException
  {
    //take the d and n value from the key file
    //output number = (input)^d mod n
    File file = new File(encryptFile);
    File out = new File(outputFile);
    BufferedReader bufferedReader =  new BufferedReader(new FileReader(file));
    PrintWriter pw = new PrintWriter(out);
    String line;
    while((line = bufferedReader.readLine()) != null) 
    {
      HugeUnsignedInt temp = new HugeUnsignedInt(line);
      //temp =temp.expo(this.d);
      //temp = temp.mod(n);
      pw.println(temp.toString());
    }
    pw.close();
    bufferedReader.close();
  
  }
}
