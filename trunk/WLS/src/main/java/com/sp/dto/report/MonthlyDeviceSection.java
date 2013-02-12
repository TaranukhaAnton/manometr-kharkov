package com.sp.dto.report;

import java.util.ArrayList;
import java.util.List;

public class MonthlyDeviceSection {
	private int partnerId;
    private List<String> deviceIMEIs = new ArrayList<String>();
    private int accountCount;
    private int deviceCount;
    private String host;
    private String dbName;
    private int dbInstanceIndex;

    public MonthlyDeviceSection() {

    }
    
	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public int getPartnerId() {
		return partnerId;
	}

	private String partner;

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPartner() {
		return partner;
	}
	

	public void setDeviceIMEIs(List<String> deviceIMEIs) {
		this.deviceIMEIs = deviceIMEIs;
	}

	public List<String> getDeviceIMEIs() {
		return deviceIMEIs;
	}

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int getDbInstanceIndex() {
        return dbInstanceIndex;
    }

    public void setDbInstanceIndex(int dbInstanceIndex) {
        this.dbInstanceIndex = dbInstanceIndex;
    }

    public int getAccountCount() {
        return accountCount;
    }

    public void setAccountCount(int accountCount) {
        this.accountCount = accountCount;
    }

    public int getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }
}
