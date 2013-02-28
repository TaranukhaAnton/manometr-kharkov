package org.krams.tutorial.service;

import org.apache.log4j.Logger;
import org.krams.tutorial.dao.GroupDao;
import org.krams.tutorial.domain.Account;
import org.krams.tutorial.domain.UnitView;
import org.krams.tutorial.dto.TreeItem;
import org.krams.tutorial.util.Constants;
import org.krams.tutorial.util.Util;
import org.krams.tutorial.view.BaseTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private final static Logger LOGGER = Logger.getLogger(GroupServiceImpl.class);

    @Autowired
    GroupDao groupDao;


    @Autowired
    AccountService accountService;


    @Transactional
    public List<UnitView> findGroups(int userId, boolean isAdmin) {
        List<UnitView> groups = groupDao.findGroups(userId, isAdmin);
        return groups;
    }

    @Transactional
    public BaseTreeNode getRootResellerTreeNode() {
        LOGGER.info("getRootResellerTreeNode() started");
        BaseTreeNode rootLeftMenuTreeNode = new BaseTreeNode(0, "All");
        // todo rename treeNode from BAseTree node
        // todo return List EnumModels from findAllItemsForTree
        // todo bug: we cannot see parent group if it empty and subgroup has item
        // todo bug if we select expired grup on list page and after that return on mapping page - current group will be expired
        // todo on the list page when you select not current item group we get NullPointerException

        List<TreeItem> allItemsForTree = accountService.findAllItemsForTree( Constants.LEFT_MENU_TREE_TYPE.notExpired.toString());
        //List<TreeItem> allItemsForTree = accountService.getAllItemsForTree(getCurrentLeftMenuTreeType());
        List<Account> accountList = Util.getAllAccountsFromFatchedList(allItemsForTree);

        for (Account account : accountList) {
            int currAccountId = account.getId();
            BaseTreeNode accNode = new BaseTreeNode(account.getId(),
                    account.getDescr(), "Account");

            //Unit Views
            BaseTreeNode vehicleViewTopRoot = new BaseTreeNode(0, "Unit Groups");
            vehicleViewTopRoot.getChildren().addAll(Util.getItemViewNodes(Constants.OBJECT_TYPE_NAME_SELECTED.vehicle.toString(),
                    Constants.NODES_TYPE_NAME.UnitView.toString(), currAccountId, allItemsForTree));

            //Phone Views
            BaseTreeNode phoneViewTopRoot = new BaseTreeNode(0, "Phone Groups");

            phoneViewTopRoot.getChildren().addAll(Util.getItemViewNodes(Constants.OBJECT_TYPE_NAME_SELECTED.phone.toString(),
                    Constants.NODES_TYPE_NAME.PhoneView.toString(), currAccountId, allItemsForTree));

            //Handheld Views
            BaseTreeNode handheldViewTopRoot = new BaseTreeNode(0, "Handheld Groups");
            handheldViewTopRoot.getChildren().addAll(Util.getItemViewNodes(Constants.OBJECT_TYPE_NAME_SELECTED.handheld.toString(),
                    Constants.NODES_TYPE_NAME.HandheldView.toString(), currAccountId, allItemsForTree));

            if (vehicleViewTopRoot.getChildren().size() > 0
                    || phoneViewTopRoot.getChildren().size() > 0
                    || handheldViewTopRoot.getChildren().size() > 0) {
                if (vehicleViewTopRoot.getChildren().size() > 0) {
                    accNode.getChildren().add(vehicleViewTopRoot);
                }
                if (phoneViewTopRoot.getChildren().size() > 0) {
                    accNode.getChildren().add(phoneViewTopRoot);
                }
                if (handheldViewTopRoot.getChildren().size() > 0) {
                    accNode.getChildren().add(handheldViewTopRoot);
                }

                BaseTreeNode rootNode = Util.getResellerNode(
                        rootLeftMenuTreeNode,
                        account.getReseller().getId(),
                        account.getReseller().getDescr());

                rootNode.getChildren().add(accNode);
            }
        }

        while (rootLeftMenuTreeNode.getChildren().size() == 1) {
            rootLeftMenuTreeNode = rootLeftMenuTreeNode.getChildren().get(0);
        }

        LOGGER.info("getRootResellerTreeNode() finished");
        return rootLeftMenuTreeNode;
    }


}
