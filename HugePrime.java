/* class to hold large prime numbers, mostly for p and q values in RSA algorithms */

import java.io.*;
import java.util.Scanner;
import java.util.Random;


public class HugePrime extends HugeUnsignedInt
{
  //constructs a huge prime from a string (entered by user)
  //Thow exception if number isn't prime, caller catches exception
    //by asking user for another number
  public HugePrime( String number )
  {
    super(number);
    //check if prime?
  }
  
  
  /* constructs huge prime by randomly choosing
   *   a prime from a resource file */  
  public HugePrime()
  {
    this(new HugePrime("0").primeFromFile());
  }
  
  private String primeFromFile()
  {
    String prime = "1";
    try{      
      File file = new File("primeNumbers.rsc");
      Scanner sc = new Scanner(file);
      
      Random rand = new Random();
      int r = rand.nextInt(20); // random int [0,20)
      
      for (int i=0; i < r; i++) //skip past r-1 lines
        sc.nextLine();
      
      prime = sc.next();
    }
    catch (FileNotFoundException fnfe){
      System.out.println("FILE NOT FOUND");
    }
    return prime;
  }
  
  
  //returns true if the caller is a prime number
  //perhaps add this to HugeUnsignedInt class??
  //  this would make verifying primality for e and d
  public boolean isPrime()
  {    
    HugeUnsignedInt i = new HugeUnsignedInt(2);
    HugeUnsignedInt n = this.divide(2);
    for (; i.isLessThan(n) || i.equals(n); i = i.add(1)){
      if (this.modulus(i).equals(0))
        return false;
    }    
    return true;
  }
  
  
  //test code
  public static void main (String[] args)
  {
    //generate random primes from file
    HugePrime p = new HugePrime();
    HugePrime p2 = new HugePrime();
    p.printNum();
    p2.printNum();
    HugePrime p3 = new HugePrime("1048576");
    HugePrime p4 = new HugePrime("1099511627776");   
    HugePrime p5 = new HugePrime("32416190071");
    
    System.out.println(p.isPrime() + " " + p2.isPrime());
    System.out.println(p3.isPrime() + " " + p4.isPrime());
    System.out.println(p5.isPrime());
  }
}