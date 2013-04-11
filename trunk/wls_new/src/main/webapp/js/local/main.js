var map;
var vehicles;
var timer;
var groupNum;

$(document).ready(function () {

    map = new GMaps({
        div:'#map',
        lat:-12.043333,
        lng:-77.028333
    });
    timer = $.timer(updateVehicles);
    timer.set({ time : 5000, autostart : false });

    $("#browser").treeview({
        collapsed:true,
        animated:"medium"
    });


    $("#groupSelectDialog").dialog({
        autoOpen:false,
        height:400,
        width:450,
        modal:true,
        resizable:false,

        open:function (event, ui) {
            $('body').css('overflow', 'hidden');
            $('.ui-widget-overlay').css('width', '100%');
        },
        close:function (event, ui) {
            $('body').css('overflow', 'auto');
        }
    });

    updateDimensions();
    groupNum = $.cookies.get('groupNum');

    if (groupNum != null) {
        selectGroup(groupNum);
    }

    $('#leftMenuSP').mCustomScrollbar({
        scrollButtons:{
            enable:true
        },
        theme:"dark-thick",
        advanced:{
            updateOnContentResize:Boolean
        }

    });
})

$(window).resize(function () {
    updateDimensions();
});


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
           // $.cookies.set('vehicles', vehicles);
            map.fitZoom();
            $('#leftMenu').html(leftMenu);
            if (!timer.isActive){
                timer.play();
            }


            $('#groupSelectDialog').dialog('close');
        });


}

function generateDiv(vehicle) {
     var style = (vehicle.expanded)?"":"style=\"display: none;\" ";
    //vehReg
    //imageName
    //heading
    var result = ' <div class="vehicleDiv" id="' + vehicle.id + '"  >' +
        '<div class="vehicleHeader" >' +
        '<div class="regNumDiv" onclick="slide(this)">' + vehicle.registrationNumber + '</div>' +
        generateIgnImg(vehicle)+
        '<div class="ActionDiv">Action</div>' +
        '<div class="zoomDiv" onclick="'+( (vehicle.zoomed)?'zoomOut':'zoomIn') + '(this,' + vehicle.id + ')">' +
       ( (vehicle.zoomed)?'Zoom out':'Zoom') +
        '</div>' +
        '</div>' +
        '<div class="vehicleContent" ' + style + ' onclick="select('+vehicle.id+')">' +
        '<div class="vcLeft">' +
        '<img src="../img/vehicleIcons/' + vehicle.imageFileName + '_E_100.png">' +
        generateIgnImg(vehicle)+
        generateArrowImg(vehicle)+
        '</div>' +
        '</div>' +
        '</div> ';

    return result;
}

function generateIgnImg(vehicle) {
    // On, Off, Idling, Unplugged
    if (vehicle.ignitionStatus == 'On') {
        return '<img class="ignImg" src="../img/vehicle_status_moving.png">';
    } else if (vehicle.ignitionStatus == 'Off') {
        return '<img class="ignImg" src="../img/vehicle_status_parked.png">';
    } else if (vehicle.ignitionStatus == 'Idling') {
        return '<img class="ignImg" src="../img/vehicle_status_idle.png">';
    } else {
        return '';
    }
}

function generateArrowImg(vehicle) {
    // On, Off, Idling, Unplugged
    return '<img  src="../img/arrows/arrows_orange_'+vehicle.directionOfTravel+'.png">';
}

function slide(headerDiv) {
    var parent = $(headerDiv).parent().parent();
    parent.find(".vehicleContent").slideToggle("slow");

    var currentId = parent.attr('id');


    var vehicle = vehicles['vehicle' + currentId]; //.expanded = !vehicles['vehicle' + vehicleId].expanded;
    vehicle.expanded = !vehicle.expanded;
}

function zoomIn(div, num) {

    var vehicle = vehicles['vehicle' + num];
    var latLng = new google.maps.LatLng(vehicle.lat, vehicle.lon);
    map.ZoomAt(latLng);

    $('#leftMenu').find(".zoomDiv").each(function (index) {
        var id = $(this).parent().parent().attr('id');
        vehicles['vehicle' + id].zoomed = false;;
        $(this).attr("onClick", "zoomIn(this," + id + ")");
        $(this).html("Zoom");
    });
    vehicle.zoomed = true;


    $(div).attr("onClick", "zoomOut(this," + num + ")");
    $(div).html("Zoom out");
}

function zoomOut(div, num) {
    vehicles['vehicle' + num].zoomed = false;
    map.fitZoom();
    $(div).attr("onClick", "zoomIn(this," + num + ")");
    $(div).html("Zoom");
}

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

function expand() {
    $('#leftMenu').find(".vehicleContent").slideDown("slow");
}

function addMarker(vehicle) {
    map.addMarker({
        lat:vehicle.lat,
        lng:vehicle.lon,
        icon:"../img/vehicleIcons/" + vehicle.imageFileName + "_E_100.png",
        id:vehicle.id
    });
}

function select(vehicleId){
    var vehicle = vehicles['vehicle' + vehicleId];
    if (vehicle.selected){
        map.removeMarker(vehicleId);
    }  else{
        addMarker(vehicle);
    }
    vehicle.selected = !vehicle.selected;
    map.fitZoom();
  //  $.cookies.set('vehicles', vehicles);
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

