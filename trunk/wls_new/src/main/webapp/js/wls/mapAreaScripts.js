        
    var smallMap;
    var imagesRootUrl;

    function getFileNameByDirectionCode(dir) {
        return "arrows/arrows_blue_" + dir.toLowerCase() + ".png";
    }

    function getObjectIconUrl(trackPoint) {
    //    log.debug("trackPoint.direction=" + trackPoint.direction
     //           + ", trackPoint.directionCode=" + trackPoint.directionCode);
        var fileName;
        if (trackPoint.journeyStart) {
            fileName = "arrows/arrows_flag_start.png";
        }
        else if (trackPoint.journeyEnd) {
            fileName = "arrows/arrows_flag_end.png";
        }
        else {
            fileName = getFileNameByDirectionCode(trackPoint.directionCode);
        }

    //    log.debug("getObjectIconHtmlContent: fileName=" + fileName);
        return imagesRootUrl + "/" + fileName;
    }

    var journeyStartPoint;
    var journeyEndPoint;

    function afterLoadLogRecords(data,map_area_id) {
        smallMap = getMap(map_area_id);
        smallMap.myGroupElems = new Array();
        imagesRootUrl = "http://" + document.location.hostname + ":" + document.location.port + "/img";
//        log.debug("afterLoadLogRecords: data.length=" + data.length);

        if (smallMap.myGroupElems != undefined && smallMap.myGroupElems.length > 0) {
            for (var i = 0; i < smallMap.myGroupElems.length; i++) {
                smallMap.removeMarker(smallMap.myGroupElems[i]);
            }
        }
        smallMap.myGroupElems = new Array();
        for (var i = 0; i < data.length; i++) {
  //          log.debug("afterLoadLogRecords: in data i=" + i);
            var item = data[i];
//            log.debug("afterLoadLogRecords: item.directionCode=" + item.directionCode
//                    + ", item.lon=" + item.lon
//                    + ", item.lat=" + item.lat
//                    + ", item.lon=" + item.lon
//                    + ", item.lat=" + item.lat
//                    );
            var imgUrl = getObjectIconUrl(item);

            var mapObj = smallMap.addImgMarker(imgUrl, item.lon, item.lat);
            smallMap.myGroupElems[smallMap.myGroupElems.length] = mapObj;
            //log.debug("afterLoadLogRecords: myGroupElems.length=" + smallMap.myGroupElems.length);

            if (item.journeyStart) {
                journeyStartPoint = item;
            }
            journeyEndPoint = item;
        }
        //map.zoomToExtent(map.getMarkersLayer().getDataExtent());
        smallMap.correctMap();
    }

    function showSnailTrailComplete(data, map_area_id, panel_id){
        hideFlexHeader();
        if (data!=undefined && data.length > 0){
            document.getElementById(map_area_id).style.display = "";
        }
        Richfaces.showModalPanel(panel_id);
        if (data!=undefined && data.length > 0){
            afterLoadLogRecords(data,map_area_id);
        }
    }

    function closeSnailTrailWindow(map_area_id, panel_id){
        showFlexHeader();
        document.getElementById(map_area_id).style.display = "none";        
        Richfaces.hideModalPanel(panel_id);
    }

    function removeMarkersIfAny(map_area_id) {
        smallMap = getMap(map_area_id);
        if (smallMap.myGroupElems != undefined && smallMap.myGroupElems.length > 0) {
            for (var i = 0; i < smallMap.myGroupElems.length; i++) {
                smallMap.removeMarker(smallMap.myGroupElems[i]);
            }
        }
    }
