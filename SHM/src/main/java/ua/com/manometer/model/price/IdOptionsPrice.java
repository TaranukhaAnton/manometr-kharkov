package ua.com.manometer.model.price;

import java.io.Serializable;

public class IdOptionsPrice implements Serializable{
    private Integer type;
    private Integer isp;
    private String param;

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
}
