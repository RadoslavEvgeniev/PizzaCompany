$(function () {
    $('.form-check-inline>input, .form-check-inline>label').click(function () {
        let userEmail = $('#user-email').text();
        let role = $(this).val();

        let data = {
            email: userEmail,
            role: role
        };

        $.ajax({
            type : 'GET',
            contentType: 'application/json',
            url: 'http://localhost:8000/profiles/roleEdit',
            data: data,
            dataType: 'json',
            success: function (success) {
                console.log(success);
            },
            error: function (error) {
                console.log(error);
            },
            done: function () {
                console.log("Done.");
            }
        });
    });
});