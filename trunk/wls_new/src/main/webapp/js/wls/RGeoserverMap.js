var mapHost = location.hostname == "localhost" ? "92.63.132.73" : location.hostname;

OpenLayers.RGeoserverMap = OpenLayers.Class(OpenLayers.Map, {

    countiesRegionLayer : null,
    format : 'image/png',
    bounds : new OpenLayers.Bounds(-10.87629, 48.0258059995958, 3.648734, 63.25623799967359),
    zoomControl : null,
    subShapeLayerList : null,
    //Resolutions added. Resolution is the zoom level. We are needed to pass them for geowebcache.

    options : null,

    markers : null,
    flexSnailTrailEnhanced : false,
    correctedPointLayer : null,
    smallIconsAllowed : null,
    //snailTrailCount : 0,

    initialize: function (div) {

        this.options = {
            controls: [],
            maxResolution: 0.05949387500030384,
            maxExtent: new OpenLayers.Bounds(-10.87629, 48.0258059995958, 3.648734, 63.25623799967359),
            resolutions:[0.05949387500030384,
                0.02974693750015192,
                0.01487346875007596,
                0.00743673437503798,
                0.00371836718751899,
                0.001859183593759495,
                9.295917968797475E-4,
                4.6479589843987376E-4,
                2.3239794921993688E-4,
                1.1619897460996844E-4,
                5.809948730498422E-5,
                2.904974365249211E-5,
                1.4524871826246055E-5,
                7.2624359131230276E-6,
                3.6312179565615138E-6],
            projection: new OpenLayers.Projection('EPSG:4326'),
            numZoomLevels: 15,
            buffer: 0
        };

        OpenLayers.Map.prototype.initialize.apply(this, [div, this.options]);
        document.getElementById(div).style.backgroundColor = "#A8C7C7";
        this.createLayers();
        this.createControls();

        this.zoomToExtent(this.bounds);

    },

    getLocationObject : function (lat, lon){
    },

    zoomOut : function(){
    },

    showTraffic : function(){
     },

     hideTraffic : function() {
     },

    createControls:function() {
        this.panZoomBar = new OpenLayers.Control.PanZoomBar({
            position: new OpenLayers.Pixel(2, 15)
        });
        this.addControl(this.panZoomBar);
        this.centerAt = new OpenLayers.Control.CenterAt();
        this.addControl(this.centerAt);
        this.navigation = new OpenLayers.Control.RNavigation();
        this.addControl(this.navigation);
        var scaleDive = document.getElementById('scale');
        if (scaleDive != null || scaleDive != undefined){
            this.scaleControl = new OpenLayers.Control.Scale(scaleDive);
            this.addControl(this.scaleControl);
        }
        //this.addControl(new OpenLayers.Control.Scale($('scale')));
        var locationDiv = document.getElementById('location');
        if (locationDiv != null || locationDiv != undefined){
            this.mousePos = new OpenLayers.Control.MousePosition({element: locationDiv});
            this.addControl(this.mousePos);
        }
        //this.addControl(new OpenLayers.Control.MousePosition({element: $('location')}));

        this.addControl(new OpenLayers.Control.LayerSwitcher());
        this.zoomControl = new OpenLayers.Control.RZoomControl({out:false});
        this.addControl(this.zoomControl);
    },

    createLayers:function() {
        var baseLayer = new OpenLayers.Layer.WMS(
                "Base Map", "http://92.63.132.73:8081/geoserver/gwc/service/wms",
        {
            layers: 'baselayer',
            format: "image/png"
        },
        {buffer: 0}
                );

        this.addLayer(baseLayer);
        this.setBaseLayer(baseLayer);

        var capitalsNames = new OpenLayers.Layer.WMS(
                "Capitals Names", "http://92.63.132.73:8081/geoserver/wms",
        {
            height: 1024,
            width: 1024,
            layers: 'Navteq:Capital_Point,Navteq:Top_Towns_point,Navteq:Major_Towns_point,' +
                    'Navteq:Named_Places_point,Navteq:Small_Places_point,Navteq:Villages_point',
            format: this.format,
            transparent:true
        },
        {
            buffer: 0,
            isBaseLayer:false,
            singleTile: true
        }
                );

        this.addLayer(capitalsNames);

        this.correctedPointLayer = new OpenLayers.Layer.Vector("Result Layer");
        this.addLayer(this.correctedPointLayer);

        this.markers = new OpenLayers.Layer.Markers("Markers");
        this.addLayer(this.markers);
    },

    getMarkersLayer : function() {
        return this.markers;
    },

    imageFileName: 'vehicle_policecar.png',

    addTextMarker : function(img, text, lon, lat, corLon, corLat, dist, isIgnitionIcons, htmlMarker,
                             showTooltip, showTooltip, regNumber) {
        var marker = this.createMarker(img, lon, lat, text, false);
        this.markers.addMarker(marker);

        if (corLon != null && corLat != null) {
            if (dist != undefined) {
                text = regNumber + "<div>" + Math.round(dist) + "m</div>";
            }
            var imgUrl = "http://" + document.location.hostname + ":" + document.location.port + "/img/locationIcon.gif";
            var image = new Image();
            image.src = imgUrl;
            image.width = 20;
            image.height = 25;
            var point = this.createMarker(image, corLon, corLat, text, true);
            this.markers.addMarker(point);
            marker.point = point;
        }

        return marker;
    },

    createMarker:function(img, lon, lat, text, needOffset){
        var data = this.prepareIcon(img, text, needOffset);
        var lonlat = new OpenLayers.LonLat(lon, lat);
        var feature = new OpenLayers.Feature(this.markers, lonlat, data);
        var marker = feature.createMarker();
        marker.feature = feature;
        marker.text = text;
        marker.events.register("mouseover", marker, this.onPointOver);
        marker.events.register("mouseout", marker, this.onPointOut);
        return marker;
    },

    /**
     * Mouse over callback
     * @param evt
     */
    onPointOver:function(evt) {
        if (this.popup == null) {
            var popup = this.feature.createPopup(false);
            popup.setBorder("2px solid #CCCCCC");
            if (this.text != null) {
                popup.setContentHTML(this.text);
            }
            popup.autoSize = true;
            this.popup = popup;
            this.map.addPopup(popup);
        } else {
            this.popup.toggle();
        }
        if (this.pointMouseOverHandler != undefined) {
            this.pointMouseOverHandler();
        }
    },

    /**
     * Mouseout callback.
     */
    onPointOut : function() {
        if (this.popup != null) {
            this.popup.hide();
        }
    },                      

    /**
     * Private function. Prepares point icon parameters.
     */
    prepareIcon : function(newImg, text, needOffset) {
        var data = {};
//        var newImg = new Image();
//        newImg.src = img;
        var height = newImg.height;
        var width = newImg.width;
        var offset = null;
        if (needOffset){
            offset = new OpenLayers.Pixel(-(width/2), -height+5);
        }
        data.icon = new OpenLayers.Icon(newImg.src, new OpenLayers.Size(width, height), offset);
        /*data.popupSize = new OpenLayers.Size(250, 100);*/
        data.autoSize = true;
        return data;
    },


    addImgMarker : function(img, lon, lat) {
        var size = new OpenLayers.Size(18, 18);
        var offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
        var icon = new OpenLayers.Icon(img.src == undefined ? img : img.src, size, offset);
        var marker = new OpenLayers.Marker(new OpenLayers.LonLat(lon, lat), icon);
        this.markers.addMarker(marker);

        return marker;
    },

    setZoomEnabled : function(isEnabled){
        if (this.zoomControl != null){
            if (isEnabled){
                this.zoomControl.activate();
            } else {
                this.zoomControl.deactivate();
            }
        }
    },

    registerMapClick : function(listener){
        var thiz = this;
        this.events.register("click",
                            this,
                            function(e){
                                var lonlat = thiz.getCoordsFromPixel(e);
                                listener(lonlat.lon, lonlat.lat);
                            });
    },

    registerClickOnShapeIndex : function(listener){
    },

    cleanUp : function(){
        this.markers.clearMarkers();
    },

    swapShapeIconToGlowedByIndex : function (curIndex){
    },

    changeShapeIconImgByIndex : function (index, fromColor, toColor){
    },

    setAllShapesIconColorToDefault : function(){
    },

    registerOnShapeMouseEvents : function(listener1,listener2,listener3){},

    drawCircleAroundPoi : function (pin,poiAreaColoured){},

    getCircle : function(loc, radius) {},

    removeAllCircles : function(){},

    hideAllPolygones :function(){},

    showPolygone : function(shape){},

    hideMapCtrls : function (){ },

    showMapCtrls : function (){ },

    fillSubShapeLayerList : function(){ },

    zoomToSubShapeList :function(shapeIndex){ },

    addTextMarkerByObj: function (img,addressedEnumObj){
        var marker = this.addTextMarker(img, addressedEnumObj.descr, addressedEnumObj.lon, addressedEnumObj.lat);
        marker.id = addressedEnumObj.id;
        marker.objType = addressedEnumObj.typeDescr.toLowerCase();
        return marker;
    },

    getCoordsFromPixel : function(e){
        return this.getLonLatFromPixel(e.xy);
    },

    removeMarker : function(marker) {
        if (marker.point != undefined) {
            //this.correctedPointLayer.removeFeatures(marker.point);
            this.markers.removeMarker(marker.point);
        }
        this.markers.removeMarker(marker);
    },

    setMaxDefaultZoomLevel : function(){},

    correctMap : function(exceptObjTypes) {
        var bounds = this.markers.getDataExtent();
        if (bounds != null){
            bounds.extend(this.correctedPointLayer.getDataExtent());
            this.zoomToExtent(bounds);
        }    
    },

    zoomToCustomExtent : function(lon, lat, zoomLevel) {
        this.setCenter(new OpenLayers.LonLat(lon, lat), zoomLevel, false, true);
    },

    zoomToMaxExtentToPoint : function(lon, lat){

         this.setCenter(new OpenLayers.LonLat(lon, lat), 14, false, true);
    }
});