package application.data.invoice;

import application.data.Customer;
import application.data.Supplier;
import application.data.User;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Entity

public class Invoice {
    //    public static final String[] purposeAlias = {"пос", "исп", "гар", "рез", "прч"};/
    public static final String[] purposeAlias = {"пос", "исп", "гар", "рез", "прч"};
    public static final String[] purposeAliasFull = {"поставка", "испытания", "гарантия", "резерв", "прочее"};

    // public static final String[] bookingAlias = {"зав", "скл", "ч/и", "исп", "ост", "анн"};
    public static final String[] curStateAlias = {"черн", "акт", "мод", "отл", "изм", "анн", "зак", "исп"};
    public static final Integer STATE_CHERN = 0;
    public static final Integer STATE_ACT = 1;
    public static final Integer STATE_MOD = 2;
    public static final Integer STATE_OTL = 3;
    public static final Integer STATE_IZM = 4;
    public static final Integer STATE_ANN = 5;
    public static final Integer STATE_ZAK = 6;
    public static final Integer STATE_ISP = 7;
    public static final Integer PURPOSE_POSTAVKA = 0;
    public static final Integer PURPOSE_ISPIT = 1;


    @Id
    @GeneratedValue()

    private Long id;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    private boolean isInvoice;
    private Integer number;
    private String numberModifier;
    private Date date;
    private Date changeDate;
    private Date controlDate;

    private Integer validity; //    срок действия
    @ManyToOne
    private Supplier supplier;
    @ManyToOne
    private Customer emploer;
    @ManyToOne
    private Customer consumer;

    private String notice;
    @Column(length = 5000)
    private String notes;  //примечания
    @Column(length = 5000)
    private String comments;  //комментарий
    private String deliveryTime;  //срок поставки


    @ManyToOne
    private User responsible;
    @ManyToOne
    private User executor;


    private Integer purpose; //назначение
    @Type(type = "application.hibernate.MyBigDecimalType")
    private BigDecimal prepayment; //предоплата
    @Type(type = "application.hibernate.MyBigDecimalType")
    private BigDecimal paymentOnTheNitice; //по извещению
    @Type(type = "application.hibernate.MyBigDecimalType")
    private BigDecimal postpay; //по факту


    private Integer daysAfterDelivery;
    private Integer currentState;
    private Date awaitingPayment;  //ож. оплаты
    private Date awaitingDelivery; //ож. поставки

    private Integer probabilityOfPayment;//вероятность оплаты
    @Type(type = "application.hibernate.MyBigDecimalType")
    private BigDecimal NDS;
    @Type(type = "application.hibernate.MyBigDecimalType")
    private BigDecimal exchangeRate;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_ID")
    @IndexColumn(name = "orders_index", base = 0)

    private List<InvoiceItem> invoiceItems;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = true)

    private Booking booking;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_ID")

    private List<Shipment> shipments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_ID")
    private List<Payment> payments;


    private Integer t0;
    private Integer t1;
    private Integer t2;
    private Integer t3;
    private Integer t4;
    private Integer t5;

    @Transient
    Long prev;
    @Transient
    Long next;

    @Transient
    private BigDecimal debt;

    @Transient
    private BigDecimal debtPercent;

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    public void addShipment(Shipment shipping) {
        if (shipments == null) shipments = new LinkedList();
        shipments.add(shipping);
    }

    public void addPayment(Payment payment) {
        if (payments == null) payments = new LinkedList<Payment>();
        payments.add(payment);
    }


    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isInvoice() {
        return isInvoice;
    }

    public void setInvoice(boolean invoice) {
        isInvoice = invoice;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }


    public Customer getEmploer() {
        return emploer;
    }

    public void setEmploer(Customer emploer) {
        this.emploer = emploer;
    }

    public Customer getConsumer() {
        return consumer;
    }

    public void setConsumer(Customer consumer) {
        this.consumer = consumer;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public void addInvoiceItems(InvoiceItem invoiceItem) {
        if (invoiceItems == null)
            invoiceItems = new ArrayList<InvoiceItem>();

        invoiceItems.add(invoiceItem);
    }


    public Integer getPurpose() {
        return purpose;
    }

    public void setPurpose(Integer purpose) {
        this.purpose = purpose;
    }


    public BigDecimal getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(BigDecimal prepayment) {
        this.prepayment = prepayment;
    }

    public BigDecimal getPaymentOnTheNitice() {
        return paymentOnTheNitice;
    }

    public void setPaymentOnTheNitice(BigDecimal paymentOnTheNitice) {
        this.paymentOnTheNitice = paymentOnTheNitice;
    }

    public BigDecimal getPostpay() {
        return postpay;
    }

    public void setPostpay(BigDecimal postpay) {
        this.postpay = postpay;
    }

    public Integer getDaysAfterDelivery() {
        return daysAfterDelivery;
    }

    public void setDaysAfterDelivery(Integer daysAfterDelivery) {
        this.daysAfterDelivery = daysAfterDelivery;
    }

    public Date getAwaitingPayment() {
        return awaitingPayment;
    }

    public void setAwaitingPayment(Date awaitingPayment) {
        this.awaitingPayment = awaitingPayment;
    }

    public Date getAwaitingDelivery() {
        return awaitingDelivery;
    }

    public void setAwaitingDelivery(Date awaitingDelivery) {
        this.awaitingDelivery = awaitingDelivery;
    }

    public Integer getProbabilityOfPayment() {
        return probabilityOfPayment;
    }

    public void setProbabilityOfPayment(Integer probabilityOfPayment) {
        this.probabilityOfPayment = probabilityOfPayment;
    }

    public BigDecimal getNDS() {
        return NDS;
    }

    public void setNDS(BigDecimal NDS) {
        this.NDS = NDS;
    }


    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getSum() {
        BigDecimal result = new BigDecimal("0");
        if (invoiceItems != null) {
            for (InvoiceItem item : invoiceItems)
                result = result.add(item.getSum());
        }
        return result;
    }

    public BigDecimal getNDSPayment() {
        return getSum().multiply(NDS).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotal() {
        return getSum().add(getNDSPayment());
    }

    public BigDecimal getAdditionToPrice() {
        /*
        *  BigDecimal znamenatel = price.divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP).add(additionalCost);


        if (znamenatel.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal res = sellingPrice.subtract(transportationCost).divide(znamenatel, 4, RoundingMode.HALF_UP);

            return res;
        } else
            return new BigDecimal("-1");
        *
        *
        *
        * */


        BigDecimal znamenatel = new BigDecimal("0");
        BigDecimal chislitel = new BigDecimal("0");

        BigDecimal result = new BigDecimal("0");
        BigDecimal tmp1, tmp2;

        if (invoiceItems != null) {
            for (InvoiceItem item : invoiceItems) {
                tmp1 = item.getPrice().divide(exchangeRate, 2, RoundingMode.HALF_UP).add(item.getAdditionalCost()).multiply(new BigDecimal(item.getQuantity()));
                tmp2 = item.getSellingPrice().subtract(item.getTransportationCost()).multiply(new BigDecimal(item.getQuantity()));
                znamenatel = znamenatel.add(tmp1);
                chislitel = chislitel.add(tmp2);
                //   BigDecimal selP = item.getSellingPrice();
                //   BigDecimal tr = item.getTransportationCost();
                //   Integer qa = item.getQuantity();


                //   result = result.add(selP.subtract(tr).multiply((new BigDecimal(qa))));
                //   System.out.println(selP + " " + tr + " " + qa + " " + result);
            }

        }
        //  BigDecimal sum = getSum();

        if (znamenatel.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ONE;
        } else {
            return chislitel.divide(znamenatel, 2, RoundingMode.HALF_UP);
        }
        //return result.divide(sum, 2, RoundingMode.HALF_UP);


    }


    public String getNumberModifier() {
        return numberModifier;
    }

    public void setNumberModifier(String numberModifier) {
        this.numberModifier = numberModifier;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Date getControlDate() {
        return controlDate;
    }

    public void setControlDate(Date controlDate) {
        this.controlDate = controlDate;
    }


    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getT0() {
        return t0;
    }

    public void setT0(Integer t0) {
        this.t0 = t0;
    }

    public Integer getT1() {
        return t1;
    }

    public void setT1(Integer t1) {
        this.t1 = t1;
    }

    public Integer getT2() {
        return t2;
    }

    public void setT2(Integer t2) {
        this.t2 = t2;
    }

    public Integer getT3() {
        return t3;
    }

    public void setT3(Integer t3) {
        this.t3 = t3;
    }

    public Integer getT4() {
        return t4;
    }

    public void setT4(Integer t4) {
        this.t4 = t4;
    }

    public Integer getT5() {
        return t5;
    }

    public void setT5(Integer t5) {
        this.t5 = t5;
    }

    public Long getPrev() {
        return prev;
    }

    public void setPrev(Long prev) {
        this.prev = prev;
    }

    public Long getNext() {
        return next;
    }

    public void setNext(Long next) {
        this.next = next;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }


    public BigDecimal getDebtPercent() {
        return debtPercent;
    }

    public void setDebtPercent(BigDecimal debtPercent) {
        this.debtPercent = debtPercent;
    }

    public BigDecimal getTotalPayments() {
        BigDecimal sumOfPayments = new BigDecimal("0");
        if (payments != null)
            for (Payment payment : payments) {
                sumOfPayments = sumOfPayments.add(payment.getPaymentSum());
            }
        return sumOfPayments;
    }

    public BigDecimal getPaymentPercent() {
//         new BigDecimal("0");
//        if (payments != null)
//            for (Payment payment : payments) {
//                sumOfPayments = sumOfPayments.add(payment.getPaymentSum());
//            }

        BigDecimal sumOfPayments = getTotalPayments();
        BigDecimal total = getTotal();
        if (total.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        } else {
            return sumOfPayments.multiply(new BigDecimal("100")).divide(total, 2, BigDecimal.ROUND_HALF_UP);
        }


    }

    public Boolean isPaymentMade() {
        BigDecimal sumOfPayments = new BigDecimal("0");
        if (payments != null)
            for (Payment payment : payments) {
                sumOfPayments = sumOfPayments.add(payment.getPaymentSum());
            }
        BigDecimal total = getTotal();
        return total.compareTo(sumOfPayments) == 0;
    }

    public Boolean isDeliveryMade() {
        Boolean result = true;
        for (InvoiceItem item : invoiceItems) {

            Integer count = 0;
            if (item.getShippingMediators() != null)
                for (ShipmentMediator sm : item.getShippingMediators())
                    count += sm.getCount();


            if (!item.getQuantity().equals(count)) {
                result = false;
                break;
            }

        }


        return result;
    }


    public Boolean isAnyGoodsNotShipped() {
        Boolean result = true;
        for (InvoiceItem item : invoiceItems) {

            Integer count = 0;
            if (item.getShippingMediators() != null)
                for (ShipmentMediator sm : item.getShippingMediators())
                    count += sm.getCount();


            if (count != 0) {
                result = false;
                break;
            }

        }


        return result;
    }

}
