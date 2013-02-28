if (typeof RMS == "undefined") {
    var RMS = new Object();
}

RMS.GisUtils = function() {
    this.initialize();
}
RMS.GisUtils.prototype = {


    initialize : function() {

    },

    printGeocodingResult:function(places, rowClassName, containerId, clickHandler) {
        if (places == null || places.length == 0) {
            alert("There were no locations found");
            return false;
        }

        var mainDiv = document.createElement("div");
        mainDiv.className = "resultItemsContainer";

        for (var i = 0; i < places.length; i++) {
            mainDiv.appendChild(this.createResultItem(clickHandler, places[i], rowClassName, i));
        }

        if (document.getElementById(containerId) != null) {
            document.getElementById(containerId).innerHTML = "";
            document.getElementById(containerId).appendChild(mainDiv);
        }
        return true;
    },

    attachEvent : function(elem, handler, eventType) {
        if (elem.addEventListener) {
            elem.addEventListener(eventType, handler, false);
        } else {
            elem.attachEvent("on" + eventType, handler);
        }
    },

    parseGeoserverResult : function(gPlaces, rowClassName, containerId, clickHandler) {
        var filteredPlaces = [];
        for (var i = 0; i < gPlaces.length; i++) {
            var newPlace = new Object();
            newPlace.street = gPlaces[i].alternateName;
            newPlace.description = gPlaces[i].descr;
            newPlace.postcode =  gPlaces[i].postcode;
            newPlace.lat = gPlaces[i].lat;
            newPlace.lon = gPlaces[i].lon;
            newPlace.district = gPlaces[i].district;
            newPlace.dotName = gPlaces[i].dotName;
            filteredPlaces[filteredPlaces.length] = newPlace;
        }
        this.printGeocodingResult(filteredPlaces, rowClassName, containerId, clickHandler);
    },

    createResultItem : function(clickHandler, place, rowClassName, index) {
        var name = place.street;
        var description = place.description;
        var lat = place.lat;
        var lon = place.lon;

        var geocodedAddress = (name != null ? name : "");
        var resultItem = document.createElement("div");
        resultItem.className = "geocodingResultItem";

        var test = function() {
            clickHandler(lon, lat, geocodedAddress);
        };

        this.attachEvent(resultItem, test, "click");
        resultItem.style.cursor = "hand";
        resultItem.style.cursor = "pointer";

        var over = function() {
            resultItem.style.backgroundColor = "#CCCCCC";
        };

        var out = function() {
            resultItem.style.backgroundColor = "";
        };

        this.attachEvent(resultItem, over, "mouseover");
        this.attachEvent(resultItem, out, "mouseout");

        var result = "Result Nr." + (index + 1) + "</b> - Select this</a><br />";

        result += "<table class='" + rowClassName + "' cellpadding='0' cellspacing='0' width='100%' style='border:0px;font-size:10'>"
                + "<tr>"
                + "<td>Lon: " + Math.round(lon*10000)/10000 + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td>Lat: " + Math.round(lat*10000)/10000 + "</td>"
                + "</tr>"

        if (place.description != null && place.description.length > 0) {
            result += "<tr>"
                    + "<td>Description: " + place.description + "</td></tr>";
        }

        if (place.street != null && place.street.length > 0) {
            result += "<tr>"
                    + "<td>Name: " + place.street + "</td></tr>";
        }

        if (place.postcode != null && place.postcode.length > 0) {
            result += "<tr>"
                    + "<td>Postcode: " + place.postcode + "</td></tr>";
        }

        if (place.district != null && place.district.length > 0) {
            result += "<tr>"
                    + "<td>District: " + place.district + "</td></tr>";
        }

        if (place.dotName != null && place.dotName.length > 0) {
            result += "<tr>"
                    + "<td>Dot Name: " + place.dotName + "</td></tr>";
        }

        result += "</table>";

        resultItem.innerHTML = result;
        return resultItem;
    }

}