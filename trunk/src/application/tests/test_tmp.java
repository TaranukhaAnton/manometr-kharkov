package application.tests;

import application.data.Customer;
import application.data.Profession;
import application.data.invoice.Invoice;
import application.hibernate.Factory;
import application.hibernate.generic.GenericHibernateDAO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 01.06.2010
 * Time: 14:51:55
 * To change this template use File | Settings | File Templates.
 */
public class test_tmp {


    public static void main(String[] args) {
        Map<String, Object> exam = new HashMap();
        exam.put("shortName", "Укргазтех");
        exam.put("status", true);
        List<Customer> consumers = Factory.getCustomerDAO().findByExample(exam);
        System.out.println(consumers.size());

    }
}
