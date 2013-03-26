package org.krams.tutorial.controller;

import org.apache.log4j.Logger;
import org.krams.tutorial.domain.Vehicle;
import org.krams.tutorial.dto.VehicleDto;
import org.krams.tutorial.service.GroupService;
import org.krams.tutorial.service.VehicleService;
import org.krams.tutorial.view.BaseTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/")
public class MainController {

    protected static Logger logger = Logger.getLogger(MainController.class);


    @Autowired
    VehicleService vehicleService;


    @Autowired
    GroupService groupService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(Map<String, Object> map) {
        logger.debug("Received request to show mapping page");
        BaseTreeNode rootResellerTreeNode = groupService.getRootResellerTreeNode();
        map.put("baseTreeNode",rootResellerTreeNode);
        return "mapping";
    }

//    @RequestMapping(value = "/IFrameMap", method = RequestMethod.GET)
//    public String mapPage(Map<String, Object> map) {
//        logger.debug("Received request to show mapping page");
//        BaseTreeNode rootResellerTreeNode = groupService.getRootResellerTreeNode();
//        map.put("baseTreeNode",rootResellerTreeNode);
//        return "IFrameMap";
//    }


    @RequestMapping(value = "/getVehicles", method = RequestMethod.POST)
    public
    @ResponseBody
    Object getPersons(Integer groupId) {
        logger.debug("Received request to get vehiles for groop id =" + groupId);
        Collection<Vehicle> vehicles = vehicleService.getByUnitViewId(groupId);
        Set vehiclesDTOs = new HashSet();
        for (Vehicle vehicle : vehicles) {
            VehicleDto e = new VehicleDto(vehicle);
            e.setDirectionOfTravel("e");
            vehiclesDTOs.add(e);
        }
        logger.debug("Found " + vehicles.size() +" vehicles.");
        return vehiclesDTOs;
    }






}
