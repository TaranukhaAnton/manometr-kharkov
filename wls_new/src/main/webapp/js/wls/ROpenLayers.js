if (window.OpenLayers != null) {
    loadLibs();
}

function loadLibs() {
    var jsfiles = new Array(
            "RGeoserverMap.js",
            "RIcon.js",
            "SWFIcon.js",
            "swf_object.js",
            "control/CenterAtControl.js",
            "handler/RDrag.js",
            "control/RDragPan.js",
            "control/RNavigation.js",
            "handler/RBox.js",
            "control/RZoomControl.js"
            );
    // etc.

    var agent = navigator.userAgent;
    var docWrite = (agent.match("MSIE") || agent.match("Safari"));
    if (docWrite) {
        var allScriptTags = new Array(jsfiles.length);
    }
    var host = "js/";
    for (var i = 0; i < jsfiles.length; i++) {
        if (docWrite) {
            allScriptTags[i] = "<script src='" + host + jsfiles[i] +
                               "'></script>";
        } else {
            var s = document.createElement("script");
            s.src = host + jsfiles[i];
            var h = document.getElementsByTagName("head").length ?
                    document.getElementsByTagName("head")[0] :
                    document.body;
            h.appendChild(s);
        }
    }
    if (docWrite) {
        document.write(allScriptTags.join(""));
    }
    window.OpenLayers.ImgPath = '';
}