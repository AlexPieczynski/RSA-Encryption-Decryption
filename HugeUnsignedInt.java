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
    
    //fill array with digits, starting with ones place
    for (int i=0; i < n; i++)
      digits[i] = (byte) Character.getNumericValue(num.charAt(n-1-i));
    
    size = n;
  }
  
  //converts integer parameter to a string, then calls other constructor
  //mainly used to quickly convert int to HugeUnsignedInt for calculations
  public HugeUnsignedInt( int num )
  {
    this(String.valueOf(num));
  }
  
  
  //adds two HugeUnsignedInts, stores result in caller
  //TODO: perhaps change parameter to int, making int constructor unnecessary?
  public void add( HugeUnsignedInt b )
  {
    int c; //carry
    if ( this.size > b.size ){
      
    }
      
    
  }
  
  
  //subtracts parameter HugeUnsignedInt from caller HugeUnsignedInt
  //report error if result would be negative
  public void subtract( HugeUnsignedInt b )
  {
  }
  
  
  //returns true if two HUI are the same, false if not
  public boolean equals( HugeUnsignedInt b )
  {
    if ( this.size == b.size ){
      for (int i=0; i < size; i++){
        if (this.digits[i] != b.digits[i])
          return false;
      }
      return true;
    }
    
    return false;
  }
  
  
  //returns true if caller is less than parameter
  public boolean isLessThan( HugeUnsignedInt b )
  {
    if (this.size < b.size)
      return true;
    else if (this.size > b.size)
      return false;
    else{
      for (int i=0; i < size; i++){
        if (this.digits[size-1-i] < b.digits[size-1-i])
          return true;
      }
      return false;
    }
  }
  
  
  //returns true if caller is greater than parameter
  public boolean isGreaterThan( HugeUnsignedInt b )
  {
    if (this.size > b.size)
      return true;
    else if (this.size < b.size)
      return false;
    else{
      for (int i=0; i < size; i++){
        if (this.digits[size-1-i] > b.digits[size-1-i])
          return true;
      }
      return false;
    }
  }
  
  //prints out a HugeUnsignedInt (must reverse digits)
  public void printNum()
  {
    for (int i=0; i < size; i++)
      System.out.print(digits[size-1-i]);
    System.out.println();
  }
  
  
  //some simple test code
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
    
    //test relational operators
    HugeUnsignedInt small = new HugeUnsignedInt("123456");
    HugeUnsignedInt small2 = new HugeUnsignedInt(123456);
    HugeUnsignedInt med = new HugeUnsignedInt("123456789");
    HugeUnsignedInt med2 = new HugeUnsignedInt("123456788");
    
    assert small.equals(small2);
    assert small.isLessThan(med);
    assert !(small.isGreaterThan(med));
    assert !(med.equals(med2));
    assert med.isGreaterThan(med2);
    assert !(med.isLessThan(med2));
    System.out.println("Assertions passed");
  }
}