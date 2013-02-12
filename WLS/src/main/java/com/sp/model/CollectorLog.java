package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * User: andrey
 */
@Entity
@Table(name = "collector_log")
public class CollectorLog extends EnumModel {

    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "collector_type")
    private String collectorType;

    public void copyTo(CollectorLog copy) {
        super.copyTo(copy);
        copy.createdDate = createdDate;
        copy.collectorType = collectorType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCollectorType() {
        return collectorType;
    }

    public void setCollectorType(String collectorType) {
        this.collectorType = collectorType;
    }
}
