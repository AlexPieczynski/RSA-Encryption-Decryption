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
   
    HugeUnsignedInt k;
   
   
  }
 
 
  //generates a public-private key set and saves each to a separate file
  public void generateKeys()
  {
  }
 
 
  public void blockFile(int blockSize, String fileName)
  {
  }
 
  public void unblockFile(int blockSize, String fileName)
  {
  }
 
  public void encrypt(String blockedFile, String keyFile, String outputFile)
  {
  }
 
  public void decrypt()
  {
  }
}
