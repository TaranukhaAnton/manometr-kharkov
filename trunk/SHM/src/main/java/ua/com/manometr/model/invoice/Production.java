package ua.com.manometr.model.invoice;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@DiscriminatorValue("ProductionPrice")
public class Production extends InvoiceItem {
    String name;

    @Override
    public InvoiceItem getClone() {
        Production clone = new Production();
        
        clone.setType(getType());
        clone.price = price;
        clone.cost = cost;
        clone.sellingPrice = sellingPrice;
        clone.additionalCost = additionalCost;
        clone.transportationCost = transportationCost;
        clone.setDeliveryTime(getDeliveryTime());
        clone.setQuantity(getQuantity());
        clone.setManufacturedDate(getManufacturedDate());
        clone.setName(name);

        return clone;

    }


    public Production() {
    }


    public String getName() {
        return name;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setupMoneyFields(BigDecimal koef) {
        //  BigDecimal price = this.getPrice();
        //this.setSellingPrice(price.multiply(koef).divide(exchangeRate,2));

        BigDecimal p1 = price.divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP).add(additionalCost);
        sellingPrice = koef.multiply(p1).add(transportationCost);

    }


}
