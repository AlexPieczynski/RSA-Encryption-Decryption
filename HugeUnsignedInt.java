/* Class to represent unsigned integers too large to fit into Java primative data types */
import java.util.Arrays;

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
    this.removeLZ();
  }
  
  //converts integer parameter to a string, then calls other constructor
  //mainly used to quickly convert int to HugeUnsignedInt for calculations
  public HugeUnsignedInt( int num ){
    this(String.valueOf(num));
  }
  
  
  //removes leading zeros
  public void removeLZ()
  {
    int i;
    for (i=0; i < this.size; i++){
      if (digits[size-1-i] != 0)
        break;
    }
    
    byte[] temp = new byte[cap];
    System.arraycopy(this.digits, 0, temp, 0, size-i);
    digits = temp;
    size = size-i;
  }
  
  
  //gets the first n digits of a HUI and returns as an HUI
  private HugeUnsignedInt getNDigits (int n)
  {
    HugeUnsignedInt result = new HugeUnsignedInt(0);
    result.size = n;
    result.cap = this.cap;
    result.digits = new byte[result.cap];
    
    for (int i=0; i < n; i++)
      result.digits[i] = this.digits[n-1+i];
      
    return result;
  }
  
  
  //returns ith digit as a string with one char
  private String getIthDigit( int i )
  {
    return Integer.toString(this.digits[this.size-1-i]);
  }
  
  
  //returns String representation of HugeUnsignedInt
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    for (int i=0; i < this.size; i++){
      builder.insert(0, this.digits[i]);
    }
    return builder.toString();
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
    if (this.isLessThan(b)){
      System.out.println("ERROR: subtraction would have resulted in negative number");
      return new HugeUnsignedInt(0);
    }
    
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
      else{
        c = 0;
      }
      difference[i] = (byte) d;
    }
    
    n = this.size;
    //fill in rest of array
    for (; i < n; i++){
      d = this.digits[i] - c;
      if (d < 0){
        d = d+10;
        c = 1;
      }
      difference[i] = (byte) d; 
    }
    
    result.digits = difference;
    result.size = this.size; //change this?
    result.cap = this.cap;
    result.removeLZ();
    return result;
  }
  
  public HugeUnsignedInt multiply( int b ){
    return this.multiply(new HugeUnsignedInt(b));
  }
  
  public HugeUnsignedInt multiply( HugeUnsignedInt b )
  {
    int a =0;
    int c =0;
    byte[] mul = new byte[(this.size + b.size) ];
    for(int i = 0; i < (this.size + b.size) ;i++)
    {
      mul[i] = 0;
    }
    for(int i = 0; i < b.size; i++)
    {
      for(int j = 0; j < this.size; j++)
      {
        int s = this.digits[j] * b.digits[i];
        
        if(s>=10){
          a = s%10;
          c = s/10;
        }
        else{
          a=s%10;
          c=0;
        }
        mul[i+j]+= a;
        mul[i+j+1]+=c;
        while(mul[i+j] >=10)
        {
          mul[i+j+1] += mul[i+j] /10;
          mul[i+j] = (byte)( mul[i+j]%10);
        }
        
      }
    }
    
    StringBuilder builder = new StringBuilder();
    for (int i=0; i < this.size +b.size; i++){
      builder.insert(0, mul[i]);
    }
    String derp = builder.toString();
    HugeUnsignedInt d = new HugeUnsignedInt(derp);
    return d;
  }
  
  
  //divide two HUI using long division
  public HugeUnsignedInt divide( int b ){
    return this.divide(new HugeUnsignedInt(b));
  }
  
  public HugeUnsignedInt divide( HugeUnsignedInt b )
  {
    //throw exception?
    if (b.equals(new HugeUnsignedInt(0))){
      System.out.println("DIVIDE BY ZERO ERROR");
      return this;
    }
    //maybe add check to see if divisor is larger than dividend? result will be zero
    
    int newCap = this.cap;
    byte[] quotient = new byte[newCap];
    
    int i,j;
    HugeUnsignedInt temp = new HugeUnsignedInt(0);
    String nd; //new divisor
    int n = this.size; //loop through all digits of dividend
    for (i=0; i < n; i++){
      nd = temp.toString().concat(this.getIthDigit(i));
      temp = new HugeUnsignedInt(nd);
      //System.out.print("no sub yet ");
      //temp.printNum();
      for (j=0; temp.isGreaterThan(b) || temp.equals(b); j++){ //count how many times divisor goes into temp
        //System.out.print("temp before sub is ");
        //temp.printNum();
        temp = temp.subtract(b);
        //System.out.print("temp after sub is ");
        //temp.printNum();
        //if (!temp.isGreaterThan(b))
        //  System.out.println("next call will fail");
      }
      //System.out.println("j is " + j);
      quotient[n-1-i] = (byte) j;      
    }
    
    StringBuilder builder = new StringBuilder();
    for (i=0; i < n; i++){
      builder.insert(0, quotient[i]);
    }
    return new HugeUnsignedInt(builder.toString());
  }
  
  
  public HugeUnsignedInt modulus( int b ){
    return this.modulus(new HugeUnsignedInt(b));
  }
  
  public HugeUnsignedInt modulus( HugeUnsignedInt b )
  {
    return this;
  }
  
  
  public boolean equals( int b ){
    return this.equals(new HugeUnsignedInt(b));
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
  
  
  public boolean isLessThan( int b ){
    return this.isLessThan(new HugeUnsignedInt(b));
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
        else if (this.digits[size-1-i] > b.digits[size-1-i])
          return false;
      }
      return false; //they are the same
    }
  }
  
  
  public boolean isGreaterThan( int b ){
    return this.isGreaterThan(new HugeUnsignedInt(b));
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
        else if (this.digits[size-1-i] < b.digits[size-1-i])
          return false;
      }
    }
    
    return false; //they are the same
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
    sum = sum.add(new HugeUnsignedInt("123123"));
    sum.printNum();    
    sum = new HugeUnsignedInt("99999999999999999999999999999999");
    sum = sum.add(new HugeUnsignedInt(1));
    sum.printNum();
    String str = sum.toString();
    System.out.println(sum);
    
    //remove leading zeroes
    HugeUnsignedInt lz = new HugeUnsignedInt("000002");
    lz.printNum();
    
    //test subtraction
    HugeUnsignedInt dif = new HugeUnsignedInt(10000);
    HugeUnsignedInt d2  = new HugeUnsignedInt(9);
    dif = dif.subtract(d2);
    dif.printNum();
    
    dif = new HugeUnsignedInt(101);
    dif = dif.subtract(2);
    dif.printNum();
    
    //test division
    System.out.println("DIVISION");
    HugeUnsignedInt div = new HugeUnsignedInt(100);
    div = div.divide(new HugeUnsignedInt(5));
    div.printNum();
    
    div = new HugeUnsignedInt("1234752");
    div = div.divide(new HugeUnsignedInt(336));
    div.printNum();
    
    div = new HugeUnsignedInt("345020140034");
    div = div.divide(new HugeUnsignedInt("12345432"));
    div.printNum();
  }
}
