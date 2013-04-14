package ua.com.manometer.model.price;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@IdClass(IdPrice.class)
@Table(name="price_first_part")
public class PriceFirstPart {
    @Id
    private Integer model;
    @Id
    private Integer isp;
    @Id
    private Integer mat;
    @Id
    private Integer klim;
    @Id
    private Integer err;

    @Column( name="cost", precision = 12, scale = 2)
    private BigDecimal cost;
    @Column ( name="price", precision = 12, scale = 2)
    private BigDecimal price;
    @Column ( name="cost_tmp", precision = 12, scale = 2)
    private BigDecimal costTmp;
    @Column ( name="price_tmp", precision = 12, scale = 2)
    private BigDecimal priceTmp;

    public PriceFirstPart(Integer model, Integer isp, Integer mat, Integer klim, Integer err) {
        this.model = model;
        this.isp = isp;
        this.mat = mat;
        this.klim = klim;
        this.err = err;
    }

    public PriceFirstPart() {

    }

    public Integer getId() {
        return model;
    }

    public void setModel(Integer model) {
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

