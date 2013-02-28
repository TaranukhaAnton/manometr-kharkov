//var loadedVehiclesCount=0;

function addVehicleOnMapFlx(selectedVehicle, key, ignitionIconsVersion, fleetIdOverIcons, iconGlow, glowColor, hideRedPin, withMapRefresh, disableOnIgnOff, stickyRoadsAllowed, showVehTooltipAllowed, drawArrowFromVehIconToRoad) {
    var dotOnMap = selectedVehicle.dotOnMap;
    var imgUrl = getIconImg(getVehicleIconImageNameFlx(selectedVehicle, ignitionIconsVersion>0, dotOnMap),
        "vehicle", iconGlow, glowColor, dotOnMap);

    var ignitionIconUrl;

    if (!dotOnMap){
        if (ignitionIconsVersion == 1 ){
            ignitionIconUrl = getIgnitionIconImg(getIgnitionIconImageNameVersion1(selectedVehicle));
        } else if (ignitionIconsVersion == 2 ){
            ignitionIconUrl = getIgnitionIconImg(getIgnitionIconImageNameVersion2(selectedVehicle));
        } else  if (ignitionIconsVersion == 3 ){
            ignitionIconUrl = getIgnitionIconImg(getIgnitionIconImageNameVersion3(selectedVehicle));

        }
    }

    var tooltipUrl;
    if (showVehTooltipAllowed) {
        tooltipUrl = getTooltipIconImg();
    }
    var newImg = new Image();
    var thiz = this;
    var textColor = "white";
    if (imgUrl.substr(imgUrl.lastIndexOf(":")).match("white") != null) {
        textColor = "black";
    }
    newImg.onload = function () {
        thiz.onVehicleImgLoad(this, selectedVehicle, key, ignitionIconsVersion, textColor,
            fleetIdOverIcons, ignitionIconUrl, hideRedPin, withMapRefresh,
            disableOnIgnOff, stickyRoadsAllowed, dotOnMap, showVehTooltipAllowed, tooltipUrl, drawArrowFromVehIconToRoad);
    };
    newImg.src = imgUrl;
}

function onVehicleImgLoad(image, selectedVehicle, key, ignitionIconsVersion, textColor, fleetIdOverIcons, ignitionIconUrl, hideRedPin, withMapRefresh, disableOnIgnOff, stickyRoadsAllowed, dotOnMap, showVehTooltipAllowed, tooltipUrl, drawArrowFromVehIconToRoad) {

    if (!(key in selectedObjects)) {
        if (typeof(log) != 'undefined') log.debug("onVehicleImgLoad start key = " + key);
        var mapObj = null;
        var fleetId = "";
        var htmlMarker = "";

        if (fleetIdOverIcons) {
            if (selectedVehicle.fleetId.length > 7) {
                fleetId = selectedVehicle.fleetId.substr(0, 6) + "..";
            } else {
                fleetId = selectedVehicle.fleetId;
            }
        }

        if ( !dotOnMap) {
            if (ignitionIconsVersion == 1){
                htmlMarker = generateHTMLMarkerForVehicle(image, selectedVehicle, textColor, fleetId, ignitionIconUrl, false);
            }  else if (ignitionIconsVersion == 2){
                htmlMarker = generateHTMLMarkerForVehicle(image, selectedVehicle, textColor, fleetId, ignitionIconUrl, true);
            } else if (ignitionIconsVersion == 3){
                    htmlMarker = generateHTMLMarkerForVehicleVer3(image,  ignitionIconUrl,vehicleIconSize);
                }
        }

        var ignitionIconsAllowed =  ignitionIconsVersion > 0;
        // tooltip generation
        if (showVehTooltipAllowed && (!ignitionIconsAllowed || dotOnMap)) {
            htmlMarker = generateHTMLMarkerWithTooltipNoIgn(image, selectedVehicle, tooltipUrl);
        } else if (showVehTooltipAllowed && ignitionIconsAllowed && !dotOnMap) {
            htmlMarker = generateHTMLMarkerWithTooltipIgn(image, htmlMarker, selectedVehicle, tooltipUrl);
        }
        //log.debug(htmlMarker);
        if (!isNaN(parseFloat(selectedVehicle.distanceToRoad)) && !(disableOnIgnOff && selectedVehicle.ignitionStatus != null && selectedVehicle.ignitionStatus == "Off") && stickyRoadsAllowed) {
            if (selectedVehicle.distanceToRoad > 20.0 && (!hideRedPin || drawArrowFromVehIconToRoad)) {
                mapObj = map.addTextMarker(image,
                    selectedVehicle.descr,
                    selectedVehicle.lon,
                    selectedVehicle.lat,
                    selectedVehicle.roadLon,
                    selectedVehicle.roadLat,
                    selectedVehicle.distanceToRoad,
                    ignitionIconsAllowed,
                    htmlMarker,
                    true,
                    selectedVehicle.regNumber,
                    dotOnMap,
                    showVehTooltipAllowed,
                    drawArrowFromVehIconToRoad);
            } else {
                mapObj = map.addTextMarker(image,
                    selectedVehicle.descr,
                    selectedVehicle.roadLon,
                    selectedVehicle.roadLat,
                    null,
                    null,
                    null,
                    ignitionIconsAllowed,
                    htmlMarker,
                    true,
                    selectedVehicle.regNumber,
                    dotOnMap
                    , showVehTooltipAllowed,
                    false);
            }
        } else {
            mapObj = map.addTextMarker(image, selectedVehicle.descr, selectedVehicle.lon, selectedVehicle.lat, null, null, null,
                ignitionIconsAllowed, htmlMarker, true, selectedVehicle.regNumber, dotOnMap, showVehTooltipAllowed, false);
        }
        mapObj.objType = "vehicle";
        mapObj.id = selectedVehicle.id;

        selectedObjects[key] = mapObj;
        if (withMapRefresh) {

            setTimeout(rezoomMapFlx, 1000);
            if (typeof(log) != 'undefined') log.debug("onVehicleImgLoad map refreshed");
        }
        if (typeof(log) != 'undefined') log.debug("onVehicleImgLoad finish key = " + key);
    } else {
        if (typeof(log) != 'undefined') log.warn("onVehicleImgLoad key EXISTS = " + key);
    }
}

function addActivityOnMapFlx(selectedActivity, key) {
    var mapObj = null;
    var imgUrl = getIconImg(getVehicleIconImageNameFlx(selectedActivity, true), "vehicle", false, null);
    var newImg = new Image();
    newImg.src = imgUrl;
    var iconUrl = getObjectIconUrl(selectedActivity);
    var statusUrl = getObjectStatusUrl(selectedActivity);
    var htmlMarker = generateHTMLMarkerForActivity(selectedActivity, iconUrl, statusUrl, newImg);
    mapObj = map.addTextMarker(newImg, selectedActivity.descr, selectedActivity.lon, selectedActivity.lat, null, null, null, true, htmlMarker, true);
    selectedObjects[key] = mapObj;
}

function addMovableItemOnMapFlx(selectedObj) {
    var imgUrl = getIconImg(selectedObj.imageFileName, selectedObj.typeDescr);
    var newImg = new Image();
    var thiz = this;
    newImg.onload = function () {
        thiz.onMovableItemImgLoad(this, selectedObj);
    };
    newImg.src = imgUrl;
}

function onMovableItemImgLoad(image, selectedObj) {
    var mapObj = null;
    mapObj = map.addTextMarker(image, selectedObj.descr, selectedObj.lon, selectedObj.lat);
    mapObj.objType = selectedObj.typeDescr;
    mapObj.id = selectedObj.id;
    selectedObjects[selectedObj.typeDescr + selectedObj.id] = mapObj;
}


function removeObjectFromMapFlx(key) {
    if (typeof(log) != 'undefined') log.debug("removeObjectFromMapFlx selectedObjects[key].id = " + selectedObjects[key].id);
    map.removeMarker(selectedObjects[key]);
    delete selectedObjects[key];
}

function removeAllObjectsFromMap() {
    selectedObjects = new Object();
    map.cleanUp();
}

function removeObjectsFromMapByType(objType) {
    if (typeof(log) != 'undefined') log.debug("removeObjectsFromMapByType objType = " + objType);
    for (var key in selectedObjects) {
        if (key.indexOf(objType) != -1) {
            removeObjectFromMapFlx(key);
        }
        if (typeof(log) != 'undefined') log.debug("removeObjectsFromMapByType key = " + key);
    }
    if (typeof(log) != 'undefined') log.debug("removeObjectsFromMapByType finished");
}

function removeRefreshedVehiclesFromMap(refreshedVehicles) {
    if (typeof(log) != 'undefined') log.debug("removeRefreshedVehiclesFromMap started refreshedVehicles.length = " + refreshedVehicles.length);
    for (var key in selectedObjects) {
        for (var i = 0; i < refreshedVehicles.length; i++) {
            if (key == "vehicle" + refreshedVehicles[i].id) {
                removeObjectFromMapFlx(key);
            }
        }
    }
    if (typeof(log) != 'undefined') log.debug("removeRefreshedVehiclesFromMap finished");
}

function removeRefreshedHandheldsFromMap(refreshedHandhelds) {
    if (typeof(log) != 'undefined') log.debug("removeRefreshedHandheldsFromMap started refreshedHandhelds.length = " + refreshedHandhelds.length);
    for (var key in selectedObjects) {
        for (var i = 0; i < refreshedHandhelds.length; i++) {
            if (key == "handheld" + refreshedHandhelds[i].id) {
                removeObjectFromMapFlx(key);
            }
        }
    }
    if (typeof(log) != 'undefined') log.debug("removeRefreshedVehiclesFromMap finished");
}

function addPoiAoiOnMapFlx(obj, multiColorPinsPoi, multiColorPinsAoi, aoiAreaColoured, chooseAoiAreaColour) {
    if (typeof(log) != 'undefined') log.debug("addPoiAoiOnMapFlx started obj.typeDescr.toLowerCase() = " + obj.typeDescr.toLowerCase());
    if (obj.typeDescr.toLowerCase() == "aoi" && obj.polygonWkt == null) return;
    var mapObj = null;
    if ((obj.typeDescr.toLowerCase() == "poi" && multiColorPinsPoi)
        || (obj.typeDescr.toLowerCase() == "aoi" && multiColorPinsAoi)) {
        imgUrl = "http://" + document.location.hostname + ":" + document.location.port + "/img/markers/marker_" + obj.pinColor.toLowerCase() + ".png";
    } else {
        var imgUrl = getIconImg("locationIcon.gif", obj.typeDescr.toLowerCase());
    }
    var image = new Image();
    image.src = imgUrl;
    image.width = 20;
    image.height = 25;
    mapObj = map.addTextMarkerByObj(image, obj, aoiAreaColoured, chooseAoiAreaColour);
    selectedObjects[obj.typeDescr.toLowerCase() + obj.id] = mapObj;
    if (typeof(log) != 'undefined') log.debug("addPoiAoiOnMapFlx finished key = " + obj.typeDescr.toLowerCase() + obj.id);
}

function addPoiClickListener(lonlat, shapeId) {
    rightClickOnMapListener(lonlat, shapeId);
}

function pinMouseOverListener(shape) {
    // if (typeof(log) != 'undefined') log.debug("pinMouseOverListener");
    if (hoverOverPoiAllowed) {
        if (shape.objType == "poi") {
            map.drawCircleAroundPoi(shape, poiAreaColoured, choosePoiAreaColour);
        }
    }
    if (hoverOverAoiAllowed) {
        if (shape.objType == "aoi") {
            map.showPolygone(shape, aoiAreaColoured, chooseAoiAreaColour);
        }
    }
}

function pinMouseOutListener(shape) {
  //  if (typeof(log) != 'undefined') log.debug("pinMouseOutListener");
    if (hoverOverPoiAllowed) {
        if (shape.objType == "poi") {
            map.removeAllCircles();
        }
    }
    if (hoverOverAoiAllowed) {
        if (shape.objType == "aoi") {
            map.hideAllPolygones();
        }
    }
}

function rezoomMapFlx() {
    if (typeof(log) != 'undefined') log.debug("rezoomMapFlx(): elemCount(selectedObjects)=" + elemCount(selectedObjects) + " , mapRezoom = " + mapRezoom);
    if (mapRezoom) {

        if (myLoc != undefined) {
            map.zoomToMaxExtentToPoint(myLoc.longitude, myLoc.latitude);
            return;
        }
        if (elemCount(selectedObjects) > 0) {
            try {

                map.correctMap(vehiclesToSelect.length > 0 || handheldsToSelect.length > 0 ? new Array("poi", "aoi", "polygonWkt") : undefined);
                //zoomOut();
            }
            catch (e) {
                if (typeof(log) != 'undefined') log.debug("rezoomMapFlx(): threw the error - " + e);
            }
        } else {
            var zoom = getInitialZoom();
            if (typeof(log) != 'undefined') log.debug("rezoomMapFlx(): zoom=" + zoom);
            map.zoomTo(zoom);
        }
    }
}

function setMapRezoom(val) {
    mapRezoom = val;
    if (mapRezoom) rezoomMapFlx();
}

function showHideMapCtrls(hide) {
    if (hide) {
        map.hideMapCtrls();
    } else {
        map.showMapCtrls();
    }
}

function zoomOut() {
    map.zoomOut();
}

var imagesRootUrl = "http://" + document.location.hostname + ":" + document.location.port + "/img";
//var journeyStartPoint;
//var journeyEndPoint;
var isMapCreated = false;
var clickedShapeCallbackFun;
var isShapeMapListenerAttached = false;
var data;
var smallIconsAllowed = false;
var ZOOM_LEVEL_FOR_SMALL_PINS = 12;
var isPinsSizeChanged = false;
var smallIcons;

function getFileNameByDirectionCode(dir, small) {
    if (small) {
        return "arrows/small/arrows_blue_" + dir.toLowerCase() + ".png";
    }

    return "arrows/arrows_blue_" + dir.toLowerCase() + ".png";
}

function getGreenDrivingSnailTrail(dir, type, color) {

    return "snail_trail/" + color + "/" + color + "-" + type + "/" + color + "-Background-with-letters_"
        + type + "_inside_" + dir.toUpperCase() + ".png";
}

function getObjectIconUrl(trackPoint, small, device) {
    var fileName;
    if (trackPoint.journeyStart) {
        fileName = "arrows/arrows_flag_start.png";
    }
    else if (trackPoint.journeyEnd) {
        fileName = "arrows/arrows_flag_end.png";
    } else if (device != null) {
        if (device.greenDrivingAllowed) {
            if (device.maxAccelerationForceAllowed) {
                if (trackPoint.harshAcceleration >= device.maxAccelerationForceRedVal) {

                    fileName = getGreenDrivingSnailTrail(trackPoint.directionCode, "HA", "Red");

                } else if (trackPoint.harshAcceleration >= device.maxAccelerationForceOrangeVal) {
                    fileName = getGreenDrivingSnailTrail(trackPoint.directionCode, "HA", "Orange");
                }
            }
            if (device.maxBrakingForceAllowed) {

                if (trackPoint.harshBraking >= device.maxBrakingForceRedVal) {

                    fileName = getGreenDrivingSnailTrail(trackPoint.directionCode, "HB", "Red");

                } else if (trackPoint.harshBraking >= device.maxBrakingForceOrangeVal) {
                    fileName = getGreenDrivingSnailTrail(trackPoint.directionCode, "HB", "Orange");
                }

            }
            if (device.maxCorneringAllowed) {
                if (trackPoint.harshCornering >= device.maxCorneringRedVal) {

                    fileName = getGreenDrivingSnailTrail(trackPoint.directionCode, "HC", "Red");

                } else if (trackPoint.harshCornering >= device.maxCorneringOrangeVal) {
                    fileName = getGreenDrivingSnailTrail(trackPoint.directionCode, "HC", "Orange");
                }
            }
        }
        if (device.overspeedAllowed) {
            if (device.maxAllowedSpeedAllowed) {
                if (trackPoint.overspeed >= device.maxAllowedSpeedRedVal) {

                    fileName = getGreenDrivingSnailTrail(trackPoint.directionCode, "ES", "Red");

                } else if (trackPoint.overspeed >= device.maxAllowedSpeedOrangeVal) {
                    fileName = getGreenDrivingSnailTrail(trackPoint.directionCode, "ES", "Orange");
                }
            }
        }
    }

    if (!fileName) {
        fileName = getFileNameByDirectionCode(trackPoint.directionCode, small);
    }


    return imagesRootUrl + "/" + fileName;
}

function getObjectStatusUrl(trackPoint) {
    var postfix = trackPoint.speed > 0 ? 'moving' : (trackPoint.ignitionStr == 'ON' ? 'idle' : 'parked');
    return imagesRootUrl + "/vehicle_status_" + postfix + ".png";
}

function snailTrailPutOnMapComplete(data, clickedShapeCallback, flexSnailTrailEnhanced, smallIconsAllowed, device) {
    this.data = data;

    map.flexSnailTrailEnhanced = flexSnailTrailEnhanced;
    map.subShapeLayerList = null;
    map.smallIconsAllowed = smallIconsAllowed;
    if (typeof(log) != 'undefined') log.debug("snailTrailPutOnMapComplete: imagesRootUrl=" + imagesRootUrl);
    if (typeof(log) != 'undefined') log.debug("snailTrailPutOnMapComplete: map=" + map);
    if (map.myGroupElems != undefined && map.myGroupElems.length > 0) {
        for (var j = 0; j < map.myGroupElems.length; j++) {
            map.removeMarker(map.myGroupElems[j]);
        }
    }
    map.myGroupElems = new Array();
    if (!isShapeMapListenerAttached) {
        map.registerClickOnShapeIndex(function (shape) {
            getClickedShapeIndex(shape);
        });
        isShapeMapListenerAttached = true;
    }
    var mapIndex = 0;
    var i = 0;
    smallIcons = undefined;
    var ro = new RepeatingOperation(function () {
        var item = data[i];
        item.logIndex = i;
        if (item.mapVisible == undefined || item.mapVisible) {
            item.mapIndex = mapIndex;
            drawLogRecordItem(item, device);
            mapIndex++;
        }
        if (++i < data.length) {
            ro.step();
        }
    }, 50);
    ro.step();
    setTimeout(correctMap, data.length < 70 ? 2000 : data.length * 30);
    clickedShapeCallbackFun = clickedShapeCallback;
    if (typeof(log) != 'undefined') log.debug("finish!!!111");
}

function taskDetailsPutOnMapComplete(data, lat, lon) {
    if (typeof(log) != 'undefined') log.debug("messageDetailsPutOnMapComplete iFrameMapLib");
    if (taskDetails.length > 0) {
        map.removeMarker(taskDetails[0]);
    }
    var mapObj = null;
    var imgUrl = getIconImg(getVehicleIconImageNameFlx(data.type, true), "vehicle", false, null);
    var newImg = new Image();
    newImg.src = imgUrl;
    mapObj = map.addTextMarker(newImg, "taskDetails" + data.id, lon, lat, null, null, null, false, null, false);
    map.zoomToMaxExtentToPoint(lon, lat);
    taskDetails[0] = mapObj;
}

function correctMap() {
    map.correctMap();
}

function mapCorrectOrZoomTo(curIndex) {//lat,lon,zoomLevel
    if (typeof(log) != 'undefined') log.debug("mapCorrectOrZoomTo curIndex = " + curIndex);
    if (curIndex == -1) {
        map.correctMap();
    } else {
        //map.zoomToCustomExtent(lon, lat, zoomLevel);
        map.zoomToSubShapeList(curIndex);
    }
}

function swapItemGlow(curIndex) {
    if (typeof(log) != 'undefined') log.debug("swapItemGlow curIndex = " + curIndex);
    map.swapShapeIconToGlowedByIndex(curIndex);
    if (typeof(log) != 'undefined') log.debug("swapItemGlow curIndex = finished");
}

function getClickedShapeIndex(index) {
    clickedShapeCallbackFun(index);
}

function drawLogRecordItem(item, device) {
    if (typeof(log) != 'undefined') log.debug("afterLoadLogRecords: item.directionCode=" + item.directionCode
        + ", item.lon=" + item.lon
        + ", item.lat=" + item.lat
        + ", item.direction=" + item.direction
        + ", item.journeyStart=" + item.journeyStart
        + ", item.journeyEnd=" + item.journeyEnd
    );
    if (map.smallIconsAllowed) {
        if (smallIcons == undefined) {
            log.debug("zoom level=" + map.getZoomLevel() + " isPinsSizeChanged = " + isPinsSizeChanged);
            smallIcons = map.getZoomLevel() <= ZOOM_LEVEL_FOR_SMALL_PINS;
        }
    } else {
        smallIcons = false;
    }
    var imgUrl = getObjectIconUrl(item, smallIcons, device);
    if (typeof(log) != 'undefined') log.debug("imgUrl = " + imgUrl);

    var newImg = new Image();
    var thiz = this;
    newImg.onload = function () {
        thiz.onSnailTrailImgLoad(this, item);
    };
    newImg.src = imgUrl;
}

function onSnailTrailImgLoad(img, item) {
    img.logIndex = item.logIndex;
    img.mapIndex = item.mapIndex;
    var mapObj = map.addImgMarker(img, item.lon, item.lat);
    //if (typeof(log) != 'undefined') log.debug("mapObj = " + mapObj);
    map.myGroupElems[map.myGroupElems.length] = mapObj;
}

function moveLocation(longitude, latitude) {
    moveLocationWithCustMarkerImg(longitude, latitude, "markers/marker_red.png", true);
}

function moveLocationProximity(longitude, latitude) {
    moveLocationWithCustMarkerImgProximity(longitude, latitude, "markers/marker_red.png", true);
}

function moveLocationWithCustomPin(longitude, latitude, pinColor, rezoom, showArea, poiAreaColoured, choosePoiAreaColour, radius, areaColor) {
    moveLocationWithCustMarkerImg(longitude, latitude, "markers/marker_" + pinColor + ".png", rezoom,
        showArea, poiAreaColoured, choosePoiAreaColour, radius, areaColor);
}

function removeLocationPin() {
    if (myLoc != undefined) {
        map.removeMarker(myLoc);
    }
    myLoc = null;
    //delete selectedObjects["locationPin"];
    //map.correctMap();
    rezoomMapFlx();
}


function moveLocationWithCustMarkerImgProximity(longitude, latitude, markerImg, rezoom) {
    if (typeof(log) != 'undefined') log.debug("moveLocationWithCusrMarkerImg longitude = " + longitude + "latitude = " + latitude + " markerImg = " + markerImg);
    if (typeof(log) != 'undefined') log.debug("myLoc = " + myLoc);

    if (myLoc != null) {
        map.removeMarker(myLoc);
    }
    var newImg = new Image();
    var thiz = this;
    newImg.onload = function () {
        thiz.onMarkerImgLoadProximity(this, longitude, latitude);
    };
    newImg.src = imagesRootUrl + "/" + markerImg;

    if (rezoom) {
        setTimeout(rezoomMapFlx, 1500);
    }
    log.debug("moveLocationWithCusrMarkerImg myLoc = " + myLoc);
}


function onMarkerImgLoadProximity(img,longitude, latitude){
    if (latitude != "undefined" && longitude != "undefined") {
        myLoc = map.addTextMarker(img, "Chosen Location", longitude, latitude,null, null, null,
            false,"",true, "",false, false,false);
        myLoc.longitude = longitude;
        myLoc.latitude = latitude;
    }
}

function moveLocationWithCustMarkerImg(longitude, latitude, markerImg, rezoom, showArea, poiAreaColoured, choosePoiAreaColour, radius, areaColor) {
    if (typeof(log) != 'undefined') log.debug("moveLocationWithCusrMarkerImg longitude = " + longitude + "latitude = " + latitude + " markerImg = " + markerImg);
    if (typeof(log) != 'undefined') log.debug("myLoc = " + myLoc);

    if (typeof(showArea) == 'undefined') showArea = false;
    if (typeof(poiAreaColoured) == 'undefined')  poiAreaColoured = false;
    if (typeof(radius) == 'undefined') radius = 0;
    if (typeof(choosePoiAreaColour) == 'undefined') choosePoiAreaColour = 'red';
    if (typeof(areaColor) == 'undefined') areaColor = 'red';

    if (myLoc != null) {
        map.cleanUp();
    }
    var newImg = new Image();
    var thiz = this;
    var poi = new Object();
    poi.lat = latitude;
    poi.lon = longitude;
    poi.typeDescr = "poi";
    poi.radius = radius;
    poi.areaColor = areaColor;

    newImg.onload = function () {
        thiz.onMarkerImgLoad(this, longitude, latitude, poi, showArea, poiAreaColoured, choosePoiAreaColour);
    };
    newImg.src = imagesRootUrl + "/" + markerImg;

    if (rezoom && !showArea) {
        setTimeout(rezoomMapFlx, 1500);
    } else if (showArea) {
        setTimeout(correctMap, 1500);
    }
    log.debug("moveLocationWithCusrMarkerImg myLoc = " + myLoc);
}

function onMarkerImgLoad(img, longitude, latitude, poi, showArea, poiAreaColoured, choosePoiAreaColour) {
    if (latitude != "undefined" && longitude != "undefined") {
        myLoc = map.addTextMarkerByObj(img, poi, false, false, showArea, poiAreaColoured, choosePoiAreaColour);
        myLoc.longitude = longitude;
        myLoc.latitude = latitude;
    }
}

function resultSearchItemClick(longitude, latitude, index) {
    if (typeof(log) != 'undefined') log.debug("resultSearchItemClick longitude = " + longitude + "latitude = " + latitude);
    moveLocation(longitude, latitude);
}

function getIconImg(fileName, objType, iconGlow, glowColor, dotOnMap) {

    if (objType == 'vehicle') {
        if (iconGlow && !dotOnMap) {

            return "http://" + document.location.hostname + ":" + document.location.port
                + "/img/vehicleIcons/glow/" + glowColor + "/" + fileName;
        } else if (dotOnMap) {
            return "http://" + document.location.hostname + ":" + document.location.port
                + "/img/vehicleIcons/dots/" + fileName;
        }

        return "http://" + document.location.hostname + ":" + document.location.port
            + "/img/vehicleIcons/" + fileName;
    } else if (objType == 'poi' || objType == 'aoi') {
        return "http://" + document.location.hostname + ":" + document.location.port + "/img/" + objType + "Types/" + fileName;
    } else {

        return "http://" + document.location.hostname + ":" + document.location.port + "/img/" + objType + "Types/" + fileName + ".png";
    }
}

function getIgnitionIconImg(fileName, objType) {
    return "http://" + document.location.hostname + ":" + document.location.port
        + "/img/arrows/" + fileName;
}

function getTooltipIconImg() {
    return "http://" + document.location.hostname + ":" + document.location.port
        + "/img/tooltip.png";
}

function getVehicleMapDescrFlx(vehicle, canUserRead, canResellerHover) {
    var text;
    if (canResellerHover == "true") {
        var date = new Date(vehicle.renewalDate);
        text = vehicle.registrationNumber + "<br>" +
            "Ignition " + vehicle.ignitionStatus + "<br>" +
            vehicle.factoredSpeed + "MPH " + "(" + vehicle.directionOfTravel.toString().toUpperCase() + ")" + "<br>" +
            date.toString() + "<br>"
            + ((vehicle.curAddress != '' && vehicle.curAddress != null) ? (vehicle.curAddress + "<br>") : "")
            + ((vehicle.aoiPoiDescr != '' && vehicle.aoiPoiDescr != null) ? vehicle.aoiPoiDescr + "<br>" : "")
            + (vehicle.vehicleDriverDescr != undefined ? ("Driver: " + vehicle.vehicleDriverDescr + "<br>") : "")
            + (( canUserRead == 'true' && vehicle.costPerMile != 0) ?
            ("Today's Cost: \u00A3" + vehicle.costToDistance) : "");
    } else {
        text = vehicle.registrationNumber;
    }
    return text;
}

function getVehicleIconImageNameFlx(data, isIgnitionIconsEnabled, dotOnMap) {
    var imageFileName = data.imageFileName;

    if ((isIgnitionIconsEnabled) && !dotOnMap) {
        return  imageFileName + "_E_"
            + vehicleIconSize + ".png";
    } else if (dotOnMap) {
        switch (data.ignitionStatus.toLowerCase()) {
            case "on":
                return "green.png";
            case "off":
                return "red.png";
            case "idling":
                return "orange.png";
            default:
                return "red.png";
        }
    } else {
        return imageFileName + "_"
            + (!isVehicleIconRotation ? "E" : data.directionOfTravel.toUpperCase()) + "_"
            + vehicleIconSize + ".png";
    }
}

function getDisruptionHtmlContent(disruption) {
    var html = '<table style="font-size:11px; background-color:#ffffff;border: solid #bfbfbf 1px; min-height:200px;width:250px;"><tr><td style="padding:10px 0px 0px 10px;" width="25"><img src="{icon}"/></td><td style="padding:10px 10px 0px 0px;" align="center">{title}</td></tr><tr><td style="padding:0 10px;" colspan="2"><hr align="center" size="1" color="#0074be"/></td></tr><tr><td style="padding:0 10px;" colspan="2">LOCATION: {location}</td></tr><tr><td style="padding:0 10px;" colspan="2"><hr align="center" size="1" color="#0074be"/></td></tr><tr><td style="padding:0 10px;" colspan="2">SEVERITY: {severity}</td></tr><tr><td style="padding:0 10px;" colspan="2"><hr align="center" size="1" color="#0074be"/></td></tr><tr><td style="padding:0 10px;" colspan="2" style="padding:4px">{event_start_time}</td></tr><tr><td style="padding:0 10px;" colspan="2">DESCRIPTION: {descr}</td></tr><tr><td style="padding:0 10px;" colspan="2"><hr align="center" size="1" color="#0074be"/></td></tr><tr><td style="padding:0 10px;" colspan="2" style="padding:4px">{remark_date}</td></tr><tr><td style="padding:0px 10px 10px 10px;" colspan="2">REMARK: {remark}</td></tr></table>';
    return html.replace('{icon}', disruption.src).replace('{title}', disruption.title).replace('{location}', disruption.location)
        .replace('{severity}', disruption.severity).replace('{event_start_time}', disruption.eventStartDateTime)
        .replace('{descr}', getTextOrHyphen(disruption.descr)).replace('{remark_date}', disruption.remarkDateTime).replace('{remark}', getTextOrHyphen(disruption.remark));
}

function getCameraHtmlContent(disruption) {
    var html = '<table width="297" height="279" cellpadding="0" cellspacing="0" style="padding:10px;background-color:#ffffff;border:solid#bfbfbf1px;min-height:200px;width:270px;"><tr><td><img src="{icon}"/></td></tr><tr><td><img src="{img_link}"/></td></tr></table>';
    return html.replace('{icon}', disruption.src).replace('{img_link}', disruption.imageLink);
}


function showDisruptionItems(items) {
    hideDisruptionItems();
    var iconPrefix = "http://" + document.location.hostname + ":" + document.location.port + "/img/markers/";
    for (var i = 0; i < items.length; i++) {
        items[i].src = iconPrefix + (items[i].category == 'Works' ? 'road_works.png' : 'warning_triangle.png');
        items[i].html = getDisruptionHtmlContent(items[i]);
        selectedObjects[items[i].type.toLowerCase() + items[i].id] = map.showIncidentShape(items[i]);
    }
}

function hideDisruptionItems() {
    removeObjectsFromMapByType('disruption');
}


function showCameraItems(items) {
    hideCameraItems();
    var icon = "http://" + document.location.hostname + ":" + document.location.port + "/img/markers/camera.png";
    for (var i = 0; i < items.length; i++) {
        items[i].src = icon;
        items[i].html = getCameraHtmlContent(items[i]);
        selectedObjects[items[i].type.toLowerCase() + items[i].id] = map.showIncidentShape(items[i]);
    }
}

function hideCameraItems() {
    removeObjectsFromMapByType('camera');
}


function getAreaColorsRGB(areaColorStr, areaColoured, chooseColor) {
    if (!areaColoured) {
        return  [255, 255, 255, 0];
    }
    if (!chooseColor) {
        return [0, 128, 0, 0.3];
    }

    switch (areaColorStr.toLowerCase()) {
        case "black":

            return [0, 0, 0, 0.3];
        case "green":
            return [0, 128, 0, 0.3];
        case "blue":
            return [0, 0, 255, 0.3];
        case "purple":
            return [160, 32 , 240 , 0.3];
        case "orange":
            return [255, 165, 0, 0.3];
        case "yellow":
            return [255, 255, 0, 0.3];
        case "white":
            return [255, 255, 255, 0.3];
        case "red":
            return [255, 0, 0, 0.3];
        default:
            return [0, 128, 0, 0.3];
    }
}
