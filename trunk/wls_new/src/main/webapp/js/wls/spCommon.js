var additionalPixelsForHtmlWidth = 30;
var additionalPixelsForHtmlHeight = 3;
var additionalPixelsForHtmlWidthActivity = 80;
var additionalPixelsForHtmlHeightActivity = 3;

function getVehicleMapDescr(vehicle, canUserRead, canResellerHover)
{
    //   canUserRead = (canUserRead == 'noflex'?canUserReadJourneyCost:canUserRead);
    var text;
    if (canResellerHover == "true") {
        var date = new Date(vehicle.renewalDate);
        text = vehicle.registrationNumber + "<br>" +
               "Ignition " + vehicle.ignitionStatus + "<br>" +
               vehicle.factoredSpeed + "MPH " + "(" + vehicle.directionOfTravel.toString().toUpperCase() + ")" + "<br>" +
               date.toString() + "<br>"
                + ((vehicle.curAddress != '' && vehicle.curAddress != null) ? (vehicle.curAddress + "<br>") : "")
                + ((vehicle.aoiPoiDescr != '' && vehicle.aoiPoiDescr != null) ? vehicle.aoiPoiDescr + "<br>" : "")
                + (vehicle.vehicleDriverDescr != undefined ? ("Driver: " + vehicle.vehicleDriverDescr + "<br>") : "")
                + (( canUserRead == 'true' && vehicle.costPerMile != 0) ?
                                ("Today's Cost: \u00A3" + vehicle.costToDistance)  : "");
    } else {
        text = vehicle.registrationNumber;
    }
    return text;
}

function getVehicleIconImageName(data, isIgnitionIcons) {
    var imageFileName = data.type == null ? data.imageFileName : data.type.imageFileName;
    if (isIgnitionIcons) {
        return  imageFileName + "_E_"
                + vehicleIconSize + ".png";
    } else {
        return imageFileName + "_"
                + (!isVehicleIconRotation ? "E" : data.directionOfTravel.toUpperCase()) + "_"
                + vehicleIconSize + ".png";
    }
}

function generateHTMLMarkerForVehicle(image, data, textColor, fleetId, icon,isIgnitionIconsVersion2) {
    var topMargin = 0;
    if (data.directionOfTravel == 'n'){
        topMargin = -22;
    }
    if (data.directionOfTravel == 'nw'){
        topMargin = -18;
    }
    if (data.directionOfTravel == 'ne'){
        topMargin = -18;
    }
    return "<span style='font-family:Arial; font-size:x-small;" +
           "color:white; background-color:transparent'>" +
           "<table width='" + (image.width + additionalPixelsForHtmlWidth) + "' height='" + (image.height + additionalPixelsForHtmlHeight) + "'" +
           "  style='position:relative;left:"+ map.calculateXOffSet(image.width + additionalPixelsForHtmlWidth) +"px;top:"+topMargin+"px;margin-top:"+map.calculateYOffSet(image.height + additionalPixelsForHtmlHeight) +"' >" +
           generateFirstTableRow(data, icon,isIgnitionIconsVersion2) +
           generateSecondTableRow(data, icon, image, textColor, fleetId,isIgnitionIconsVersion2) +
           generateThirdTableRow(data, icon,isIgnitionIconsVersion2) +
           "</table></span>";
}
function generateHTMLMarkerForVehicleVer3(image,  icon, vehicleIconSize) {
    if (vehicleIconSize=='25'){
        return generateHTMLMarkerForVehicleVer3_25(image,  icon);
    } else if (vehicleIconSize=='50'){
        return generateHTMLMarkerForVehicleVer3_50(image,  icon);
    }else if (vehicleIconSize=='100'){
        return generateHTMLMarkerForVehicleVer3_100(image,  icon);
    }
}

function generateHTMLMarkerForVehicleVer3_25(image,  icon) {
    return "<span style='font-family:Arial; font-size:x-small; color:white; background-color:transparent'>" +
        "<div style=\" width: 45px; height: 45px; position:relative; left:"+ map.calculateXOffSet(45 + additionalPixelsForHtmlWidth) +"px;   top:"+map.calculateYOffSet(45 + additionalPixelsForHtmlHeight) +"px;      background-repeat: no-repeat; background-image: url('" + icon + "');\" >" +
            "<img style='position:relative;  left: 11px;  top: 17px; ' src='" + image.src + "'>"+
        "</div>" +
        "</span>";
}
function generateHTMLMarkerForVehicleVer3_50(image,  icon) {
    return "<span style='font-family:Arial; font-size:x-small; color:white; background-color:transparent'>" +
        "<div style=\" width: 50px; height: 50px; position:relative; left:"+ map.calculateXOffSet(45 + additionalPixelsForHtmlWidth) +"px;   top:"+map.calculateYOffSet(45 + additionalPixelsForHtmlHeight) +"px;      background-repeat: no-repeat; background-image: url('" + icon + "');\" >" +
            "<img style='position:relative;  left: 11px;  top: 17px; ' src='" + image.src + "'>"+
        "</div>" +
        "</span>";
}
function generateHTMLMarkerForVehicleVer3_100(image,  icon) {
    return "<span style='font-family:Arial; font-size:x-small; color:white; background-color:transparent'>" +
        "<div style=\" width: 100px; height: 100px; position:relative; left:"+ map.calculateXOffSet(45 + additionalPixelsForHtmlWidth) +"px;   top:"+map.calculateYOffSet(45 + additionalPixelsForHtmlHeight) +"px;      background-repeat: no-repeat; background-image: url('" + icon + "');\" >" +
            "<img style='position:relative;  left: 20px;  top: 37px; ' src='" + image.src + "'>"+
        "</div>" +
        "</span>";
}



function generateHTMLMarkerWithTooltipNoIgn(image, data, tooltipUrl) {
    return "<span style='font-family:Arial; font-size:x-small; position:relative; top:-41px;z-index:-1;" +
           "color:white; background-color:transparent'>" +
           "<table width='" + (image.width + 94) + "' height='" + (image.height + 41) + "'" +
           " >" +
           "<tr><td></td><td height='41' width='94' style='background-image: url(" + tooltipUrl + ");background-repeat:no-repeat;padding-top:5px;' align='center' valign='top'>" + data.regNumber + "</td></tr>" +
           "<tr><td style='background-position:center; background-repeat:no-repeat;' background='" + image.src + "' align='center' vertical width='" + (image.width + 3) + "' height='" + image.height + "'>" +
                "</td><td>" +
                 "</td></tr>" +
           "</table></span>";
}

function generateHTMLMarkerWithTooltipIgn(image, text, data, tooltipUrl) {
    return "<span style='font-family:Arial; font-size:x-small; position:relative; top:-41px;z-index:-1;" +
           "color:white; background-color:transparent'>" +
           "<table width='" + (image.width + 87 + additionalPixelsForHtmlWidth) + "' height='" + (image.height + 41 + additionalPixelsForHtmlHeight) + "'" +
           " >" +
           "<tr><td height='41' width='87' style='background-image: url(" + tooltipUrl + ");background-repeat:no-repeat;padding-top:5px;' align='center' valign='top'>" + data.regNumber + "</td></tr>" +
           "<tr><td>" + text + 
                "</td><td>" +
                 "</td></tr>" +
           "</table></span>";
}

function generateHTMLMarkerForActivity(selectedActivity, iconUrl, statusUrl, image) {
    var backgorundColor = "style='align:center;filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#40fffffff,endColorstr=#40fffffff);background:rgba(255, 255, 255, 0.6)'";
    return   "<span style='font-family:Arial; font-size:x-small;" +
           "color:white; background-color:transparent'>" +
           "<table border='0' cellpadding='5' cellspacing='0' width='" + (image.width + additionalPixelsForHtmlWidthActivity) + "' height='" + (image.height + additionalPixelsForHtmlHeightActivity) + "'" +
           "  style='position:relative;left:"+ map.calculateXOffSet(image.width + additionalPixelsForHtmlWidthActivity) +";margin-top:"+map.calculateYOffSet(image.height + additionalPixelsForHtmlHeightActivity) +"' >" +
           "<tr>" +
             "<td " + backgorundColor + "><img src='" + iconUrl + "'></td><td " + backgorundColor + "><img src='" + statusUrl + "'></td><td " + backgorundColor + "><b>" + selectedActivity.recDate + "</b></td><td><img src='" + image.src + "'><td></tr>" +
           "</table></span>";
}

function generateFirstTableRow(data, icon,isIgnitionIconsVersion2) {
    if (data.ignitionStatus == "On" || isIgnitionIconsVersion2) {
        if (data.directionOfTravel == "nw") {
            return "<tr><td><img style='position:relative;bottom:-12px;right:-10px;;z-index:-1' src='" + icon + "'></td><td></td><td></td></tr>";
        } else if (data.directionOfTravel == "n") {
            return "<tr><td></td><td align='center'><img style='position:relative;bottom:-6px;z-index:-1' src='" + icon + "'></td><td></td></tr>";
        } else if (data.directionOfTravel == "ne") {
            return "<tr><td></td><td></td><td><img style='position:relative;bottom:-12px;left:-10px;z-index:-1' src='" + icon + "'></td></tr>";
        }
    }
    return "";
}

function generateSecondTableRow(data, icon, img, textColor, fleetId,isIgnitionIconsVersion2) {
    var rgba = "0, 0, 0, 0.4";
    var fontSize = 12;
    if (vehicleIconSize == 50) {
        fontSize = 6;
    } else if(vehicleIconSize == 25) {
        fontSize = 6;    
    }
    var hex = "#40000000";
    if (textColor.match("black")) {
        rgba = "255, 255, 255, 0.4";
        hex = '#40fffffff';
    }
    if (data.ignitionStatus == "On" || isIgnitionIconsVersion2) {
        if (data.directionOfTravel == "e") {
            return   "<tr><td></td><td style='background-position:center;background-repeat:no-repeat;' background='" + img.src + "' align='center' width='" + (img.width + 3) + "' height='" + img.height + "'>" +
                     "<b style='font-size:" + fontSize + "px;color:" + textColor + "; filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=" + hex + ",endColorstr=" + hex + ");zoom: 1;background:rgba(" + rgba + ")'>" +
                     fleetId + "</b></td><td>" +
                     "<div'><img style='position:relative;left:-12px;z-index:-1' src='" + icon + "'></div></td></tr>";
        } else if (data.directionOfTravel == "w") {
            return  "<tr><td><div><img style='position:relative;right:-8px;z-index:-1' src='" + icon + "'></div></td><td style='background-position:center;background-repeat:no-repeat;' background='" + img.src + "' align='center' width='" + (img.width + 3) + "' height='" + img.height + "'>" +
                    "<b style='font-size:" + fontSize + "px;color:" + textColor + "; filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=" + hex + ",endColorstr=" + hex + ");zoom: 1;background:rgba(" + rgba + ")'>" +
                    fleetId + "</b></td><td>" +
                    "</td></tr>";
        }
    } else if (data.ignitionStatus == "Idling" || data.ignitionStatus == "Off") {
        return   "<tr><td></td><td style='background-position:center; background-repeat:no-repeat;' background='" + img.src + "' align='center' vertical width='" + (img.width + 3) + "' height='" + img.height + "'>" +
                 "<b style='font-size:" + fontSize + "px;color:" + textColor + "; filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=" + hex + ",endColorstr=" + hex + ");zoom: 1;background:rgba(" + rgba + ")'>" +
                 fleetId + "</b></td><td>" +
                 "<div ><img height='16' style='position:relative;z-index:-1;left:-5px;' src='" + icon + "'></div></td></tr>";
    }
    return "<tr><td></td><td style='background-position:center;background-repeat:no-repeat;' background='" + img.src + "' align='center' width='" + (img.width + 3) + "' height='" + img.height + "'>" +
           "<b style='font-size:" + fontSize + "px;color:" + textColor + "; filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=" + hex + ",endColorstr=" + hex + ");zoom: 1;background:rgba(" + rgba + ")'>" +
           fleetId + "</b></td><td>" +
           "</td></tr>";
}

function generateThirdTableRow(data, icon,isIgnitionIconsVersion2) {
    if (data.ignitionStatus == "On" || isIgnitionIconsVersion2) {
        if (data.directionOfTravel == "sw") {
            return "<tr><td><img style='position:relative;top:-14px;right:-14px;z-index:-1' src='" + icon + "'></td><td></td><td></td></tr>";
        } else if (data.directionOfTravel == "s") {
            return "<tr><td></td><td align='center'><img style='position:relative;top:-8px;z-index:-1' src='" + icon + "'></td><td></td></tr>";
        } else if (data.directionOfTravel == "se") {
            return "<tr><td></td><td></td><td><img style='position:relative;top:-14px;left:-14px;z-index:-1' src='" + icon + "'></td></tr>";
        }
    }
    return "";
}

function getIgnitionIconImageNameVersion1(data) {
    if (data.ignitionStatus == "On") {
        return "ignition_status_direction_" + data.directionOfTravel.toLowerCase() + ".png";
    } else if (data.ignitionStatus == "Off") {
        return "ignition_status_off.png";
    } else {
        return "ignition_status_idling.png";
    }
}

function getIgnitionIconImageNameVersion2(data) {
    var postfix = "_direction_" + data.directionOfTravel.toLowerCase() + ".png";
    var prefix = "version2/ignition_status";
    if (data.ignitionStatus == "On") {
        return prefix+postfix;
    } else if (data.ignitionStatus == "Off") {
        return prefix + "_off" + postfix;
    } else {
        return prefix + "_idling" + postfix;
    }
}

function getIgnitionIconImageNameVersion3(data) {

    var postfix = "-glow_" + data.directionOfTravel.toUpperCase()+"_" +vehicleIconSize+ ".png";
    var prefix = "version3/";
    if (data.ignitionStatus == "On") {
        return prefix + "green" + postfix;
    } else if (data.ignitionStatus == "Off") {
        return prefix + "red" + postfix;
    } else {
        return prefix + "yellow" + postfix;
    }
}

function getTextOrHyphen(text){
    return text == undefined ? ' - ' : text;
}

function getVehicleEastIconImageName(data) {
    var imageFileName = data.type == null ? data.imageFileName : data.type.imageFileName;
    return data.typeDescr == "Vehicle" ? imageFileName + "_E_100.png" : imageFileName + ".png";
}

function trim(string) {
    if (string == null) {
        return null;
    }
    return string.replace(/(^\s+)|(\s+$)/g, "");
}

function isEnterPressed(field, event) {
    var theCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
    return (theCode == 13);
}

function elemCount(obj) {
    var res = 0;
    for (var key in obj) {
        res++;
    }

    return res;
}

function getPos(obj) {
    var output = new Object();
    var mytop = 0, myleft = 0;
    while (obj) {
        mytop += obj.offsetTop;
        myleft += obj.offsetLeft;
        obj = obj.offsetParent;
    }
    output.left = myleft;
    output.top = mytop;
    return output;
}

function pageWidth() {
    return window.innerWidth != null
            ? window.innerWidth
            : document.documentElement && document.documentElement.clientWidth
            ? document.documentElement.clientWidth
            : document.body != null ? document.body.clientWidth : null;
}
function pageHeight() {
    return  window.innerHeight != null
            ? window.innerHeight
            : document.documentElement && document.documentElement.clientHeight
            ? document.documentElement.clientHeight
            : document.body != null ? document.body.clientHeight : null;
}

function arrayContains(arr, value) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == value) {
            return true;
        }
    }

    return false;
}

function latLonToDistanse(pStartLon, pEndLon, pStartLat, pEndLat) {
    var D2R = 0.017453;
    var a = 6378137.0;
    var e2 = 0.006739496742337;

    var fdLambda = (pStartLon - pEndLon) * D2R;
    var fdPhi = (pStartLat - pEndLat) * D2R;
    var fPhimean = ((pStartLat + pEndLat) / 2.0) * D2R;

    var fTemp = 1 - e2 * (Math.pow(Math.sin(fPhimean), 2));
    var fRho = (a * (1 - e2)) / Math.pow(fTemp, 1.5);
    var fNu = a / (Math.sqrt(1 - e2 * (Math.sin(fPhimean) * Math.sin(fPhimean))));

    var fz = Math.sqrt(Math.pow(Math.sin(fdPhi / 2.0), 2) + Math.cos(pEndLat * D2R) * Math.cos(pStartLat * D2R) *
                                                            Math.pow(Math.sin(fdLambda / 2.0), 2));
    fz = 2 * Math.asin(fz);

    var fAlpha = Math.cos(pEndLat * D2R) * Math.sin(fdLambda) / Math.sin(fz);
    fAlpha = Math.asin(fAlpha);

    var fR = (fRho * fNu) / ((fRho * Math.pow(Math.sin(fAlpha), 2)) + (fNu * Math.pow(Math.cos(fAlpha), 2)));
    var result = fz * fR;

    return result;
}

function getInitialZoom() {
    return 3;
}

function checkBranding(brandingNamePart){
    return document.location.href.toString().indexOf(brandingNamePart)!=-1;
}

function getElementsByClassName(className, tag, elm) {
    if (document.getElementsByClassName) {
        getElementsByClassName = function (className, tag, elm) {
            elm = elm || document;
            var elements = elm.getElementsByClassName(className),
                    nodeName = (tag) ? new RegExp("\\b" + tag + "\\b", "i") : null,
                    returnElements = [],
                    current;
            for (var i = 0, il = elements.length; i < il; i += 1) {
                current = elements[i];
                if (!nodeName || nodeName.test(current.nodeName)) {
                    returnElements.push(current);
                }
            }
            return returnElements;
        };
    }
    else if (document.evaluate) {
        getElementsByClassName = function (className, tag, elm) {
            tag = tag || "*";
            elm = elm || document;
            var classes = className.split(" "),
                    classesToCheck = "",
                    xhtmlNamespace = "http://www.w3.org/1999/xhtml",
                    namespaceResolver = (document.documentElement.namespaceURI === xhtmlNamespace) ? xhtmlNamespace : null,
                    returnElements = [],
                    elements,
                    node;
            for (var j = 0, jl = classes.length; j < jl; j += 1) {
                classesToCheck += "[contains(concat(' ', @class, ' '), ' " + classes[j] + " ')]";
            }
            try {
                elements = document.evaluate(".//" + tag + classesToCheck, elm, namespaceResolver, 0, null);
            }
            catch (e) {
                elements = document.evaluate(".//" + tag + classesToCheck, elm, null, 0, null);
            }
            while ((node = elements.iterateNext())) {
                returnElements.push(node);
            }
            return returnElements;
        };
    }
    else {
        getElementsByClassName = function (className, tag, elm) {
            tag = tag || "*";
            elm = elm || document;
            var classes = className.split(" "),
                    classesToCheck = [],
                    elements = (tag === "*" && elm.all) ? elm.all : elm.getElementsByTagName(tag),
                    current,
                    returnElements = [],
                    match;
            for (var k = 0, kl = classes.length; k < kl; k += 1) {
                classesToCheck.push(new RegExp("(^|\\s)" + classes[k] + "(\\s|$)"));
            }
            for (var l = 0, ll = elements.length; l < ll; l += 1) {
                current = elements[l];
                match = false;
                for (var m = 0, ml = classesToCheck.length; m < ml; m += 1) {
                    match = classesToCheck[m].test(current.className);
                    if (!match) {
                        break;
                    }
                }
                if (match) {
                    returnElements.push(current);
                }
            }
            return returnElements;
        };
    }
    return getElementsByClassName(className, tag, elm);
}
;

RepeatingOperation = function(op, yieldEveryIteration) {
    var count = 0;
    var instance = this;
    this.step = function(args) {
        if (++count >= yieldEveryIteration) {
            count = 0;
            setTimeout(function() { op(args); }, 1, []);
            return;
        }
        op(args);
    };
};
