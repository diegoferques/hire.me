$(document).ready(function () {

    $urlJson = "http://localhost:8080/mostUses";

    $.getJSON($urlJson, function(result){
        var tableMostUses = createTableMostUses(result);
        $("#mostUsesAjax").html(tableMostUses);
    });

});
/*
function getJsonString() {
    var jsonTxt = '[{"order":"1","originalUrl":"http://www.google.com.br","alias":"diego","shortenedUrl":"http://192.168.1.2:8080/u/diego","mostUses":"8"},{"order":"2","originalUrl":"http://www.uol.com.br","alias":"garry","shortenedUrl":"http://192.168.1.2:8080/u/garry","mostUses":"1"}]';

    return jsonTxt;
}
*/
function createTableMostUses(jsonObj) {
    var tabelaMostUses = "";
    var i = 1;

    $.each(jsonObj, function (key, value) {
        tabelaMostUses += "<tr>";
        tabelaMostUses += "<td>" + value.order + "</td>";
        tabelaMostUses += "<td><a href='"+ value.shortenedUrl +"' target='_new'>" + value.shortenedUrl + "</a></td>";
        tabelaMostUses += "<td><a href='"+ value.originalUrl +"' target='_new'>" + value.originalUrl + "</a></td>";
        tabelaMostUses += "<td>" + value.mostUses + "</td>";
        tabelaMostUses += "</tr>";

        i++;
    })
    return tabelaMostUses;
}