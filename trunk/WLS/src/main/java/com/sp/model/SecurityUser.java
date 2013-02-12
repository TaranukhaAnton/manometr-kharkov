package com.sp.model;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "security_user")
public class SecurityUser extends BaseSecurityUser {
    @Column(insertable = false, updatable = false)
    private int code;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "reseller_id")
    private BaseReseller reseller;

    @Column(name = "user_name")
    private String userName;

    private String password;

    private String email;

    @Column(name = "is_driver")
    boolean driver;

    @Column(name = "is_super_administrator")
    boolean usualAdministrator;

    @Column(name = "reports_order")
    String reportsOrder;

    @Column(name = "is_phone_user")
    boolean phoneUser;

    @Column(name = "is_handheld_user")
    boolean handheldUser;

    @Column(name = "is_engineer")
    private boolean engineer;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;

    private String salutation;

    @Column(name = "creation_date", insertable = false, updatable = false)
    private Date creationDate;

    @Column(name = "is_deleted")
    private boolean deleted;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tag_id")
    private Asset asset;

    @Column(name = "access_level")
    private String accessLevel;

    @Column(name = "is_employee")
    private boolean employee;

    @Column(name = "is_account_manager")
    private boolean accountManager;

    @Column(name = "is_report_scheduling_allowed")
    private boolean reportSchedulingAllowed;

    @Column(name = "is_report_send_now_allowed")
    private boolean reportSendNowAllowed;

    @Column(name = "is_can_event_report_configurator_allowed")
    private boolean canEventReportConfiguratorAllowed;

    @Column(name = "is_ios_connection_allowed")
    private boolean iOSConnectionAllowed;


    @Column(name = "display_name")
    private String displayName;

    @Transient
    private String accountDescrs;

    public void copyTo(SecurityUser copy) {
        super.copyTo(copy);
        copy.code = code;
        copy.reseller = reseller;
        copy.userName = userName;
        copy.password = password;
        copy.email = email;
        copy.driver = driver;
        copy.engineer = engineer;
        copy.telephoneNumber = telephoneNumber;
        copy.mobilePhoneNumber = mobilePhoneNumber;
        copy.salutation = salutation;
        copy.creationDate = creationDate;
        copy.deleted = deleted;
        copy.asset = asset;
        copy.accessLevel = accessLevel;
        copy.employee = employee;
        copy.accountManager = accountManager;
        copy.phoneUser = phoneUser;
        copy.handheldUser = handheldUser;
        copy.canEventReportConfiguratorAllowed = canEventReportConfiguratorAllowed;
        copy.reportsOrder = reportsOrder;
        copy.displayName = displayName;
        copy.usualAdministrator = usualAdministrator;
        copy.reportSchedulingAllowed = reportSchedulingAllowed;
        copy.reportSendNowAllowed = reportSendNowAllowed;
        copy.iOSConnectionAllowed = iOSConnectionAllowed;
    }

    public boolean isHandheldUser() {
        return handheldUser;
    }

    public void setHandheldUser(boolean handheldUser) {
        this.handheldUser = handheldUser;
    }

    public boolean isPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(boolean phone_user) {
        this.phoneUser = phone_user;
    }


    public String getUserName() {
        if (userName != null && userName.contains("generated_")) {
            return "N/A";
        }
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }    

    public String getEmail() {
        if (email == null || email.length() < 1) {
            return "N/A";
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDriver() {
        return driver;
    }

    public void setDriver(boolean driver) {
        this.driver = driver;
    }

    public boolean isEngineer() {
        return engineer;
    }

    public void setEngineer(boolean engineer) {
        this.engineer = engineer;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

    public boolean isAccountManager() {
        return accountManager;
    }

    public void setAccountManager(boolean accountManager) {
        this.accountManager = accountManager;
    }

       public String toOptionText() {
          return super.toOptionText();
      }

    public void setAccountDescrs(String accountDescrs) {
        this.accountDescrs = accountDescrs;
    }

    public String getAccountDescrs() {
        return accountDescrs;
    }

    public boolean isCanEventReportConfiguratorAllowed() {
        return canEventReportConfiguratorAllowed;
    }

    public void setCanEventReportConfiguratorAllowed(boolean canEventReportConfiguratorAllowed) {
        this.canEventReportConfiguratorAllowed = canEventReportConfiguratorAllowed;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BaseReseller getReseller() {
        return reseller;
    }

    public void setReseller(BaseReseller reseller) {
        this.reseller = reseller;
    }

    public String getReportsOrder() {
        return reportsOrder;
    }

    public void setReportsOrder(String reportsOrder) {
        this.reportsOrder = reportsOrder;
    }


    public String getFullCodeStr() {
        if (reseller == null) {
            return null;
        } else {
            NumberFormat formatter = new DecimalFormat("000");
            String resellerCode = formatter.format(reseller.getCode());
            formatter = new DecimalFormat("00000");
            String userCode = formatter.format(code);
            return resellerCode + "-" + userCode;
        }
    }

    public void setFullCodeStr(String fullCodeStr) {
    }

    public boolean isReportSchedulingAllowed() {
        return reportSchedulingAllowed;
    }

    public void setReportSchedulingAllowed(boolean reportSchedulingAllowed) {
        this.reportSchedulingAllowed = reportSchedulingAllowed;
    }

    public boolean isUsualAdministrator() {
        return usualAdministrator;
    }

    public void setUsualAdministrator(boolean usualAdministrator) {
        this.usualAdministrator = usualAdministrator;
    }

    public boolean isReportSendNowAllowed() {
        return reportSendNowAllowed;
    }

    public void setReportSendNowAllowed(boolean reportSendNowAllowed) {
        this.reportSendNowAllowed = reportSendNowAllowed;
    }

    public boolean isiOSConnectionAllowed() {
        return iOSConnectionAllowed;
    }

    public void setiOSConnectionAllowed(boolean iOSConnectionAllowed) {
        this.iOSConnectionAllowed = iOSConnectionAllowed;
    }
}
