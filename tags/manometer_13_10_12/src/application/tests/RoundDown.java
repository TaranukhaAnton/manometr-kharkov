package application.tests;

import java.math.BigDecimal;


public class RoundDown
{
  private static Integer[] koef = {1000, 1, 10, 100000, 1000, 1, 10, 100000, 10, 10000};
  public RoundDown()
  {
  }

  public static double roundDownScale2(double aValue)
  {
    BigDecimal decimal = new BigDecimal(aValue);
//    decimal = decimal.setScale(10,BigDecimal.ROUND_UP);
    decimal = decimal.setScale(3,BigDecimal.ROUND_DOWN);
    double result = decimal.doubleValue();
    return result;
  }

  public static void main(String[] args)
  {
   float hiLimit = 6.3f;
     int ed_izm = 0;

   double value = ((hiLimit / 2) > (0.1 * koef[ed_izm])) ? (hiLimit - 0.1 * koef[ed_izm] ): (hiLimit / 2) ;
    System.out.println("res = " + value);

   // double value = 0.63;
    double valueRounded = roundDownScale2(value);

    System.out.println("value = " + value);
    System.out.println("valueRounded = " + valueRounded);

    value = value /2;
    valueRounded = roundDownScale2(value);
    System.out.println("value = " + value);
    System.out.println("valueRounded = " + valueRounded);

  }

}
