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
        "listCustomers", {
            width:260,
            selectFirst:false,
            parse:function (data) {
                var array = new Array();
                for (var i = 0; i < data.length; i++) {
                    array[array.length] ={
                        data: [data[i].shortName],
                        value: data[i].shortName,
                        result: data[i].shortName
                    };

                }
                return array;
            }
        });

    jQuery.validator.addMethod("greaterThanZero", function(value, element, options) {
        var numberRequired = options[0];
        var selector = options[1];
        var validOrNot = $(selector, element.form).filter(function() {
            return $(this).val();
        }).length >= numberRequired;
        return validOrNot;
    }, "Одно из полей должно быть заполненно");

    jQuery.validator.addClassRules("fillone", {
        greaterThanZero: [1,".fillone"]
    });
    $("#customerForm").validate({
        rules : {
            shortName : {required : true, minlength: 2},
            name : "required"
//            ,
//            fillone: { greaterThanZero : true }
        },
        messages : {
            shortName : {
                required : "Поле должно быть заполнено ",
                minlength : "Введите не менее, чем 2 символа."
            },
            name : "Введите пароль"
        }
    });

    resize();

});


$(window).resize(resize);

function resize() {
    var winHeight = $(window).height() - 170;
    winHeight = (winHeight < 500) ? 500 : winHeight;
    $("#variableHeightDiv").css("height", winHeight + "px");

}

