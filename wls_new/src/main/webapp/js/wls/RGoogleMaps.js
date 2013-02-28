if (typeof RMS == "undefined") {
    var RMS = new Object();
}

RMS.RGoogleMaps = function(div, zoomLevel, lat, lon, showBE) {
    this.initialize(div, zoomLevel, lat, lon, showBE);
};
RMS.RGoogleMaps.prototype = {

    markers : new Array(),
    markersArray : new Array(),
    flexSnailTrailEnhanced : false,
    gdir : null,
    registerOnShapeMouseOver : null,
    registerOnShapeMouseOut : null,
    subShapeLayerList : null,
    SHAPES_COUNT_IN_SUBSHAPELIST : 3,
    smallIconsAllowed : false,
    maxDefaultZoomLevel: 19,

    drawControl: null,
    //snailTrailCount : 0,

    initialize : function(div, zoomLevel, lat, lon, showBE) {
        var coords = null;
        if (lat != null && lon != null) {
            coords = new google.maps.LatLng(lat, lon);
        } else {
            coords = new google.maps.LatLng(51.5, 0);
        }

        var myOptions = {
            zoom: 4,
            center: coords,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        this.map = new google.maps.Map(document.getElementById(div), myOptions);



        //this.map.setCenter(coords, zoomLevel);
       // this.map.enableScrollWheelZoom();
       // this.map.setUIToDefault();
    },

    zoomTo : function(zoomLevel) {
        this.map.setZoom(zoomLevel);
    },

    getLocationObject : function (lat, lon){
        return new google.maps.LatLng(lat, lon);
    },

    showTraffic : function(){
     },

     hideTraffic : function() {
     },

    setMaxDefaultZoomLevel : function(){},

    zoomToCustomExtent : function(lon, lat, zoomLevel) {
        var coords = new google.maps.LatLng(lat, lon);
        this.map.setCenter(coords, zoomLevel);
    },

    _getShapeByLogIndex : function(index){

      var shapeList = this.markers;
      for (var i=0; i < shapeList.length; i++){
           if (shapeList[i].logIndex == index) return shapeList[i];
       }
       return null;
    },

    fillSubShapeLayerList : function(){

        if (this.markers != undefined){
            this.subShapeLayerList = new Array();
            var shapeList = this.markers;
            var curShapeLayer = new Array();
            for (var i=0; i < shapeList.length; i++){
                if (i!=0 && i%this.SHAPES_COUNT_IN_SUBSHAPELIST == 0){
                    this.subShapeLayerList[this.subShapeLayerList.length] = curShapeLayer;
                    curShapeLayer = new Array();
                }
                //curShapeLayer[curShapeLayer.length] = shapeList[i];
                curShapeLayer[curShapeLayer.length] = this._getShapeByLogIndex(i);
            }
            if (curShapeLayer.length > 0){
                curShapeLayer = new Array();
                for (var j=shapeList.length-this.SHAPES_COUNT_IN_SUBSHAPELIST; j<shapeList.length; j++){
                   // curShapeLayer[curShapeLayer.length] = shapeList[j];
                    curShapeLayer[curShapeLayer.length] = this._getShapeByLogIndex(j);
                }
                this.subShapeLayerList[this.subShapeLayerList.length] = curShapeLayer;
            }
        }
    },

    zoomToSubShapeList :function(shapeIndex){

        if (this.markers != undefined){
            if (this.subShapeLayerList == undefined){
                this.fillSubShapeLayerList();
            }
            var curShapeLayerIndex = parseInt(shapeIndex / this.SHAPES_COUNT_IN_SUBSHAPELIST);
            var bounds = this._getBoundingRectangleByShapeList(this.subShapeLayerList[curShapeLayerIndex]);
            this.map.setCenter(bounds.getCenter(), this.map.getBoundsZoomLevel(bounds));
        }
    },

    _getBoundingRectangleByShapeList: function(shapeList){

        var bound = new google.maps.LatLngBounds();
        for (var i = 0; i < shapeList.length; i++) {
            if (shapeList[i] != null){
                var latlng = new google.maps.LatLng(shapeList[i].getLatLng().lat(), shapeList[i].getLatLng().lng());
                bound.extend(latlng);
            }
        }
        return bound;
    },

    zoomOut : function(){
        this.map.zoomOut();
    },

    hideMapCtrls : function (){
        this.map.hideControls();
        document.getElementById('logocontrol').style.display = "none";
        getElementsByClassName('terms-of-use-link')[0].style.display = "none";
    },

    showMapCtrls : function (){
        this.map.showControls();
        document.getElementById('logocontrol').style.display = "";
        getElementsByClassName('terms-of-use-link')[0].style.display = "";
   },

    correctMap : function(exceptObjTypes) {

        this.map.fitBounds(this.getMarkersBoundingBox(exceptObjTypes));

       // this.map.setCenter(this.getMarkersBoundingBox(exceptObjTypes).getCenter(), this.map.getBoundsZoomLevel(this.getMarkersBoundingBox(exceptObjTypes)));
    },

    getCoordsFromPixel : function(e) {
        //TODO
        var x = e.mapX;
        var y = e.mapY;
        var pixel = new VEPixel(x, y);
        var LL = this.map.PixelToLatLong(pixel);
        var lonlat = {};
        lonlat.lon = LL.Longitude;
        lonlat.lat = LL.Latitude;
        return lonlat;
    },

    swapShapeIconToGlowedByIndex : function (curIndex){
        this.setAllShapesIconColorToDefault();
        if (curIndex != -1){
            this.changeShapeIconImgByIndex(curIndex,"blue","green");
        }
    },

    changeShapeIconImgByIndex : function (index, fromColor, toColor){
        //var shape = this.markers[index];
        var shape = this._getShapeByLogIndex(index);
        if (shape != null && this.flexSnailTrailEnhanced){
            var icon = shape.getIcon().image.replace(fromColor,toColor);
            shape.setImage(icon);
            shape.isGlowed = toColor == "green";
        }
    },

    setAllShapesIconColorToDefault : function(){
        var shapeList = this.markers;
        for (var i = 0; i<shapeList.length; i++){
//            if (shapeList[i] != null && shapeList[i].Rs.__src__.indexOf("green")!=-1){
            if (shapeList[i] != null && shapeList[i].isGlowed){
//                this.changeShapeIconImgByIndex(i,"green","blue");
                 this.changeShapeIconImgByIndex(shapeList[i].logIndex,"green","blue");
            }
        }
    },

    registerMapClick : function(listener) {
        google.maps.event.addListener(this.map, "click", function(event)  {
            var latlng = event.latLng;
            listener(latlng.lng(), latlng.lat());
        });
    },

    registerClickOnShapeIndex : function(listener){
        var thiz = this;
        var f = function(e){
            thiz.setAllShapesIconColorToDefault();
            if (this.prevClickedShape != e){
                thiz.changeShapeIconImgByIndex(e.logIndex,"blue","green");
            }
            this.prevClickedShape = e;
            listener(e.logIndex);
        };
        google.maps.event.addListener(this.map,"click",f);
    },
    registerOnShapeMouseEvents : function(mouseOverListener,mouseOutListener,rightClickListener){
        var over = function(){
            if (this!=null){
                mouseOverListener(this);
            }
        };
        var out = function(){
            if (this!=null){
                mouseOutListener(this);
            }
        };
        
        this.registerOnShapeMouseOver = over;
        this.registerOnShapeMouseOut = out;
    },

    drawCircleAroundPoi : function (pin,poiAreaColoured){
          var polyline = new GPolyline(this.getCircle(pin.getLatLng(), pin.radius),"#FF0000", 2, 1);
          this.map.addOverlay(polyline);
          //pin.withCircle = true;
          polyline.pos = this.markers.length;
          polyline.objType = "circle";
          this.markers.push(polyline);
    },

    getCircle : function(loc, radius) {
          var R = 6371; // earth's mean radius in km
          var lat = (loc.lat() * Math.PI) / 180; //rad
          var lon = (loc.lng() * Math.PI) / 180; //rad
          var d = parseFloat(radius) / R;  // d = angular distance covered on earth's surface
          var locs = new Array();
          for (var x = 0; x <= 360; x++) {
              //var p2 = new google.maps.LatLng(0, 0);
              brng = x * Math.PI / 180; //rad
              var Latitude = Math.asin(Math.sin(lat) * Math.cos(d) + Math.cos(lat) * Math.sin(d) * Math.cos(brng));
              var Longitude = ((lon + Math.atan2(Math.sin(brng) * Math.sin(d) * Math.cos(lat), Math.cos(d) - Math.sin(lat) * Math.sin(Latitude))) * 180) / Math.PI;
              Latitude = (Latitude * 180) / Math.PI;
              locs.push(new google.maps.LatLng(Latitude, Longitude));
          }
          return locs;
    },
   
    removeAllCircles : function(){
        var shapeList = this.markers;
        for (var i = 0; i<shapeList.length; i++){
            if (shapeList[i] != null && shapeList[i].objType == "circle"){
                  this.removeMarker(shapeList[i]);
            }
//            else if(shapeList[i] != null && shapeList[i].objType == "poi"){
//                 shapeList[i].withCircle = false;
//            }
        }
    },

    hideAllPolygones :function(){},

    showPolygone : function(shape){},

    addTextMarkerByObj: function (img,addressedEnumObj){
        if (addressedEnumObj.lat == undefined && addressedEnumObj.lon == undefined && addressedEnumObj.polygonWkt!=undefined) return;
        var blueIcon = new google.maps.Icon();
//        var image_1 = new Image();
//        image_1.src = img;
        blueIcon.image = img.src == undefined ? img : img.src;
        blueIcon.shadow = "";
        if (img.width != undefined && img.height != undefined){
            blueIcon.iconSize = new GSize(img.width, img.height);
        }    
        blueIcon.shadowSize = new GSize(0,0);
        blueIcon.infoWindowAnchor = new GPoint(5,5);
        blueIcon.iconAnchor = new GPoint(5,5);

        var latlng = new google.maps.LatLng(addressedEnumObj.lat, addressedEnumObj.lon);
        var marker = new GMarker(latlng, {
            title:"Click on the marker to get info",
            icon:blueIcon
        });
        marker.id = addressedEnumObj.id;
        marker.objType = addressedEnumObj.typeDescr.toLowerCase();
        if (addressedEnumObj.radius != undefined){
            marker.radius = addressedEnumObj.radius / 1000;
            //marker.withCircle = false;
            if (this.registerOnShapeMouseOver != null && this.registerOnShapeMouseOut != null){
                google.maps.event.addListener(marker,"mouseover",this.registerOnShapeMouseOver);
                google.maps.event.addListener(marker,"mouseout",this.registerOnShapeMouseOut);
            }
        }

        marker.bindInfoWindowHtml(addressedEnumObj.descr);
        this.map.addOverlay(marker);
        marker.pos = this.markers.length;
        this.markers.push(marker);
        return marker;
    },

    
    addTextMarker : function(img, text, lon, lat, corLon, corLat, dist, isIgnitionIcons,
                             htmlMarker, regNumber) {

       // alert(img.src);
        //var blueIcon = new google.maps.Icon(img.src);
//        var image_1 = new Image();
//        image_1.src = img;
       // blueIcon.image = img.src == undefined ? img : img.src;;
       // alert("Here");
       // blueIcon.shadow = "";
//        blueIcon.printImage = img;
//        blueIcon.mozPrintImage = img;
//        blueIcon.printShadow = "";
//        blueIcon.transparent = img;
      //  if (img.width != undefined && img.height != undefined){
           // blueIcon.iconSize = new google.maps.Size(img.width, img.height);
       // }

       // blueIcon.shadowSize = new google.maps.Size(0,0);
       // blueIcon.infoWindowAnchor = new google.maps.Point(5,5);
       // blueIcon.iconAnchor = new google.maps.Point(5,5);
       // var latLng = new google.maps.LatLng(-33.890542, 151.274856);
        var latlng = new google.maps.LatLng(lat, lon);

        var marker = new google.maps.Marker({
            position: latlng,
            map: this.map,
            icon:img.src,
            title:"Click on the marker to get info"
        });
        var infoWindow = new google.maps.InfoWindow({
            content: text
        });
        this.markersArray.push(infoWindow);
        google.maps.event.addListener(marker, 'click', function () {
            infoWindow.open(this.map, marker);
        });


        marker.setMap(this.map);


        if (corLon != null && corLat != null) {
//            var point = new google.maps.Icon(G_DEFAULT_ICON);
            if (dist != undefined) {
                text = text + ", " + Math.round(dist * 10) / 10 + "m";
            }
            //point.title(text);
            var corlatlng = new google.maps.LatLng(corLat, corLon);
            var cormarker = new google.maps.Marker({
                position: corlatlng,
                title:"Click on the marker to get info",
                map: this.map

            });
            var corInfoWindow = new google.maps.InfoWindow({
                content: text
            });
            this.markersArray.push(corInfoWindow);
            google.maps.event.addListener(cormarker, 'click', function () {
                corInfoWindow.open(this.map, cormarker);
            });
            cormarker.setMap(this.map);
//            marker.point = point;
            cormarker.objType = "corPin";
        }
//        marker.pos = this.markers.length;
//        this.markers.push(marker);
        marker.pos = this.markers.length;
        this.markers.push(marker);
        return marker;
    },




    getMarkersBoundingBox:function(exceptObjTypes) {

        var bound = new google.maps.LatLngBounds();
        for (var i = 0; i < this.markers.length; i++) {
            if (this.markers[i] != null){

                if (exceptObjTypes!=undefined && arrayContains(exceptObjTypes,this.markers[i].objType)) continue;
                //if  (exceptObjType!=undefined && this.markers[i].objType == exceptObjType) continue;
                var latlng = this.markers[i].getPosition();

                bound.extend(latlng);
            }
        }
        return bound;
    },

    zoomToMaxExtentToPoint : function(lon, lat) {
        this.map.setZoom(19);
        this.map.setCenter(new google.maps.LatLng(lat, lon));
    },

    addImgMarker : function(img, lon, lat) {
       // var blueIcon = new google.maps.Icon();
//        var image_1 = new Image();
//        image_1.src = img;
       // blueIcon.image = img.src == undefined ? img : img.src;

        //blueIcon.iconSize = new GSize(image_1.width, image_1.height);
       // blueIcon.shadowSize = new GSize(0,0);
       // blueIcon.infoWindowAnchor = new GPoint(5,5);
      //  blueIcon.iconAnchor = new GPoint(5,5);

        var latlng = new google.maps.LatLng(lat, lon);

        var marker = new google.maps.Marker({
            position: latlng,
            map: this.map,
            icon:img.src
        });
        marker.pos = this.markers.length;
        if (img.logIndex!=undefined){
            marker.logIndex = img.logIndex;
        }
        var curZIndex = 1000000;
        var journeyEnd = img.src.indexOf("flag_end") != -1;
        var journeyStart = img.src.indexOf("flag_start") != -1;
        var driverBehavior = img.src.indexOf("snail_trail") != -1;
        marker.setZIndex(curZIndex + (journeyEnd ? 5 : (journeyStart ? 3 : ( driverBehavior ? 2 : 0))));

        //marker.isGlowed = false;
        this.markers.push(marker);
        return marker;
    },

    drawLineImgMarker : function (imgUrl,descr, startLon, startLat, endLon, endLat) {
        var polilyneCoordinates = [
            new google.maps.LatLng(startLat, startLon),
            new google.maps.LatLng(endLat, endLon)

        ];

        var line = new google.maps.Polyline({
            path: polilyneCoordinates,
            strokeColor: "#FF0000",
            strokeOpacity: 1.0,
            strokeWeight: 3
        });

//        var blueIcon = new google.maps.Icon();
////        var image_1 = new Image();
////        image_1.src = img;
//        blueIcon.image = imgUrl;
//        blueIcon.shadow = "";
////        blueIcon.iconSize = new GSize(image_1.width, image_1.height);
//        blueIcon.shadowSize = new GSize(0,0);
//        blueIcon.infoWindowAnchor = new GPoint(5,5);
//        blueIcon.iconAnchor = new GPoint(5,5);

        var latlng = new google.maps.LatLng(endLat, endLon);

        var icon = "http://" + document.location.hostname + ":" + document.location.port + "/"
            +imgUrl;

        var marker = new google.maps.Marker({
            position: latlng,
            map: this.map,
            icon: icon
        });

        marker.setMap(this.map);
        line.setMap(this.map);

        marker.pos = this.markers.length;
        this.markers.push(marker);

        return [marker, line];
    },

    removeMarker : function(marker) {
        if (marker.length != undefined && marker.length > 0) {
            for (var i = 0; i < marker.length; i++) {

                this._clinUpMarkers(marker[i]);

                if (marker[i].point!=undefined){

                    this.map.removeOverlay(marker[i].point);
                }
                marker[i].setMap(null);
            }
        } else {

            this._clinUpMarkers(marker);

            if (marker.point!=undefined){
                this.map.removeOverlay(marker.point);
            }
            marker.setMap(null);
        }
    },

    cleanUp : function(){
        while(this.markers[0]){
            this.markers.pop().setMap(null);
        }
    },

    _clinUpMarkers : function(marker){

        if (this.markers[marker.pos] != null) {

            var resMarkers = new Array();

            for (var i=0; i<this.markers.length; i++){

                if (this.markers[i].pos != marker.pos){
                    resMarkers[resMarkers.length] = this.markers[i];

                    resMarkers[resMarkers.length-1].pos = resMarkers.length-1;
                }
            }
            //this.markers[marker.pos] = null;
            this.markers = resMarkers;
        }
    },

    /**
     * Function searches places by search string
     * @param searchText - search string
     * @param findCallback - function that will be performed at the end of searching.
     */
    geocode : function(searchText, findCallback) {

        var geocoder =  new google.maps.Geocoder();

        //  geocoder.setBaseCountryCode("gb,uk");
        this.findCallback = findCallback;
        var thiz = this;
        var countryCode;
        if (location.toString().indexOf(".de") != -1) {
            countryCode = "DE";
        } else {
            countryCode = "UK";
        }
        geocoder.geocode({ 'address': searchText, 'region': countryCode},

                function(response, status) {
                    thiz.onSearchComplete(response, status);
                }
                );
    },

    onSearchComplete : function(response, status) {

        if (this.findCallback != undefined) {
            var filteredPlaces = [];
            if (!response || status != google.maps.GeocoderStatus.OK) {
                this.findCallback(filteredPlaces);
            } else {

                if (response.length != undefined && response.length > 0) {

                    for (var i = 0; i < response.length; i++) {
                        var place = response[i];
                        var parcedPlace = this.parcePlace(response[i]);
                        if (this.isLatLonInUk(parcedPlace.lat, parcedPlace.lon)) {
                            filteredPlaces[filteredPlaces.length] = parcedPlace;
                        }
                    }
                }
            }
            this.findCallback(filteredPlaces);
        }
    },

    addPolygonFromWkt : function(strWKT) {
        var priv = {
            trimSpaces: function(str) {
                // Trim beginning spaces
                while (priv.startsWith(str, " ")) {
                    str = str.substring(1);
                }
                // Trim ending spaces
                while (priv.endsWith(str, " ")) {
                    str = str.substring(0, str.length - 1);
                }
                return str;
            },
            startsWith: function(str, startstr) {
                return str.substring(0, startstr.length) == startstr;
            },
            endsWith: function(str, endstr) {
                return str.substring(str.length - endstr.length) == endstr;
            }
        };
        var wktPoints = strWKT.substring(9, strWKT.length - 2);
        wktPoints = wktPoints.split(",");
        this.path = new  google.maps.MVCArray();
        // Convert the WKT Points to VELatLong locations
        var shapePoints = new Array();
        for (var i = 0; i < wktPoints.length; i++) {
            // Split the "Longitude Latitude" apart
            var loc = priv.trimSpaces(wktPoints[i]).split(" ");
            // Create VELatLong location

            this.drawControl.path.insertAt(this.path.length, new google.maps.LatLng(parseFloat(loc[1]), parseFloat(loc[0])));
        }




    }
    ,

    parcePlace : function(place) {
        var newPlace = new Object();

        newPlace.street = place.formatted_address;

        newPlace.description = place.address_components[2].short_name;

        var point = place.geometry.location;

        newPlace.lat = point.lat();
        newPlace.lon = point.lng();
        return newPlace;
    },

    isLatLonInUk : function(lat, lon) {
        return (lat < 63.25623799967359 && lat > 48.0258059995958 && lon > -10.87629 && lon < 3.648734);
    },

    calculateDirection : function(locations, addReportResult) {
        this.gdir = new GDirections();
        this.gdir.loadFromWaypoints(locations);

        var thiz = this;

        google.maps.event.addListener(this.gdir, "load", function(){
            addReportResult({Distance:thiz.gdir.getDistance().html});
        });

    },
    initDrawControl: function(map,polygoneWkt, finished, pinColor)
    {

        this.drawControl = new RMS.GoogleMapsPolygonControl(map, this.onFinishDrawing, pinColor);
        this.polyWkt = polygoneWkt;
        this.finishedCallback = finished;
        if (typeof(log) != 'undefined') log.debug("initDrawControl polyWkt = " + this.polyWkt);
        if (this.polyWkt != ""){

            this.addPolygonFromWkt(this.polyWkt);
            this.drawControl.showPolygonPushpins();
            if (this.drawControl.path.length > 2){
                this.drawControl.disableDrawing();

                this.correctMap();
            }

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
    CLASS_NAME : "RMS.RGoogleMaps"
};