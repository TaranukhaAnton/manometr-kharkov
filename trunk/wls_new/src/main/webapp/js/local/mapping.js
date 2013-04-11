var map;
$(document).ready(function () {

    map = new GMaps({
        div:'#map',
        lat:-12.043333,
        lng:-77.028333
    });


    leftMenuInit();



    updateDimensions();




})

$(window).resize(function () {
    updateDimensions();
});

function updateDimensions() {
    var windowWidth = $(window).width(); //retrieve current window width
    var windowHeight = window.innerHeight;//$(window).height(); //retrieve current window height
    var windowWidthIn = $(window).innerWidth(); //retrieve current window width
    var windowHeightIn = $(window).innerHeight(); //retrieve current window height


    var documentWidth = $(document).width(); //retrieve current document width
    var documentHeight = $(document).height(); //retrieve current document height
    //alert(windowWidth+" "+windowHeight  + " /"+documentWidth+" " +documentHeight+" /" +windowWidthIn+" "+ windowHeightIn);
    // alert(windowHeight+" "+window.innerHeight +"  "+document.body.clientHeight);


    var d = 150;
    $("#menu").height(windowHeight - d);
    $("#leftMenuSP").height(windowHeight - d - 40);

    $("#map").height(windowHeight - d);
    $("#map").width(windowWidth - 380);
    map.fitZoom();

}


function addMarker(vehicle) {
    map.addMarker({
        lat:vehicle.lat,
        lng:vehicle.lon,
        icon:"../img/vehicleIcons/" + vehicle.imageFileName + "_E_100.png",
        id:vehicle.id
    });
}

function selectGroup(num) {
    $.cookies.set('groupNum', num);

    $.post("getVehicles", {"groupId":num},
        function (data) {
            var leftMenu = '';
            vehicles = new Object();
            map.removeMarkers(map.markers);
            for (var i = 0; i < data.length; i++) {
                var vehicle = data[i];
                vehicle.selected = true;
                vehicle.expanded = false;
                vehicle.zoomed = false;

                vehicles['vehicle' + vehicle.id] = vehicle;


                leftMenu += generateDiv(vehicle);
                addMarker(vehicle);
            }
         //   $.cookies.set('vehicles', vehicles);
         //   map.fitZoom();
            $('#leftMenu').html(leftMenu);
            if (!timer.isActive){
                timer.play();
            }


            $('#groupSelectDialog').dialog('close');
        });


}


function updateVehicles(){
    //todo


    $.post("getVehicles", {"groupId":groupNum},
        function (data) {
            var leftMenu = '';
            var  vehiclesLoc = new Object();
            map.removeMarkers(map.markers);
            for (var i = 0; i < data.length; i++) {
                var vehicle = data[i];
                var oldVeh = vehicles['vehicle' + vehicle.id]
                if (oldVeh==undefined){
                    vehicle.selected =  true;
                    vehicle.expanded =  false;
                    vehicle.zoomed =  false;
                }   else{
                    vehicle.selected =  oldVeh.selected;
                    vehicle.expanded =  oldVeh.expanded;
                    vehicle.zoomed =  oldVeh.zoomed;
                }

                vehiclesLoc['vehicle' + vehicle.id] = vehicle;
                leftMenu += generateDiv(vehicle);
                if (vehicle.selected)  {
                    addMarker(vehicle);
                }
            }
            vehicles =  vehiclesLoc;
          //  $.cookies.set('vehicles', vehicles);
            $('#leftMenu').html(leftMenu);
        });
}


function select(vehicleId) {
    var vehicle = vehicles['vehicle' + vehicleId];
    if (vehicle.selected) {
        map.removeMarker(vehicleId);
    } else {
        addMarker(vehicle);
    }
    vehicle.selected = !vehicle.selected;
   // var cookieVehicles = $.cookies.get('vehicles');
   // cookieVehicles[vehicle.id].selected = vehicle.selected
    //$.cookies.set('vehicles', cookieVehicles);
    map.fitZoom();

}