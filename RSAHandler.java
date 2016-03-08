/* class to create a public and private RSA key and save them to a file
 *   -also contains methods to encrypt/decrypt messages using the keys
 */

public class RSAHandler
{
  private HugePrime p;
  private HugePrime q;
  
  public RSAHandler (HugePrime p, HugePrime q)
  {
    this.p = p;
    this.q = q;
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