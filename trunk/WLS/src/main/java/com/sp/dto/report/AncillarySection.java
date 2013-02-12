package com.sp.dto.report;

import com.sp.util.Util;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 19.01.2010
 * Time: 19:24:19
 * To change this template use File | Settings | File Templates.
 */
public class AncillarySection {
    private List<AncillaryItem> ancillaryItems;
    private String input1Name;
    private String input2Name;
    private String input3Name;
    private int boxTypeId;

    public List<AncillaryItem> getAncillaryItems() {
        return ancillaryItems;
    }

    public void setAncillaryItems(List<AncillaryItem> incomingLogRecord) {
        this.ancillaryItems = incomingLogRecord;
    }

    public String getInput1Name() {
        if (Util.isNullOrEmptyStr(input1Name)) return "No Name";
        if (ancillaryItems.get(0).isStealthMode()) return "Stealth";
        return input1Name;
    }

    public String getInput2Name() {
        if (Util.isNullOrEmptyStr(input2Name)) return "No Name";
        if (ancillaryItems.get(0).isStealthMode()) return "Stealth";
        return input2Name;
    }

    public String getInput3Name() {
        if (Util.isNullOrEmptyStr(input3Name)) return "No Name";
        if (ancillaryItems.get(0).isStealthMode()) return "Stealth";
        return input3Name;
    }

    public void setInput1Name(String input1Name) {
        this.input1Name = input1Name;
    }

    public void setInput2Name(String input2Name) {
        this.input2Name = input2Name;
    }

    public void setInput3Name(String input3Name) {
        this.input3Name = input3Name;
    }

    public int getBoxTypeId() {
        return boxTypeId;
    }

    public void setBoxTypeId(int boxTypeId) {
        this.boxTypeId = boxTypeId;
    }
}
