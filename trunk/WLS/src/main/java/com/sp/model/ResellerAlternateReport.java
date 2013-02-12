package com.sp.model;

import javax.persistence.*;

@Entity
@Table(name = "reseller_alternate_report")
public class ResellerAlternateReport extends BaseModel {
    @Column(name = "is_pdf")
    private boolean pdf;
    @Column(name = "is_xls")
    private boolean xls;
    @Column(name = "reseller_id")
    private int resellerId;
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "alternate_report_id")
    private AlternateReport alternateReport;

    public void copyTo(ResellerAlternateReport copy) {
        super.copyTo(copy);
        copy.pdf = pdf;
        copy.xls = xls;
        copy.resellerId = resellerId;
        copy.alternateReport = alternateReport;
    }

    public boolean isPdf() {
        return pdf;
    }

    public void setPdf(boolean pdf) {
        this.pdf = pdf;
    }

    public boolean isXls() {
        return xls;
    }

    public void setXls(boolean xls) {
        this.xls = xls;
    }

    public int getResellerId() {
        return resellerId;
    }

    public void setResellerId(int resellerId) {
        this.resellerId = resellerId;
    }

    public AlternateReport getAlternateReport() {
        return alternateReport;
    }

    public void setAlternateReport(AlternateReport alternateReport) {
        this.alternateReport = alternateReport;
    }
}
