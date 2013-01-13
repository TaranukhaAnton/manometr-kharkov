//    $().ready(
//            function() {
//                $("#headCustomer").autocomplete(
//                        "CustomerSetUp.do?method=findCustomers", {
//                            width :260,
//                            selectFirst :false
//                        });
//                $("#oldRecord").autocomplete(
//                        "CustomerSetUp.do?method=findCustomers", {
//                            width :260,
//                            selectFirst :false
//                        });
//
//
//                $("#CountryInput").hide();
//                $("#CountryButton").hide();
//
//                $("#RegionInput").hide();
//                $("#RegionButton").hide();
//                $("#AreaInput").hide();
//                $("#AreaButton").hide();
//
//                $("#CityInput").hide();
//                $("#CityButton").hide();
//
//                $("#OrgFormInput").hide();
//                $("#OrgFormButton").hide();
//
//                $("#CountryClick").click(function() {
//                    $('#CountryInput').toggle(400);
//                    $('#CountryButton').toggle(400);
//
//                    return false;
//                });
//                $("#RegionClick").click(function() {
//                    $('#RegionInput').toggle(400);
//                    $('#RegionButton').toggle(400);
//
//                    return false;
//                });
//                $("#AreaClick").click(function() {
//                    $('#AreaInput').toggle(400);
//                    $('#AreaButton').toggle(400);
//
//                    return false;
//                });
//                $("#CityClick").click(function() {
//                    $('#CityInput').toggle(400);
//                    $('#CityButton').toggle(400);
//
//                    return false;
//                });
//                $("#OrgFormClick").click(function() {
//                    $('#OrgFormInput').toggle(400);
//                    $('#OrgFormButton').toggle(400);
//
//                    return false;
//                });
//
//
//            });

$(function () {
    $("#CountryInput").dialog({
        autoOpen:false,
        height:200,
        width:350,
        modal:true,
        resizable:false,
        buttons:{
            'Создать':function () {

                var name = $('#newCountry').val();
                $.post("add_country", { "name":name},
                    function (data) {
                        $('#country')
                            .append($("<option></option>")
                            .attr("value", data.id)
                            .text(data.name));
                        $('#CountryInput').dialog('close');
                    });

            },
            'Отмена':function () {
                $(this).dialog('close');
            }
        },
        open:function (event, ui) {
            $('body').css('overflow', 'hidden');
            $('.ui-widget-overlay').css('width', '100%');
        },
        close:function (event, ui) {
            $('body').css('overflow', 'auto');
        }
    });
    $("#RegionInput").dialog({
        autoOpen:false,
        height:200,
        width:350,
        modal:true,
        resizable:false,
        buttons:{
            'Создать':function () {

                var name = $('#newRegion').val();
                $.post("add_region", {"name":name},
                    function (data) {
                        $('#region')
                            .append($("<option></option>")
                            .attr("value", data.id)
                            .text(data.name));
                        $('#RegionInput').dialog('close');
                    });

            },
            'Отмена':function () {
                $(this).dialog('close');
            }
        },
        open:function (event, ui) {
            $('body').css('overflow', 'hidden');
            $('.ui-widget-overlay').css('width', '100%');
        },
        close:function (event, ui) {
            $('body').css('overflow', 'auto');
        }     });
    $("#AreaInput").dialog({
        autoOpen:false,
        height:200,
        width:350,
        modal:true,
        resizable:false,
        buttons:{
            'Создать':function () {

                var country = $('#country>option:selected').val();
                var name = $('#newArea').val();
                $.post("add_area", {"country.id":country, "name":name},
                    function (data) {
                        $('#area')
                            .append($("<option></option>")
                            .attr("value", data.id)
                            .text(data.name));
                        $('#AreaInput').dialog('close');
                    });

            },
            'Отмена':function () {
                $(this).dialog('close');
            }
        },
        open:function (event, ui) {
            $('body').css('overflow', 'hidden');
            $('.ui-widget-overlay').css('width', '100%');
        },
        close:function (event, ui) {
            $('body').css('overflow', 'auto');
        }     });
    $("#CityInput").dialog({
        autoOpen:false,
        height:250,
        width:400,
        modal:true,
        resizable:false,
        buttons:{
            'Создать':function () {
                var area = $('#area>option:selected').val();
                var name = $('#newCityRus').val();
                var nameUkr = $('#newCityUkr').val();
                $.post("add_city", {"area.id":area, "name":name, "nameUkr":nameUkr},
                    function (data) {
                        $('#city')
                            .append($("<option></option>")
                            .attr("value", data.id)
                            .text(data.name));
                        $('#CityInput').dialog('close');
                    });


            },
            'Отмена':function () {
                $(this).dialog('close');
            }
        },
        open:function (event, ui) {
            $('body').css('overflow', 'hidden');
            $('.ui-widget-overlay').css('width', '100%');
        },
        close:function (event, ui) {
            $('body').css('overflow', 'auto');
        }     });
    $("#OrgFormInput").dialog({
        autoOpen:false,
        height:200,
        width:350,
        modal:true,
        resizable:false,
        buttons:{
            'Создать':function () {
                var name = $('#newOrgForm').val();
                $.post("add_org_form", { "name":name},
                    function (data) {
                        $('#orgForm')
                            .append($("<option></option>")
                            .attr("value", data.id)
                            .text(data.name));
                        $('#OrgFormInput').dialog('close');
                    });
            },
            'Отмена':function () {
                $(this).dialog('close');
            }
        },
        open:function (event, ui) {
            $('body').css('overflow', 'hidden');
            $('.ui-widget-overlay').css('width', '100%');
        },
        close:function (event, ui) {
            $('body').css('overflow', 'auto');
        }     });

    var country = $('#country');
    var area = $('#area');
    var city = $('#city');

    area.selectChain({
        target:city,
        url:'listCity',
        value:'name',
        type:'post'
    });
// note that we're assigning in reverse order
// to allow the chaining change trigger to work


    country.selectChain({
        target:area,
        url:'listArea',
        value:'name',
        type:'post'
    });
    $("#oldRecord").autocomplete(
        {
            source:"../customers/listCustomers",
            width:260,
            selectFirst:false
        });

//    jQuery.validator.addMethod("greaterThanZero", function(value, element, options) {
//        var numberRequired = options[0];
//        var selector = options[1];
//        var validOrNot = $(selector, element.form).filter(function() {
//            return $(this).val();
//        }).length >= numberRequired;
//        return validOrNot;
//    }, "Одно из полей должно быть заполненно");
//
//    jQuery.validator.addClassRules("fillone", {
//        greaterThanZero: [1,".fillone"]
//    });
//    $("#customerForm").validate({
//        rules : {
//            shortName : {required : true, minlength: 2},
//            name : "required"
////            ,
////            fillone: { greaterThanZero : true }
//        },
//        messages : {
//            shortName : {
//                required : "Поле должно быть заполнено ",
//                minlength : "Введите не менее, чем 2 символа."
//            },
//            name : "Введите пароль"
//        }
//    });

    resize();

});


$(window).resize(resize);

function resize() {
    var winHeight = $(window).height() - 170;
    winHeight = (winHeight < 500) ? 500 : winHeight;
    $("#variableHeightDiv").css("height", winHeight + "px");

}

