/* class to hold large prime numbers, mostly for p and q values in RSA algorithms */

public class HugePrime extends HugeUnsignedInt
{
  //constructs a huge prime from a string (entered by user)
  //Thow exception if number isn't prime, caller catches exception
    //by asking user for another number
  public HugePrime( String number )
  {
    super(number);
    //check if prime
  }
  
  
  /* constructs huge prime by randomly choosing
   *   a prime from a resource file */  
  public HugePrime()
  {
    super("todo");
  }
  
  
  //returns true if the caller is a prime number
  //perhaps add this to HugeUnsignedInt class??
  //  this would make verifying primality for e and d
  public boolean isPrime()
  {
    return true;
  }
}