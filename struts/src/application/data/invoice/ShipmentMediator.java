package application.data.invoice;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 26.10.2010
 * Time: 12:25:05
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class ShipmentMediator {
    @Id
    @GeneratedValue()
    Long id;
    @ManyToOne
    @JoinColumn(name = "INVOICE_ITEM_ID", nullable = true)
    InvoiceItem invoiceItem;
    Integer count;


    @ManyToOne   
    @JoinColumn(name = "SHIPMENT_ID", nullable = true)
    private Shipment shipment;

    public ShipmentMediator() {
    }

    public ShipmentMediator(InvoiceItem invoiceItem, Integer count) {
        this.invoiceItem = invoiceItem;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InvoiceItem getInvoiceItem() {
        return invoiceItem;
    }

    public void setInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Shipment getShipping() {
        return shipment;
    }

    public void setShipping(Shipment shipping) {
        this.shipment = shipping;
    }
}
