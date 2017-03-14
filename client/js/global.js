$(document).ready(function () {

    $('#buttonSubmit').click(function () {
        $('#formUrlShortener').validate({
            rules: {
                inputUrl: {
                    required: true,
                    url: true
                }
            },
            highlight: function (element) {
                $(element).closest('.control-group').removeClass('success').addClass('error');
            },
            success: function (element) {
                element.text('OK!').addClass('valid')
                    .closest('.control-group').removeClass('error').addClass('success');
            }
        });
    });

});
