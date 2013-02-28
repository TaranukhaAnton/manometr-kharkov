<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript" src="../js/wls/inMapping.js?version=14"></script>

<script src="../js/wls/RVirtualEarth6.js?version=14"    type="text/javascript"></script>
<script src="../js/wls/GisUtils.js?version=14"   type="text/javascript"></script>

<script type="text/javascript" src="../js/wls/BingPolygonControl.js"></script>
<script type="text/javascript" src="../js/wls/VirtualEarthWKT6.js"></script>
<script type="text/javascript" src="../js/wls/spCommon.js"></script>
<script type="text/javascript" src="../js/wls/outsideSelectionPanel.js"></script>

<script type="text/javascript" src='https://dev.virtualearth.net/mapcontrol/mapcontrol.ashx?v=6.1&s=1'></script>


<script type="text/javascript">
    if (GlobalVariables == null) {
        var GlobalVariables = new Object();
    }
    GlobalVariables.map = null;

    GlobalVariables.utilProvider = new RMS.GisUtils();
    var canUserReadJourneyCost = 'true';
    var canResellerVehicleHover = 'true';
    var canResellerVehicleHoverActivity = 'false';
    var canResellerVehicleHoverProximity = 'false';
</script>





<script type="text/javascript">
    var showBE = parent.location.toString().indexOf("geoZones") == -1 && parent.location.toString().indexOf("proximity") == -1;
    function getMapFlx(mapDiv){
        return getMap(mapDiv);
    }

    function getMap(mapdiv) {
        //GlobalVariables.map = new RMS.RVirtualEarth(mapdiv, 8, 51.5, 0, showBE);
        var m = new RMS.RVirtualEarth6(mapdiv, 8, 51.5, 0, showBE);
        return m;
    }

</script>