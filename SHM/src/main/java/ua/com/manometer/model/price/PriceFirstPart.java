package ua.com.manometer.model.price;

import org.hibernate.annotations.Type;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@IdClass(IdPrice.class)
public class PriceFirstPart {
    @Id
    private Long model;

    @Id
    private Integer isp;
    @Id
    private Integer mat;
    @Id
    private Integer klim;
    @Id
    private Integer err;

    private BigDecimal cost;
    private BigDecimal price;
    private BigDecimal costTmp;
    private BigDecimal priceTmp;

    public PriceFirstPart(Long model, Integer isp, Integer mat, Integer klim, Integer err) {
        this.model = model;
        this.isp = isp;
        this.mat = mat;
        this.klim = klim;
        this.err = err;
    }

    public PriceFirstPart() {

    }

    public Long getId() {
        return model;
    }

    public void setModel(Long model) {
        this.model = model;
    }

    public Integer getIsp() {
        return isp;
    }

    public void setIsp(Integer isp) {
        this.isp = isp;
    }

    public Integer getMat() {
        return mat;
    }

    public void setMat(Integer mat) {
        this.mat = mat;
    }

    public Integer getKlim() {
        return klim;
    }

    public void setKlim(Integer klim) {
        this.klim = klim;
    }

    public Integer getErr() {
        return err;
    }

    public void setErr(Integer err) {
        this.err = err;
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

    public BigDecimal getCostTmp() {
        return costTmp;
    }

    public void setCostTmp(BigDecimal costTmp) {
        this.costTmp = costTmp;
    }

    public BigDecimal getPriceTmp() {
        return priceTmp;
    }

    public void setPriceTmp(BigDecimal priceTmp) {
        this.priceTmp = priceTmp;
    }


        public String toString()
    {
        return "isp=" +isp+" model="+model+" mat="+mat+  " klim= "+klim+ " err="+err+ " cost="   +cost+" price="+price;
    }
}



@Embeddable
 class IdPrice implements Serializable {
    private Long model;


    private Integer isp;

    private Integer mat;

    private Integer klim;

    private Integer err;

    public Long getModel() {
        return model;
    }

    public void setModel(Long model) {
        this.model = model;
    }

    public Integer getIsp() {
        return isp;
    }

    public void setIsp(Integer isp) {
        this.isp = isp;
    }

    public Integer getMat() {
        return mat;
    }

    public void setMat(Integer mat) {
        this.mat = mat;
    }

    public Integer getKlim() {
        return klim;
    }

    public void setKlim(Integer klim) {
        this.klim = klim;
    }

    public Integer getErr() {
        return err;
    }

    public void setErr(Integer err) {

        this.err = err;
    }



}

