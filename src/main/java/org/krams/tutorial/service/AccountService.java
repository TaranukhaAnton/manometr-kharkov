package org.krams.tutorial.service;

import org.krams.tutorial.dao.AccountDao;
import org.krams.tutorial.dto.TreeItem;
import org.krams.tutorial.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {


    @Autowired
    AccountDao accountDao;


    public List<TreeItem> findAllItemsForTree(String treeType){
//        LOGGER.info("findAllItemsForTree() started ");
        List<TreeItem> list = new ArrayList<TreeItem>();
        Integer userId = null;
    //    if (!SpContext.getUserDetailsInfo().getAuthoritiesMap().containsKey(Constants.ROLE_ADMIN)) {
     //       userId = SpContext.getUserDetailsInfo().getUserId();
     //   }


        if (treeType.equalsIgnoreCase(Constants.LEFT_MENU_TREE_TYPE.notExpired.toString())){
            list = accountDao.findAllItemsForTreeNotExpired(userId);
        }else if (treeType.equalsIgnoreCase(Constants.LEFT_MENU_TREE_TYPE.all.toString())){
            list = accountDao.findAllItemsForTree(userId);
        }
   //     LOGGER.info("findAllItemsForTree() finished ");
        return list;
    }
}
