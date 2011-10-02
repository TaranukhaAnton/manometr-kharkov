package ua.com.manometr.model.price;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigDecimal;



@Entity
@IdClass(ua.com.manometr.model.price.IdOptionsPrice.class)
public class OptionsPrice {
    @Id
    private Integer type;
    @Id
    private Integer isp;
    @Id
    private String param;
    @Type(type = "application.hibernate.MyBigDecimalType")
    private BigDecimal cost;
    @Type(type = "application.hibernate.MyBigDecimalType")
    private BigDecimal price;


    public OptionsPrice() {
    }

    public OptionsPrice(Integer type, Integer isp, String param) {
        this.type = type;
        this.isp = isp;
        this.param = param;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsp() {
        return isp;
    }

    public void setIsp(Integer isp) {
        this.isp = isp;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
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

    public String toString()
    {
        return "isp= " +isp+" type= "+type+" param= "+param+" cost= "+cost+" price= "+price;
    }



}

//@Embeddable
//class IdOptionsPrice implements Serializable {
//    private Integer type;
//    private Integer isp;
//    private String param;
//
//    public Integer getType() {
//        return type;
//    }
//
//    public void setType(Integer type) {
//        this.type = type;
//    }
//
//    public Integer getIsp() {
//        return isp;
//    }
//
//    public void setIsp(Integer isp) {
//        this.isp = isp;
//    }
//
//    public String getParam() {
//        return param;
//    }
//
//    public void setParam(String param) {
//        this.param = param;
//    }
//}
