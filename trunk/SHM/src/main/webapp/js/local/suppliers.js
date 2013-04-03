$(function () {
    resize();
})


$(window).resize(resize);

function resize() {
    var winHeight = $(window).height() - 170;
    winHeight = (winHeight < 500) ? 500 : winHeight;
    $("#variableHeightDiv").css("height", winHeight + "px");

}
