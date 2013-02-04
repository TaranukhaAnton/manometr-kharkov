package ua.com.manometer.model.price;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class ProductionPrice {
    @Id
    @GeneratedValue

    Integer id;
    String name;
    Integer type;
    private BigDecimal cost;
    private BigDecimal price;


    public ProductionPrice(Integer id, String name, Integer type, BigDecimal cost, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.price = price;
    }

    public ProductionPrice() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
