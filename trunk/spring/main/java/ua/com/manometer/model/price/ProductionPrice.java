package ua.com.manometer.model.price;

import org.hibernate.annotations.Type;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Anton
 * Date: 25.01.2010
 * Time: 23:31:48
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class ProductionPrice {
    @Id
    @GeneratedValue
    Long id;
    String name;
    Integer type;
    @Type(type = "application.hibernate.MyBigDecimalType")
    private BigDecimal cost;
    @Type(type = "application.hibernate.MyBigDecimalType")
    private BigDecimal price;


    public ProductionPrice(Long id, String name, Integer type, BigDecimal cost, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.price = price;
    }

    public ProductionPrice() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
