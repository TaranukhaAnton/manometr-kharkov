$(document).ready(function() {
    $('div#menu>ul>li>a').click(function() {
        if ($(this).parent().find('ul').length) {
            $(this).parent().find('ul').slideToggle(200);
            return false;
        }
    });
});