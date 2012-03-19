function testJson() {
    $.getJSON("json", { name: 'qwer' }, function(availability) {

        alert("*"+availability.length);

    });
}