var VirtualEarthWKT7 = new (function() {
    // Declare some "private" methods that will only be used internally
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
    // Declare the "public" methods that will be exposed
    var that = {
        ///<summary>Converts a VEShape object to WKT (Well-Known-Text) string representation.</summary>
        ShapeToWKT: function(shape) {
            if (shape == null) {
                throw "VirtualEarthWKT.ShapeToWKT: 'shape' parameter can not be null.";
            }
            var wktTemplate = "";
            var wktGeomPoints = "";

            // Figure out the WKT Geometry Type
            switch (shape.toString()) {
                case 'Pushpin':
                    wktTemplate = "POINT({points})";
                    break;
                case 'Polygon':
                    wktTemplate = "POLYGON(({points}))";
                    break;
                case 'Polyline':
                    wktTemplate = "LINESTRING({points})";
                    break;
                default:
                    throw "VirtualEarthWKT.ShapeToWKT: VEShapeType (" + shape.toString() + ") not supported.";
                    break;
            }

            // Get the List of VELatLong objects represented as WKT compatible list of points
            var shapePoints = shape.getLocations();
            for (var i = 0; i < shapePoints.length; i++) {
                if (wktGeomPoints.length > 0) {
                    wktGeomPoints += ", ";
                }
                wktGeomPoints += shapePoints[i].longitude + " " + shapePoints[i].latitude;
            }

            // return WKT representation of the VEShape
            return wktTemplate.replace("{points}", wktGeomPoints);
        },
        ///<summary>
        ///Converts WKT (Well-Known-Text) string representation of a point/polygon/linestring to a Shape object.
        ///</summary>
        ShapeFromWKT: function(strWKT) {
            if (strWKT == null) {
                throw "VirtualEarthWKT.ShapeFromWKT: 'strWKT' parameter can not be null.";
            }
            if (strWKT.length == 0) {
                throw "VirtualEarthWKT.ShapeFromWKT: 'strWKT' parameter can not be an empty string.";
            }
            var shapeType = null;
            var wktPoints = null;

            // Get the Shape Type and list of "Longitude Latitude" location points
            switch (strWKT.substring(0, 5).toLowerCase()) {
                case "point":
                    shapeType = "Pushpin";
                    wktPoints = strWKT.substring(6, strWKT.length - 1);
                    break;
                case "polyg":
                    shapeType = "Polygon";
                    wktPoints = strWKT.substring(9, strWKT.length - 2);
                    break;
                case "lines":
                    shapeType = "Polyline";
                    wktPoints = strWKT.substring(11, strWKT.length - 1);
                    break;
                default:
                    throw "VirtualEarthWKT.ShapeFromWKT: Unknown WKT Geometry Type";
                    break;
            }

            // split out the wkt points to be seperate
            wktPoints = wktPoints.split(",");

            // Convert the WKT Points to Location
            var shapePoints = new Array();
            for (var i = 0; i < wktPoints.length; i++) {
                // Split the "Longitude Latitude" apart
                var loc = priv.trimSpaces(wktPoints[i]).split(" ");
                // Create Location
                shapePoints[shapePoints.length] = new Microsoft.Maps.Location(parseFloat(loc[1]), parseFloat(loc[0]));
            }
            var shape;
            switch(shapeType){
                case 'Pushpin':
                    shape = new Microsoft.Maps.Pushpin(shapePoints); 
                break;
                case 'Polygon':
                    shape = new Microsoft.Maps.Polygon(shapePoints);
                break;
                case 'Polyline':
                    shape = new Microsoft.Maps.Polyline(shapePoints);
                break;
            }
            // Return a Shape that represents this WKT Geometry
            return shape;
        }
    };
    // Return the object that contains the "public" and "private" methods
    return that;
})();