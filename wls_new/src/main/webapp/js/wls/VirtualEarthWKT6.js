var VirtualEarthWKT6 = new (function() {
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
            switch (shape.GetType()) {
                case VEShapeType.Pushpin:
                    wktTemplate = "POINT({points})";
                    break;
                case VEShapeType.Polygon:
                    wktTemplate = "POLYGON(({points}))";
                    break;
                case VEShapeType.Polyline:
                    wktTemplate = "LINESTRING({points})";
                    break;
                default:
                    throw "VirtualEarthWKT.ShapeToWKT: VEShapeType (" + shape.GetType() + ") not supported.";
                    break;
            }

            // Get the List of VELatLong objects represented as WKT compatible list of points
            var shapePoints = shape.GetPoints();
            for (var i = 0; i < shapePoints.length; i++) {
                if (wktGeomPoints.length > 0) {
                    wktGeomPoints += ", ";
                }
                wktGeomPoints += shapePoints[i].Longitude + " " + shapePoints[i].Latitude;
            }

            // return WKT representation of the VEShape
            return wktTemplate.replace("{points}", wktGeomPoints);
        },
        ///<summary>
        ///Converts WKT (Well-Known-Text) string representation of a point/polygon/linestring to a VEShape object.
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
                    shapeType = VEShapeType.Pushpin;
                    wktPoints = strWKT.substring(6, strWKT.length - 1);
                    break;
                case "polyg":
                    shapeType = VEShapeType.Polygon;
                    wktPoints = strWKT.substring(9, strWKT.length - 2);
                    break;
                case "lines":
                    shapeType = VEShapeType.Polyline;
                    wktPoints = strWKT.substring(11, strWKT.length - 1);
                    break;
                default:
                    throw "VirtualEarthWKT.ShapeFromWKT: Unknown WKT Geometry Type";
                    break;
            }

            // split out the wkt points to be seperate
            wktPoints = wktPoints.split(",");

            // Convert the WKT Points to VELatLong locations
            var shapePoints = new Array();
            for (var i = 0; i < wktPoints.length; i++) {
                // Split the "Longitude Latitude" apart
                var loc = priv.trimSpaces(wktPoints[i]).split(" ");
                // Create VELatLong location
                shapePoints[shapePoints.length] = new VELatLong(parseFloat(loc[1]), parseFloat(loc[0]));
            }

            // Return a VEShape that represents this WKT Geometry
            return new VEShape(shapeType, shapePoints);
        }
    };
    // Return the object that contains the "public" and "private" methods
    return that;
})();