package ua.com.manometer.model.invoice;


import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import ua.com.manometer.util.CustomDateSerializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.*;


@Entity
public class InvoiceFilter  implements Serializable {
    @Id
    @GeneratedValue()
    private Integer id;

    Integer f0 = 0;
    Integer f1 = 0;
    Integer f2 = 0;

    Integer f1From;
    Integer f1To;


    @DateTimeFormat(pattern="dd.MM.yyyy")
    Date f2From;
    @DateTimeFormat(pattern="dd.MM.yyyy")
    Date f2To;

    @Type(type = "ua.com.manometer.util.IntegerListCustomType")
    List<Integer> users = new LinkedList<Integer>();

    @Type(type = "ua.com.manometer.util.IntegerListCustomType")
    List<Integer> purposeFilter = new LinkedList<Integer>();

    @Type(type = "ua.com.manometer.util.IntegerListCustomType")
    List<Integer> stateFilter = new LinkedList<Integer>();

    @Type(type = "ua.com.manometer.util.IntegerListCustomType")
    List<Integer> currencyFilter = new LinkedList<Integer>();


    public List<Integer> getCurrencyFilter() {
        return currencyFilter;
    }

    public void setCurrencyFilter(List<Integer> currencyFilter) {
        this.currencyFilter = currencyFilter;
    }

    public List<Integer> getStateFilter() {
        return stateFilter;
    }

    public void setStateFilter(List<Integer> stateFilter) {
        this.stateFilter = stateFilter;
    }

    public List<Integer> getPurposeFilter() {
        return purposeFilter;
    }

    public void setPurposeFilter(List<Integer> purposeFilter) {
        this.purposeFilter = purposeFilter;
    }

    public List<Integer> getUsers() {
        return users;
    }

    public void setUsers(List<Integer> users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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


    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getF2From() {
        return f2From;
    }

    public void setF2From(Date f2From) {
        this.f2From = f2From;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getF2To() {
        return f2To;
    }

    public void setF2To(Date f2To) {
        this.f2To = f2To;
    }

/*    public List<Invoice> doFilter() {
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
        for (Integer id : users) {
            u.add(dao.findById(id));
        }
        inList.put("executor", u);

        List<Integer> p = new LinkedList();
        for (Integer purpose : purposeFilter)
            p.add(purpose.intValue());
        inList.put("purpose", p);


        List<Integer> s = new LinkedList();
        for (Integer state : stateFilter)
            s.add(state.intValue());
        inList.put("currentState", s);

        List<application.data.Currency> c = new LinkedList();
        for (Integer id : currencyFilter)
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
    }*/




}
