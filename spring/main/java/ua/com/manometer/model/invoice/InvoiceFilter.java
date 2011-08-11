package ua.com.manometer.model.invoice;

import ua.com.manometer.model.User;
import application.hibernate.Factory;
import application.hibernate.generic.GenericHibernateDAO;
//import application.hibernate.generic.UserDAO;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.*;


@Entity
public class InvoiceFilter {
    @Id
    @GeneratedValue()
    private Long id;

    Integer f0 = 0;
    Integer f1 = 0;
    Integer f2 = 0;
    Integer f1From;
    Integer f1To;
    Date f2From;
    Date f2To;

    @Type(type = "application.hibernate.LongArrayCustomType")
    Long[] users;

    @Type(type = "application.hibernate.LongArrayCustomType")
    Long[] purposeFilter;

    @Type(type = "application.hibernate.LongArrayCustomType")
    Long[] stateFilter;

    @Type(type = "application.hibernate.LongArrayCustomType")
    Long[] currencyFilter;


    public Long[] getCurrencyFilter() {
        return currencyFilter;
    }

    public void setCurrencyFilter(Long[] currencyFilter) {
        this.currencyFilter = currencyFilter;
    }

    public Long[] getStateFilter() {
        return stateFilter;
    }

    public void setStateFilter(Long[] stateFilter) {
        this.stateFilter = stateFilter;
    }

    public Long[] getPurposeFilter() {
        return purposeFilter;
    }

    public void setPurposeFilter(Long[] purposeFilter) {
        this.purposeFilter = purposeFilter;
    }

    public Long[] getUsers() {
        return users;
    }

    public void setUsers(Long[] users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getF0() {
        return f0;
    }

    public void setF0(Integer f0) {
        this.f0 = f0;
    }

    public Integer getF1() {
        return f1;
    }

    public void setF1(Integer f1) {
        this.f1 = f1;
    }

    public Integer getF2() {
        return f2;
    }

    public void setF2(Integer f2) {
        this.f2 = f2;
    }

    public Integer getF1From() {
        return f1From;
    }

    public void setF1From(Integer f1From) {
        this.f1From = f1From;
    }

    public Integer getF1To() {
        return f1To;
    }

    public void setF1To(Integer f1To) {
        this.f1To = f1To;
    }

    public Date getF2From() {
        return f2From;
    }

    public void setF2From(Date f2From) {
        this.f2From = f2From;
    }

    public Date getF2To() {
        return f2To;
    }

    public void setF2To(Date f2To) {
        this.f2To = f2To;
    }

    public List<Invoice> doFilter() {
        Map<String, Object> eq = new HashMap();
        Map<String, Object> larger = new HashMap();
        Map<String, Object> less = new HashMap();
        Map<String, List> inList = new HashMap();

        if (f0 == 1)
            eq.put("isInvoice", true);
        if (f0 == 2)
            eq.put("isInvoice", false);
        if (f1 == 1) {
            larger.put("number", f1From);
            less.put("number", f1To);
        }
        if (f2 == 1) {
            Integer year = (new Date()).getYear() + 1900;
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            try {

                //  System.out.println(sdf.parse("1.1." + year));
                // System.out.println(sdf.parse("31.12." + year));
                larger.put("date", sdf.parse("1.1." + year));
                less.put("date", sdf.parse("31.12." + year));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (f2 == 3) {
            larger.put("date", f2From);
            less.put("date", f2To);
        }

        List<User> u = new LinkedList();
        GenericHibernateDAO<User> dao = Factory.getUserDAO();
        for (Long id : users) {
            u.add(dao.findById(id));
        }
        inList.put("executor", u);

        List<Integer> p = new LinkedList();
        for (Long purpose : purposeFilter)
            p.add(purpose.intValue());
        inList.put("purpose", p);


        List<Integer> s = new LinkedList();
        for (Long state : stateFilter)
            s.add(state.intValue());
        inList.put("currentState", s);

        List<ua.com.manometer.model.Currency> c = new LinkedList();
        for (Long id : currencyFilter)
            c.add(Factory.getCurrencyDAO().findById(id));

        inList.put("supplier.currency", c);


        return Factory.getInvoiceDAO().findByQuery(eq, larger, less, inList);
    }


    public List<Booking> doBookingFilter() {
        List<Booking> result = new LinkedList();
        List<Invoice> invoices = doFilter();
        for (Invoice invoice : invoices)
            if (invoice.getBooking() != null)
                result.add(invoice.getBooking());
        return result;
    }


    public String toJSONString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        StringBuilder result = new StringBuilder();
        result.append("{");
        if (users != null) {
            result.append("\"user\":[");
            for (int i = 0; i < users.length; i++)
                result.append(users[i] + ((i == users.length - 1) ? "" : ","));
            result.append("],");
        }
        if (purposeFilter != null) {
            result.append("\"purpose\":[");
            for (int i = 0; i < purposeFilter.length; i++)
                result.append(purposeFilter[i] + ((i == purposeFilter.length - 1) ? "" : ","));
            result.append("],");
        }

        if (stateFilter != null) {
            result.append("\"state\":[");
            for (int i = 0; i < stateFilter.length; i++)
                result.append(stateFilter[i] + ((i == stateFilter.length - 1) ? "" : ","));
            result.append("],");
        }

        if (currencyFilter != null) {
            result.append("\"currency\":[");
            for (int i = 0; i < currencyFilter.length; i++)
                result.append(currencyFilter[i] + ((i == currencyFilter.length - 1) ? "" : ","));
            result.append("],");
        }

        result.append("\"f0\":" + f0 + ",");
        result.append("\"f1\":" + f1 + ",");
        result.append("\"f2\":" + f2 + ",");

        result.append("\"f2_to\":\"" + ((f2To == null) ? "" : sdf.format(f2To)) + "\",");
        result.append("\"f2_from\":\"" + ((f2From == null) ? "" : sdf.format(f2From)) + "\",");
        result.append("\"f1_to\":\"" + ((f1To == null) ? "" : f1To) + "\",");
        result.append("\"f1_from\":\"" + ((f1From == null) ? "" : f1From) + "\"}");
        //   System.out.println(result);
        return result.toString();

    }

}
