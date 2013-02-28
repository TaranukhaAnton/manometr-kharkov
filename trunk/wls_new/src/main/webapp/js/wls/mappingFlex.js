var isZoomMode = false;
var viewType;
var currentFlashMovie;
var currentSnailTrailIndex = 0;
var currentTaskDetailsIndex = 0;

function getFlexFrame() {
    return document.getElementById("flexFrame").contentWindow;
}

function getSpFlexFrame() {
    return document.getElementById("spApplication").contentWindow;
}

function getFlashMovie() {
    if (typeof(log) != 'undefined') log.debug("getFlashMovie currentFlashMovie = " + currentFlashMovie);
    var isIE = navigator.appName.indexOf("Microsoft") != -1;
    var w = getFlexFrame();
    return (isIE) ? w.window[currentFlashMovie] : w.document[currentFlashMovie];
}

function setCurrentFlashMovie() {
    var pathname = window.location.pathname;
    if (pathname.indexOf("mappingFlx") != -1) {
        currentFlashMovie = "mapping";
    } else if (pathname.indexOf("geoZonesFlx") != -1) {
        currentFlashMovie = "geoZones";
    } else if (pathname.indexOf("proximityFlx") != -1) {
        currentFlashMovie = "proximity";
    }
}

function initSnailTrailMapArea(width, height, panelType) {
    if (typeof(log) != 'undefined') log.debug("initSnailTrailMapArea");
    setMapPanelDimensions(width, height, panelType);
}

function initTaskDetailsMapArea(width, height, panelType) {
    if (typeof(log) != 'undefined') log.debug("initTaskDetailsMapArea width = " + width + " height = " + height);
    setMapPanelDimensions(width, height, panelType);
}

function setMapPanelDimensions(width, height, panelType) {
    getIFrameMapByType(panelType).setMapPanelDimensions(width, height,panelType);
    getIFrameMapByType(panelType).rezoomMapFlx();
}

function getMapObject(panelType) {
    return getIFrameMapByType(panelType).map;
}

function snailTrailPutOnMapComplete(data,flexSnailTrailEnhanced, smallIconsAllowed, device) {
    if (typeof(log) != 'undefined') log.debug("snailTrailPutOnMapComplete data.length = " + data.length + " " + device);

    getIFrameMapByType("snailTrail").snailTrailPutOnMapComplete(data, clickedShapeCallback,flexSnailTrailEnhanced
            , smallIconsAllowed, device);
}

function taskDetailsPutOnMapComplete(data, lat, lon) {
    if (typeof(log) != 'undefined') log.debug("taskDetailsPutOnMapComplete");
    getIFrameMapByType("taskDetails").taskDetailsPutOnMapComplete(data, lat, lon);
}

function flexTrace(str){
    if (typeof(log) != 'undefined') log.debug("*****flex Trace : " + str);
}

function mapCorrectOrZoomToWithHiglight(curIndex, curMapIndex, isZoomAllowed){
    if (typeof(log) != 'undefined') log.info("mapCorrectOrZoomToWithHiglight curIndex = " + curIndex + " curMapIndex = " + curMapIndex);
    getIFrameMapByType("snailTrail").swapItemGlow(curIndex);
    //getIFrameMapByType("snailTrail").mapCorrectOrZoomTo(lat,lon,zoomLevel);
    if (isZoomAllowed) {
        //getIFrameMapByType("snailTrail").mapCorrectOrZoomTo(curIndex);
        getIFrameMapByType("snailTrail").mapCorrectOrZoomTo(curMapIndex);
    }
}

function clickedShapeCallback(index){
    getFlashMovie().getClickedShapeIndex(index);
}

function addPoiClickListener(lonlat,shapeId){
    getFlashMovie().addPoiListener(lonlat,shapeId);
}

function getIFrameMapByType(panelType) {
    var frame;
    if (panelType == "sp_main") {
        frame = getSpFlexFrame().document.getElementById('iframe_mapIFrame0');
    }    if (panelType == "main") {
        frame = getFlexFrame().document.getElementById('iframe_mapIFrame0');
    } else if (panelType == "snailTrail") {
        if (typeof(log) != 'undefined') log.debug("getIFrameMapByType currentSnailTrailIndex = " + currentSnailTrailIndex);
        frame = getFlexFrame().document.getElementById('iframe_snailTrailMapIFrame' + currentSnailTrailIndex);
        if (frame != null) {
            if (typeof(log) != 'undefined') log.debug("getIFrameMapByType frame.id = " + frame.id);
        }
    } else if (panelType == "taskDetails") {
        if (typeof(log) != 'undefined') log.debug("getIFrameMapByType currentTaskDetailsIndex = " + currentTaskDetailsIndex);
        frame = getFlexFrame().document.getElementById('iframe_taskDetailsMapIFrame' + currentTaskDetailsIndex);
        if (frame != null) {
            if (typeof(log) != 'undefined') log.debug("getIFrameMapByType frame.id = " + frame.id);
        }
    } 
    return frame == null ? null : frame.contentWindow;
}

function removeSnailTrailMapIFrame() {
    var div = getFlexFrame().document.getElementById("snailTrailMapIFrame" + currentSnailTrailIndex);
    div.parentNode.removeChild(div);
    currentSnailTrailIndex ++;
    isShapeMapListenerAttached = false;
    if (typeof(log) != 'undefined') log.debug("getIFrameMapByType currentSnailTrailIndex = " + currentSnailTrailIndex);
}

function removeTaskDetailsMapIFrame() {
    var div = getFlexFrame().document.getElementById("taskDetailsMapIFrame" + currentTaskDetailsIndex);
    div.parentNode.removeChild(div);
    currentTaskDetailsIndex ++;
    isShapeMapListenerAttached = false;
    if (typeof(log) != 'undefined') log.debug("getIFrameMapByType currentTaskDetailsIndex = " + currentTaskDetailsIndex);

}

function setViewType(type) {
    viewType = type;
}

function drawLogRecordItem(data) {
    getIFrameMapByType("snailTrail").drawLogRecordItem(data,true,true);
}

function setHoverOverPoiAoiParams(hoverOverPoiAllowed,poiAreaColoured,hoverOverAoiAllowed,aoiAreaColoured, choosePoiAreaColour
    , chooseAoiAreaColour){
    if (typeof(log) != 'undefined')  log.debug("setHoverOverPoiAoiAllowed hoverOverPoiAllowed = " +
                                               hoverOverPoiAllowed + ", isPoiAreaColoured="+poiAreaColoured +
                                                ", hoverOverPoiAllowed = " +
                                                 hoverOverAoiAllowed + ", isAoiAreaColoured="+aoiAreaColoured);
    getIFrameMapByType("main").hoverOverPoiAllowed = hoverOverPoiAllowed;
    getIFrameMapByType("main").poiAreaColoured = poiAreaColoured;
    getIFrameMapByType("main").hoverOverAoiAllowed = hoverOverAoiAllowed;
    getIFrameMapByType("main").aoiAreaColoured = aoiAreaColoured;
    getIFrameMapByType("main").choosePoiAreaColour = choosePoiAreaColour;
    getIFrameMapByType("main").chooseAoiAreaColour = chooseAoiAreaColour;
}

function postcodeSearch(searchText){
    getIFrameMapByType("main").map.geocode(searchText,postcodeSearchCallback);
}

function postcodeSearchCallback(places) {
    if (places == null || places.length == 0) {
        alert("There were no locations found");
    }
    getIFrameMapByType("main").moveLocation(places[0].lon, places[0].lat);
}

function hideTraffic(){
    getIFrameMapByType("main").map.hideTraffic();
}

function showTraffic(){
    getIFrameMapByType("main").map.showTraffic();
}

function showDisruptionItems(items){
    getIFrameMapByType("main").showDisruptionItems(items);
}

function hideDisruptionItems(){
    getIFrameMapByType("main").hideDisruptionItems();
}

function showCameraItems(items){
    getIFrameMapByType("main").showCameraItems(items);
}

function hideCameraItems(){
    getIFrameMapByType("main").hideCameraItems();
}


setCurrentFlashMovie();