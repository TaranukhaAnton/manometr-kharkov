package com.sp.dto.flex;

/**
 * Created by Alexander
 */

public class SmsMonthlyRecordDto {
    private String accountDescr;
    private Integer jan;
    private Integer feb;
    private Integer mar;
    private Integer apr;
    private Integer may;
    private Integer jun;
    private Integer jul;
    private Integer aug;
    private Integer sep;
    private Integer oct;
    private Integer nov;
    private Integer dec;
    private Integer total;

    public SmsMonthlyRecordDto(String accountDescr) {
        this.accountDescr = accountDescr;
    }

    public SmsMonthlyRecordDto() {
    }

    public String getAccountDescr() {
        return accountDescr;
    }

    public void setAccountDescr(String accountDescr) {
        this.accountDescr = accountDescr;
    }

    public Integer getJan() {
        return jan;
    }

    public void setJan(Integer jan) {
        this.jan = jan;
    }

    public Integer getFeb() {
        return feb;
    }

    public void setFeb(Integer feb) {
        this.feb = feb;
    }

    public Integer getMar() {
        return mar;
    }

    public void setMar(Integer mar) {
        this.mar = mar;
    }

    public Integer getApr() {
        return apr;
    }

    public void setApr(Integer apr) {
        this.apr = apr;
    }

    public Integer getMay() {
        return may;
    }

    public void setMay(Integer may) {
        this.may = may;
    }

    public Integer getJun() {
        return jun;
    }

    public void setJun(Integer jun) {
        this.jun = jun;
    }

    public Integer getJul() {
        return jul;
    }

    public void setJul(Integer jul) {
        this.jul = jul;
    }

    public Integer getAug() {
        return aug;
    }

    public void setAug(Integer aug) {
        this.aug = aug;
    }

    public Integer getSep() {
        return sep;
    }

    public void setSep(Integer sep) {
        this.sep = sep;
    }

    public Integer getOct() {
        return oct;
    }

    public void setOct(Integer oct) {
        this.oct = oct;
    }

    public Integer getNov() {
        return nov;
    }

    public void setNov(Integer nov) {
        this.nov = nov;
    }

    public Integer getDec() {
        return dec;
    }

    public void setDec(Integer dec) {
        this.dec = dec;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
