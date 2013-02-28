<%@ page contentType="text/html;charset=UTF-8" language="java" %>




<!-- saved from url=(0014)about:internet -->
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--<link href="<h:outputText value="#{urlRebrandingController.urlRebrandingMap['css/rebranding.css']}"/>?version=<%=com.sp.util.Util.getApplicationVersion()%>" rel="stylesheet" type="text/css"/>--%>
<title></title>
<%--<style>--%>
<%--body { margin: 0px; overflow:hidden }--%>
<%--</style>--%>
</head>
<jsp:include page="jsLoggerInit.html"/>
<script type="text/javascript" src="../js/wls/spCommon.js?version=14"></script>
<jsp:include page="initMap.jsp"/>
<script type="text/javascript" language="javascript">AC_FL_RunContent = 0;</script>
<script type="text/javascript" src="../js/wls/IFrameMapLib.js?version=14"></script>


<script language="JavaScript" type="text/javascript">
// SPSystem map activation javascript
    var map;
    var currentFlashMovie;
    var selectedObjects = new Object();
    var taskDetails = new Array();
    var vehiclesToSelect = new Array();
    var vehiclesToSelectDepo = new Array();
    var activity = new Array();
    var allVehicles = new Array();
    var handheldsToSelect = new Array();
    var allHandhelds = new Array();
    var isMapViewType = true;
    var isFlex = true;
    var vehicleIconSize = 50;
    var isVehicleIconRotation = true;
    var isZoomMode = typeof(isVehicleZoomMode) != "undefined" ? isVehicleZoomMode : false; //from mapping.jsp
    var disableZoomWhileRefresh = false;
    var isVehicleZoomClickProcessingAllowed = true;
    var zoomedVehicleId = null;
    var zoomedVehicle = null;
    //var drawControl;
    var msvePlaces = null;
    //var polyWkt = null;
    var myLoc = null;
    var hoverOverPoiAllowed = false;
    var poiAreaColoured = false;
    var hoverOverAoiAllowed = false;
    var aoiAreaColoured = false;
    var choosePoiAreaColour = false;
    var chooseAoiAreaColour = false;
    var mapRezoom = true;
    var rightClickOnMapListener;

    function setAdditionalVars(maxDefaultZoomLevel){
        if(map!=undefined){
            map.setMaxDefaultZoomLevel(maxDefaultZoomLevel);
        }
    }

    function init(width, height, panelType,registerListeners){
        if (typeof(log) != 'undefined') log.debug("init started panelType = " + panelType);
        var d = document.getElementById('flex_maparea');
        var hw = getHW(panelType);
        d.style.width=(width - hw[1]);
        d.style.height=(height - hw[0]);
        if (typeof(log) != 'undefined') log.debug("init map width = " + d.style.width);
        if (typeof(log) != 'undefined') log.debug("init map height = " + d.style.height);

        map = getMapFlx('flex_maparea');
        if (registerListeners == undefined || registerListeners){
            map.registerOnShapeMouseEvents(pinMouseOverListener,pinMouseOutListener,addPoiClickListener);
        }
        if (map.setZoomEnabled != null && map.setZoomEnabled != undefined){
            map.setZoomEnabled(false);
        }
        if (map.hideBE != null && map.hideBE != undefined){
            map.hideBE(false);
        }
        var zoom = getInitialZoom();
        if (typeof(log) != 'undefined') log.debug("init map zoom=" + zoom);
        map.zoomTo(zoom);
        if (typeof(log) != 'undefined') log.debug("init finished");
    }

    function getHW(panelType){
        var h = 0;
        var w = 0;
        if (panelType == 'taskDetails'){
             h = 2;
             w = 2;
        }else{
            if (location.toString().indexOf("vine")!= -1){
                h = 35;
                w = 4;
            }else if (location.toString().indexOf("truck") != -1){
                h = 39;
                w = 8;
            } else if(location.toString().indexOf("vtlive") != -1) {
                h = 40;
                w = 12;
            } else if(location.toString().indexOf("livetrak") != -1) {
                h = 30;
                w = -1;

            } else if(location.toString().indexOf("webtrack365") != -1) {
                h = 32;
                w = 8;
            }  else if(location.toString().indexOf("cdgi") != -1) {
                h = panelType == 'snailTrail' ? 42 : 38;
                w = panelType == 'snailTrail' ? 22 : 2;
            } else if(location.toString().indexOf("vehiclestracked") != -1) {
                 h = 31;                          
                 w = 4;
            } else if(location.toString().indexOf("citytalk") != -1) {
                 h = 52;
                 w = 32;
            }  else if(location.toString().indexOf("azlan") != -1) {
                 h = 32;
                 w = 4;
            }  else if(location.toString().indexOf("payasyougotracking") != -1
                    || location.toString().indexOf("redsky") != -1) {
                h = 42;
                w = 12;
            } else if(location.toString().indexOf("fleettrackers") != -1) {
                h = 32;
                w = 4;
            }   else if(location.toString().indexOf("whitelabelsaas") != -1) {

                if (panelType == 'snailTrail') {
                    h = 40;
                    w = 22;
                } else if(location.toString().indexOf("azlan") != -1) {
                    h = 32;
                    w = 4;
                }else {
                    h = 26;
                    w = -5;
                }
            }
        }
        return new Array(h,w);
    } 
    
    function setMapPanelDimensions(width,height,panelType,registerListeners) {
        if (typeof(log) != 'undefined') log.debug("setMapPanelDimensions started");
        init (width,height,panelType,registerListeners);
        if (typeof(log) != 'undefined') log.debug("setMapPanelDimensions finished");
    }

    function clearAllArrays(){
        if (vehiclesToSelect.length != null) {
            vehiclesToSelect.length = 0;
        }
        if (allVehicles.length != null) {
            allVehicles.length = 0;
        }
        if (handheldsToSelect.length != null) {
            handheldsToSelect.length = 0;
        }
        if (allHandhelds.length != null) {
            allHandhelds.length = 0;
        }
        if (activity != null) {
            activity = null;
        }
    }

    function setActivity(activity) {
        if (typeof(activity) != "undefined") {
            this.activity = {
                id:activity.id,
                imageFileName:activity.vehicle.imageFileName,
                descr:getVehicleMapDescrFlx(activity.vehicle,canUserReadJourneyCost,canResellerVehicleHoverActivity),
                recDate:activity.recDate,
                lat:activity.lat,
                lon:activity.lon,
                directionCode:activity.directionCode,
                journeyStart:activity.journeyStart,
                journeyEnd:activity.journeyEnd,
                speed:activity.factoredSpeed,
                ignitionStr:activity.ignitionStr
            };
      }
    }

    function getVehicleObject(vehicle){
        var canHover = parent.location.href.indexOf("proximity")==-1 ? canResellerVehicleHover : canResellerVehicleHoverProximity;
        var curVehicle = {
            id:vehicle.id,
            descr:getVehicleMapDescrFlx(vehicle,canUserReadJourneyCost,canHover),
            imageFileName:vehicle.imageFileName,
            lat:vehicle.lat,
            lon:vehicle.lon,
            roadLon:vehicle.roadLon,
            roadLat:vehicle.roadLat,
            distanceToRoad:vehicle.distanceToRoad,
            typeDescr:vehicle.typeDescr,
            directionOfTravel:vehicle.directionOfTravel,
            fleetId:vehicle.fleetId,
            ignitionStatus:vehicle.ignitionStatus,
            regNumber:vehicle.registrationNumber,
            selected:vehicle.selected,
            dotOnMap:vehicle.dotOnMap
        };
        return curVehicle;
    }

    function refreshVehicleToSelectArr(vehicles){
        if (vehiclesToSelect.length != null) {
            vehiclesToSelect.length = 0;
        }
        for (var i=0;i<vehicles.length;i++){
            var curVehicle = getVehicleObject(vehicles[i]);
            for (var j=0;j<allVehicles.length;j++){
                if(allVehicles[j].id == curVehicle.id){
                    allVehicles[j] = curVehicle;
                     if (vehicles[i].selected){
                        vehiclesToSelect[vehiclesToSelect.length]=curVehicle;
                     }
                    break;
                }
            }
        }
    }

    function refreshHandheldToSelectArr(handhelds){
        for (var i=0;i<handhelds.length;i++){
            var curHandheld = handhelds[i];
            for (var j=0;j<allHandhelds.length;j++){
                if(allHandhelds[j].id == curHandheld.id){
                    allHandhelds[j] = curHandheld;
                    break;
                }
            }
        }
        if (handheldsToSelect.length != null) {
            handheldsToSelect.length = 0;
        }
        for (var k=0;k<allHandhelds.length;k++){                                         
            if (allHandhelds[k].selected){
                handheldsToSelect[handheldsToSelect.length] = allHandhelds[k];
            }
        }
    }

    function setVehiclesToSelectArr(vehicles){
        if (typeof(vehicles) != "undefined") {
            clearAllArrays();
            for (var i=0; i < vehicles.length; i++){
                var curVehicle = getVehicleObject(vehicles[i]);
                allVehicles[i] = curVehicle;
                if (vehicles[i].selected){
                    vehiclesToSelect[vehiclesToSelect.length]=curVehicle;
                }
            }
      }
    }

    function setHandheldsToSelectArr(units){
        if (typeof(units) != "undefined") {
            clearAllArrays();
            for (var i=0; i < units.length; i++){
                var curmovableItem = {
                    id:units[i].id,
                    descr:units[i].clientDescr,
                    imageFileName:units[i].imageFileName + "",
                    lat:units[i].lat,
                    lon:units[i].lon,
                    typeDescr:units[i].typeDescr.toString().toLowerCase(),
                    clientDescr:units[i].clientDescr
                };
                allHandhelds[i] = curmovableItem;
                if (units[i].selected){
                    handheldsToSelect[handheldsToSelect.length]=curmovableItem;
                }
            }
       }
    }

    function putRefreshedVehicleListOnMap(ignitionIconsVersion, fleetIdOverIcons, iconGlow, glowColor,
                                          hideRedPin, disableOnIgnOff, stickyRoadsAllowed, showVehTooltipAllowed, drawArrowFromVehIconToRoad){
        if (typeof(log) != 'undefined') log.debug("putRefreshedVehicleListOnMap start refreshedVehicleList.length = " + vehiclesToSelect.length);
        for(var i = 0; i < vehiclesToSelect.length; i++) {
            if (vehiclesToSelect[i].selected) {
                addVehicleOnMapFlx(vehiclesToSelect[i],'vehicle' + vehiclesToSelect[i].id, ignitionIconsVersion, fleetIdOverIcons, iconGlow,
                     glowColor, hideRedPin,true, disableOnIgnOff, stickyRoadsAllowed, showVehTooltipAllowed, drawArrowFromVehIconToRoad);
            }
        }
        if (typeof(log) != 'undefined') log.debug("putRefreshedVehicleListOnMap finished");
    }

    function putRefreshedHandheldListOnMap(refreshedHandheldList){
        if (typeof(log) != 'undefined') log.debug("putRefreshedHandheldListOnMap start refreshedVehicleList.length = " + refreshedVehicleList.length);
        for(var i = 0; i < refreshedHandheldList.length; i++) {
            addMovableItemOnMapFlx(refreshedHandheldList[i]);
        }
        if (typeof(log) != 'undefined') log.debug("putRefreshedHandheldListOnMap finished");
    }

    function putVehicleListOnMap(ignitionIconsVersion, fleetIdOverIcons, iconGlow, glowColor,
                                 hideRedPin, disableOnIgnOff, stickyRoadsAllowed, showVehTooltipAllowed, drawArrowFromVehIconToRoad){
        if (typeof(log) != 'undefined') log.debug("putVehicleListOnMap start vehiclesToSelect.length = " + vehiclesToSelect.length);
        for(var i = 0; i < vehiclesToSelect.length; i++) {
            var withMapRefresh = false; 
            if (vehiclesToSelect.length<7 || i>vehiclesToSelect.length-7){
                withMapRefresh = true;
            }

            addVehicleOnMapFlx(vehiclesToSelect[i],'vehicle' + vehiclesToSelect[i].id, ignitionIconsVersion, fleetIdOverIcons, iconGlow,
                    glowColor, hideRedPin,withMapRefresh, disableOnIgnOff, stickyRoadsAllowed, showVehTooltipAllowed, drawArrowFromVehIconToRoad);
        }
        //setTimeout(rezoomMapFlx,2000);
        if (typeof(log) != 'undefined') log.debug("putVehicleListOnMap finished");
    }

    function putHandheldListOnMap(){
        if (typeof(log) != 'undefined') log.debug("putHandheldListOnMap start handheldsToSelect.length = " + handheldsToSelect.length);
        for(var i = 0; i < handheldsToSelect.length; i++) {
            addMovableItemOnMapFlx(handheldsToSelect[i]);
        }
        setTimeout(rezoomMapFlx,2000);
        if (typeof(log) != 'undefined') log.debug("putHandheldListOnMap finished");
    }

    function addRemovePoiAoiList(items, objType, multiColorPinsPoi, multiColorPinsAoi, aoiAreaColoured, chooseAoiAreaColour){
        if (typeof(log) != 'undefined') log.debug("addRemovePoiAoiList items.length = " + items.length);
         removeObjectsFromMapByType(objType);
         for (var i =0; i < items.length; i++){
               addPoiAoiOnMapFlx(items[i],  multiColorPinsPoi, multiColorPinsAoi, aoiAreaColoured, chooseAoiAreaColour);
         }
         rezoomMapFlx();
    }

    function refreshVehiclesOnMap(refreshedVehicles, ignitionIconsVersion, fleetIdOverIcons, iconGlow, glowColor,
                                  hideRedPin, disableOnIgnOff,
                                  stickyRoadsAllowed, showVehTooltipAllowed, drawArrowFromVehIconToRoad){
        if (typeof(log) != 'undefined') log.debug("refreshVehiclesOnMap started");
        removeRefreshedVehiclesFromMap(refreshedVehicles);
        refreshVehicleToSelectArr(refreshedVehicles);
        putRefreshedVehicleListOnMap(ignitionIconsVersion, fleetIdOverIcons, iconGlow, glowColor,
                hideRedPin, disableOnIgnOff,
                stickyRoadsAllowed, showVehTooltipAllowed, drawArrowFromVehIconToRoad);
        if (typeof(log) != 'undefined') log.debug("refreshVehiclesOnMap finished");
    }

    function refreshHandheldsOnMap(refreshedHandhelds) {
        removeRefreshedHandheldsFromMap(refreshedHandhelds);
        refreshHandheldToSelectArr(refreshedHandhelds);
        putRefreshedHandheldListOnMap(refreshedHandhelds);
    }

    function refreshMovableItemsOnMap(refreshedUnits,movableItemType, ignitionIconsVersion, fleetIdOverIcons, iconGlow, glowColor,
                                  hideRedPin, disableOnIgnOff,
                                  stickyRoadsAllowed,
                                  showVehTooltipAllowed, drawArrowFromVehIconToRoad){
        if (typeof(log) != 'undefined') log.debug("refreshMovableItemsOnMap started");
        if (movableItemType == "vehicle"){
            refreshVehiclesOnMap(refreshedUnits, ignitionIconsVersion, fleetIdOverIcons, iconGlow, glowColor,
                                  hideRedPin, disableOnIgnOff,
                                  stickyRoadsAllowed,
                                  showVehTooltipAllowed, drawArrowFromVehIconToRoad);
        } else if (movableItemType == "handheld") {
            refreshHandheldsOnMap(refreshedUnits);
        }
        if (typeof(log) != 'undefined') log.debug("refreshMovableItemsOnMap finished");
    }

    function putMovableItemsOnMap(movableItems,movableItemType, ignitionIconsVersion, fleetIdOverIcons, iconGlow, glowColor,
                                  hideRedPin, disableOnIgnOff, stickyRoadsAllowed, showVehTooltipAllowed, drawArrowFromVehIconToRoad){
        if (typeof(log) != 'undefined') log.debug("putMovableItemsOnMap started movableItemType = " + movableItemType);

        removeObjectsFromMapByType(movableItemType);
        if (movableItemType == "vehicle"){

            setVehiclesToSelectArr(movableItems);
            putVehicleListOnMap(ignitionIconsVersion, fleetIdOverIcons, iconGlow, glowColor,
                    hideRedPin, disableOnIgnOff, stickyRoadsAllowed, showVehTooltipAllowed, drawArrowFromVehIconToRoad);
        } else if (movableItemType == "activity") {
            if (typeof(log) != 'undefined') log.debug("putMovableItemsOnMap 1");
            clearAllArrays();
            if (typeof(log) != 'undefined') log.debug("putMovableItemsOnMap 2");
            setActivity(movableItems);
            if (typeof(log) != 'undefined') log.debug("putMovableItemsOnMap 3");
            addActivityOnMapFlx(activity, "activity" + activity.id);
            if (typeof(log) != 'undefined') log.debug("putMovableItemsOnMap 4");
            rezoomMapFlx();
        } else if(movableItemType == "handheld"){
            setHandheldsToSelectArr(movableItems);
            putHandheldListOnMap();
            //initSelectedMovableItems(movableItemType,handheldsToSelect);
        }
        if (typeof(log) != 'undefined') log.debug("putMovableItemsOnMap finished");
    }

    function reloadVehiclesOnMap(vehicles){
        if (typeof(log) != 'undefined') log.debug("reloadVehiclesOnMap start");
        setVehiclesToSelectArr(vehicles);
        reloadSelectedVehicle();
        if (typeof(log) != 'undefined') log.debug("reloadVehiclesOnMap finish");
    }

 // for polygones

    //var finishedCallback;

    function initDrawControl(polygoneWkt, finished, pinColor, areaColor){

       map.initDrawControl(map,polygoneWkt, finished, pinColor, areaColor);

    }

    function onFinishDrawing(pointsWkt) {
        map.onFinishDrawing(pointsWkt);
    }

    function deletePolygon(){
        map.deletePolygon();
    }

    function addPointToPoly(lon, lat) {

        return map.addPointToPoly(lon, lat);
    }

    function deletePointFromPoly(lon, lat, shapeTitle) {
        return map.deletePointFromPoly(lon, lat, shapeTitle);
    }

    function disableDrawing(){
        map.disableDrawing();
    }

    function enableDrawing(){
        map.enableDrawing();
    }

    function getLocatons(lat,lon){
        var locations;
        log.debug("getLocatons map.CLASS_NAME = " + map.CLASS_NAME);
        if (map.CLASS_NAME == "RMS.RVirtualEarth6" || map.CLASS_NAME == "RMS.RVirtualEarth7" || map.CLASS_NAME == "RMS.RGoogleMaps"){
            locations = new Array(map.getLocationObject(myLoc.latitude, myLoc.longitude), map.getLocationObject(lat, lon));
        }    
        log.debug("getLocatons locations = " + locations);
        return locations;
    }

    function setPolygonPinColor(pinColor) {
        map.setPolygonPinColor(pinColor);
    }

    function setPolygonAreaColor(areaColor) {
        map.setPolygonAreaColor(areaColor);
    }


    // end for polygones

    // start for proximity page
    function onVehicleSelectionClickProximity(id,vehicleList){
        setVehiclesToSelectArr(vehicleList);
        log.debug("onVehicleSelectionClickProximity allVehicles.length = " + allVehicles.length);
        for (var i = 0; i < allVehicles.length; i++){
            if (id == allVehicles[i].id) {
                drawEraseLineImgMarker(allVehicles[i].id,allVehicles[i].lon,allVehicles[i].lat,allVehicles[i].imageFileName,allVehicles[i].descr);
            }
        }
        setTimeout(correctMap, 2000);
    }

    function drawEraseLineImgMarker(id,lon,lat,imageFileName,descr){
        if (myLoc != null) {
            var key = 'vehicle' + id;

            if (selectedObjects[key] == null) {
                var startLat = myLoc.latitude;
                var startLon = myLoc.longitude;
                var endLon = lon;
                var endLat = lat;

                var newImg = new Image();
                var thiz = this;
                newImg.onload = function () {
                    thiz.onProximityImgLoad(this,key, descr, startLon, startLat, endLon, endLat);
                };
                newImg.src = "img/vehicleIcons/" + imageFileName + "_E_100.png";


//                selectedObjects[key] = map.drawLineImgMarker("img/vehicleIcons/"
//                            + imageFileName + "_E_100.png",descr,
//                        startLon, startLat, endLon, endLat);
            } else {
                map.removeMarker(selectedObjects[key]);
                if (typeof(log) != 'undefined') log.debug("addRemoveObjectToMap: after map.removeMarker()");
                delete selectedObjects[key];
                if (typeof(log) != 'undefined') log.debug("addRemoveObjectToMap: before unHighlighObject()");
            }
            return true;
        }else{
           // alert("Select vehicle, POI or address in Location Tab");
            return false;
        }
    }

    function onProximityImgLoad(image,key,descr, startLon, startLat, endLon, endLat){
        selectedObjects[key] = map.drawLineImgMarker(image,descr,
                        startLon, startLat, endLon, endLat);
    }

    function onSelectionDropDownClickProximity(vehicleList){
        removeAllMarkers();

        moveLocationProximity(myLoc.longitude,myLoc.latitude);
        setVehiclesToSelectArr(vehicleList);
        log.debug("onSelectionDropDownClickProximity allVehicles.length = " + allVehicles.length);
        log.debug("onSelectionDropDownClickProximity vehiclesToSelect.length = " + vehiclesToSelect.length);
        for (var i = 0; i < vehiclesToSelect.length; i++){
            var isSelectedPin = drawEraseLineImgMarker(vehiclesToSelect[i].id,vehiclesToSelect[i].lon,vehiclesToSelect[i].lat,vehiclesToSelect[i].imageFileName,vehiclesToSelect[i].descr);
            if (!isSelectedPin) break;
        }
        setTimeout(correctMap, 2500);
    }

    function removeAllMarkers() {
        removeAllObjectsFromMap();
    }
    // end for proximity page

// -->
</script>

            <%--<div id="flex_maparea" ></div>--%>

