<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xml:lang="ru" xmlns="http://www.w3.org/1999/xhtml" lang="ru">
<head>


<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
<title>табы jQuery</title>
<style type="text/css" media="screen">
body {
margin: 10px;
padding: 0;
font: 1em Verdana, arial, sans-serif;
font-size: .8em;
}

h1 {
margin-bottom: 1em;
}

div.tabs {
background: #333;
padding: 1em;
}

div.container {
margin: auto;
width: 90%;
margin-bottom: 10px;
}

ul.tabNavigation {
list-style: none;
margin: 0;
padding: 0;
}

ul.tabNavigation li {
display: inline;
}

ul.tabNavigation li a {
padding: 3px 9px;
background-color: #666;
color: #000;
text-decoration: none;
}

ul.tabNavigation li a.selected,
ul.tabNavigation li a.selected:hover {
background: #FFF;
color: #000;
}

ul.tabNavigation li a:hover {
background: #ccc;
color: #000;
}

ul.tabNavigation li a:focus {
outline: 0;
}

div.tabs div {
padding: 5px;
margin-top: 3px;
border: 1px solid #FFF;
background: #FFF;
}

div.tabs div h2 {
margin-top: 0;
}
</style>

<script src="js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">
$(function () {
    var tabContainers = $('div.tabs > div');
    tabContainers.hide().filter(':first').show();

    $('div.tabs ul.tabNavigation a').click(function () {
        tabContainers.hide();
        tabContainers.filter(this.hash).show();
        $('div.tabs ul.tabNavigation a').removeClass('selected');
        $(this).addClass('selected');
        return false;
    }).filter(':first').click();
});
</script>
</head>
<body>
<h1>заголовок</h1>
<div class="tabs">
    <ul class="tabNavigation">
        <li><a class="" href="#id0">д.д. спец.</a></li>
        <li><a class="" href="#id1">БП</a></li>
        <li><a class="" href="#id2">ИБ</a></li>
        <li><a class="" href="#id3">Диаф. кам.</a></li>
        <li><a class="" href="#id4">Проч. прод</a></li>
        <li><a class="" href="#id5">Вычисл.</a></li>
    </ul>

    <div id="id0">
             <h2>содержание0</h2>
        <p>бла бла бла</p>
    </div>
    <div id="id1">
             <h2>содержание1</h2>
        <p>бла бла бла</p>
    </div>
    <div id="id2">
        <h2>содержание2</h2>
        <p>бла бла бла</p>
    </div>
    <div id="id3">
        <h2>содержание3</h2>
        <p>бла бла бла</p>
    </div>
    <div id="id4">
        <h2>содержание4</h2>
        <p>бла бла бла</p>
    </div>
    <div id="id5">
        <h2>содержание5</h2>
        <p>бла бла бла</p>
    </div>




</div>
</body>
</html>