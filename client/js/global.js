$(document).ready(function () {
    var urlApi = "http://localhost:8080/";
    var urlShortener = urlApi + "create";
    var urlMostUses = urlApi + "mostUses";

    getMostUses(urlMostUses);

    $("#formUrlShortener").unbind("submit").on("submit", function(e){
        e.stopPropagation();
        e.preventDefault();

        var inputUrl = $("#url").val();
        var inputAlias = $("#alias").val();

        var response = shortenerUrlAjax(urlShortener, inputUrl, inputAlias);

        responseShortenerUrl(response, inputUrl, inputAlias);

        getMostUses(urlMostUses);

        return false;
    })

});

function shortenerUrlAjax(url, inputUrl, inputAlias){
    var response = null;

    $.ajax({
        type: "POST",
        url: url,
        dataType: 'json', //expect return data as html from server
        data: {
            url: inputUrl,
            alias: inputAlias
        },
        async: false,
        success: function (data) {
            response = data;
            //responseShortenerUrl(response, inputUrl, inputAlias)
        },
    });

    return response;
}

function responseShortenerUrl(data, inputUrl, inputAlias) {
    var status = false;
    var errorCode = null;
    var description = null;
    var originalUrl = null;
    var url = null;
    var alias = null;
    var timeTaken = null;

    if(data == null) {
        errorCode = "003";
        description = "Unknown error - Try again later!";
        originalUrl = inputUrl;
        alias = inputAlias;
        timeTaken = "---";

    } else if("errorCode" in data) {
        errorCode = data.errorCode;
        description = data.description;
        originalUrl = data.originalUrl;
        alias = data.alias;
        timeTaken = data.timeTaken;

    } else {
        status = true;
        originalUrl = data.originalUrl;
        url = data.url;
        alias = data.alias;
        timeTaken = data.timeTaken;
    }


    html = "<table class='table table-bordered table-responsive'>";
    html += "<thead>";
    if(status == false) {
        html += "<th class='text-center'>Código de Erro</th>";
        html += "<th class='text-center'>Descrição</th>";
    } else {
        html += "<th class='text-center'>Url Encurtada</th>";
    }
    html += "<th class='text-center'>URL Original</th>";
    html += "<th class='text-center'>Alias</th>";
    html += "<th class='text-center'>Tempo gasto</th>";
    html += "</thead>";

    html += "<tbody>";
    html += "<tr>";
    if(status == false) {
        html += "<td class='text-center'>"+ errorCode +"</td>";
        html += "<td class='text-center'>"+ description +"</td>";
    } else {
        html += "<td class='text-center'>"+ url +"</td>";
    }
    html += "<td class='text-center'>"+ originalUrl +"</td>";
    html += "<td class='text-center'>"+ alias +"</td>";
    html += "<td class='text-center'>"+ timeTaken +"</td>";
    html += "</tr>";
    html += "</tbody>";

    html += "<tfoot>";
    html += "</tfoot>";
    if(status == false) {
        html += "<td colspan='5' class='alert-danger'>ERRO AO ENCURTAR URL</td>";
    } else {
        html += "<td colspan='4' class='alert-success'>URL ENCURTADA COM SUCESSO!</td>";
    }
    html += "</table>";

    $("#retornoAjax").html(html);

}

function getMostUses(url) {
    $.getJSON(url, function(result){
        var tableMostUses = createTableMostUses(result);
        $("#mostUsesAjax").html(tableMostUses);
    });
}

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
    });

    return tabelaMostUses;
}