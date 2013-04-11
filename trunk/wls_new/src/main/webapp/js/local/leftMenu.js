var vehicles;
var groupNum;
var timer;


function leftMenuInit(){
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
    timer = $.timer(updateVehicles);
    timer.set({ time:5000, autostart:false });


    $('#leftMenuSP').mCustomScrollbar({
        scrollButtons:{
            enable:true
        },
        theme:"dark-thick",
        advanced:{
            updateOnContentResize:Boolean
        }

    });

    groupNum = $.cookies.get('groupNum');


    if (groupNum != null) {
        selectGroup(groupNum);
    }



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

function expand() {
    $('#leftMenu').find(".vehicleContent").slideDown("slow");
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