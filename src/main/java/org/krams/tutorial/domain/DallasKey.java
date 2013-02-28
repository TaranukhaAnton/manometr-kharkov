package org.krams.tutorial.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "dallas_key")
public class DallasKey extends BaseModel {

    @Column(name = "dallas_key_code")
    private String dallasKeyCode;

    @Column(name = "reseller_id")
    private int resellerId;

    @Column(name = "account_id")
    private Integer accountId;

    public void copyTo(DallasKey copy) {
        super.copyTo(copy);
        copy.dallasKeyCode = dallasKeyCode;
        copy.resellerId = resellerId;
        copy.accountId = accountId;
    }

    public String toOptionText() {
        return dallasKeyCode;
    }

    public String getDallasKeyCode() {
        return dallasKeyCode;
    }

    public void setDallasKeyCode(String dallasKeyCode) {
        this.dallasKeyCode = dallasKeyCode;
    }

    public int getResellerId() {
        return resellerId;
    }

    public void setResellerId(int resellerId) {
        this.resellerId = resellerId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
