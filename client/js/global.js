$(document).ready(function () {

    var jsonString = getJsonString();
    var jsonObj = $.parseJSON(jsonString);

    var tabelaMostUses = createTableMostUses(jsonObj);

    $("#mostUsesAjax").html(tabelaMostUses);

});

function getJsonString() {
    var jsonTxt = '[{"order":"1","originalUrl":"http://www.google.com.br","alias":"diego","shortenedUrl":"http://192.168.1.2:8080/u/diego","mostUses":"8"},{"order":"2","originalUrl":"http://www.uol.com.br","alias":"garry","shortenedUrl":"http://192.168.1.2:8080/u/garry","mostUses":"1"}]';

    return jsonTxt;
}

function createTableMostUses(jsonObj) {
    var tabelaMostUses = "";
    var i = 1;

    $.each(jsonObj, function (key, value) {
        tabelaMostUses += "<tr>";
        tabelaMostUses += "<td>" + value.order + "</td>";
        tabelaMostUses += "<td>" + value.shortenedUrl + "</td>";
        tabelaMostUses += "<td>" + value.originalUrl + "</td>";
        tabelaMostUses += "<td>" + value.mostUses + "</td>";
        tabelaMostUses += "</tr>";

        i++;
    })

    //alert(tabelaMostUses);

    return tabelaMostUses;
}