package com.sp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "sim")
public class Sim extends BaseSim {
    @Column(insertable = false, updatable = false)
    private int code;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "contract_operator_id")
    private SimOperator contractOperator;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "reseller_id")
    private int resellerId;

    @Column(name = "serial_num")
    private String serialNum;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(insertable = false, updatable = false)
    private Date timestamp;

    @Column(name = "is_deleted")
    private boolean deleted;


    public void copyTo(Sim copy) {
        super.copyTo(copy);
        copy.contractOperator = contractOperator;
        copy.startDate = startDate;
        copy.endDate = endDate;
        copy.owner = owner;
        copy.serialNum = serialNum;
        copy.account = account;
        copy.resellerId = resellerId;
        copy.deleted = deleted;
        copy.code = code;
        copy.timestamp = timestamp;
    }

    public SimOperator getContractOperator() {
        return contractOperator;
    }

    public void setContractOperator(SimOperator contractOperator) {
        this.contractOperator = contractOperator;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getResellerId() {
        return resellerId;
    }

    public void setResellerId(int resellerId) {
        this.resellerId = resellerId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
