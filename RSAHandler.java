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

 
  public RSAHandler (HugePrime p, HugePrime q)
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
   
    HugeUnsignedInt k;
   
   
  }
 
  
 
  //generates a public-private key set and saves each to a separate file
  public void generateKeys()
  {
    //look up how to make pretty XML files. then public is e and n, private is d and n
  }
 
 
  public void blockFile(int blockSize, String fileName)
  {
    /* First open the file and give it to a HUI
     * mod the HUI by the blocksize to see how many 00 we need to add
     * then use for loop till blocksize taking the thing
     * to do this backwards have a for loop inside with the size of the number?
     * then add to another file?
     * 
     **/
  }
 
  public void unblockFile(int blockSize, String fileName)
  {
    /* When we read the line, the number is backwards. How to reverse it?
     * -just put the line into a string then keep adding it then make a HUI
     * WHAT IF WE HAVE LEADING 0 IN THE HUI ERROR
     * 
     * */
  }
 
  public void encrypt(String blockedFile, String keyFile, String outputFile)
  {
  }
 
  public void decrypt()
  {
  }
}
