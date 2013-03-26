package org.krams.tutorial.controller;


import org.apache.log4j.Logger;
import org.krams.tutorial.service.GroupService;
import org.krams.tutorial.view.BaseTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
@RequestMapping("/reports")
public class ReportController {
    protected static Logger logger = Logger.getLogger(ReportController.class);

    @Autowired
    GroupService groupService;



    @RequestMapping(value = "/journey", method = RequestMethod.GET)
    public String getJourneyRep(Map<String, Object> map) {
        logger.debug("Received request to show JourneyRep page");
        BaseTreeNode rootResellerTreeNode = groupService.getRootResellerTreeNode();
        map.put("baseTreeNode", rootResellerTreeNode);
        return "reports/journeyReport";
    }

}
