package org.krams.tutorial.service;


import org.krams.tutorial.domain.UnitView;
import org.krams.tutorial.view.BaseTreeNode;

import java.util.List;

public interface GroupService {
    List<UnitView> findGroups(int userId, boolean isAdmin);
    BaseTreeNode getRootResellerTreeNode();
}
