package com.sp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "license")
public class License extends BaseModel {
    @Column(name = "issue_date")
    private Date issueDate;

    @Column(name = "activation_date")
    private Date activationDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "max_node_count")
    private int maxNodeCount;

    @Column(name = "sales_order_ref")
    private String salesOrderRef;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "is_deleted")
    private boolean deleted;

    public void copyTo(License copy) {
        super.copyTo(copy);
        copy.setIssueDate(issueDate);
        copy.setActivationDate(activationDate);
        copy.setEndDate(endDate);
        copy.setMaxNodeCount(maxNodeCount);
        copy.setSalesOrderRef(salesOrderRef);
        copy.setAccount(account);
        copy.deleted = deleted;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getMaxNodeCount() {
        return maxNodeCount;
    }

    public void setMaxNodeCount(int maxNodeCount) {
        this.maxNodeCount = maxNodeCount;
    }

    public String getSalesOrderRef() {
        return salesOrderRef;
    }

    public void setSalesOrderRef(String salesOrderRef) {
        this.salesOrderRef = salesOrderRef;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String toOptionText() {
        StringBuffer sb = new StringBuffer(getIssueDate().toString());
            sb.append(", ").append(getMaxNodeCount()).append(", ").append(getSalesOrderRef() == null ? "" : getSalesOrderRef());

        return sb.toString();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
