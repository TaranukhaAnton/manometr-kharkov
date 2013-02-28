package org.krams.tutorial.view;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class BaseTreeNode {
    private int id;
    private String descr;
    private String type;
    private List<BaseTreeNode> children = new ArrayList<BaseTreeNode>();
    private int accountId;
    private boolean selected; // this field is used for CheckedTree. selected or no depend on method

    public BaseTreeNode(int id, String descr, String type) {
        this.id = id;
        this.descr = descr;
        this.type = type;
    }

    public BaseTreeNode(int id, String descr) {
        this(id, descr, null);
    }

    public BaseTreeNode() {
        
    }

    public String toString() {
        return descr;
    }

    public void setChildren(List<BaseTreeNode> children) {
        this.children = children;
    }

    public List<BaseTreeNode> getChildren() {
        return children;
    }

    public int getId() {
        return id;
    }

    public String getDescr() {
        return descr;
    }

    public String getType() {
        return type;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) { // for the low level elements like vehicles here is group id instead account id
        this.accountId = accountId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
