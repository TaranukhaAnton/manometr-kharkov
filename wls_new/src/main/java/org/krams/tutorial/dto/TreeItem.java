package org.krams.tutorial.dto;

/**
 * Please, write comment here
 * Created by Oleg Golushkov
 * Date: 04.11.2009
 */
public class TreeItem {

    private String itemType;
    private int accountId;
    private String accountDescription;
    private int resellerId;
    private String resellerDescription;
    private int groupId;
    private String groupDescription;
    private int groupParentId;
    private boolean selected;
    private String lowLevelNodeType;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountDescription() {
        return accountDescription;
    }

    public void setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
    }

    public int getResellerId() {
        return resellerId;
    }

    public void setResellerId(int resellerId) {
        this.resellerId = resellerId;
    }

    public String getResellerDescription() {
        return resellerDescription;
    }

    public void setResellerDescription(String resellerDescription) {
        this.resellerDescription = resellerDescription;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public int getGroupParentId() {
        return groupParentId;
    }

    public void setGroupParentId(int groupParentId) {
        this.groupParentId = groupParentId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getLowLevelNodeType() {
        return lowLevelNodeType;
    }

    public void setLowLevelNodeType(String lowLevelNodeType) {
        this.lowLevelNodeType = lowLevelNodeType;
    }

}
