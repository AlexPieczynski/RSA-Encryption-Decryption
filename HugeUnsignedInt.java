/* Class to represent unsigned integers too large to fit into Java primative data types */

public class HugeUnsignedInt
{
  public int size = 0; //number of digits in number
  private int cap = 32; //default max number of digits in array
  private byte[] digits; //array to hold digits 0-9
  
  
  //converts a string into a HugeUnsignedInt
  public HugeUnsignedInt( String num )
  {
    //make array large enough to hold number
    int n = num.length();
    while (cap < n)
      cap = cap*2;
    digits = new byte[cap];
    
    //fill array with digits
    for (int i=0; i < n; i++)
      digits[i] = (byte) Character.getNumericValue(num.charAt(i));
    
    size = n;
  }
  
  //converts integer parameter to a string, then calls other constructor
  //mainly used to quickly convert int to HugeUnsignedInt for calculations
  public HugeUnsignedInt( int num )
  {
    this(String.valueOf(num));
  }
  
  
  //prints out a HugeUnsignedInt
  public void printNum()
  {
    for (int i=0; i < size; i++)
      System.out.print(digits[i]);
    System.out.println();
  }
  
  public static void main(String[] args)
  {
    //test for string
    String largeNumber = "123456789123456789123456789123456789";
    System.out.println("making number of length " + largeNumber.length());
    HugeUnsignedInt num = new HugeUnsignedInt(largeNumber);
    num.printNum();
    
    //test for integer
    HugeUnsignedInt n2 = new HugeUnsignedInt(Integer.MAX_VALUE);
    n2.printNum();
  }
}