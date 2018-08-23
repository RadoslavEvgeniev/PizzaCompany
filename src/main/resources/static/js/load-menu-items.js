$(function() {
    $('#orderRadio').click(function () {
        $.get('/templates/order-template.html', function (template) {


            $('#menu').append(template);
        });
    });

    $('.form-check-inline>input, .form-check-inline>label').click(function () {
        $('#menu').empty();

        let itemType = $(this).val();

        $.ajax({
            type: 'GET',
            url: 'http://localhost:8000/menu/' + itemType,
            success: function (data) {
                $.get('/templates/menu-template.html', function (template) {
                    if (itemType === 'pizza') {
                        $('#menu').append(getMakeOurOwnTemplate());
                    }

                    for (const object of data) {

                        let renderedTemplate = template
                            .replace('{{imageUrl}}', object['imageUrl'])
                            .replace('{{name}}', object['name'])
                            .replace('{{description}}', object['description'])
                            .replace('{{item}}', itemType);

                        if (object['description'] === undefined) {
                            renderedTemplate = renderedTemplate.replace(undefined, object['name']);
                        }

                        $('#menu').append(renderedTemplate);

                        let orderBtn = $('#order-btn');
                        orderBtn.val(itemType);
                        orderBtn.prop('id', object['name']);

                        orderBtn.click(sendItemToOrder);
                    }
                });
            },
            error: function (error) {
                console.log(error);
            }
        });
    });

    $('#addressRadio').click();
});

function sendItemToOrder() {

    let itemType = $(this).val();
    let itemName = $(this).prop('id');

    let data = {name: itemName};

    if (true) {
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: 'http://localhost:8000/order/' + itemType + '/add',
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
    }

}

function getMakeOurOwnTemplate() {
    return '<div class="col col-md-3 d-flex justify-content-center mb-4">\n' +
        '    <div class="card bg-forms text-center d-flex justify-content-center">\n' +
        '        <img class="card-img-top img-menu" src="https://res.cloudinary.com/rado-cloud/image/upload/v1534929580/pizza-anime-image.jpg" alt="Card image cap">\n' +
        '        <div class="card-body" style="height: auto">\n' +
        '            <div class="d-flex justify-content-center" style="height: auto">\n' +
        '                <h3 class="text-danger">Make Your Own</h3>' +
        '            </div>\n' +
        '            <div class="d-flex justify-content-center">\n' +
        '                Choose from our products and create a pizza by your own design.\n' +
        '            </div>\n' +
        '        </div>\n' +
        '        <div class="card-footer d-flex justify-content-center" style="height: auto">\n' +
        '            <form>\n' +
        '                <button type="button" class="btn btn-success" style="width: 67px; height: 38px" id="order-btn">Order</button>\n' +
        '            </form>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div>\n';
    // return '<div class="col col-md-3 d-flex justify-content-center mb-4">\n' +
    //     '    <div class="card bg-forms text-center d-flex justify-content-center">\n' +
    //     '        <img class="card-img-top img-menu" src="https://res.cloudinary.com/rado-cloud/image/upload/v1534929580/pizza-anime-image.jpg" alt="Card image cap">\n' +
    //     '        <div class="card-header d-flex justify-content-center" style="width: 238px; height: 90px">\n' +
    //     '            <h3 class="text-danger">Make Your Own</h3>\n' +
    //     '        </div>\n' +
    //     '        <div class="card-body d-flex justify-content-center" style="width: 238px; height: 105px">\n' +
    //     '            Choose from our products and create a pizza by your own design.\n' +
    //     '        </div>\n' +
    //     '        <div class="card-body d-flex justify-content-center">\n' +
    //     '            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#order{{item}}Modal" style="width: 67px; height: 38px">\n' +
    //     '                Order\n' +
    //     '            </button>\n' +
    //     '            <div class="modal fade" id="{{item}}Modal" tabindex="-1" role="dialog" aria-labelledby="{{item}}ModalLabel" aria-hidden="true">\n' +
    //     '                <div class="modal-dialog" role="document">\n' +
    //     '                    <div class="modal-content text-center bg-forms">\n' +
    //     '\n' +
    //     '\n' +
    //     '\n' +
    //     '                    </div>\n' +
    //     '                </div>\n' +
    //     '            </div>\n' +
    //     '        </div>\n' +
    //     '    </div>\n' +
    //     '</div>';

}

function getModifyButtonTemplate() {
    return '<button type="button" class="btn btn-success" data-toggle="modal" data-target="#order{{item}}Modal" style="width: 67px; height: 38px">\n' +
        '                Modify\n' +
        '            </button>\n' +
        '            <div class="modal fade" id="{{item}}Modal" tabindex="-1" role="dialog" aria-labelledby="{{item}}ModalLabel" aria-hidden="true">\n' +
        '                <div class="modal-dialog" role="document">\n' +
        '                    <div class="modal-content text-center bg-forms">\n' +
        '\n' +
        '\n' +
        '\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>';
}


