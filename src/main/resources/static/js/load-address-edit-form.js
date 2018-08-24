$(function () {
    $('.table-rows').click(function () {
        let addressId = $(this).prop('id');
        $('#toggle-on-add-address-form').prop('hidden', true);

        $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: 'http://localhost:8000/addresses/edit',
            data: {id: addressId},
            success: function (data) {
                $.get('/templates/edit-address-form.html', function (template) {
                    let renderedTemplate = template
                        .replace('{{id}}', addressId)
                        .replace('{{name}}', data.name)
                        .replace('{{street}}', data.street)
                        .replace('{{number}}', data.number)
                        .replace('{{postCode}}', data.postCode)
                        .replace('{{municipality}}', data.municipality)
                        .replace('{{phoneNumber}}', data.phoneNumber)
                        .replace('{{block}}', data.block)
                        .replace('{{entrance}}', data.entrance)
                        .replace('{{floor}}', data.floor)
                        .replace('{{apartment}}', data.apartment)
                        .replace('null', "");

                    let formDiv = $('<div>').append(renderedTemplate);

                    $('#edit-address-form').empty();
                    $('#edit-address-form').append(formDiv);
                });
            },
            error: function (error) {
                console.log(error);
            }
        });
    });
});