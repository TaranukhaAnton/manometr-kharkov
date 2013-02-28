if (typeof RMS == "undefined") {
    var RMS = new Object();
}

RMS.BingPolygonControl = function(map,  pinColor, areaColor) {

    this.initialize(map, pinColor, areaColor);

};
RMS.BingPolygonControl.prototype = {

    map : null,

    mainMap : null,

    tempShape : null,

    tempPoints : null,

    polygonPoints : new Array(),

    shapeLayer : null,

    polygonShape : null,

    polygonPushpins : new Array(),

    callback : null,

    pinColor : null,

    areaColor : null,

    initialize : function(map, pinColor, areaColor) {

        if (map == null) {
            return null;
        }

        if (map.shapeLayer == null){
            map.shapeLayer = new VEShapeLayer();
            map.map.AddShapeLayer(map.shapeLayer);
        }

        this.polygonPoints = new Array();
        this.map = map.map;
        this.mainMap = map;
        this.shapeLayer = map.shapeLayer;

        this.pinColor = pinColor;
        this.areaColor = areaColor;

    },

    addPointToPoly : function(lon, lat) {

        this.showPolygonPushpins();
                
        return this.getPolygonCoordsWKT(this.polygonPoints);
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
        for (var i = 0; i < this.polygonPushpins.length; i++) {
            this.shapeLayer.DeleteShape(this.polygonPushpins[i]);
        }

        this.polygonPushpins = new Array();
        var points = this.polygonShape.GetPoints();
        for (var i = 0; i < points.length - 1; i++) {
            var shape = new VEShape(VEShapeType.Pushpin, points[i]);
            shape.SetTitle("Point " + i);
            shape.SetCustomIcon("img/locationIcon.gif");
            this.shapeLayer.AddShape(shape);
            this.polygonPushpins[i] = shape;
        }
    },

    enableDrawing : function() {
        var thiz = this;
        this.clickCallback = function(e) {
            thiz.drawPolyMouseClick(e);
        };
        this.map.AttachEvent("onclick", this.clickCallback);
        this.moveCallback = function(e) {
            thiz.mouseMove(e);
        };
        this.map.AttachEvent("onmousemove", this.moveCallback);
    },

    disableDrawing:function() {
        if (this.clickCallback != null) {
            this.map.DetachEvent("onclick", this.clickCallback);
        }
        if (this.moveCallback != null) {
            this.map.DetachEvent("onmousemove", this.moveCallback);
        }
    },

    deletePolygon : function(){
        this.polygonPoints = new Array();
        this.shapeLayer.DeleteShape(this.polygonShape);
        for (var i = 0; i < this.polygonPushpins.length; i++) {
            this.shapeLayer.DeleteShape(this.polygonPushpins[i]);
        }

        this.polygonPushpins = new Array();
        this.polygonShape = null;
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
            this.tempShape.SetFillColor(new VEColor(this.areaColor[0], this.areaColor[1], this.areaColor[2], this.areaColor[3]));
            this.tempShape.HideIcon();
            this.shapeLayer.AddShape(this.tempShape);
        }
    },

    drawPolyMouseClick : function(e) {
        var x = e.mapX;
        var y = e.mapY;
        var pixel = new VEPixel(x, y);
        var LL = this.map.PixelToLatLong(pixel);

        this.polygonPoints.push(LL);

        if (e.rightMouseButton && this.polygonPoints.length > 2) {
            try {
                this.disableDrawing();
                this.shapeLayer.DeleteShape(this.tempShape);
            } catch (err) {
            }
            this.polygonShape = new VEShape(VEShapeType.Polygon, this.polygonPoints);
            this.polygonShape.SetFillColor(new VEColor(this.areaColor[0], this.areaColor[1], this.areaColor[2], this.areaColor[3]));
            //this.polygonShape.SetDescription("<input type='button' value='Delete' id='deletePolygon'>");
            this.polygonShape.SetCustomIcon("img/markers/marker_" + this.pinColor +  ".png");
            this.shapeLayer.AddShape(this.polygonShape);

            /*var deleteButt = document.getElementById('deletePolygon');
            var thiz = this;
            deleteButt.onclick = function(){
                thiz.deletePolygon();
            };*/
            if (this.mainMap.onFinishDrawing != null) {
                this.mainMap.onFinishDrawing(this.getPolygonCoordsWKT(this.polygonPoints));
            }
            /* document.getElementById("txtShapeID").value = "shape" + i;
             i = i + 1;
             var dummy = document.getElementById("divShapeInfo");
             dummy.style.left = e.clientX + "px";
             dummy.style.top = e.clientY + "px";
             dummy.style.visibility = "visible";
             document.getElementById("divMap").style.cursor = 'http://maps.live.com/cursors/grab.cur';*/
        }

    },

    setPinColor : function(pinColor) {
        this.pinColor = pinColor;
        this.shapeLayer.DeleteShape(this.polygonShape);
        this.polygonShape = new VEShape(VEShapeType.Polygon, this.polygonPoints);
        this.polygonShape.SetCustomIcon("img/markers/marker_" + this.pinColor +  ".png");
        this.polygonShape.SetFillColor(new VEColor(this.areaColor[0], this.areaColor[1], this.areaColor[2], this.areaColor[3]));
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
            var lat = point.Latitude;
            var lon = point.Longitude;
            wktString += lon + " " + lat + ",";
        }
        point = points[0];
        lat = point.Latitude;
        lon = point.Longitude;
        wktString += lon + " " + lat + "))";
        /*wktString = wktString.substr(0, wktString.length - 1);
        wktString += "))";*/
        return wktString;
    },

    CLASS_NAME : "RMS.BingPolygonControl"
}
