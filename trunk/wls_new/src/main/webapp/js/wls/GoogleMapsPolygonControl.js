if (typeof RMS == "undefined") {
    var RMS = new Object();
}

RMS.GoogleMapsPolygonControl = function(map, onEndDrawingCallback, pinColor) {
    this.initialize(map, onEndDrawingCallback, pinColor);

};
RMS.GoogleMapsPolygonControl.prototype = {

    map : null,

    mainMap: null,

    poly : null,

    tempShape : null,

    tempPoints : null,

    polygonPoints : new Array(),

    shapeLayer : null,

    polygonShape : null,



    callback : null,

    pinColor : null,

    markers : [],

    path : null,

    mapPolygon : null,


    initialize : function(map, onEndDrawingCallback, pinColor) {

        if (map == null) {
            return null;
        }
        this.map = map.map;
        this.mainMap = map;

        this.mapPolygon = new google.maps.Polygon({map : this.map,
            strokeColor   : '#ff0000',
            strokeOpacity : 0.6,
            strokeWeight  : 4
        });
        this.path = new  google.maps.MVCArray();
        var paths = new google.maps.MVCArray([this.path]);

        try {
            this.mapPolygon.setPaths(paths);
        }  catch (e) {
            if (typeof(log) != 'undefined') log.debug("set path: threw the error - " + e);
        }


       // google.maps.event.addListener( this.map, 'click', this.addPointToPoly);

//        this.polygonPoints = new Array();


        this.callback = onEndDrawingCallback;
        this.pinColor = pinColor;

    },

    addPointToPoly : function(event) {

        this.path.insertAt(this.path.length, event.latLng);

        var marker = new google.maps.Marker({
            position: event.latLng,
            map: map,
            draggable: true
        });
        this.markers.push(marker);
        marker.setTitle("#" + path.length);

        google.maps.event.addListener(marker, 'click', function() {
                marker.setMap(null);
                for (var i = 0, I = this.markers.length; i < I && this.markers[i] != marker; ++i);
                this.markers.splice(i, 1);
                this.path.removeAt(i);
            }
        );

        google.maps.event.addListener(marker, 'dragend', function() {
                for (var i = 0, I =this. markers.length; i < I && this.markers[i] != marker; ++i);
                this.path.setAt(i, marker.getPosition());
            }
        );
    },

    deletePointFromPoly : function (lon, lat, shapeTitle) {
        this.polygonPoints = this.polygonShape.GetPoints();
        if (this.polygonPoints.length < 5) {
            alert("You cannot delete any point. AOI should have at least 3 point");
        } else {
            var pointIndex = -1;
            for (var i = 0; i < this.polygonPoints.length - 1; i++) {
                if (shapeTitle == "Point " + i) {
                    pointIndex = i;
                }
            }
            if (pointIndex > -1) {
                this.polygonPoints.splice(pointIndex, 1);
                this.polygonShape.SetPoints(this.polygonPoints);
                this.showPolygonPushpins();
            }
        }
        return this.getPolygonCoordsWKT(this.polygonPoints);
    },

    showPolygonPushpins : function() {

        for (var i = 0; i < this.mainMap.markers.length; i++) {
            this.mainMap.markers[i].setMap(null);
        }

        this.mainMap.markers = new Array();

        var points = this.mapPolygon.getPath();
        for (i = 0; i < points.length - 1; i++) {
            var marker = new google.maps.Marker({
                position: points.getAt(i),
                map: this.map,
                icon: "http://" + document.location.hostname + ":" + document.location.port + "/" + "img/locationIcon.gif",
                title: "Point " + i
            });

            marker.setMap(this.map);
            this.mainMap.markers.push(marker);

        }
    },

    enableDrawing : function() {
        var thiz = this;
        this.clickCallback = function(e) {
            thiz.drawPolyMouseClick(e);
        };
        google.maps.event.addListener(this.map, 'click', this.clickCallback);
        this.rightclickcallback = function(e) {
            thiz.endDraw(e);
        };

        google.maps.event.addListener(this.map, 'rightclick', this.rightclickcallback);
        this.moveCallback = function(e) {
            thiz.mouseMove(e);
        };
        google.maps.event.addListener(this.map, 'mousemove', this.moveCallback);


    },

    disableDrawing:function() {
        this.mapPolygon.stopEdit();
        google.maps.event.clearListeners(this.map, 'click');
        google.maps.event.clearListeners(this.map, 'rightclick');
        google.maps.event.clearListeners(this.map, 'mousemove');
    },

    deletePolygon : function(){
        this.polygonPoints = new Array();
        this.path.clear();


        this.mainMap.markers = new Array();

    },

    mouseMove : function(e) {
        var x = e.mapX;
        var y = e.mapY;
        var pixel = new VEPixel(x, y);
        var LL = this.map.PixelToLatLong(pixel);
        this.tempPoints = this.polygonPoints.slice(0, this.polygonPoints.length);

        this.tempPoints.push(LL);

        try {
            this.shapeLayer.DeleteShape(this.tempShape);
        }
        catch (err) {
        }

        if (this.tempPoints.length == 2) {
            this.tempShape = new VEShape(VEShapeType.Polyline, this.tempPoints);
            this.tempShape.HideIcon();
            this.shapeLayer.AddShape(this.tempShape);
        }

        if (this.tempPoints.length > 2)
        {
            this.tempShape = new VEShape(VEShapeType.Polygon, this.tempPoints);
            this.tempShape.HideIcon();
            this.shapeLayer.AddShape(this.tempShape);
        }
    }
    ,

    drawPolyMouseClick : function(e) {



        this.path.insertAt(this.path.length, e.latLng);
        this.polygonPoints.push(e.latLng);

        try {
            this.mapPolygon.runEdit(false);
        }  catch (e) {
            if (typeof(log) != 'undefined') log.debug("polygon draw: threw the error - " + e);
        }



    },

    endDraw : function(e) {
        if(this.polygonPoints.length > 2) {
            try {
                this.disableDrawing();

            } catch (err) {
                if (typeof(log) != 'undefined') log.error("polygon disable draw: threw the error - " + e);
            }


            //this.polygonShape.SetDescription("<input type='button' value='Delete' id='deletePolygon'>");
           // this.polygonShape.SetCustomIcon("img/markers/marker_" + this.pinColor +  ".png");


            /*var deleteButt = document.getElementById('deletePolygon');
             var thiz = this;
             deleteButt.onclick = function(){
             thiz.deletePolygon();
             };*/
            if (this.mainMap.onFinishDrawing != null) {
                this.mainMap.onFinishDrawing(this.getPolygonCoordsWKT(this.polygonPoints));
            }
        }
    }
    ,
    setPinColor : function(pinColor) {
        this.pinColor = pinColor;
        this.shapeLayer.DeleteShape(this.polygonShape);
        this.polygonShape = new VEShape(VEShapeType.Polygon, this.polygonPoints);
        this.polygonShape.SetCustomIcon("img/markers/marker_" + this.pinColor +  ".png");
        this.shapeLayer.AddShape(this.polygonShape);
    },

    setAreaColor : function(areaColor) {
        this.areaColor = areaColor;
        if (this.polygonShape != null) {
            this.polygonShape.SetFillColor(new VEColor(this.areaColor[0], this.areaColor[1], this.areaColor[2], this.areaColor[3]));
        }
    },

    getPolygonCoordsWKT : function(points){
        var wktString = "POLYGON((";
        for (var i = 0; i < points.length; i++){
            var point = points[i];
            var lat = point.lat();
            var lon = point.lng();
            wktString += lon + " " + lat + ",";
        }
        point = points[0];
        lat = point.lat();
        lon = point.lng();
        wktString += lon + " " + lat + "))";
        /*wktString = wktString.substr(0, wktString.length - 1);
         wktString += "))";*/
        return wktString;
    },

    CLASS_NAME : "RMS.GoogleMapsPolygonControl"
}
