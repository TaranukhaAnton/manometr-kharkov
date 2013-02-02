package ua.com.manometer.model.price;

import java.io.Serializable;


public class  IdPrice implements Serializable {
    private Integer model;

    private Integer isp;

    private Integer mat;

    private Integer klim;

    private Integer err;

    public IdPrice() {
    }

    public IdPrice(Integer model, Integer isp, Integer mat, Integer klim, Integer err) {
        this.model = model;
        this.isp = isp;
        this.mat = mat;
        this.klim = klim;
        this.err = err;
    }

    public Integer getModel() {
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
}


