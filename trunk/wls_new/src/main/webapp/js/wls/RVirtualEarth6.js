if (typeof RMS == "undefined") {
    var RMS = new Object();
}

RMS.RVirtualEarth6 = function(div, zoomLevel, lat, lon, showBE) {
    this.initialize(div, zoomLevel, lat, lon, showBE);
};
RMS.RVirtualEarth6.prototype = {
    earthRadius : 6367,
    shapeLayer : null,
    flexSnailTrailEnhanced : false,
    subShapeLayerList : null,
    clickCallback : null,
    SHAPES_COUNT_IN_SUBSHAPELIST : 3,
    DEFAULT_BING_ICON_SIDE : 26,
    zoomLevel : null,
    smallIconsAllowed : false,
    drawControl: null,
    polyWkt: null,
    finishedCallback: null,


    initialize : function(div, zoomLevel, lat, lon, showBE) {
        this.map = new VEMap(div);
        var coords = null;
        if (lat != null && lon != null) {
            coords = new VELatLong(lat, lon);
        } else {
            coords = new VELatLong(51.5, 0);
        }

        var options = new VEMapOptions();
        options.EnableBirdseye = showBE;

        this.map.LoadMap(coords, //Center point
                zoomLevel, //Zoom level
                VEMapStyle.Road, //Map style
                false, //Static map? No.
                VEMapMode.Mode2D,
                true,
                0,
                options);
        zoomLevel = this.map.GetZoomLevel();
        this.map.AttachEvent("onendzoom", this.onEndZoom);
        this.map.ClearInfoBoxStyles();
    },

    zoomTo : function(zoomLevel) {
        this.map.SetZoomLevel(zoomLevel);
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
        //this.map.LoadMap(new VELatLong(lon, lat), zoomLevel, map.GetMapStyle(), false);
        this.map.SetCenterAndZoom(new VELatLong(lat, lon),zoomLevel);
    },

    _getShapeByLogIndex : function(index){
       var shape;
       for (var i=0; i < this.shapeLayer.GetShapeCount(); i++){
            shape = this.shapeLayer.GetShapeByIndex(i);
            if (shape.logIndex == index) return shape;
        }
        return null;
    },

    _getShapeByMapIndex : function(index){
       var shape;
       for (var i=0; i < this.shapeLayer.GetShapeCount(); i++){
            shape = this.shapeLayer.GetShapeByIndex(i);
            if (shape.mapIndex == index) return shape;
        }
        return null;
    },

    fillSubShapeLayerList : function(){
        if (this.shapeLayer != undefined){
            this.subShapeLayerList = new Array(); 
            var curShapeLayer = new VEShapeLayer();
            var shape;
            for (var i=0; i < this.shapeLayer.GetShapeCount(); i++){
                if (i!=0 && i%this.SHAPES_COUNT_IN_SUBSHAPELIST == 0){
                    this.subShapeLayerList[this.subShapeLayerList.length] = curShapeLayer;
                    curShapeLayer = new VEShapeLayer();
                }
                //curShapeLayer.AddShape(new VEShape(VEShapeType.Pushpin, this.shapeLayer.GetShapeByIndex(i).GetPoints()));
                //curShapeLayer.AddShape(new VEShape(VEShapeType.Pushpin, this._getShapeByLogIndex(i).GetPoints()));
                shape = this._getShapeByMapIndex(i);
                var s = new VEShape(VEShapeType.Pushpin, shape.GetPoints());
                s.logIndex = shape.logIndex;
                s.mapIndex = shape.mapIndex;
                log.info("shape.logIndex = " + shape.logIndex + " shape.mapIndex = " + shape.mapIndex);
                curShapeLayer.AddShape(s);
            }
            if (curShapeLayer.GetShapeCount() > 0){
                curShapeLayer = new VEShapeLayer();
                for (var j=this.shapeLayer.GetShapeCount()-this.SHAPES_COUNT_IN_SUBSHAPELIST; j<this.shapeLayer.GetShapeCount(); j++){
                    //curShapeLayer.AddShape(new VEShape(VEShapeType.Pushpin, this.shapeLayer.GetShapeByIndex(j).GetPoints()));
                    //curShapeLayer.AddShape(new VEShape(VEShapeType.Pushpin, this._getShapeByLogIndex(j).GetPoints()));
                    shape = this._getShapeByMapIndex(j);
                    s = new VEShape(VEShapeType.Pushpin, shape.GetPoints());
                    s.logIndex = shape.logIndex;
                    s.mapIndex = shape.mapIndex;
//                    log.info("shape.logIndex = " + shape.logIndex + " shape.mapIndex = " + shape.mapIndex);
                    curShapeLayer.AddShape(s);
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
            this.map.SetMapView(this.subShapeLayerList[curShapeLayerIndex].GetBoundingRectangle());
        }
    },

    zoomOut : function(){
        this.map.ZoomOut();
    },

    hideMapCtrls : function (){
       this.map.HideScalebar();
       this.map.HideDashboard();
       getElementsByClassName("MSVE_PoweredByLogo")[0].style.display="none";
       getElementsByClassName("MSVE_Copyright")[0].style.display="none";
       getElementsByClassName("MSVE_Copyright")[1].style.display="none";
    },

    restrictZoom : function (){
    },

    showMapCtrls : function (){
       this.map.ShowDashboard();
       this.map.ShowScalebar();
       getElementsByClassName("MSVE_PoweredByLogo")[0].style.display="";
       getElementsByClassName("MSVE_Copyright")[0].style.display="";
       getElementsByClassName("MSVE_Copyright")[1].style.display="";
    },

    correctMap : function(exceptObjTypes) {
        var rect;
        if (this.shapeLayer != undefined) {
            if (exceptObjTypes != undefined){
                var forBoundShapeLayer = new VEShapeLayer();
                var shapeList = this.shapeLayer.Annotations;
                for (var i = 0; i<this.shapeLayer.GetShapeCount(); i++){
                    if (shapeList[i]!=null && !arrayContains(exceptObjTypes,shapeList[i].objType)){
                        var shape = new VEShape(VEShapeType.Pushpin, shapeList[i].GetPoints());
                        forBoundShapeLayer.AddShape(shape);
                    }
                }
                rect = forBoundShapeLayer.GetBoundingRectangle();
            }else{
                rect = this.shapeLayer.GetBoundingRectangle();
            }
            if (this.shapeLayer.GetShapeCount() > 0) {
               this.map.SetMapView(rect);
               if (map.getZoomRange!=undefined && this.map.GetZoomLevel() > map.getZoomRange().max){
                  this.map.SetZoomLevel(map.getZoomRange().max);
               }
            } else {
               this.map.SetZoomLevel(7);
            }

        }
    },

    cleanUp : function(){
        this.map.DeleteAllShapes();
    },
    

    getCoordsFromPixel : function(e) {
        var x = e.mapX;
        var y = e.mapY;
        var pixel = new VEPixel(x, y);
        var LL = this.map.PixelToLatLong(pixel);
        var lonlat = {};
        lonlat.lon = LL.Longitude;
        lonlat.lat = LL.Latitude;

        return lonlat;
    },

    registerMapClick : function(listener) {
        var map = this;
        this.clickCallback = function(e) {
            var lonlat = map.getCoordsFromPixel(e);
            var shapeTitle;
            if (e.elementID != null) {
                var shape = map.map.GetShapeByID(e.elementID);
                if (shape != null) {
                    shapeTitle = shape.GetTitle();
                }
            }

            listener(lonlat.lon, lonlat.lat, shapeTitle);
        };
        this.map.AttachEvent("onclick", this.clickCallback);
    },

    unregisterMapClick : function() {
        if (this.clickCallback != null) {
            this.map.DetachEvent("onclick", this.clickCallback);
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
            //var shape = this.shapeLayer.GetShapeByIndex(index);
            var shape = this._getShapeByLogIndex(index);
            
            var img = shape.GetCustomIcon().Image.replace(fromColor,toColor);
            var offset = shape.GetCustomIcon().ImageOffset;
            var icon = new VECustomIconSpecification();
            icon.Image = img;
            icon.ImageOffset = offset;
            icon.TextContent=" ";

            shape.SetCustomIcon(icon);
            shape.isGlowed = toColor == "green";
            shape.SetZIndex(shape.isGlowed ? (shape.GetZIndex() + 150) : (shape.GetZIndex() - 150));
        }
    },

    setAllShapesIconColorToDefault : function(){
        var shapeList = this.shapeLayer.Annotations;
        for (var i = 0; i<this.shapeLayer.GetShapeCount(); i++){
            if (shapeList[i] != null && shapeList[i].isGlowed){
                this.changeShapeIconImgByIndex(shapeList[i].logIndex,"green","blue");
            }
        }
    },

    registerClickOnShapeIndex : function(listener){
        var thiz = this;
        var f = function(e){
            if (e.elementID != null){
                var shape = map.map.GetShapeByID(e.elementID);
                thiz.setAllShapesIconColorToDefault();
                if (e.elementID != this.prevClickedElementId){
                    //thiz.changeShapeIconImgByIndex(shape.index,"blue","green");
                    thiz.changeShapeIconImgByIndex(shape.logIndex,"blue","green");
                }
                this.prevClickedElementId = e.elementID;
                //listener(shape.index);
                listener(shape.logIndex);
            }    
        };
        this.map.AttachEvent("onclick",f);
    },

    registerOnShapeMouseEvents : function(mouseOverListener,mouseOutListener, rightClickListener){
        var over = function(e){
            if (e!=null && e.elementID != null){
                var shape = map.map.GetShapeByID(e.elementID);
                mouseOverListener(shape);
            }
        };
        var out = function(e){
            if (e!=null && e.elementID != null){
                var shape = map.map.GetShapeByID(e.elementID);
                mouseOutListener(shape);
            }
        };
         var right = function (e){
            if (e.rightMouseButton){
                var lonlat = map.getCoordsFromPixel(e);
                var shapeId;
                if (e.elementID != null) {
                    var shape = map.map.GetShapeByID(e.elementID);
                    if (shape != null && shape.objType == "poi") {
                        shapeId = shape.id;
                    }
                }
                rightClickListener(lonlat,shapeId);
            }
        };
        this.map.AttachEvent("onmouseover",over);
        this.map.AttachEvent("onmouseout",out);
        this.map.AttachEvent('onclick', right);
    },

    drawCircleAroundPoi : function (pin,poiAreaColoured, choosePoiAreaColour){
          var center = new VELatLong(pin.Latitude, pin.Longitude);
          var shape;
          if(poiAreaColoured){
              shape = new VEShape(VEShapeType.Polygon, this.getCircle(center, pin.radius));
              if (choosePoiAreaColour) {
                  alpha=0.5, RGB=0, 0, 255
                shape.SetFillColor(new VEColor(pin.areaColor[0], pin.areaColor[1], pin.areaColor[2], pin.areaColor[3]));
              }

          }else{
              shape = new VEShape(VEShapeType.Polyline, this.getCircle(center, pin.radius));
              shape.SetLineColor(new VEColor(255, 0, 0, 1.0));
          }
          shape.SetTitle(pin.GetTitle());
          shape.HideIcon();
          shape.objType = "circle";
          this.shapeLayer.AddShape(shape);
    },

    removeAllCircles : function(){
        var shapeList = this.shapeLayer.Annotations;
        for (var i = 0; i<this.shapeLayer.GetShapeCount(); i++){
            if (shapeList[i] != null && shapeList[i].objType == "circle"){
                 this.removeMarker(shapeList[i]);
            }
//            else if(shapeList[i] != null && shapeList[i].objType == "poi"){
//                 shapeList[i].withCircle = false;
//            }
        }
    },

    hideAllPolygones :function(){
        var shapeList = this.shapeLayer.Annotations;
        for (var i = 0; i<this.shapeLayer.GetShapeCount(); i++){
            if (shapeList[i] != null && shapeList[i].objType == "aoi"){
                shapeList[i].polygon.Hide();
               // shapeList[i].polygonVisible = false;
            }
        }
    },

    showPolygone : function(shape){
        shape.polygon.Show();
        //shape.polygonVisible = true;
    },

    getCircle : function(loc, radius) {
          var R = 6371; // earth's mean radius in km
          var lat = (loc.Latitude * Math.PI) / 180; //rad
          var lon = (loc.Longitude * Math.PI) / 180; //rad
          var d = parseFloat(radius) / R;  // d = angular distance covered on earth's surface
          var locs = new Array();
          for (var x = 0; x <= 360; x++) {
              var p2 = new VELatLong(0, 0);
              brng = x * Math.PI / 180; //rad
              p2.Latitude = Math.asin(Math.sin(lat) * Math.cos(d) + Math.cos(lat) * Math.sin(d) * Math.cos(brng));
              p2.Longitude = ((lon + Math.atan2(Math.sin(brng) * Math.sin(d) * Math.cos(lat), Math.cos(d) - Math.sin(lat) * Math.sin(p2.Latitude))) * 180) / Math.PI;
              p2.Latitude = (p2.Latitude * 180) / Math.PI;
              locs.push(p2);
          }
          return locs;
      },
      
    addTextMarkerByObj: function (img,addressedEnumObj, aoiAreaColoured, chooseAoiAreaColour){
        if (this.shapeLayer == undefined) {
            this.shapeLayer = new VEShapeLayer();
            this.map.AddShapeLayer(this.shapeLayer);
        }
        if (addressedEnumObj.lat == undefined && addressedEnumObj.lon == undefined && addressedEnumObj.polygonWkt!=undefined){
            var polygon = this.addPolygonFromWkt(addressedEnumObj.polygonWkt);
            var areaColor = getAreaColorsRGB(addressedEnumObj.areaColor, aoiAreaColoured, chooseAoiAreaColour);
            polygon.SetFillColor(new VEColor(areaColor[0], areaColor[1], areaColor[2], areaColor[3]));
            polygon.Hide();
            //polygon.SetCustomIcon(img.src.replace("aoiTypes/locationIcon.gif","red_pixel.jpg"));
            polygon.HideIcon();
            addressedEnumObj.lat = polygon.GetCenterPoint()[1];
            addressedEnumObj.lon = polygon.GetCenterPoint()[0];
        }
        var shape = new VEShape(VEShapeType.Pushpin, new VELatLong(addressedEnumObj.lat, addressedEnumObj.lon));
        shape.id = addressedEnumObj.id;
        shape.objType = addressedEnumObj.typeDescr.toLowerCase();
        if (addressedEnumObj.radius != undefined){
            shape.radius = addressedEnumObj.radius / 1000;
            //shape.withCircle = false;
        }
        if (polygon!=undefined){
            shape.polygon = polygon;

            //shape.polygonVisible = false;
        }  else {
            shape.areaColor = getAreaColorsRGB(addressedEnumObj.areaColor, true, true);
        }
        shape.SetTitle(addressedEnumObj.descr);
        shape.SetCustomIcon(img.src == undefined ? img : img.src);
        this.shapeLayer.AddShape(shape);
        return shape;
    },

    calculateXOffSet : function (width){
        return (this.DEFAULT_BING_ICON_SIDE - width)/2;
    },

    calculateYOffSet : function (height){
        return (this.DEFAULT_BING_ICON_SIDE - height)/2;
    },

    addTextMarker : function(img, text, lon, lat, corLon, corLat, dist,
                             isIgnitionIcons,
                             htmlMarker,
                             showTooltip, regNumber,dotOnMap, showVehTooltipAllowed
                             ,drawArrowFromVehIconToRoad) {
        if (this.shapeLayer == undefined) {
            this.shapeLayer = new VEShapeLayer();
            this.map.AddShapeLayer(this.shapeLayer);
        }

        var shape = new VEShape(VEShapeType.Pushpin, new VELatLong(lat, lon));
        if (showTooltip) {
            shape.SetCustomInfoBox("<html><header><style>.customInfoBox-body{border-width: 0px;background-color: #feffdd;font-weight:600;font-size:11px;}</style></header>"+text+"</html>");
        }
        if (isIgnitionIcons && !dotOnMap || showVehTooltipAllowed) {
            shape.SetCustomIcon(htmlMarker);
        } else {
            if (img.src != undefined){
                var icon = new VECustomIconSpecification();
                icon.Image = img.src;
                icon.ImageOffset = new VEPixel(this.calculateXOffSet(img.width),this.calculateYOffSet(img.height));
                icon.TextContent=" ";
                shape.SetCustomIcon(icon);
            }else{
                shape.SetCustomIcon(img);
            }
        }
        this.shapeLayer.AddShape(shape);

        if (corLon != null && corLat != null) {
            if (!drawArrowFromVehIconToRoad) {
                var point = new VEShape(VEShapeType.Polyline, new VELatLong(corLat, corLon));
                if (dist != undefined) {
                    text = (regNumber != undefined ? regNumber : "") + " " + Math.round(dist * 10) / 10 + "m";
                }
                point.SetTitle(text);
                this.shapeLayer.AddShape(point);
                shape.point = point;
                point.objType = "corPin";
            } else {
                var point1 = new VELatLong(lat, lon);
                var point2 = new VELatLong(corLat, corLon);
                var points = this.generatePolylinePointsWithArrow([point1,point2]);
                var polyline = new VEShape(VEShapeType.Polyline,points);
                VEShapeStyle.prototype.stroke_dashstyle = "Dash";
                polyline.HideIcon();
                polyline.SetLineColor(new VEColor(255,0,0,1.0));
                this.shapeLayer.AddShape(polyline);
                
            }
        }
        return shape;
    },

    zoomToMaxExtentToPoint : function(lon, lat) {
        this.map.SetZoomLevel(19);
        this.map.SetCenter(new VELatLong(lat, lon));
    },


    addImgMarker : function(img, lon, lat) {
        if (this.shapeLayer == undefined) {
            this.shapeLayer = new VEShapeLayer();
//            this.shapeLayer.SetClusteringConfiguration(this.snailTrailCount > this.PINS_COUNT_FOR_CLUSTERING ? VEClusteringType.Grid : VEClusteringType.None);
            this.map.AddShapeLayer(this.shapeLayer);
        }
        var shape = new VEShape(VEShapeType.Pushpin, new VELatLong(lat, lon));
        shape.latlon = new VELatLong(lat, lon);
        shape.isGlowed = false;
        var imgUrl

        if (img.src != undefined){
            var icon = new VECustomIconSpecification();
            icon.Image = img.src;
            icon.ImageOffset = new VEPixel((this.DEFAULT_BING_ICON_SIDE - img.width)/2,(this.DEFAULT_BING_ICON_SIDE - img.height)/2);
            icon.TextContent=" ";
            shape.SetCustomIcon(icon);
            shape.logIndex = img.logIndex;
            shape.mapIndex = img.mapIndex;
            imgUrl = img.src;
        }else{
            shape.SetCustomIcon(img);
            imgUrl = img;
        }

        //shape.SetTitle(text);
        //shape.SetDescription(text);
        var curZIndex = shape.GetZIndex();
        var journeyEnd = imgUrl.indexOf("flag_end") != -1;
        var journeyStart = imgUrl.indexOf("flag_start") != -1;
        var driverBehavior = imgUrl.indexOf("snail_trail") != -1;
        shape.SetZIndex(curZIndex + (journeyEnd ? 5 : (journeyStart ? 3 : ( driverBehavior ? 2 : 0))));

        this.shapeLayer.AddShape(shape);
        return shape;
    },


    drawLineImgMarker : function (img,descr, startLon, startLat, endLon, endLat) {
        var shape = new VEShape(VEShapeType.Polyline, [new VELatLong(startLat, startLon),
            new VELatLong(endLat, endLon)]);

        //Set the line color
        var lineColor = new VEColor(255, 0, 0, 1.0);
        shape.SetLineColor(lineColor);

        //Set the line width
        shape.SetLineWidth(4);

        //Set the icon
        shape.SetCustomIcon(img.src);
        shape.SetTitle(descr);
        shape.SetIconAnchor(new VELatLong(endLat, endLon));

        //Set the info box
        /*  map.ClearInfoBoxStyles();
         shape.SetTitle("<h2>Custom Pin</h2>");
         shape.SetDescription(infobox);*/

        //Add the shape the the map
        this.shapeLayer.AddShape(shape);
        return shape;
    },

    addPolygonFromWkt : function(wktString) {
        var shape = null;
        try {

            shape = VirtualEarthWKT6.ShapeFromWKT(wktString);

        } catch(e) {
            alert(e);
        }
        if (shape != null) {
            if (this.shapeLayer == undefined) {
                this.shapeLayer = new VEShapeLayer();

                this.map.AddShapeLayer(this.shapeLayer);
            }

            shape.objType = "polygonWkt";
            this.shapeLayer.AddShape(shape);
        }
        return shape;
    },

    removeMarker : function(marker) {
        if (this.shapeLayer != undefined) {
//            var shapeList = this.shapeLayer.Annotations;
//            if (typeof(log) != 'undefined') log.debug("!!removeMarker start count = " + this.shapeLayer.GetShapeCount() + " annot count = " + shapeList.length);
//            for (var i = 0; i<this.shapeLayer.GetShapeCount(); i++){
//                if (shapeList[i] != null){
//                    if (typeof(log) != 'undefined') log.debug("!!removeMarker id = " + shapeList[i].id);
//                }
//            }
//            if (typeof(log) != 'undefined') log.debug("!!removeMarker finish");
//
            if (marker.point != undefined) {
                this.shapeLayer.DeleteShape(marker.point);
            }
            try {
                this.shapeLayer.DeleteShape(marker);
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
        this.findCallback = findCallback;
        this.onSearchComplete.map = this;
        this.results = this.map.Find("",
                searchText + countryCode,
                null,
                null,
                0,
                10,
                false,
                false,
                false,
                false,
                this.onSearchComplete);
    },

    onSearchComplete : function(layer, resultsArray, places, hasMore, veErrorMessage) {
        if (this.Callback.map.findCallback != undefined && places != null) {
            var filteredPlaces = [];
            for (var i = 0; i < places.length; i++) {
                var newPlace = new Object();
                newPlace.street = places[i].Name;
                newPlace.description = places[i].Description;
                newPlace.lat = places[i].LatLong.Latitude;
                newPlace.lon = places[i].LatLong.Longitude;
                filteredPlaces[filteredPlaces.length] = newPlace;
            }
            this.Callback.map.findCallback(filteredPlaces);
        }
    },

    calculateDirection : function(locations, addReportResult){
        var routeOptions = new VERouteOptions();
        //TODO: get units from server
        routeOptions.DistanceUnit = VERouteDistanceUnit.Mile;
        routeOptions.DrawRoute = false;
        routeOptions.SetBestMapView = false;
        routeOptions.RouteCallback  = function(route){
            route.Distance = Math.round(route.Distance * 100) / 100;
            addReportResult(route);
        };
        routeOptions.ShowDisambiguation = false;
        this.map.GetDirections(locations, routeOptions);
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
                isPinsSizeChanged = (this.map.zoomLevel <= ZOOM_LEVEL_FOR_SMALL_PINS && e.zoomLevel > ZOOM_LEVEL_FOR_SMALL_PINS) ||
                                    (this.map.zoomLevel > ZOOM_LEVEL_FOR_SMALL_PINS && e.zoomLevel <= ZOOM_LEVEL_FOR_SMALL_PINS);
                this.map.saveZoomLevel(e.zoomLevel);
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
     
   generatePolylinePointsWithArrow: function(points)
    {
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
        var lat1 = this.DegtoRad(origin.Latitude);
        var lon1 = this.DegtoRad(origin.Longitude);
        var centralAngle = arcLength /this.earthRadius;
        var lat2 = Math.asin( Math.sin(lat1)*Math.cos(centralAngle) + Math.cos(lat1)*Math.sin(centralAngle)*Math.cos(this.DegtoRad(brng)));
        var lon2 = lon1+Math.atan2(Math.sin(this.DegtoRad(brng))*Math.sin(centralAngle)*Math.cos(lat1),Math.cos(centralAngle)-Math.sin(lat1)*Math.sin(lat2));
        return new VELatLong(this.RadtoDeg(lat2),this.RadtoDeg(lon2));
    }
    ,
    calculateBearing: function(A,B)
    {
        var lat1 = this.DegtoRad(A.Latitude);
        var lon1 = A.Longitude;
        var lat2 = this.DegtoRad(B.Latitude);
        var lon2 = B.Longitude;
        var dLon = this.DegtoRad(lon2-lon1);
        var y = Math.sin(dLon) * Math.cos(lat2);
        var x = Math.cos(lat1)*Math.sin(lat2) - Math.sin(lat1)*Math.cos(lat2)*Math.cos(dLon);
        var brng = (this.RadtoDeg(Math.atan2(y, x))+360)%360;
        return brng;
    }
    ,

    initDrawControl: function(map, polygoneWkt, finished, pinColor, areaColor)
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
        this.drawControl.deletePolygon();
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
    CLASS_NAME : "RMS.RVirtualEarth6"
};