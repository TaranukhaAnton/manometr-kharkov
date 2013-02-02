package ua.com.manometer.model.price;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigDecimal;


@Entity
@IdClass(IdOptionsPrice.class)
public class OptionsPrice {
    @Id
    private Integer type;
    @Id
    private Integer isp;
    @Id
    private String param;
    private BigDecimal cost;

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

    public String toString() {
        return "isp= " + isp + " type= " + type + " param= " + param + " cost= " + cost + " price= " + price;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(isp)
                .append(type)
                .append(param)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof OptionsPrice) {
            final OptionsPrice other = (OptionsPrice) obj;
            return new EqualsBuilder()
                    .append(isp, other.isp)
                    .append(type, other.type)
                    .append(param, other.param)
                    .isEquals();
        } else {
            return false;
        }
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
