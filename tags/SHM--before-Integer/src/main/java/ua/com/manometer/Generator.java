package ua.com.manometer;

import ua.com.manometer.model.Customer;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: User
 * Date: 29.02.12
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public class Generator {
    public static void main(String[] args) {
        Class cl = Customer.class;
        // выводим поля класса
        Field[] fields = cl.getDeclaredFields();
        for (Field field : fields) {

            System.out.println("        <tr>\n" +
                    "            <td class=\"tdLabel\">\n" +
                    "                \n" +
                    "            </td>\n" +
                    "            <td>\n" +
                    "                <form:input path=\""+field.getName()+"\" size=\"40\"/>\n" +
                    "            </td>\n" +
                    "        </tr>");

        }
    }

}
