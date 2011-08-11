package ua.com.manometer.model.price;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 15.01.2011
 * Time: 11:29:32
 * To change this template use File | Settings | File Templates.
 */
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
