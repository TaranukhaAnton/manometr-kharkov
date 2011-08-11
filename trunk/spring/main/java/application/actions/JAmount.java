package application.actions;

import java.math.BigDecimal;
import java.math.BigInteger;
/**
* Преобразование суммы цифрами в сумму пропись
* @author Aleksey Osipov
* e-mal: aliks-os@yandex.ru
*/
public class JAmount {
    private static class jAmCurrency {
        public CurrencyParam curParam;
        public class CurrencyParam {
            short ID_cur;
            String ISO_name;
            String Full_name;
            String i1;
            String i24;
            String i5;
            String r1;
            String r24;
            String r5;
            String Sex;
        }
        public jAmCurrency(Integer ID_Curr) {
            CurrencyParam[] Curr = new CurrencyParam[5];
            //---
            Curr[2] = new CurrencyParam();
            Curr[2].ID_cur    = 2;
            Curr[2].ISO_name  = "RUR";
            Curr[2].Full_name = "Российские рубли";
            Curr[2].i1        = "рубль";
            Curr[2].i24       = "рубля";
            Curr[2].i5        = "рублей";
            Curr[2].r1        = "копейка";
            Curr[2].r24       = "копейки";
            Curr[2].r5        = "копеек";
            Curr[2].Sex       = "M";
            //---
            Curr[1] = new CurrencyParam();
            Curr[1].ID_cur    = 1;
            Curr[1].ISO_name  = "UAH";
            Curr[1].Full_name = "Украинские гривны";
            Curr[1].i1        = "гривна";
            Curr[1].i24       = "гривны";
            Curr[1].i5        = "гривен";
            Curr[1].r1        = "копейка";
            Curr[1].r24       = "копейки";
            Curr[1].r5        = "копеек";
            Curr[1].Sex       = "F";
            //---
            Curr[3] = new CurrencyParam();
            Curr[3].ID_cur    = 3;
            Curr[3].ISO_name  = "USD";
            Curr[3].Full_name = "Доллары США";
            Curr[3].i1        = "доллар";
            Curr[3].i24       = "доллара";
            Curr[3].i5        = "долларов";
            Curr[3].r1        = "цент";
            Curr[3].r24       = "цента";
            Curr[3].r5        = "центов";
            Curr[3].Sex       = "M";
            //---
            Curr[4] = new CurrencyParam();
            Curr[4].ID_cur    = 4;
            Curr[4].ISO_name  = "EUR";
            Curr[4].Full_name = "Евро";
            Curr[4].i1        = "евро";
            Curr[4].i24       = "евро";
            Curr[4].i5        = "евро";
            Curr[4].r1        = "цент";
            Curr[4].r24       = "цента";
            Curr[4].r5        = "центов";
            Curr[4].Sex       = "M";
            curParam = Curr[ID_Curr];
        }
    }
    private BigInteger summ;
    private static final BigInteger zero     = new BigInteger ("0");
    private static final BigInteger hundred  = new BigInteger ("100");
    private static final BigInteger thousand = new BigInteger ("1000");
    private final jAmCurrency.CurrencyParam curParam;
    // Конструктор класса
    public JAmount(int Currency, String s) {
        curParam = new jAmCurrency(Currency).curParam;
        s=s.replaceAll(",", ".");
        try {
            BigDecimal decimal = new BigDecimal (s);
            // Преобразуем в копейки (центы, пфенниги и т.д.),
            // одним словом - убираем дробную часть
            decimal = decimal.multiply (new BigDecimal (100.00));
            summ = decimal.toBigInteger ();
            // Приступить к преобразованию
            toString ();
        }
        catch (NumberFormatException e) {
            // Ой !!!!! Что-то не так: скорее всего, в строке
            // представляющей собой сумму цифрами, встретились символы
            // отличные от цифр и точки. Можно просто выводить сообщение
            // об ошибках на консоль:
            System.out.println(e);
            System.out.println("Ошибка преобразование суммы");
        }
    }
    // Получить правую (дробную) часть суммы
    public String getRightPart () {
        return alignSumm (summ.remainder (hundred).abs ().toString ());
    }
    // Если сумма меньше 10, то выровнять ее дописыванием "0"
    String alignSumm (String s) {
        switch (s.length ()) {
            case 0: s = "00"; break;
            case 1: s = "0" + s; break;
        }
        return s;
    }
    @Override
    public String toString () {
        StringBuffer result = new StringBuffer ();
        BigInteger [] divrem = summ.divideAndRemainder (hundred);
        if (divrem [0].signum () == 0) result.append ("Ноль ");
        divrem = divrem [0].divideAndRemainder (thousand);
        BigInteger quotient  = divrem [0];
        BigInteger remainder = divrem [1];
        int group = 0;
        do {
           int value = remainder.intValue ();
           result.insert (0, groupToString (value, group));
           // Для нулевой группы добавим в конец соответствующую валюту
           if (group == 0) {
               int rank10 = (value % 100) / 10;
               int rank = value % 10;
               if (rank10 == 1) {
                   result = result.append (curParam.i5);
               }
               else {
                    switch (rank) {
                    case 1: result = result.append (curParam.i1); break;
                    case 2:
                    case 3:
                    case 4: result = result.append (curParam.i24); break;
                   default: result = result.append (curParam.i5); break;
                    }
               }
           }
           divrem = quotient.divideAndRemainder (thousand);
           quotient  = divrem [0];
           remainder = divrem [1];
           group++;
        }
        while (!quotient.equals (zero) || !remainder.equals (zero));
        // Дробная часть суммы
        String s = getRightPart ();
        result = result.append (" ").append (s);
        result = result.append (" ");
        if (s.charAt (0) == '1') {
            result = result.append (curParam.r5);
        }
        else {
             switch (s.charAt(1)) {
             case '1': result = result.append (curParam.r1); break;
             case '2':
             case '3':
             case '4': result = result.append (curParam.r24); break;
             default:  result = result.append (curParam.r5); break;
            }
        }
        // По правилам бухгалтерского учета первая буква строкового
        // представления должна быть в верхнем регистре
        result.setCharAt (0, Character.toUpperCase (result.charAt (0)));
        return result.toString();
    }
    // Преобразование группы цифр в строку
    String groupToString (int value, int group) {
        if (value < 0 || value > 999) throw new IllegalArgumentException ("value must be between 0 an 999 inclusively");
        if (group < 0) throw new IllegalArgumentException ("group must be more than 0");
        StringBuffer result = new StringBuffer (32);
        if (value == 0) {
            return result.toString();
        }
        // Разбор числа по разрядам, начиная с сотен
        int rank = value / 100;
        switch (rank) {
        default: break;
        case 1:  result = result.append ("сто ");       break;
        case 2:  result = result.append ("двести ");    break;
        case 3:  result = result.append ("триста ");    break;
        case 4:  result = result.append ("четыреста "); break;
        case 5:  result = result.append ("пятьсот ");   break;
        case 6:  result = result.append ("шестьсот ");  break;
        case 7:  result = result.append ("семьсот ");   break;
        case 8:  result = result.append ("восемьсот "); break;
        case 9:  result = result.append ("девятьсот "); break;
        }
        // Далее, десятки
        rank = (value % 100) / 10;
        switch (rank) {
        default: break;
        case 2:  result = result.append ("двадцать ");    break;
        case 3:  result = result.append ("тридцать ");    break;
        case 4:  result = result.append ("сорок ");       break;
        case 5:  result = result.append ("пятьдесят ");   break;
        case 6:  result = result.append ("шестьдесят ");  break;
        case 7:  result = result.append ("семьдесят ");   break;
        case 8:  result = result.append ("восемьдесят "); break;
        case 9:  result = result.append ("девяносто ");   break;
        }
        // Если десятки = 1, добавить соответствующее значение. Иначе - единицы
        int rank10 = rank;
        rank = value % 10;
        if (rank10 == 1) {
            switch (rank) {
            case 0: result = result.append ("десять ");       break;
            case 1: result = result.append ("одиннадцать ");  break;
            case 2: result = result.append ("двенадцать ");   break;
            case 3: result = result.append ("тринадцать ");   break;
            case 4: result = result.append ("четырнадцать "); break;
            case 5: result = result.append ("пятнадцать ");   break;
            case 6: result = result.append ("шестнадцать ");  break;
            case 7: result = result.append ("семнадцать ");   break;
            case 8: result = result.append ("восемнадцать "); break;
            case 9: result = result.append ("девятнадцать "); break;
            }
        }
        else {
            switch (rank) {
            default:
                 break;
            case 1:
                 if (group == 1) // Тысячи
                     result = result.append ("одна ");
                 else {
                    // Учесть род валюты (поле "Sex" настроечной информации)
                    if (curParam.Sex.equals ("M")) result = result.append ("один ");
                    if (curParam.Sex.equals ("F")) result = result.append ("одна ");
                 }
                 break;
            case 2:
                 if (group == 1) // Тысячи
                     result = result.append ("две ");
                 else {
                    // Учесть род валюты (поле "Sex" настроечной информации)
                    if (curParam.Sex.equals ("M")) result = result.append ("два ");
                    if (curParam.Sex.equals ("F")) result = result.append ("две ");
                 }
                 break;
            case 3: result = result.append ("три ");    break;
            case 4: result = result.append ("четыре "); break;
            case 5: result = result.append ("пять ");   break;
            case 6: result = result.append ("шесть ");  break;
            case 7: result = result.append ("семь ");   break;
            case 8: result = result.append ("восемь "); break;
            case 9: result = result.append ("девять "); break;
            }
        }
        // Значение группы: тысячи, миллионы и т.д.
        switch (group) {
        default:
             break;
        case 1:
             if (rank10 == 1)
                 result = result.append ("тысяч ");
                else {
                switch (rank) {
                default: result = result.append ("тысяч ");  break;
                case 1:  result = result.append ("тысяча "); break;
                case 2:
                case 3:
                case 4:  result = result.append ("тысячи "); break;
                }
             }
             break;
        case 2:
             if (rank10 == 1)
                 result = result.append ("миллионов ");
             else {
                  switch (rank) {
                  default: result = result.append ("миллионов "); break;
                  case 1:  result = result.append ("миллион ");   break;
                  case 2:
                  case 3:
                  case 4:  result = result.append ("миллиона ");  break;
                  }
             }
             break;
        case 3:
             if (rank10 == 1)
                 result = result.append ("миллиардов ");
             else {
                  switch (rank) {
                  default: result = result.append ("миллиардов "); break;
                  case 1:  result = result.append ("миллиард ");   break;
                  case 2:
                  case 3:
                  case 4:  result = result.append ("миллиарда ");  break;
                  }
             }
            break;
        case 4:
             if (rank10 == 1)
                 result = result.append ("триллионов ");
             else {
                  switch (rank) {
                  default: result = result.append ("триллионов "); break;
                  case 1:  result = result.append ("триллион ");   break;
                  case 2:
                  case 3:
                  case 4:  result = result.append ("триллиона ");  break;
                  }
             }
             break;
        }
        return result.toString();
    }
  }
