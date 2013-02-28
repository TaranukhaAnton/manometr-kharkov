    var map,streetview;
    var isVehicleZoomMode = false;
    var vehiclesToSelect = null;
    var myLoc = null;
//    var msvePlaces = null;
    var currentFlashMovie;
    
    

    function correctMapAreaWidth(madDiv) {       
        if (typeof(log) != 'undefined') log.debug("correctMapAreaWidth started");
        //var mapAreaWidth = 500;

        document.getElementById(madDiv).style.width = (pageWidth() - getPos(document.getElementById(madDiv)).left) - 20;
                //- (navigator.appVersion.indexOf("MSIE") == -1 ? 20 : 0) + "px";

        if (typeof(log) != 'undefined') log.debug("correctMapAreaWidth exit");
    }

    function StreetViewError(errorCode) {
        if (errorCode == 603) {
            alert('Error: Flash doesn’t appear to be supported by your browser');
            return;
        }
        if (errorCode == 600) {
            $("#streetview").html('Street View Not Available For This Location');
            return;
        }
    }

    function initMap(){
        map = null;
       // var mapAreaDiv = document.getElementById('maparea');
//         if (mapAreaDiv == null) {
//            //the map is absent on the page
//            return;
//        } else {
//            correctMapAreaWidth('maparea');
//            document.getElementById('maparea').style.height = (pageHeight() - getPos(document.getElementById("maparea")).top) - 15;
//        }
        map = getMap('maparea');
        if (vehiclesToSelect == null || vehiclesToSelect.length == 0){
            var zoom = getInitialZoom();
            if (typeof(log) != 'undefined') log.debug("initMap(): zoom=" + zoom);
            map.zoomTo(zoom);
        }
    }


    function init() {
        initMap();
        initSelectedVehicles();
        initSelectedAssets();
        initSelectedPhones();
        initSelectedMovableItems("handheld",handheldsToSelect);
    }

    function afterVehicleSelectionClick(data) {
        if (typeof(log) != 'undefined') log.debug("afterVehicleSelectionClick start. isVehicleZoomMode="+isVehicleZoomMode);
        afterVehicleSelectionClickPageType(data,'map');
    }

    function afterPhoneSelectionClick(data) {
        var descr = "";
        if (data.associatedRegularDriver != null){
            descr += data.associatedRegularDriver.lastName + "'s ";
        }
        descr += "Phone";
        addRemoveObjectToMap(data.id,
                         data.lon, data.lat,
                         data.type.imageFileName, 'phone', descr,
                         data.roadLon, data.roadLat, data.distanceToRoad);
    }

    function afterMovableItemSelectionClick(data) {
        if (typeof(log) != 'undefined') log.debug("afterMovableItemSelectionClick") ;
        afterMovableItemSelectionClickPageType(data,'map');
    }

    function afterPoiSelectionClick(data) {
        addRemoveObjectToMap(data.id,
                             data.lon, data.lat,
                             "locationIcon.gif", 'poi', data.descr, data.roadLon, data.roadLat, data.distanceToRoad);
    }

    function afterAoiSelectionClick(data) {
        addRemoveObjectToMap(data.id,
                             data.polygonWkt, null,
                             "locationIcon.gif", 'aoi', data.descr, null, null, null);
    }

    function afterAssetSelectionClick(data) {
        addRemoveObjectToMap(data.id,
                             data.lon, data.lat,
                             data.type.imageFileName, 'asset', data.descr, data.roadLon, data.roadLat, data.distanceToRoad);
    }

    function initSelectedPhones(){
        if (typeof(log) != 'undefined') log.debug("initSelectedPhones:");

        for(var i = 0; i < phonesToSelect.length; i++) {
            var selectedPhone = phonesToSelect[i];

            var key = 'phone' + selectedPhone.id;
            if (selectedObjects[key] != null) {
                if (selectedObjects[key].lon != selectedPhone.lon
                        || selectedObjects[key].lat != selectedPhone.lat) {
                    removeObject('phone',selectedPhone.id, true);

                    addRemoveObjectToMap(selectedPhone.id,
                                        selectedPhone.lon, selectedPhone.lat,
                                        selectedPhone.imageFileName, 'phone', selectedPhone.descr,
                                        selectedPhone.roadLon, selectedPhone.roadLat, selectedPhone.distanceToRoad);

                }
            }
            else {
                addRemoveObjectToMap(selectedPhone.id,
                                        selectedPhone.lon, selectedPhone.lat,
                                        selectedPhone.imageFileName, 'phone', selectedPhone.descr,
                                        selectedPhone.roadLon, selectedPhone.roadLat, selectedPhone.distanceToRoad);
            }
        }
        highlightSelectedObjects('phone');
        rezoomMap();
    }

    function initSelectedMovableItems(itemType,itemsToSelect){
        if (typeof(log) != 'undefined') log.debug("initSelectedMovableItems() started, itemType = " + itemType);
        for(var i = 0; i < itemsToSelect.length; i++) {
            var selectedItem = itemsToSelect[i];
            var key = itemType + selectedItem.id;
            if (selectedObjects[key] != null) {
                if (selectedObjects[key].lon != selectedItem.lon
                        || selectedObjects[key].lat != selectedItem.lat) {
                    removeObject(itemType,selectedItem.id, true);

                    addRemoveObjectToMap(selectedItem.id,
                                        selectedItem.lon, selectedItem.lat,
                                        selectedItem.imageFileName, itemType, selectedItem.descr, selectedItem.roadLon,
                                        selectedItem.roadLat, selectedItem.distanceToRoad);
                }
            }
            else {
                addRemoveObjectToMap(selectedItem.id,
                                        selectedItem.lon, selectedItem.lat,
                                        selectedItem.imageFileName, itemType, selectedItem.descr, selectedItem.roadLon,
                                        selectedItem.roadLat, selectedItem.distanceToRoad);
            }
        }
        if (!isFlex){
            highlightSelectedObjects(itemType);
        }
        rezoomMap();
    }

    function initSelectedVehicles() {
        if (typeof(log) != 'undefined') log.debug("!!!initSelectedVehicles: ");
//        if (typeof(log) != 'undefined') log.debug("in inMapping: vehiclesToSelect.length = " + vehiclesToSelect.length);
        for(var i = 0; i < vehiclesToSelect.length; i++) {
            var selectedVehicle = vehiclesToSelect[i];
            var key = 'vehicle' + selectedVehicle.id;
            if (typeof(log) != 'undefined') log.debug("key = " + key);

            if (selectedObjects[key] != null) {
                if (typeof(log) != 'undefined') log.debug("initSelectedVehicles: != null");
                if (selectedObjects[key].lon != selectedVehicle.lon
                        || selectedObjects[key].lat != selectedVehicle.lat) {
                    removeObject('vehicle',selectedVehicle.id, true);
                    addRemoveObjectToMap(selectedVehicle.id,
                                        selectedVehicle.lon, selectedVehicle.lat,
                                        getVehicleIconImageName(selectedVehicle),
                                        'vehicle', selectedVehicle.descr,
                                        selectedVehicle.roadLon, selectedVehicle.roadLat, selectedVehicle.distanceToRoad);
                }
            } else {
                if (typeof(log) != 'undefined') log.debug("initSelectedVehicles: != null");
                addRemoveObjectToMap(selectedVehicle.id,
                                        selectedVehicle.lon, selectedVehicle.lat,
                                        getVehicleIconImageName(selectedVehicle),
                                        'vehicle', selectedVehicle.descr,
                                        selectedVehicle.roadLon, selectedVehicle.roadLat, selectedVehicle.distanceToRoad);
            }
        }

        if (isMapViewType && !isFlex){
            highlightSelectedObjects('vehicle');
        }
        rezoomMap();
    }


    function reloadSelectedObjectsInternal(objType,doRezoom){
        var objToSelect;
        switch (objType){
            case 'phone':
                objToSelect = phonesToSelect;
                break;
            case 'vehicle':
                objToSelect = vehiclesToSelect;
                break;
            case 'asset':
                objToSelect = assetsToSelect;
                break;
            case 'handheld':
                objToSelect = handheldsToSelect;
                break;
        }
        for(var i = 0; i < objToSelect.length; i++) {

         var selObject = objToSelect[i];
            var imageFileName = objType == "vehicle"
                                ? getVehicleIconImageName(selObject)
                                : selObject.imageFileName;
            correctMapHighliteObj(selObject.id,
                                      selObject.lon, selObject.lat,
                                      imageFileName, objType, selObject.descr,
                                      selObject.roadLon, selObject.roadLat, selObject.distanceToRoad);

        }
        if (doRezoom) {rezoomMap();}
    }

    function reloadSelectedObjectsNoRezoom(objType){
         reloadSelectedObjectsInternal(objType,false);
    }
    function reloadSelectedObjects(objType){
         reloadSelectedObjectsInternal(objType,true);
    }

    function initSelectedAssets() {
        for(var i = 0; i < assetsToSelect.length; i++) {
            var selectedAsset = assetsToSelect[i];
            addRemoveObjectToMap(selectedAsset.id,
                                    selectedAsset.lon, selectedAsset.lat,
                                    selectedAsset.imageFileName, 'asset', selectedAsset.descr,
                                    selectedAsset.roadLon, selectedAsset.roadLat, selectedAsset.distanceToRoad);
        }

        highlightSelectedObjects('asset');
    }

    function getIconImg(fileName, objType, iconGlow, glowColor) {
        if (objType == 'vehicle') {
            if (iconGlow) {
                    return "http://" + document.location.hostname + ":" + document.location.port
                    + "/img/vehicleIcons/glow/" + glowColor + "/" + fileName;
            }
            return "http://" + document.location.hostname + ":" + document.location.port
                    + "/img/vehicleIcons/" + fileName;
        } else {
            return "http://" + document.location.hostname + ":" + document.location.port + "/img/" + objType + "Types/"+ fileName;
        }
    }

    function getImg(fileName) {
        return "http://" + document.location.hostname + ":" + document.location.port + fileName;
    }

    function getIgnitionIconImg(fileName, objType) {
        return "http://" + document.location.hostname + ":" + document.location.port
            + "/img/arrows/" + fileName;
    }


    function rezoomMap(){
        if (typeof(log) != 'undefined') log.debug("rezoomMap: elemCount(selectedObjects)=" + elemCount(selectedObjects));
        var f = function(){
            if (elemCount(selectedObjects) > 0){
                try {
                 map.correctMap(undefined);
                }
                catch(e) {
                    if (typeof(log) != 'undefined') log.debug("rezoomMap: map.zoomToExtent() threw the error - " + e);
                }
            }else{
                var zoom = getInitialZoom();
                if (typeof(log) != 'undefined') log.debug("rezoomMap(): zoom=" + zoom);
                map.zoomTo(zoom);
            }
        };
        setTimeout(f,2000);
        if (typeof(log) != 'undefined') log.debug("rezoomMap: finish");
    }

    function removeObject(objType, id, doUnhighlight){
        removeObjectPageType(objType, id, doUnhighlight, 'map');
    }

    function onObjImgLoad(image, id, lon, lat, fileName, objType, descr, roadLon, roadLat, distanceToRoad){
        var mapObj = null;
        var key = objType + id;
        distanceToRoad = parseFloat(distanceToRoad);
        if (!isNaN(distanceToRoad)) {
            if (distanceToRoad > 20.0) {
                mapObj = map.addTextMarker(image, descr, lon, lat, roadLon, roadLat, distanceToRoad);
            } else {
                mapObj = map.addTextMarker(image, descr, roadLon, roadLat);
            }
        } else {
            if (typeof(log) != 'undefined') log.debug('mapping.jsp addObject img:' + image.src + ", descr: " + descr + ", (" + lon + "," + lat + ")");
            mapObj = map.addTextMarker(image, descr, lon, lat);
        }
        mapObj.objType = objType;
        mapObj.id = id;
        selectedObjects[key] = mapObj;
        var obj = document.getElementById(key);
        if (obj != null && !isFlex) {
            if (typeof(log) != 'undefined') log.debug('mapping.jsp addObject: highlighObject');
            highlighObject(obj);
        }
    }

    function addObject(id, lon, lat, fileName, objType, descr, roadLon, roadLat, distanceToRoad){
        var mapObj = null;
        var key = objType + id;
        if ("aoi" == objType) {
            if (map.addPolygonFromWkt != undefined){
                mapObj = map.addPolygonFromWkt(lon);
                mapObj.SetTitle(descr);
            }
            mapObj.objType = objType;
            mapObj.id = id;
            selectedObjects[key] = mapObj;
            var obj = document.getElementById(key);
            if (obj != null && !isFlex) {
                if (typeof(log) != 'undefined') log.debug('mapping.jsp addObject: highlighObject');
                highlighObject(obj);
            }
        } else {
            var imgUrl = getIconImg(fileName, objType);
            var newImg = new Image();
            var thiz = this;
            newImg.onload = function(){
                thiz.onObjImgLoad(this,id, lon, lat, fileName, objType, descr, roadLon, roadLat, distanceToRoad);
            };
            newImg.src = imgUrl;
        }
    }


    function correctMapHighliteObj(id, lon, lat, fileName, objType, descr, roalLon, roadLat, dist){
       if (typeof(log) != 'undefined') log.debug("correctMapHighliteObj: lat = "+lat+" lon = "+lon + "id = " + id);
       if (typeof(log) != 'undefined') log.debug("correctMapHighliteObj: start");
       removeObject(objType,id, false);
       addObject(id, lon, lat, fileName, objType, descr, roalLon, roadLat, dist);
    }

    function addObjectListToMap(objectList,objectType) {
        if (typeof(log) != 'undefined') log.debug("addObjectListToMap started:");
        for (var i = 0; i < objectList.length; i++) {
            var data = objectList[i];
            data.descr = getDescr(data);
            var v;
            if (typeof(log) != 'undefined') log.debug("objectType + data.id=" + objectType + data.id);
            if (selectedObjects[objectType + data.id] == null) {
                if (objectType == 'vehicle') {
                    for (var k = 0; k < vehiclesToSelect.length; k++) {
                        if (vehiclesToSelect[k].id == data.id){
                            v = vehiclesToSelect[k];
                            break;
                        }
                    }
                    //var v = getSelectedVehicleById(data.id);
                    if (v == null) {
                        vehiclesToSelect[vehiclesToSelect.length] = data;
                    }
                }

                addRemoveObjectToMap(data.id,
                                     data.lon, data.lat,
                                     (objectType == 'vehicle' ? getVehicleIconImageName(data) : getImageFileName(data)),
                                     objectType,
                                    (objectType == 'vehicle'? getVehicleMapDescr(data,canUserReadJourneyCost,canResellerVehicleHover):data.descr),
                                     data.roadLon, data.roadLat, data.distanceToRoad);
            }
        }
    }

    var allPois = null;
    var poiRendered = false;
    function drawPois(data) {
        allPois = data;
        showAllPois();
    }

    function addRemovePoiListToMap(pois){
      for (var i =0; i < pois.length; i++){
            addRemoveObjectToMap(pois[i].id,
                         pois[i].lon, pois[i].lat,
                         "locationIcon.gif", 'poi', pois[i].descr, pois[i].roadLon, pois[i].roadLat, pois[i].distanceToRoad);
       }
    }

    function showAllPois(){
        if(allPois == null || typeof(allPois) == "undefined"){
            loadPois();
        } else {
            var button = document.getElementById("showPois");
            if (poiRendered){
                button.innerHTML = "Show POIs";
                poiRendered = false;
            } else {
                button.innerHTML = "Hide POIs";
                poiRendered = true;
            }
            addRemovePoiListToMap(allPois);
        }
    }

