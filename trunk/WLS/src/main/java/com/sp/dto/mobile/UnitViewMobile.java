package com.sp.dto.mobile;

import com.sp.model.UnitView;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 28.08.12
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "unitView")
public class UnitViewMobile {

    private int id;
    private String description;
    private String accountName;
    private int accountId;

    public UnitViewMobile() {}

    public UnitViewMobile(UnitView unitView) {
        this.id = unitView.getId();
        this.description = unitView.getDescr();
        this.accountName = unitView.getAccount().getDescr();
        this.accountId = unitView.getAccount().getId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
