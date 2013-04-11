$(document).ready(function () {

    leftMenuInit(false);
    updateDimensions();
})

$(window).resize(function () {
    updateDimensions();
});

function updateDimensions() {
    var windowWidth = $(window).width(); //retrieve current window width
    var windowHeight = window.innerHeight;//$(window).height(); //retrieve current window height

    var d = 150;
    $("#menu").height(windowHeight - d);
    $("#leftMenuSP").height(windowHeight - d - 40);

    $("#journeyReport").height(windowHeight - d);
    $("#journeyReport").width(windowWidth - 380);


}




function selectGroup(num) {
    $.cookies.set('groupNum', num);

    $.post("/getVehicles", {"groupId":num},
        function (data) {
            var leftMenu = '';
            vehicles = new Object();

            for (var i = 0; i < data.length; i++) {
                var vehicle = data[i];
                vehicle.selected = true;
                vehicle.expanded = false;
                vehicle.zoomed = false;
                vehicles['vehicle' + vehicle.id] = vehicle;
                leftMenu += generateDiv(vehicle);
            }
            $('#leftMenu').html(leftMenu);
            if (!timer.isActive){
                timer.play();
            }
            $('#groupSelectDialog').dialog('close');
        });


}


function updateVehicles(){
    //todo


    $.post("/getVehicles", {"groupId":groupNum},
        function (data) {
            var leftMenu = '';
            var  vehiclesLoc = new Object();
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
                   //todo
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

    } else {
    }
    vehicle.selected = !vehicle.selected;
    // var cookieVehicles = $.cookies.get('vehicles');
    // cookieVehicles[vehicle.id].selected = vehicle.selected
    //$.cookies.set('vehicles', cookieVehicles);
   // map.fitZoom();

}