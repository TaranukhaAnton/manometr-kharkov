package ua.com.manometer.util.amount;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Преобразование суммы цифрами в сумму пропись
 *
 * @author Aleksey Osipov
 *         e-mal: aliks-os@yandex.ru
 */
public class JAmountUA implements JAmount {
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
            Curr[2].ID_cur = 2;
            Curr[2].ISO_name = "RUR";
            Curr[2].Full_name = "російські рублі";
            Curr[2].i1 = "рубль";
            Curr[2].i24 = "рубля";
            Curr[2].i5 = "рублів";
            Curr[2].r1 = "копійка";
            Curr[2].r24 = "копійки";
            Curr[2].r5 = "копійок";
            Curr[2].Sex = "M";
            //---
            Curr[1] = new CurrencyParam();
            Curr[1].ID_cur = 1;
            Curr[1].ISO_name = "UAH";
            Curr[1].Full_name = "українські гривні";
            Curr[1].i1 = "гривня";
            Curr[1].i24 = "гривні";
            Curr[1].i5 = "гривень";
            Curr[1].r1 = "копійка";
            Curr[1].r24 = "копійки";
            Curr[1].r5 = "копійок";
            Curr[1].Sex = "F";
            //---
            Curr[3] = new CurrencyParam();
            Curr[3].ID_cur = 3;
            Curr[3].ISO_name = "USD";
            Curr[3].Full_name = "Долары США";
            Curr[3].i1 = "долар";
            Curr[3].i24 = "долара";
            Curr[3].i5 = "доларів";
            Curr[3].r1 = "цент";
            Curr[3].r24 = "цента";
            Curr[3].r5 = "центів";
            Curr[3].Sex = "M";
            //---
            Curr[4] = new CurrencyParam();
            Curr[4].ID_cur = 4;
            Curr[4].ISO_name = "EUR";
            Curr[4].Full_name = "євро";
            Curr[4].i1 = "євро";
            Curr[4].i24 = "євро";
            Curr[4].i5 = "євро";
            Curr[4].r1 = "цент";
            Curr[4].r24 = "цента";
            Curr[4].r5 = "центів";
            Curr[4].Sex = "M";
            curParam = Curr[ID_Curr];
        }
    }

    private BigInteger summ;
    private static final BigInteger zero = new BigInteger("0");
    private static final BigInteger hundred = new BigInteger("100");
    private static final BigInteger thousand = new BigInteger("1000");
    private jAmCurrency.CurrencyParam curParam;

    // Конструктор класса
    public JAmountUA() {
    }


    public String getAmount(int Currency , BigDecimal decimal) {
        curParam = new jAmCurrency(Currency).curParam;
       // s = s.replaceAll(",", ".");
        try {
          //  BigDecimal decimal = new BigDecimal(s);
            // Преобразуем в копейки (центы, пфенниги и т.д.),
            // одним словом - убираем дробную часть
            decimal = decimal.multiply(new BigDecimal(100.00));
            summ = decimal.toBigInteger();
            // Приступить к преобразованию
            return toString();
        } catch (NumberFormatException e) {
            // Ой !!!!! Что-то не так: скорее всего, в строке
            // представляющей собой сумму цифрами, встретились символы
            // отличные от цифр и точки. Можно просто выводить сообщение
            // об ошибках на консоль:
            System.out.println(e);
            System.out.println("Ошибка преобразование суммы");
            return "Error";
        }
    }

    // Получить правую (дробную) часть суммы
    public String getRightPart() {
        return alignSumm(summ.remainder(hundred).abs().toString());
    }

    // Если сумма меньше 10, то выровнять ее дописыванием "0"
    String alignSumm(String s) {
        switch (s.length()) {
            case 0:
                s = "00";
                break;
            case 1:
                s = "0" + s;
                break;
        }
        return s;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        BigInteger[] divrem = summ.divideAndRemainder(hundred);
        if (divrem[0].signum() == 0) result.append("Нуль ");
        divrem = divrem[0].divideAndRemainder(thousand);
        BigInteger quotient = divrem[0];
        BigInteger remainder = divrem[1];
        int group = 0;
        do {
            int value = remainder.intValue();
            result.insert(0, groupToString(value, group));
            // Для нулевой группы добавим в конец соответствующую валюту
            if (group == 0) {
                int rank10 = (value % 100) / 10;
                int rank = value % 10;
                if (rank10 == 1) {
                    result = result.append(curParam.i5);
                } else {
                    switch (rank) {
                        case 1:
                            result = result.append(curParam.i1);
                            break;
                        case 2:
                        case 3:
                        case 4:
                            result = result.append(curParam.i24);
                            break;
                        default:
                            result = result.append(curParam.i5);
                            break;
                    }
                }
            }
            divrem = quotient.divideAndRemainder(thousand);
            quotient = divrem[0];
            remainder = divrem[1];
            group++;
        }
        while (!quotient.equals(zero) || !remainder.equals(zero));
        // Дробная часть суммы
        String s = getRightPart();
        result = result.append(" ").append(s);
        result = result.append(" ");
        if (s.charAt(0) == '1') {
            result = result.append(curParam.r5);
        } else {
            switch (s.charAt(1)) {
                case '1':
                    result = result.append(curParam.r1);
                    break;
                case '2':
                case '3':
                case '4':
                    result = result.append(curParam.r24);
                    break;
                default:
                    result = result.append(curParam.r5);
                    break;
            }
        }
        // По правилам бухгалтерского учета первая буква строкового
        // представления должна быть в верхнем регистре
        result.setCharAt(0, Character.toUpperCase(result.charAt(0)));
        return result.toString();
    }

    // Преобразование группы цифр в строку
    String groupToString(int value, int group) {
        if (value < 0 || value > 999) throw new IllegalArgumentException("value must be between 0 an 999 inclusively");
        if (group < 0) throw new IllegalArgumentException("group must be more than 0");
        StringBuffer result = new StringBuffer(32);
        if (value == 0) {
            return result.toString();
        }
        // Разбор числа по разрядам, начиная с сотен
        int rank = value / 100;
        switch (rank) {
            default:
                break;
            case 1:
                result = result.append("сто ");
                break;
            case 2:
                result = result.append("двісті ");
                break;
            case 3:
                result = result.append("триста ");
                break;
            case 4:
                result = result.append("чотиреста ");
                break;
            case 5:
                result = result.append("п'ятсот ");
                break;
            case 6:
                result = result.append("шістсот ");
                break;
            case 7:
                result = result.append("сімсот ");
                break;
            case 8:
                result = result.append("вісімсот ");
                break;
            case 9:
                result = result.append("дев'ятсот ");
                break;
        }
        // Далее, десятки
        rank = (value % 100) / 10;
        switch (rank) {
            default:
                break;
            case 2:
                result = result.append("двадцять ");
                break;
            case 3:
                result = result.append("тридцять ");
                break;
            case 4:
                result = result.append("сорок ");
                break;
            case 5:
                result = result.append("п'ятьдесят ");
                break;
            case 6:
                result = result.append("шістьдесят ");
                break;
            case 7:
                result = result.append("сімдесят ");
                break;
            case 8:
                result = result.append("вісімдесят ");
                break;
            case 9:
                result = result.append("дев'яносто ");
                break;
        }
        // Если десятки = 1, добавить соответствующее значение. Иначе - единицы
        int rank10 = rank;
        rank = value % 10;
        if (rank10 == 1) {
            switch (rank) {
                case 0:
                    result = result.append("десять ");
                    break;
                case 1:
                    result = result.append("одинадцять ");
                    break;
                case 2:
                    result = result.append("дванадцять ");
                    break;
                case 3:
                    result = result.append("тринадцять ");
                    break;
                case 4:
                    result = result.append("чотирнадцять ");
                    break;
                case 5:
                    result = result.append("п'ятнадцять ");
                    break;
                case 6:
                    result = result.append("шістнадцять ");
                    break;
                case 7:
                    result = result.append("сімнадцять ");
                    break;
                case 8:
                    result = result.append("вісімнадцять ");
                    break;
                case 9:
                    result = result.append("дев'ятнадцять ");
                    break;
            }
        } else {
            switch (rank) {
                default:
                    break;
                case 1:
                    if (group == 1) // Тысячи
                        result = result.append("одна ");
                    else {
                        // Учесть род валюты (поле "Sex" настроечной информации)
                        if (curParam.Sex.equals("M")) result = result.append("один ");
                        if (curParam.Sex.equals("F")) result = result.append("одна ");
                    }
                    break;
                case 2:
                    if (group == 1) // Тысячи
                        result = result.append("дві ");
                    else {
                        // Учесть род валюты (поле "Sex" настроечной информации)
                        if (curParam.Sex.equals("M")) result = result.append("два ");
                        if (curParam.Sex.equals("F")) result = result.append("дві ");
                    }
                    break;
                case 3:
                    result = result.append("три ");
                    break;
                case 4:
                    result = result.append("чотири ");
                    break;
                case 5:
                    result = result.append("п'ять ");
                    break;
                case 6:
                    result = result.append("шість ");
                    break;
                case 7:
                    result = result.append("сім ");
                    break;
                case 8:
                    result = result.append("вісім ");
                    break;
                case 9:
                    result = result.append("дев'ять ");
                    break;
            }
        }
        // Значение группы: тысячи, миллионы и т.д.
        switch (group) {
            default:
                break;
            case 1:
                if (rank10 == 1)
                    result = result.append("тисяч ");
                else {
                    switch (rank) {
                        default:
                            result = result.append("тисяч ");
                            break;
                        case 1:
                            result = result.append("тисяча ");
                            break;
                        case 2:
                        case 3:
                        case 4:
                            result = result.append("тисячі ");
                            break;
                    }
                }
                break;
            case 2:
                if (rank10 == 1)
                    result = result.append("мільйонів ");
                else {
                    switch (rank) {
                        default:
                            result = result.append("мільйонів ");
                            break;
                        case 1:
                            result = result.append("мільйон ");
                            break;
                        case 2:
                        case 3:
                        case 4:
                            result = result.append("мільйона ");
                            break;
                    }
                }
                break;
            case 3:
                if (rank10 == 1)
                    result = result.append("мільярдів ");
                else {
                    switch (rank) {
                        default:
                            result = result.append("мільярдів ");
                            break;
                        case 1:
                            result = result.append("мільярд ");
                            break;
                        case 2:
                        case 3:
                        case 4:
                            result = result.append("мільярда ");
                            break;
                    }
                }
                break;
            case 4:
                if (rank10 == 1)
                    result = result.append("трильйонів ");
                else {
                    switch (rank) {
                        default:
                            result = result.append("трильйонів ");
                            break;
                        case 1:
                            result = result.append("трильйон ");
                            break;
                        case 2:
                        case 3:
                        case 4:
                            result = result.append("трильйона ");
                            break;
                    }
                }
                break;
        }
        return result.toString();
    }
}
