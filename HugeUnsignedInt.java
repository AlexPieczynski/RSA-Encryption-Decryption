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
    //Hey alex what happens if the user adds leading 0? should we take care of that?
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
  
  
  //adds two HugeUnsignedInts, returns result
  //TODO: perhaps change parameter to int, making int constructor unnecessary?
  public HugeUnsignedInt add( int b ){
    return this.add(new HugeUnsignedInt(b));
  }
  
  public HugeUnsignedInt add( HugeUnsignedInt b )
  {
    HugeUnsignedInt result = new HugeUnsignedInt(0);
    
    int s = 0; //sum of digits and carry
    int c = 0; //carry
    HugeUnsignedInt bigger, smaller;
    
    if (this.isLessThan(b)){
      bigger = b;
      smaller = this;
    }
    else{
      bigger = this;
      smaller = b;
    }
    
    int newCap = bigger.cap; 
    byte[] sum = new byte[newCap]; //size of result array 
    
    int n = smaller.size; //number of digits to loop through
    //sum numbers
    int i;
    for (i=0; i < n; i++){
      s = this.digits[i] + b.digits[i] + c;
      if (s >= 10){
        c = s/10;
        s = s%10;
      }
      else
        c = 0;
      sum[i] = (byte) s; 
    }
    
    n = bigger.size;
    //fill in rest of array
    for (; i < n; i++){
      s = bigger.digits[i] + c;
      if (s >= 10){
        c = s/10;
        s = s%10;
      }
      sum[i] = (byte) s; 
    }
    
    if (c > 0){
      if (i >= newCap){
        //resize array, copy contents, update newCap
        byte[] d2 = new byte[newCap*2];
        System.arraycopy(sum, 0, d2, 0, newCap);
        newCap = newCap*2;
        sum = d2;
      }
      sum[i] = (byte) c;
      i++;
    }
    
    result.digits = sum;
    result.size = i;
    result.cap = newCap;
    return result;
  }
  
  
  //subtracts parameter HugeUnsignedInt from caller HugeUnsignedInt
  //report error if result would be negative
  public HugeUnsignedInt subtract( int b ){
    return this.subtract(new HugeUnsignedInt(b));
  }
  
  public HugeUnsignedInt subtract( HugeUnsignedInt b )
  {
    //throw exception?
    if (this.isLessThan(b))
      return new HugeUnsignedInt(0);
    
    HugeUnsignedInt result = new HugeUnsignedInt(0);
    byte[] difference = new byte[this.cap]; //size of result array 
    
    int d = 0; //difference betweens digits and carry
    int c = 0; //carry
    
    int n = b.size; //number of digits to loop through
    int i;
    for (i=0; i < n; i++){
      d = this.digits[i] - b.digits[i] - c;
      if (d < 0){
        d = d+10;
        c = 1;
      }
      else
        c = 0;
      
      difference[i] = (byte) d;
    }
    return b;
  }
  
  public HugeUnsignedInt multiply( int b ){
    return this.multiply(new HugeUnsignedInt(b));
  }
  
  public HugeUnsignedInt multiply( HugeUnsignedInt b )
  {
    return b;
  }
  
  
  public HugeUnsignedInt divide( int b ){
    return this.divide(new HugeUnsignedInt(b));
  }
  
  public HugeUnsignedInt divide( HugeUnsignedInt b )
  {
    //throw exception?
    if (b.equals(new HugeUnsignedInt(0)))
      return this;
    return this;
  }
  
  
  public HugeUnsignedInt modulus( int b ){
    return this.modulus(new HugeUnsignedInt(b));
  }
  
  public HugeUnsignedInt modulus( HugeUnsignedInt b )
  {
    return this;
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
    System.out.println("relational Assertions passed");
    
    //test addition
    HugeUnsignedInt sum = new HugeUnsignedInt("123");
    sum.add(new HugeUnsignedInt("123123"));
    sum.printNum();    
    sum = new HugeUnsignedInt("99999999999999999999999999999999");
    sum.add(new HugeUnsignedInt(1));
    sum.printNum();
    
    
  }
}
