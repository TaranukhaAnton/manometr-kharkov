// pageType should be 'map', 'list'

function removeObjectsFromMap(objType) {
    if (typeof(log) != 'undefined') log.debug("removeObjectsFromMap started: objType= " + objType);
    cleanUpArrays(objType);
    if (objType != 'all') {
        for (var key in selectedObjects) {
            var obj = selectedObjects[key];
            if (obj.objType == objType) {
                if (typeof(log) != 'undefined') log.debug("removeObjectsFromMap kill : key=" + key);
                removeObjectPageType(obj.objType, obj.id, true, 'map');
            }
        }
    } else {
        for (var key2 in selectedObjects) {
            var obj2 = selectedObjects[key2];
            removeObjectPageType(obj2.objType, obj2.id, true, 'map');
        }
    }
    if (typeof(log) != 'undefined') log.debug("removeObjectsFromMap finished ");
}

function hideFlexHeader(){
    document.getElementById('flexHeaderApplicationDiv').style.display = "none";
}

function showFlexHeader(){
    document.getElementById('flexHeaderApplicationDiv').style.display = "";
}


function cleanUpArrays(objType) {
    switch (objType) {
        case 'all':
            if (typeof(log) != 'undefined') log.debug("removeObjectsFromMap all = new Array();");
            phonesToSelect = new Array();
            vehiclesToSelect = new Array();
            assetsToSelect = new Array();
            handheldsToSelect = new Array();
            break;
        case 'phone':
            if (typeof(log) != 'undefined') log.debug("removeObjectsFromMap phonesToSelect = new Array();");
            phonesToSelect = new Array();
            break;
        case 'vehicle':
            if (typeof(log) != 'undefined') log.debug("removeObjectsFromMap vehiclesToSelect = new Array();");
            vehiclesToSelect = new Array();
            break;
        case 'asset':
            if (typeof(log) != 'undefined') log.debug("removeObjectsFromMap assetsToSelect = new Array()");
            assetsToSelect = new Array();
            break;
        case 'handheld':
            if (typeof(log) != 'undefined') log.debug("removeObjectsFromMap handheldsToSelect = new Array()");
            handheldsToSelect = new Array();
            break;
    }
}

function removeObjectsFromFilter(objType) {
    if (typeof(log) != 'undefined') log.debug("removeObjectsFromFilter: objType = " + objType);
    cleanUpArrays(objType);
    for (var key in selectedObjects) {
        var obj = selectedObjects[key];
        if (objType == 'vehicle') {
            afterVehicleSelectionClickPageType(obj, 'list');
        } else {
            afterMovableItemSelectionClickPageType(obj, 'list');
        }
    }
}

function addVehicleListToFilter(vehicleList) {
    addObjectListToFilter(vehicleList, 'vehicle');
}

function addObjectListToFilter(itemList, itemType) {
    for (var i = 0; i < itemList.length; i++) {
        var data = itemList[i];
        if (selectedObjects[itemType + data.id] == null) {
            if (typeof(log) != 'undefined') log.debug("addObjectListToFilter: data.id=" + data.id);
            afterMovableItemSelectionClickPageType(data, 'list');
        }
    }
}

function addRemoveObjectToMap(id, lon, lat, fileName, objType, descr, roadLon, roadLat, distanceToRoad) {
    var key = objType + id;
    if (selectedObjects[key] == null) {
        if (typeof(log) != 'undefined') log.debug('>>>>>>> add ' + key);
        addObject(id, lon, lat, fileName, objType, descr, roadLon, roadLat, distanceToRoad);
    }
    else {
        if (typeof(log) != 'undefined') log.debug('>>>>>>> remove ' + key);
        removeObject(objType, id, true);
    }
    rezoomMap();
}

function getDescr(data) {
    if (typeof(data.clientDescr) != "undefined") {
        return data.clientDescr;
    }
    return data.descr;
}

function getImageFileName(data) {
    if (typeof(data.type) != "undefined") {
        data.imageFileName = data.type.imageFileName;
    }
    return data.imageFileName;
}

function afterVehicleSelectionClickPageType(data, pageType) {

    if (pageType == 'map') {
        addRemoveObjectToMap(data.id,
                data.lon, data.lat,
                getVehicleIconImageName(data),
                'vehicle',
                getVehicleMapDescr(data,canUserReadJourneyCost,canResellerVehicleHover),
                data.roadLon, data.roadLat, data.distanceToRoad);
    } else if (pageType == 'list') {
        addRemoveObjectToList(data, 'vehicle', 'selectedVehicles');
    }
}

function afterMovableItemSelectionClickPageType(data, pageType) {
    if (typeof(log) != 'undefined') log.debug("afterMovableItemSelectionClickPageType: data.id=" + data.id + "   pageType = " + pageType);
    if (pageType == "map") {
        data.descr = getDescr(data);

        addRemoveObjectToMap(data.id,
                data.lon, data.lat,
                data.imageFileName,
                data.typeDescr.toLowerCase(), data.descr, data.roadLon, data.roadLat, data.distanceToRoad);
//        addRemoveObjectToMap(data.id,
        //                         data.lon, data.lat,
        //                         data.imageFileName, data.typeDescr.toLowerCase(), data.descr);

    } else if (pageType == 'list') {
        addRemoveObjectToList(data, data.typeDescr.toLowerCase(), 'selected' + data.typeDescr + 's');
    }
}

function addRemoveObjectToList(data, objectType, placeHolderId) {
    addRemoveSelectedItem(objectType, data);
    var selectedItemsHtml = "";
    var selectedItemsPlaceHolder = document.getElementById(placeHolderId);
    selectedItemsHtml = "<table><tr>";
    var leftBorderStyle = "";
    var folderName = objectType == 'vehicle' ? "vehicleIcons" : objectType + "Types";
    var colNum = 0;
    for (var key in selectedObjects) {
        if (colNum++ > 5) {
            colNum = 0;
            leftBorderStyle = "";
            selectedItemsHtml += "</tr><tr>"
        }
        selectedItemsHtml +=
        "<td style='margin:3;padding:5;" + leftBorderStyle + "'>"
                + "<table style='font-size:10px;font-family:verdana,arial;' class='report-form'><tr>"
                + "<td>"
                + "<img src='img/" + folderName + "/" + getVehicleEastIconImageName(selectedObjects[key]) + "'>"
                + "</td>"
                + "<td>"
                + selectedObjects[key].clientDescr + "<br>"
                + "</tr></table>"
                + "</td><td>&nbsp;</td>";

        leftBorderStyle = "border-left:1px dotted #FFFFFF;";
    }
    selectedItemsHtml += "</tr></table><div style='float:none'>&nbsp;</div"
    selectedItemsPlaceHolder.innerHTML = selectedItemsHtml;
}

function removeObjectPageType(objType, id, doUnhighlight, pageType) {
    var key = objType + id;
    if (typeof(log) != 'undefined') log.debug("!!!!2)removeObject key = " + key);
    if (pageType == 'map') {
        if (typeof(log) != 'undefined') log.debug("selectedObjects[key] =" + selectedObjects[key]);
        map.removeMarker(selectedObjects[key]);
    }
    delete selectedObjects[key];
    if (doUnhighlight) {
        if (typeof(unHighlighObject) != "undefined") {
            if (typeof(log) != 'undefined') log.debug("!!!!3)doUnhighlight key = " + key);
            unHighlighObject(document.getElementById(key));
        }
    }
}

function setVisibilityForTD(isVisible, tdId) {
    document.getElementById(tdId).style.display
            = isVisible == true
            ? (navigator.appVersion.indexOf("MSIE 7") != -1 ? 'block' : 'table-cell')
            : 'none';
}

// for zoom js code start

function setZoomMode(bValue) {
    isVehicleZoomMode = isZoomMode = bValue;
    if (typeof(log) != 'undefined') log.debug("isZoomMode changed! isZoomMode = " + isZoomMode);
}

function highlighObject(obj) {
    if (!isZoomMode && obj != null) {
        obj["class"] = "left-menu-selection";
        obj.className = "left-menu-selection";
        //obj.style.border = "1px solid black";
    }
}

function unHighlighObject(obj) {
    if (!isZoomMode && obj != null) {
        obj["class"] = "left-menu-non-selected";
        obj.className = "left-menu-non-selected";
        //obj.style.border = "1px solid white";
    }
}

function onVehicleZoomClick(vehicleId, bForceToSelection) {
    if (typeof(log) != 'undefined') log.debug('onVehicleZoomClick ' + vehicleId + ' disableZoomWhileRefresh: ' + disableZoomWhileRefresh);
    if (disableZoomWhileRefresh) return false;
    if (!isVehicleZoomClickProcessingAllowed) {
        return false;
    }
    ;
    isVehicleZoomClickProcessingAllowed = false;
    var bForced = ((zoomedVehicleId != null) && bForceToSelection);
    try {
        if (typeof(log) != 'undefined') log.debug('Zoom Candidate Vehicle Id: ' + vehicleId + ' Current Zoomed Vehicle: ' + zoomedVehicleId);
        if (vehicleId != null) {
            var zoomText = "Zoom";  //for case zoomedVehicleId == vehicleId
            if (zoomedVehicleId == null) {
                zoomText = "Zoom Out";
                zoomedVehicleId = vehicleId;
                if (!isFlex) {
                    document.getElementById('zoomLinkId' + zoomedVehicleId).innerHTML = zoomText;
                }
                if (typeof(log) != 'undefined') log.debug('to Zoom Mode');
                switchToZoomMode();
            } else if (zoomedVehicleId == vehicleId || bForced) {
                if (!isFlex) {
                    document.getElementById('zoomLinkId' + zoomedVehicleId).innerHTML = "Zoom";
                }
                zoomedVehicleId = null;
                if (typeof(log) != 'undefined') log.debug('to Selection Mode');
                switchToSelectionMode(!bForced);//if forced to selection mode - do not restore previous selection
            } else if (zoomedVehicleId != vehicleId) {
                if (!isFlex) {
                    document.getElementById('zoomLinkId' + zoomedVehicleId).innerHTML = "Zoom";
                }
                zoomedVehicleId = vehicleId;
                if (!isFlex) {
                    document.getElementById('zoomLinkId' + zoomedVehicleId).innerHTML = "Zoom Out";
                }
                if (typeof(log) != 'undefined') log.debug('Invoke reloadSelectedObjectsNoRezoom(vehicle) from ZoomClick');
                reloadSelectedObjectsNoRezoom('vehicle');
            }

            zoomToVehicleById(zoomedVehicleId);
        }
    } catch (e) {
        if (typeof(log) != 'undefined') log.debug(e.message);
    } finally {
        isVehicleZoomClickProcessingAllowed = true;
    }
}
function zoomToVehicleById(zoomedVehicleId) {
    if (zoomedVehicleId != null) {
        zoomedVehicle = getVehicleById(zoomedVehicleId);
        if (zoomedVehicle != null) {
            if (typeof(log) != 'undefined') log.debug('zoomedVehicle: ' + zoomedVehicle.id + '(' + zoomedVehicle.lon + ',' + zoomedVehicle.lat + ')');
            map.zoomToMaxExtentToPoint(zoomedVehicle.lon, zoomedVehicle.lat);
        }
    }
}
function initZoomedVehicle() {
    if (zoomedVehicleId != null && !isFlex) {
        document.getElementById('zoomLinkId' + zoomedVehicleId).innerHTML = "Zoom Out";
    }
}

function switchToZoomMode() {
    if (typeof(log) != 'undefined') log.debug('Switching to Zoom Mode!');

    //addRemoveSelectedVehicles(vehiclesToSelect);  //do unhighlighting left menu
    //remove from map
    removeVehiclesMarkersFromMap(vehiclesToSelect, true);//remove marker and unhightlight from left menu
    //backup selection
    backup(vehiclesToSelect, vehiclesToSelectDepo);
    addAllVehicleToSelected();
    setZoomMode(true);//must be only here - for correct highlighting
    addRemoveSelectedVehicles(vehiclesToSelect);//just add to selectedObjects
    if (typeof(log) != 'undefined') log.debug('Invoke reloadSelectedObjectsNoRezoom(vehicle) from switchToZoomMode');
    reloadSelectedObjectsNoRezoom('vehicle');//will not highlight in left menu, due to isZoomMode == true
}

function removeVehiclesMarkersFromMap(vehicles, doUnhighlight) {
    for (var i = 0; i < vehicles.length; i++) {
        if (vehicles[i].id < 0) continue;
        try {
            removeObject('vehicle', vehicles[i].id, doUnhighlight);
        } catch (e) {
            if (typeof(log) != 'undefined') log.debug('removeVehiclesMarkersFromMap: ' + e.message);
        }
    }
}

function switchToSelectionMode(bDoSelectionRestore) {
    if (typeof(log) != 'undefined') log.debug('Switching to Selection Mode!');
    setZoomMode(false);//do not move it - for correct highlighting
    //addRemoveSelectedVehicles(vehiclesToSelect);//unhighlight all in left menu - do not invoke, because of selectedObject[key] became deleted
    //remove markers from map and unhighlight in menu (parameter = true)
    removeVehiclesMarkersFromMap(vehiclesToSelect, true);
    if (bDoSelectionRestore) {
        if (typeof(log) != 'undefined') log.debug("restore previous selection vehiclesToSelectDepo.length = " + vehiclesToSelectDepo.length);

        //restore selection
        backup(vehiclesToSelectDepo, vehiclesToSelect);
    } else {
        if (typeof(log) != 'undefined') log.debug("do NOT restore previous selection");
        //vehiclesToSelect.clear();
        vehiclesToSelect.length = 0;
    }
    addRemoveSelectedVehicles(vehiclesToSelect);//just add to selectedObjects

    if (bDoSelectionRestore) {
        if (typeof(log) != 'undefined') log.debug('Invoke reloadSelectedObjects(vehicle) from switchToSelectionMode');
        reloadSelectedObjects('vehicle');//correct markers with new coords and highlight in menu
    }
}

function reloadSelectedVehicle() {
    if (typeof(log) != 'undefined') log.debug('reloadSelectedVehicle, isZoomMode: ' + isZoomMode);
    try {
        if (isZoomMode) {
            if (typeof(log) != 'undefined') log.debug('}}}}}}size of vehiclesToSelect:' + vehiclesToSelect.length);
            //if (vehiclesToSelect.length == 0) alert('size of vehiclesToSelect:'+vehiclesToSelect.length);
            backup(vehiclesToSelect, vehiclesToSelectDepo);
            addAllVehicleToSelected();
            reloadSelectedObjectsNoRezoom('vehicle');
            zoomToVehicleById(zoomedVehicleId);
        } else {
            reloadSelectedObjects('vehicle');
        }
    } catch (e) {
        if (typeof(log) != 'undefined') log.debug(e.message);
    }

}
function enableZoomLinkControls(bEnable) {
    if (typeof(log) != 'undefined') log.debug('enableZoomLinkControls ' + bEnable);
    disableZoomWhileRefresh = !bEnable;
}

function modifyVehiclesToSelect(data) {
    if (isZoomMode) return;
    try {
        if (typeof(log) != 'undefined') log.debug('before addVehiclesToSelect: ' + vehiclesToSelect.length);
        var existed = getVehicleById(data.id);
        var selected = getSelectedVehicleById(data.id);
        if (existed != null && selected == null) {
            vehiclesToSelect[vehiclesToSelect.length] = existed;
        } else if (existed != null && selected != null) {
            removeFromArray(vehiclesToSelect, selected.id);
        }
        if (typeof(log) != 'undefined') log.debug('after addVehiclesToSelect: ' + vehiclesToSelect.length);
    } catch (e) {
        if (typeof(log) != 'undefined') log.debug(e.message);
    }

}

function removeFromArray(array, objectid) {
    var index = -1;
    for (var i = 0; i < array.length; i++) {
        if (array[i].id == objectid) {
            index = i;
            break;
        }
    }
    if (index != -1) {
        array.splice(index, 1);//remove 1 element
    }
}

function afterVehicleSelectionClickProcessZoom(data) {
    if (isZoomMode) {
        if (typeof(log) != 'undefined') log.debug('afterVehicleSelectionClickProcessZoom: Zoom Mode');
        if (typeof(log) != 'undefined') log.debug('afterVehicleSelectionClickProcessZoom: Force to selection mode');
        onVehicleZoomClick(-1, true);//force to selection mode
    }
    afterVehicleSelectionClick(data); //mapping.jsp
    modifyVehiclesToSelect(data);
}

function getVehicleById(vehicleId) {
    return getVehicleByIdInternal(allVehicles, vehicleId);
}
function getSelectedVehicleById(vehicleId) {
    return getVehicleByIdInternal(vehiclesToSelect, vehicleId);
}
function getVehicleByIdInternal(vehicles, vehicleId) {
    for (var i = 0; i < vehicles.length; i++) {
        if (vehicles[i].id == vehicleId) return vehicles[i];
    }
    return null;
}


function backup(from, to) {
    if (typeof(log) != 'undefined') log.debug('SelectionPanel.jsp backup from {' + toStr(from).toString() + '} to {' + toStr(to).toString() + '}');
    if (from.length == 0) {
        if (typeof(log) != 'undefined') log.debug("Source array is Empty!");
        //return;
    }
    if (typeof(log) != 'undefined') log.debug('clear destination array');
    //to.clear();
    to.length = 0;
    if (typeof(log) != 'undefined') log.debug('push src to destination array');
    for (var i = 0; i < from.length; i++) {
        to[to.length] = from[i];
    }
    //from.clear();
    from.length = 0;
    if (typeof(log) != 'undefined') log.debug('SelectionPanel.jsp backuped! from {' + toStr(from) + '} to {' + toStr(to) + '}');
}

function addAllVehicleToSelected() {
    if (typeof(log) != 'undefined') log.debug('addAllVehicleToSelected entered. Length:' + vehiclesToSelect.length);
    for (var i = 0; i < allVehicles.length; i++) {
        if (allVehicles[i].id > 0) {
            vehiclesToSelect[vehiclesToSelect.length] = allVehicles[i]; //mark as selected
        }
    }
    if (typeof(log) != 'undefined') log.debug('addAllVehicleToSelected exit. Length:' + vehiclesToSelect.length);
}

function addRemoveSelectedVehicles(vehicles) {
    if (typeof(log) != 'undefined') log.debug('addRemoveSelectedVehicles {' + toStr(vehicles) + '}');
    for (var i = 0; i < vehicles.length; i++) {
        if (vehicles[i].id > 0) {
            addRemoveSelectedVehicle(vehicles[i]);//add or remove vehicles
        }
    }
}

function addRemoveSelectedVehicle(data) {
    addRemoveSelectedItem('vehicle', data);
}

function addRemoveSelectedItem(itemType, data) {
    var key = itemType + data.id;
    if (selectedObjects[key] == null) {
        data.objType = itemType;
        if (typeof(data.type) != "undefined") {
            if (typeof(log) != 'undefined') log.debug("addRemoveSelectedPhone: data.type.imageFileName=" + data.type.imageFileName);
            data.imageFileName = data.type.imageFileName;
        }
        data.descr = getDescr(data);
        selectedObjects[key] = data;
        if (!isFlex) {
            highlighObject(document.getElementById(key));
        }
    }
    else {
        delete selectedObjects[key];
        if (!isFlex) {
            unHighlighObject(document.getElementById(key));
        }
    }
}

function toStr(vehicles) {
    var s = "";
    for (var i = 0; i < vehicles.length; i++) {
        if (vehicles[i].id > 0) {
            s = s + vehicles[i].id + ',';
        }
    }
    return s;
}

// for zoom js code end

