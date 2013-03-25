package org.krams.tutorial.util;

import org.krams.tutorial.domain.Account;
import org.krams.tutorial.domain.Reseller;
import org.krams.tutorial.dto.TreeItem;
import org.krams.tutorial.view.BaseTreeNode;

import java.util.*;

public class Util {
    public static enum NODES_TYPE_NAME {PhoneView,PoiGroup,AssetsView,HandheldView,UnitView,AoiView,Account,Vehicle}



    public static List<Account> getAllAccountsFromFatchedList(List<TreeItem> allItemsForTree) {
        SortedMap<String, Account> accounts = new TreeMap<String, Account>();
        for (TreeItem treeItem : allItemsForTree) {
            String key = treeItem.getAccountDescription() + treeItem.getAccountId();
            if (accounts.get(key) == null) {
                Account acc = new Account();
                acc.setId(treeItem.getAccountId());
                acc.setDescr(treeItem.getAccountDescription());
                Reseller res = new Reseller();
                res.setId(treeItem.getResellerId());
                res.setDescr(treeItem.getResellerDescription());
                acc.setReseller(res);
                accounts.put(key, acc);
            }
        }
        return new ArrayList<Account>(accounts.values());
    }

    public static List<BaseTreeNode> getItemViewNodes(String itemType,
                                                      String objTypeName,
                                                      int currAccountId,
                                                      List<TreeItem> allItemsForTree) {
        List<Account> itemViewRoots = getRootsByItemAccountId(itemType, currAccountId, allItemsForTree);
        List<BaseTreeNode> itemViewNodes = new ArrayList<BaseTreeNode>();
        for (Account itemView : itemViewRoots) {
            BaseTreeNode itemViewTreeNode =
                    new BaseTreeNode(itemView.getId(),
                            itemView.getDescr(), objTypeName);
            itemViewTreeNode.setAccountId(currAccountId);
            itemViewTreeNode.setChildren(getNodesChildrenByItemViewId(itemType,
                    itemView.getId(), allItemsForTree));
            itemViewNodes.add(itemViewTreeNode);
        }
        return itemViewNodes;
    }

    public static List<Account> getRootsByItemAccountId(String itemType, long accountId, List<TreeItem> allItemsForTree) {
        HashMap<Integer, Account> roots = new HashMap<Integer, Account>();
        for (TreeItem ti : allItemsForTree) {
            if (ti.getItemType().equals(itemType) &&
                    ti.getGroupParentId() == 0 &&
                    ti.getAccountId() == accountId &&
                    roots.get(ti.getGroupId()) == null) {
                Account account = new Account();
                account.setId(ti.getGroupId());
                account.setDescr(ti.getGroupDescription());
                roots.put(account.getId(), account);
            }
        }
        return new ArrayList<Account>(roots.values());
    }


    public static List<BaseTreeNode> getNodesChildrenByItemViewId(String itemType, int groupId, List<TreeItem> allItemsForTree) {
        List<BaseTreeNode> children = new ArrayList<BaseTreeNode>();
        for (TreeItem ti : allItemsForTree) {
            int currGroupid = ti.getGroupId();
            if (ti.getItemType().equals(itemType) &&
                    ti.getGroupParentId() == groupId &&
                    !isIdInList(children, currGroupid)) {
                BaseTreeNode groupNode = new BaseTreeNode(currGroupid,
                        ti.getGroupDescription(), Constants.NODES_TYPE_NAME.UnitView.toString());
                groupNode.setAccountId(groupId);
                groupNode.setSelected(ti.isSelected());
                if(!ti.getLowLevelNodeType().equals(NODES_TYPE_NAME.Vehicle.toString())){
                    groupNode.setChildren(getNodesChildrenByItemViewId(itemType,
                            ti.getGroupId(), allItemsForTree));
                }
                children.add(groupNode);
            }
        }
        return children;
    }
    public static boolean isIdInList(List<BaseTreeNode> list, int id) {
        for (BaseTreeNode btn : list) {
            if (btn.getId() == id) {
                return true;
            }
        }
        return false;
    }


    public static BaseTreeNode getResellerNode(BaseTreeNode root, int id, String descr) {
        for (BaseTreeNode node : root.getChildren()) {
            if (node.getId() == id) {
                return node;
            }
        }
        BaseTreeNode node = new BaseTreeNode(id, descr);
        SortedMap<String, BaseTreeNode> childrenSortedMap = new TreeMap<String, BaseTreeNode>();
        for (BaseTreeNode child : root.getChildren()) {
            childrenSortedMap.put(child.getDescr(), child);
        }
        childrenSortedMap.put(descr, node);
        root.setChildren(new ArrayList<BaseTreeNode>(childrenSortedMap.values()));
        return node;
    }

    public static Constants.IgnitionStatus getIgnitionStatus(boolean ignitionActive, double curSpeed) {
        if (ignitionActive) {
            if (curSpeed > 0) {
                return Constants.IgnitionStatus.On;
            } else {
                return Constants.IgnitionStatus.Idling;
            }
        } else {
            return Constants.IgnitionStatus.Off;
        }
    }


}
