package ua.com.manometer.model.invoice;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Entity
public class Shipment {
    @Id
    @GeneratedValue()
    private Integer id;
    private Date date;
    private String shipmentNum;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "SHIPMENT_ID")
//    @Cache(usage = CacheConcurrencyStrategy.NONE)
    
    private List<ShipmentMediator> shippingMediators;

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID", nullable = true)
    private Invoice invoice;


    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getShipmentNum() {
        return shipmentNum;
    }

    public void setShipmentNum(String shipmentNum) {
        this.shipmentNum = shipmentNum;
    }

    public List<ShipmentMediator> getShippingMediators() {
        return shippingMediators;
    }

    public void setShippingMediators(List<ShipmentMediator> shippingMediators) {
        this.shippingMediators = shippingMediators;
    }

    public void addShippingMediator(ShipmentMediator shippingMediator) {
        if (shippingMediators == null) shippingMediators = new LinkedList();
        shippingMediators.add(shippingMediator);
    }

}
