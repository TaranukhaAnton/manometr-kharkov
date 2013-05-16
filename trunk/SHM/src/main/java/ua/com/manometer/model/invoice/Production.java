package ua.com.manometer.model.invoice;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@DiscriminatorValue("ProductionPrice")
@Table(name="production")
public class Production extends InvoiceItem {
    String name;

    @Override
    public InvoiceItem getClone(BigDecimal oldExchangeRate, BigDecimal newExchangeRate) {
        Production clone = new Production();

        clone.setType(getType());
        clone.price = price;
        clone.cost = cost;
        clone.sellingPrice =  sellingPrice.subtract(transportationCost).multiply(oldExchangeRate).divide(newExchangeRate, 2, RoundingMode.HALF_UP).add(transportationCost);
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



}
