package ua.com.manometr.model.invoice;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Payment {
    public static final String[] PAYMENT_PURPOSE_ALIAS ={"предоплата","за запущенную","за изготовленную","за отгруженную","ручная коррекция"};


    @Id
    @GeneratedValue()
    private Long id;
    private Date date;


    @ManyToOne
    @JoinColumn(name = "INVOICE_ID", nullable = true)
    private Invoice invoice;

    @Type(type = "application.hibernate.MyBigDecimalType")
    private BigDecimal exchangeRate;

    @Type(type = "application.hibernate.MyBigDecimalType")
    private BigDecimal paymentSum;


    private Integer purpose;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(BigDecimal paymentSum) {
        this.paymentSum = paymentSum;
    }

    public Integer getPurpose() {
        return purpose;
    }

    public void setPurpose(Integer purpose) {
        this.purpose = purpose;
    }
}
