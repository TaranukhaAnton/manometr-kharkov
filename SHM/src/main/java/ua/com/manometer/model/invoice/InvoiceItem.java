package ua.com.manometer.model.invoice;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Entity


@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)

@Table(name = "InvoiceItem")
public abstract class InvoiceItem {
    @Id
    @GeneratedValue()
    private Integer id;

    private Integer type;

    protected BigDecimal price;
    protected BigDecimal cost;
    protected BigDecimal sellingPrice;
    protected BigDecimal additionalCost;
    protected BigDecimal transportationCost;

    private Integer deliveryTime;
    private Integer quantity;
    private Date manufacturedDate;

    public InvoiceItem() {

    }


    public abstract String getName();

//    public abstract InvoiceItem getClone();

  //  public abstract void setupMoneyFields(BigDecimal koef);

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID", nullable = true)
    protected Invoice invoice;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "INVOICE_ITEM_ID")

    private List<ShipmentMediator> shippingMediators;


    public List<ShipmentMediator> getShippingMediators() {
        return shippingMediators;
    }

    public void setShippingMediators(List<ShipmentMediator> shippingMediators) {
        this.shippingMediators = shippingMediators;
    }

    public void addShippingMediators(ShipmentMediator shippingMediator) {

        if (shippingMediators == null) shippingMediators = new LinkedList();
        shippingMediators.add(shippingMediator);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.HALF_UP);
    }


    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice.setScale(2, RoundingMode.HALF_UP);
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getManufacturedDate() {
        return manufacturedDate;
    }

    public void setManufacturedDate(Date manufacturedDate) {
        this.manufacturedDate = manufacturedDate;
    }

    public BigDecimal calculatePercent() {
        BigDecimal znamenatel = price.divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP).add(additionalCost);


        if (znamenatel.compareTo(BigDecimal.ZERO) != 0) {
            return sellingPrice.subtract(transportationCost).divide(znamenatel, 4, RoundingMode.HALF_UP);

        } else
            return new BigDecimal("-1");
    }

    public void setPercent(BigDecimal percent) {
        BigDecimal p1 = price.divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP).add(additionalCost);
        sellingPrice = percent.multiply(p1).add(transportationCost).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getSum() {
        return sellingPrice.multiply(new BigDecimal(quantity)).setScale(2, RoundingMode.HALF_UP);
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public static int daysBetween(Date date1, Date date2) {
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        Long daysBetween = (time2 - time1 + 60 * 60 * 1000) / (24 * 60 * 60 * 1000);
        return daysBetween.intValue();
    }

    public static Date dateBeforeNDays(Date date, int n) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c.setTime(date);
        c.add(Calendar.DATE, n);
        return c.getTime();
    }

    public Integer getTotalShipments() {
        Integer result = 0;
        if (shippingMediators != null)
            for (ShipmentMediator shippingMediator : shippingMediators) {
                result += shippingMediator.getCount();
            }
        return result;

    }

    public BigDecimal getAdditionalCost() {
        return additionalCost;
    }

    public void setAdditionalCost(BigDecimal additionalCost) {
        if (sellingPrice != null) {
            BigDecimal coef = calculatePercent();
            BigDecimal s1 = price.divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP).add(additionalCost);
            sellingPrice = coef.multiply(s1).add(transportationCost).setScale(2, RoundingMode.HALF_UP);
        }

        this.additionalCost = additionalCost.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTransportationCost() {
        return transportationCost;
    }

    public void setTransportationCost(BigDecimal transportationCost) {

        if (sellingPrice != null)
            sellingPrice = sellingPrice.subtract(this.transportationCost).add(transportationCost).setScale(2, RoundingMode.HALF_UP);

        this.transportationCost = transportationCost.setScale(2, RoundingMode.HALF_UP);
    }
}
