if (typeof RMS == "undefined") {
    var RMS = new Object();
}

RMS.RVirtualEarth7 = function(div, zoomLevel, lat, lon, showBE,bingKey) {
    this.initialize(div, zoomLevel, lat, lon, showBE,bingKey);
};
RMS.RVirtualEarth7.prototype = {
    earthRadius : 6367,
    shapeLayer : null,
    infoBoxLayer : null,
    flexSnailTrailEnhanced : false,
    subShapeLayerList : null,
    clickCallback : null,
    SHAPES_COUNT_IN_SUBSHAPELIST : 3,
    DEFAULT_BING_ICON_WIDTH : 25,
    DEFAULT_BING_ICON_HEIGHT : 39,
    zoomLevel : null,
    smallIconsAllowed : false,
    drawControl: null,
    polyWkt: null,
    finishedCallback: null,
    trafficLayer : null,
    searchGeocodeText : null,
    findGeocodeCallback : null,
    clickCallbackHandlerId  : null,
    mouseOverListener : null,
    mouseOutListener : null,
    isEnterPoi : false,
    circleLayer : new Microsoft.Maps.EntityCollection(),

    initialize : function(div, zoomLevel, lat, lon, showBE, bingKey) {
        var coords = null;
        if (lat != null && lon != null) {
            coords =  new Microsoft.Maps.Location(lat, lon);
        } else {
            coords =  new Microsoft.Maps.Location(51.5, 0);
        }
        this.map = new Microsoft.Maps.Map(
                    document.getElementById(div), {
                    credentials: bingKey,
                    center : coords,
                    customizeOverlays: true,
                    zoom: zoomLevel,
                    disableBirdseye : !showBE,
                    mapTypeId:Microsoft.Maps.MapTypeId.road,
                    enableSearchLogo: false
                    });
        this.map.entities.push(this.circleLayer);
        this.zoomLevel = this.map.getZoom();
        Microsoft.Maps.Events.addHandler(this.map, 'viewchange', this.onEndZoom);// todo eventName ?
    },

    zoomTo : function(zoomLevel) {
        this.map.setView({zoom:zoomLevel});
    },

    trafficInited : function (){
        map.trafficLayer = new Microsoft.Maps.Traffic.TrafficLayer(map.map);
        map.showTraffic();
    },

    showTraffic : function(){
        if(this.trafficLayer==undefined){
            Microsoft.Maps.loadModule('Microsoft.Maps.Traffic', { callback: this.trafficInited});
            return;
        }
        this.trafficLayer.show();
     },

    showIncidentShape : function(item){
        this._createMainShapeLayerIfAbsent();
        var shape = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(item.lat, item.lon),{icon: item['src']});
        shape.objType = item.type.toLowerCase();
        shape.id = item.id;
        this._attachInfoBoxToShapeCust(shape,undefined,200,270,item.html);
        this.shapeLayer.push(shape);
        return shape;
    },

     hideTraffic : function() {
        this.trafficLayer.hide();
     },

    setMaxDefaultZoomLevel : function(max_zoom_level) {
        map.getZoomRange = function ()
        {
          return {
            max:      max_zoom_level>0 && max_zoom_level<=19 ? max_zoom_level : 19,
            min:      3
          };
        };

    },

    zoomToCustomExtent : function(lon, lat, zoomLevel) {
        this.map.setView({center:new Microsoft.Maps.Location(lat, lon),zoom : zoomLevel});
    },

    _getShapeByLogIndex : function(index){
       for (var i=0; i < this.shapeLayer.getLength(); i++){
            var shape = this.shapeLayer.get(i);
            if (shape.logIndex == index) return shape;
        }
        return null;
    },

    _getShapeByMapIndex : function(index){ 
       for (var i=0; i < this.shapeLayer.getLength(); i++){
            var shape = this.shapeLayer.get(i);
            if (shape.mapIndex == index) return shape;
        }
        return null;
    },

    fillSubShapeLayerList : function(){ 
        if (this.shapeLayer != undefined){
            this.subShapeLayerList = new Array();
            var curShapeLayer = new Microsoft.Maps.EntityCollection();
            var shape;
            for (var i=0; i < this.shapeLayer.getLength(); i++){
                if (i!=0 && i%this.SHAPES_COUNT_IN_SUBSHAPELIST == 0){
                    this.subShapeLayerList[this.subShapeLayerList.length] = curShapeLayer;
                    curShapeLayer = new Microsoft.Maps.EntityCollection();
                }
                shape = this._getShapeByMapIndex(i);
                var s = new Microsoft.Maps.Pushpin(shape.getLocation());
                s.logIndex = shape.logIndex;
                s.mapIndex = shape.mapIndex;
                log.info("shape.logIndex = " + shape.logIndex + " shape.mapIndex = " + shape.mapIndex);
                curShapeLayer.push(s);
            }
            if (curShapeLayer.getLength() > 0){
                curShapeLayer = new Microsoft.Maps.EntityCollection();
                for (var j=this.shapeLayer.getLength()-this.SHAPES_COUNT_IN_SUBSHAPELIST; j<this.shapeLayer.getLength(); j++){
                    shape = this._getShapeByMapIndex(j);
                    s = new Microsoft.Maps.Pushpin(shape.getLocation());
                    s.logIndex = shape.logIndex;
                    s.mapIndex = shape.mapIndex;
                    curShapeLayer.push(s);
                }
                this.subShapeLayerList[this.subShapeLayerList.length] = curShapeLayer;
            }
        }
    },
    zoomToSubShapeList :function(shapeIndex){
        if (this.shapeLayer != undefined){
            if (this.subShapeLayerList == undefined){
                this.fillSubShapeLayerList();
            }
            var curShapeLayerIndex = parseInt(shapeIndex / this.SHAPES_COUNT_IN_SUBSHAPELIST);
            var bestview = Microsoft.Maps.LocationRect.fromLocations(this._getAllEntitiesLocationFromCollection(this.subShapeLayerList[curShapeLayerIndex]));
	        this.map.setView({bounds:bestview });
        }
    },

    zoomOut : function(){
         this.map.setView({center:new Microsoft.Maps.Location(51.5, 0),zoom : 8});
    },

    hideMapCtrls : function (){
        // todo doesn't work
       this.map.setOptions({showDashboard:false,disableZooming:true,enableSearchLogo:false,showCopyright:false,showMapTypeSelector:false,showScalebar:false});
    },

    restrictZoom : function (){
    },

    showMapCtrls : function (){
        this.map.setOptions({showDashboard:true,disableZooming:false,enableSearchLogo:true,showCopyright:true,showMapTypeSelector:true,showScalebar:true});
    },

    _getAllEntitiesFromCollection : function(){
        var res = [];
        for(var i=0;i<this.shapeLayer.getLength();i++){
            res[i] = this.shapeLayer.get(i);
        }
        return res;
    },

    _getAllEntitiesLocationFromCollection : function(curShapeLayer){
        var res = [];

        for(var i=0;i<curShapeLayer.getLength();i++){

            if(typeof(curShapeLayer.get(i).getLocation) == "function"){
                res.push(curShapeLayer.get(i).getLocation());
            } else if(curShapeLayer.get(i).objType == "circle") {
                var points =  this.getCircle(curShapeLayer.get(i).center, curShapeLayer.get(i).radius);
                for (var j=0; j < points.length; j++) {
                    res.push(points[j]);
                }

            }
        }
        return res;
    },
     
    correctMap : function(exceptObjTypes) {
        var rect;
        if (this.shapeLayer != undefined && this.shapeLayer.getLength() > 0) {
            if (exceptObjTypes != undefined){
                var locationList = [];
                for (var i = 0; i<this.shapeLayer.getLength(); i++){
                    var shapeItem = this.shapeLayer.get(i);
                    if (shapeItem!=null && !arrayContains(exceptObjTypes,shapeItem.objType) && typeof(shapeItem.getLocation) == "function"){
                        locationList.push(shapeItem.getLocation());
                    }
                }
                rect = Microsoft.Maps.LocationRect.fromLocations(locationList);
            }else{
                rect = Microsoft.Maps.LocationRect.fromLocations(this._getAllEntitiesLocationFromCollection(this.shapeLayer));
            }
           this.map.setView({bounds:rect });
           if (map.getZoomRange!=undefined && this.map.getZoom() > map.getZoomRange().max){
              this.map.setView({zoom:map.getZoomRange().max});
           } else if(this.shapeLayer.getLength() ==1){
               this.map.setView({zoom:19});
           }
        } else {
           this.map.setView({zoom:7});
        }
    },

    cleanUp : function(){
        this.shapeLayer.clear();
    },
    

    getCoordsFromPixel : function(e) { 
        var point = new Microsoft.Maps.Point(e.getX(), e.getY());
        var LL = e.target.tryPixelToLocation(point);
        var lonlat = {};
        lonlat.lon = LL.longitude;
        lonlat.lat = LL.latitude;
        return lonlat;
    },

    registerMapClick : function(listener) {      
        var map = this;
        this.clickCallback = function(e) {
            if (e.targetType == "pushpin") {
                if (e.target != null && e.target.infoboxCtrl!=null) {
                    var shapeTitle = e.target.infoboxCtrl._description; 
                    lonlat = e.target.getLocation();
                    listener(lonlat.longitude, lonlat.latitude, shapeTitle);
                }
            } else if(e.targetType == "map"){
                var lonlat = map.getCoordsFromPixel(e);
                listener(lonlat.lon, lonlat.lat, "");
            }
        };
        this.clickCallbackHandlerId = Microsoft.Maps.Events.addHandler(this.map, 'click', this.clickCallback);
    },

    unregisterMapClick : function() {
        if (this.clickCallbackHandlerId != null) {
             Microsoft.Maps.Events.removeHandler(this.clickCallbackHandlerId);
        }
    },

    swapShapeIconToGlowedByIndex : function (curIndex){ 
        this.setAllShapesIconColorToDefault();
        if (curIndex != -1){
            this.changeShapeIconImgByIndex(curIndex,"blue","green");
        }    
    },

    changeShapeIconImgByIndex : function (index, fromColor, toColor){ 
        if (this.flexSnailTrailEnhanced){
            var shape = this._getShapeByLogIndex(index);
            shape.isGlowed = toColor == "green";
            var img = shape.getIcon().replace(fromColor,toColor);
            var zindex = shape.isGlowed ? (shape.getZIndex() + 750) : (shape.getZIndex() - 750);
            shape.setOptions({icon:img,zIndex:zindex});
        }
    },

    setAllShapesIconColorToDefault : function(){
        for (var i = 0; i<this.shapeLayer.getLength(); i++){
            var shape = this.shapeLayer.get(i);
            if (shape != null && shape.isGlowed){
                this.changeShapeIconImgByIndex(shape.logIndex,"green","blue");
            }
        }
    },

    registerClickOnShapeIndex : function(listener){
        var thiz = this;
        var f = function(e){
            if (e.targetType == "pushpin"){
                var shape = e.target;
                thiz.setAllShapesIconColorToDefault();
                if (e.target.logIndex != this.prevClickedElementId){
                    thiz.changeShapeIconImgByIndex(shape.logIndex,"blue","green");
                }
                this.prevClickedElementId = e.target.logIndex;
                listener(shape.logIndex);
            }    
        };
        Microsoft.Maps.Events.addHandler(this.map, 'click', f);
    },


    registerOnShapeMouseEvents : function(mouseOverListener,mouseOutListener, rightClickListener){   // todo impl
         var right = function (e){
            var shapeId;
            if (e.targetType == "pushpin") {
                var shape = e.target;
                lonlat = shape.getLocation();
                if (shape != null && shape.objType == "poi") {
                    shapeId = shape.id;
                }
            }else if(e.targetType == "map"){
                var lonlat = map.getCoordsFromPixel(e);
            }
            rightClickListener(lonlat,shapeId);
        };
        var over = function(e){
            if (e.targetType == "pushpin") {
                var shape = e.target;
                mouseOverListener(shape);
            }


        };
        var out = function(e){

            if (e.targetType == "map") {
                var shape = e.target;
                if (shape != null) {
                    shape.objType = "poi";
                }
                mouseOutListener(shape);
            }
        };
        Microsoft.Maps.Events.addHandler(this.map, 'rightclick', right);
        Microsoft.Maps.Events.addHandler(this.map, 'mousemove', over);
        Microsoft.Maps.Events.addHandler(this.map, 'mousemove', out);
    },

    drawCircleAroundPoi : function (pin,poiAreaColoured, choosePoiAreaColour){
        if (!this.isEnterPoi) {

              var center = new Microsoft.Maps.Location(pin.getLocation().latitude, pin.getLocation().longitude);
              var shape;
              if(poiAreaColoured){
                 // alert(this.getCircle(center, pin.radius));
                  shape = new Microsoft.Maps.Polygon(this.getCircle(center, pin.radius));
                  var polygoncolor = null;
                  if (choosePoiAreaColour) {
                     polygoncolor = new Microsoft.Maps.Color(80, pin.areaColor[0], pin.areaColor[1], pin.areaColor[2]);
                  } else {
                     polygoncolor =  new Microsoft.Maps.Color(127,0,0,255);
                  }
                  shape.setOptions({fillColor: polygoncolor, strokeColor: new Microsoft.Maps.Color(127,0,0,255), strokeThickness: 3});
              }else{
                  shape = new Microsoft.Maps.Polygon(this.getCircle(center, pin.radius));
                  shape.setOptions({fillColor: new Microsoft.Maps.Color(0,0,0,0), strokeColor: new Microsoft.Maps.Color(127,0,0,255), strokeThickness: 3});
              }
             // shape.SetTitle(pin.GetTitle());
              //shape.HideIcon();
              shape.objType = "circle";
              this.circleLayer.push(shape);
              this.isEnterPoi = true;
        }
    },

    removeAllCircles : function(){
        if (this.isEnterPoi) {
            this.circleLayer.clear();
            this.isEnterPoi = false;
         }

    },

    hideAllPolygones :function(){ 
        for (var i = 0; i<this.shapeLayer.getLength(); i++){
            var shape = this.shapeLayer.get(i);
            if (shape != null && shape.objType == "aoi"){
                shape.polygon.setOptions({visible:true});
            }
        }
    },

    showPolygone : function(shape){   
        shape.polygon.setOptions({visible:false});
    },

    getCircle : function(loc, radius) {   // todo impl
          var R = 6371; // earth's mean radius in km
          var lat = (loc.latitude * Math.PI) / 180; //rad
          var lon = (loc.longitude * Math.PI) / 180; //rad
          var d = parseFloat(radius) / R;  // d = angular distance covered on earth's surface
          var locs = new Array();
          for (var x = 0; x <= 360; x++) {
              var p2 = new Microsoft.Maps.Location(0, 0);
              brng = x * Math.PI / 180; //rad
              p2.latitude = Math.asin(Math.sin(lat) * Math.cos(d) + Math.cos(lat) * Math.sin(d) * Math.cos(brng));
              p2.longitude = ((lon + Math.atan2(Math.sin(brng) * Math.sin(d) * Math.cos(lat), Math.cos(d) - Math.sin(lat) * Math.sin(p2.latitude))) * 180) / Math.PI;
              p2.latitude = (p2.latitude * 180) / Math.PI;
              locs.push(p2);
          }
          return locs;
      },



    addTextMarkerByObj: function (img,addressedEnumObj, aoiAreaColoured, chooseAoiAreaColour, showArea, poiAreaColoured, choosePoiAreaColour){
        this._createMainShapeLayerIfAbsent();

        if (addressedEnumObj.lat == undefined && addressedEnumObj.lon == undefined && addressedEnumObj.polygonWkt!=undefined){
            var polygon = this.addPolygonFromWkt(addressedEnumObj.polygonWkt);
            var areaColor = getAreaColorsRGB(addressedEnumObj.areaColor, aoiAreaColoured, chooseAoiAreaColour);
            var polygoncolor = new Microsoft.Maps.Color(areaColor[0], areaColor[1], areaColor[2], areaColor[3]);
            var poligonOptions = {fillColor: polygoncolor,strokeColor: polygoncolor,visible:false};
            polygon.setOptions(poligonOptions);
//            polygon.SetFillColor();
//            polygon.Hide();
            //polygon.HideIcon();
//            addressedEnumObj.lat = polygon.GetCenterPoint()[1]; todo impl
//            addressedEnumObj.lon = polygon.GetCenterPoint()[0];
        }

        var shape = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(addressedEnumObj.lat, addressedEnumObj.lon));
        shape.id = addressedEnumObj.id;
        shape.objType = addressedEnumObj.typeDescr.toLowerCase();

        if (addressedEnumObj.radius != undefined){
            var radius = addressedEnumObj.radius / 1000;
            shape.radius = radius;
            if (showArea) {

                var center = new Microsoft.Maps.Location(addressedEnumObj.lat, addressedEnumObj.lon);
                var shapePoi;
                if(poiAreaColoured){
                    // alert(this.getCircle(center, pin.radius));
                    shapePoi = new Microsoft.Maps.Polygon(this.getCircle(center,radius));
                    var polygoncolorPoi = null;
                    if (choosePoiAreaColour) {
                        var colors = getAreaColorsRGB(addressedEnumObj.areaColor, true, true);
                        polygoncolorPoi = new Microsoft.Maps.Color(80, colors[0], colors[1], colors[2]);
                    } else {
                        polygoncolorPoi =  new Microsoft.Maps.Color(127,0,0,255);
                    }
                    shapePoi.setOptions({fillColor: polygoncolorPoi, strokeColor: new Microsoft.Maps.Color(127,0,0,255), strokeThickness: 3});
                }else{

                    shapePoi = new Microsoft.Maps.Polygon(this.getCircle(center, radius));
                    shapePoi.setOptions({fillColor: new Microsoft.Maps.Color(0,0,0,0), strokeColor: new Microsoft.Maps.Color(127,0,0,255), strokeThickness: 3});
                }
                // shape.SetTitle(pin.GetTitle());
                //shape.HideIcon();
                shapePoi.objType = "circle";
                shapePoi.center = {latitude: addressedEnumObj.lat, longitude: addressedEnumObj.lon};
                shapePoi.radius = radius;
                this.shapeLayer.push(shapePoi);
            }
        }
        if (polygon!=undefined){
            shape.polygon = polygon;
        } else {
            shape.areaColor = getAreaColorsRGB(addressedEnumObj.areaColor, true, true);
        }
        var options = {};
        options['icon'] = img.src == undefined ? img : img.src;
        options['anchor'] = new Microsoft.Maps.Point(8,25);
        shape.setOptions(options);
        this._attachInfoBoxToShape(shape,addressedEnumObj.descr);
        this.shapeLayer.push(shape);
        shape.point =  new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(addressedEnumObj.lat, addressedEnumObj.lon));
        return shape;
    },

    _attachInfoBoxToShape : function(shape,text){
        this._attachInfoBoxToShapeCust(shape,text,290,150,undefined);
    },

    _attachInfoBoxToShapeCust : function(shape,text, w,h,html){
        var pinInfobox = new Microsoft.Maps.Infobox(new Microsoft.Maps.Location(0,0),
                        {width :w, height :h, showCloseButton: true, zIndex: 0, visible: false,
                            offset:new Microsoft.Maps.Point(10,0), showPointer: false});
        if(text!=undefined){
            pinInfobox.setOptions({ description:text});
        }else if(html!=undefined){
            pinInfobox.setHtmlContent(html);
        }
        this.infoBoxLayer.push(pinInfobox);
        shape.infoboxCtrl = pinInfobox;

        Microsoft.Maps.Events.addHandler(shape, 'mouseover', this.displayInfobox);
        Microsoft.Maps.Events.addHandler(shape, 'mouseout', this.hideInfobox);

        Microsoft.Maps.Events.addHandler(shape, 'mouseenter', this.displayInfobox);
        Microsoft.Maps.Events.addHandler(shape, 'mouseleave', this.hideInfobox);
    },

    calculateXOffSet : function (width){
        return (this.DEFAULT_BING_ICON_WIDTH - width)/2;
    },

    calculateYOffSet : function (height){
        return (this.DEFAULT_BING_ICON_HEIGHT - height)/2;
    },

    hideInfobox : function(e){
        if ((e.targetType == "pushpin" || e.targetType == "infobox") && e.target.infoboxCtrl!=undefined) {
            e.target.infoboxCtrl.setOptions({
                visible:false
            });
        }
    },

    displayInfobox : function(e){
         if ((e.targetType == "pushpin" || e.targetType == "infobox") && e.target.infoboxCtrl!=undefined) {
              var pinInfobox = e.target.infoboxCtrl;
              var buffer = 33;
              var infoboxOffset = pinInfobox.getOffset();
              var infoboxAnchor = pinInfobox.getAnchor();
              var infoboxLocation = map.map.tryLocationToPixel(e.target.getLocation(), Microsoft.Maps.PixelReference.control);
              var dx = infoboxLocation.x + infoboxOffset.x - infoboxAnchor.x;
              var dy = infoboxLocation.y - 33 - infoboxAnchor.y;
              if (dy < buffer) {    //Infobox overlaps with top of map.
                  //add buffer from the top edge of the map.
                  dy += buffer;
              } else {
                  //If dy is greater than zero than it does not overlap.
                  dy = 0;
              }
              if (dx < buffer) {    //Check to see if overlapping with left side of map.
                  //add a buffer from the left edge of the map.
                  dx += buffer;
              } else {              //Check to see if overlapping with right side of map.
                  dx = map.map.getWidth() - infoboxLocation.x + infoboxAnchor.x - pinInfobox.getWidth();
                  //If dx is greater than zero then it does not overlap.
                  if (dx > buffer) {
                      dx = 0;
                  } else {
                      //add a buffer from the right edge of the map.
                      dx -= buffer;
                  }
              }
              pinInfobox.setLocation(e.target.getLocation());
              if (e.targetType == "pushpin") {
                var offsetX = e.target.getWidth() / 2;
                var offsetY = e.target.getHeight() / 2;
              } else {
                  var offsetX = e.target.getWidth() / 4;
                  var offsetY = e.target.getHeight() / 4;
              }
              dx = dx != 0 ? dx - offsetX : offsetX;
              dy = dy != 0 ? dy - offsetY : offsetY;
              pinInfobox.setOptions({
                    offset: new Microsoft.Maps.Point(dx, dy),
                    visible:true
              });

        }
    },
    
    addTextMarker : function(img, text, lon, lat, corLon, corLat, dist,
                             isIgnitionIcons,
                             htmlMarker,
                             showTooltip, regNumber,dotOnMap, showVehTooltipAllowed
                             ,drawArrowFromVehIconToRoad) {


        this._createMainShapeLayerIfAbsent();
        var shape = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(lat, lon));
        var optionsArr = {};
        //var shape = new VEShape(VEShapeType.Pushpin, new VELatLong(lat, lon));

        if (isIgnitionIcons && !dotOnMap || showVehTooltipAllowed) {

            var infoboxOptions = {
                htmlContent : htmlMarker
            };

            shape = new Microsoft.Maps.Infobox(new Microsoft.Maps.Location(lat, lon), infoboxOptions);


        } else {
            if (img.src != undefined){
                optionsArr['icon'] = img.src;
                //optionsArr['anchor'] = new Microsoft.Maps.Point(this.calculateXOffSet(img.width),this.calculateYOffSet(img.height));   // todo check
                optionsArr['width'] = img.width;
                optionsArr['height'] = img.height;
            }else{
                optionsArr['icon'] = img;
            }
            shape.setOptions(optionsArr);
        }
        if (showTooltip) {
            this._attachInfoBoxToShape(shape,text);
        }
        this.shapeLayer.push(shape);

        if (corLon != null && corLat != null) {
            if (!drawArrowFromVehIconToRoad) {
                var point = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(corLat, corLon));
                if (dist != undefined) {
                    text = (regNumber != undefined ? regNumber : "") + " " + Math.round(dist * 10) / 10 + "m";
                }
                this._attachInfoBoxToShape(point,text);
                this.shapeLayer.push(point);
                shape.point = point;
                point.objType = "corPin";
            } else {
                var point1 = new Microsoft.Maps.Location(lat, lon);
                var point2 = new Microsoft.Maps.Location(corLat, corLon);
                var points = this.generatePolylinePointsWithArrow([point1,point2]);
                var polyline = new Microsoft.Maps.Polyline(points,{strokeColor:new Microsoft.Maps.Color(255,0,0,1.0),strokeThickness:3, strokeDashArray:"20 4"});
                this.shapeLayer.push(polyline);
                shape.point = polyline;
                polyline.objType = "corPin";
            }
        }
        return shape;
    },

    zoomToMaxExtentToPoint : function(lon, lat) {
        this.map.setView({zoom:19,center:new Microsoft.Maps.Location(lat, lon)});
    },

    _createMainShapeLayerIfAbsent : function(){
        if (this.shapeLayer == undefined) {
            this.shapeLayer = new Microsoft.Maps.EntityCollection();
            this.map.entities.push(this.shapeLayer);
        }
        if(this.infoBoxLayer == undefined){
            this.infoBoxLayer = new Microsoft.Maps.EntityCollection();
            this.map.entities.push(this.infoBoxLayer);
        }
    },

    addImgMarker : function(img, lon, lat) {
        this._createMainShapeLayerIfAbsent();
        var shape = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(lat, lon));
        shape.latlon = new Microsoft.Maps.Location(lat, lon);
        shape.isGlowed = false;
        var imgUrl;
        var optionsArr = {};
        var possZindex = 0;
        if (img.src != undefined){
            optionsArr['icon'] = img.src;
            //optionsArr['anchor'] = new Microsoft.Maps.Point((this.calculateXOffSet(img.width),this.calculateYOffSet(img.height)));
            optionsArr['width'] = img.width;
            optionsArr['height'] = img.height;
            possZindex = shape.logIndex = img.logIndex;
            shape.mapIndex = img.mapIndex;
            imgUrl = img.src;
        }else{
            optionsArr['icon'] = img;
            imgUrl = img;
        }
        var journeyEnd = imgUrl.indexOf("flag_end") != -1;
        var journeyStart = imgUrl.indexOf("flag_start") != -1;
        var driverBehavior = imgUrl.indexOf("snail_trail") != -1;
        optionsArr['zIndex'] = possZindex + (journeyEnd ? 500 : (journeyStart ? 300 : ( driverBehavior ? 200 : 0)));
        shape.setOptions(optionsArr);
        this.shapeLayer.push(shape);
        return shape;
    },


    drawLineImgMarker : function (img,descr, startLon, startLat, endLon, endLat) {
        var polyline = new Microsoft.Maps.Polyline([new Microsoft.Maps.Location(startLat, startLon),new Microsoft.Maps.Location(endLat, endLon)],
                                          {strokeColor : new Microsoft.Maps.Color(255, 0, 0, 1.0), strokeThickness : 3});
        var shape = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(endLat, endLon),{icon:img.src,width:img.width,height:img.height});
        shape.point = polyline;
        this.shapeLayer.push(shape);
        this.shapeLayer.push(polyline);
        this._attachInfoBoxToShape(shape,descr);
        return shape;
    },

    addPolygonFromWkt : function(wktString) {   
        var shape = null;
        try {
            shape = VirtualEarthWKT7.ShapeFromWKT(wktString);
        } catch(e) {
            alert(e);
        }
        if (shape != null) {
            this._createMainShapeLayerIfAbsent();
            shape.objType = "polygonWkt";
            this.shapeLayer.push(shape);
        }
        return shape;
    },

    removeMarker : function(marker) {
        if (this.shapeLayer != undefined) {
            if (marker.point != undefined) {
                //this.shapeLayer.DeleteShape(marker.point);
                this.shapeLayer.remove(marker.point);
            }
            if(marker.infoboxCtrl!=undefined){
                this.infoBoxLayer.remove(marker.infoboxCtrl);
            }
            try {
                //this.shapeLayer.DeleteShape(marker);
                this.shapeLayer.remove(marker);
            } catch (e) {
                if (typeof(log) != 'undefined') log.error("RVirtualEarth removeMarker " + e.message);
            }
        }

    },

    /**
     * Function searches places by search string
     * @param searchText - search string
     * @param findCallback - function that will be performed at the end of searching.
     */

    geocode : function(searchText, findCallback) {
        var countryCode;
        if (location.toString().indexOf(".de") != -1) {
            countryCode = ", DE";
        } else {
            countryCode = ", UK";
        }
        this.searchGeocodeText = searchText+countryCode;
        this.findGeocodeCallback = findCallback;
        this.map.getCredentials(this.makeGeocodeRequest);
    },

    makeGeocodeRequest : function (credentials) {
        var geocodeRequest = "http://dev.virtualearth.net/REST/v1/Locations?query=" + encodeURI(map.searchGeocodeText) + "&output=json&jsonp=map.geocodeCallback&key=" + credentials;
        var script = document.createElement("script");
        script.setAttribute("type", "text/javascript");
        script.setAttribute("src", geocodeRequest);
        document.body.appendChild(script);
     },

     geocodeCallback : function (result){
        if (result &&
               result.resourceSets &&
               result.resourceSets.length > 0 &&
               result.resourceSets[0].resources &&
               result.resourceSets[0].resources.length > 0){

            var filteredPlaces = [];
            for (var i = 0; i < result.resourceSets[0].resources.length; i++) {
                var place = result.resourceSets[0].resources[i]; 
                var newPlace = new Object();
                newPlace.street = place.name;
                newPlace.description = place.address.formattedAddress;
                newPlace.lat = place.point.coordinates[0];
                newPlace.lon = place.point.coordinates[1];
                filteredPlaces[filteredPlaces.length] = newPlace;
            }
            map.findGeocodeCallback(filteredPlaces);
        }
     },

    getLocationObject : function (lat, lon){
        return new Microsoft.Maps.Location(lat, lon);
    },

    getZoomLevel : function() {
        return this.zoomLevel;
    },

    saveZoomLevel : function(zoomLevel) {
        this.zoomLevel = zoomLevel;
    },

    onEndZoom : function (e) {
        if (this.map != undefined && this.map.smallIconsAllowed != undefined) {
            if (this.map.smallIconsAllowed) {
                var zoomLevel = this.map.getZoom();
                isPinsSizeChanged = (this.map.zoomLevel <= ZOOM_LEVEL_FOR_SMALL_PINS && zoomLevel > ZOOM_LEVEL_FOR_SMALL_PINS) ||
                                    (this.map.zoomLevel > ZOOM_LEVEL_FOR_SMALL_PINS && zoomLevel <= ZOOM_LEVEL_FOR_SMALL_PINS);
                this.map.saveZoomLevel(zoomLevel);
                if (this.map.myGroupElems != undefined && this.map.myGroupElems.length > 0 && isPinsSizeChanged) {
                    for (var j = 0; j < this.map.myGroupElems.length; j++) {
                        this.map.removeMarker(this.map.myGroupElems[j]);
                    }
                    smallIcons = undefined;
                    var mapIndex = 0;
                    var i = 0;
                    var ro = new RepeatingOperation(function(){
                        var item = data[i];
                        item.logIndex = i;
                        if (item.mapVisible == undefined || item.mapVisible){
                            item.mapIndex = mapIndex;
                            drawLogRecordItem(item);
                            mapIndex++;
                        }
                        if (++i < data.length){
                            ro.step();
                        }
                    },50);
                    ro.step();
                }
            } else{
                this.map.saveZoomLevel(e.zoomLevel);
            }
        }
        },
     
   generatePolylinePointsWithArrow: function(points){
        //last point in polyline array
        var anchorPoint = points[points.length-1];

        //bearing from last point to second last point in pointline array
        var bearing = this.calculateBearing(anchorPoint,points[points.length-2]);

        //length of arrow head lines in km
        var arrowLength = 0.01;
        //angle of arrow lines relative to polyline in degrees
        var arrowAngle = 15;
        //calculate coordinates of arrow tips
        var arrowPoint1 = this.calculateCoord(anchorPoint, bearing-arrowAngle, arrowLength);
        var arrowPoint2 = this.calculateCoord(anchorPoint, bearing+arrowAngle, arrowLength);
        //go from last point in polyline to one arrow tip, then back to the
        //last point then to the second arrow tip.
        points.push(arrowPoint1);
        points.push(anchorPoint);
        points.push(arrowPoint2);
        return points;
    },

    DegtoRad: function(x)
    {
        return x*Math.PI/180;
    }
    ,
    RadtoDeg: function(x)
    {
        return x*180/Math.PI;
    }
    ,
    calculateCoord: function(origin, brng, arcLength)
    {
        var lat1 = this.DegtoRad(origin.latitude);
        var lon1 = this.DegtoRad(origin.longitude);
        var centralAngle = arcLength /this.earthRadius;
        var lat2 = Math.asin( Math.sin(lat1)*Math.cos(centralAngle) + Math.cos(lat1)*Math.sin(centralAngle)*Math.cos(this.DegtoRad(brng)));
        var lon2 = lon1+Math.atan2(Math.sin(this.DegtoRad(brng))*Math.sin(centralAngle)*Math.cos(lat1),Math.cos(centralAngle)-Math.sin(lat1)*Math.sin(lat2));
        return new Microsoft.Maps.Location(this.RadtoDeg(lat2),this.RadtoDeg(lon2));
    }
    ,
    calculateBearing: function(A,B)
    {
        var lat1 = this.DegtoRad(A.latitude);
        var lon1 = A.longitude;
        var lat2 = this.DegtoRad(B.latitude);
        var lon2 = B.longitude;
        var dLon = this.DegtoRad(lon2-lon1);
        var y = Math.sin(dLon) * Math.cos(lat2);
        var x = Math.cos(lat1)*Math.sin(lat2) - Math.sin(lat1)*Math.cos(lat2)*Math.cos(dLon);
        var brng = (this.RadtoDeg(Math.atan2(y, x))+360)%360;
        return brng;
    }
    ,

    initDrawControl: function(map, polygoneWkt, finished, pinColor, areaColor)  // todo impl
    {
        this.polyWkt = polygoneWkt;
        this.finishedCallback = finished;
        this.drawControl = new RMS.BingPolygonControl(map, pinColor, areaColor);

        if (typeof(log) != 'undefined') log.debug("initDrawControl polyWkt = " + this.polyWkt);
        if (this.polyWkt != ""){
            var polygon = this.addPolygonFromWkt(this.polyWkt);
            polygon.SetFillColor(new VEColor(areaColor[0], areaColor[1], areaColor[2], areaColor[3]));
            if (polygon != null){
                this.drawControl.disableDrawing();
                this.drawControl.polygonShape = polygon;
                this.correctMap();
            }
            this.drawControl.showPolygonPushpins();
        } else {
            this.drawControl.enableDrawing();
        }

    }

    ,

    onFinishDrawing: function(pointsWkt) {
        if (typeof(log) == 'undefined') { log = { debug: function (str) { alert(str); } }; } log.debug("onFinishDrawing start");

        this.polyWkt = pointsWkt;

        this.finishedCallback(this.polyWkt);
        if (typeof(log) == 'undefined') { log = { debug: function (str) { alert(str); } }; } log.debug("onFinishDrawing finish");
    }
    ,

    deletePolygon: function(){
        this.drawControl.deletePolygon();   // todo impl inner
        this.drawControl.enableDrawing();
        this.polyWkt = "";
    }
    ,
    addPointToPoly: function(lon, lat) {
         return this.drawControl.addPointToPoly(lon, lat);
    }
    ,

    deletePointFromPoly: function(lon, lat, shapeTitle) {
        return this.drawControl.deletePointFromPoly(lon, lat, shapeTitle);
    }
    ,

    disableDrawing: function(){
        this.drawControl.disableDrawing();
    }
    ,
    enableDrawing: function(){
        this.drawControl.enableDrawing();
    }
    ,
    setPolygonPinColor: function(pinColor) {
        this.drawControl.setPinColor(pinColor);
    }
    ,
    setPolygonAreaColor: function(areaColor) {
        this.drawControl.setAreaColor(areaColor);
    }
    ,
    CLASS_NAME : "RMS.RVirtualEarth7"
};