package com.sp.dto.flex;

import com.sp.model.EnumModel;

public class ReportHeaderFlx {

    private EnumModel unitView;
    private String dateRange;

    public EnumModel getUnitView() {
        return unitView;
    }

    public void setUnitView(EnumModel unitView) {
        this.unitView = unitView;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }
}
