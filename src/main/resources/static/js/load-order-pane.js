$(function () {


    $('.form-check-inline>input, .form-check-inline>label').click(function () {
        $('#order').empty();

        let tab = $(this).val();

        $('#pane-header').text(tab[0].toUpperCase() + tab.substr(1, tab.length));

        $.ajax({
            type: 'GET',
            url: 'http://localhost:8000/order/' + tab,
            success: function (data) {
                if (tab === 'address') {
                    loadAddresses(data);
                } else if (tab === 'order') {

                } else {
                    loadMenuItems(data, tab);
                }
            },
            error: function (error) {
                console.log(error);
            }
        });
    });

    $('#addressRadio').click();
});

function loadAddresses(data) {
    $.get('/templates/order-addresses-template.html', function (template) {
        let templateObj = $(template);
        let select = $('<select class="form-control">');
        select.change(sendAddressToOrder);

        select.append($('<option disabled selected>Select address</option>'));

        for (const object of data) {
            let option = $('<option>');
            option.val(object.id);
            option.text(`${object.municipality}, ${object.street} ${object.number}`);

            select.append(option);
        }

        templateObj.append(select);

        $('#order').append(templateObj);
    })
}

function loadMenuItems(data, tab) {
    $.get('/templates/order-items-template.html', function (template) {
        if (tab === 'pizza') {
            $('#order').append(getMakeOurOwnTemplate());
        }

        for (const object of data) {
            let renderedTemplate = template
                .replace('{{imageUrl}}', object['imageUrl'])
                .replace('{{name}}', object['name'])
                .replace('{{description}}', object['description'])
                .replace('{{item}}', tab);

            if (object['description'] === undefined) {
                renderedTemplate = renderedTemplate.replace(undefined, object['name']);
            }

            $('#order').append(renderedTemplate);

            let orderItemCheckbox = $('#order-item-checkbox');
            orderItemCheckbox.val(tab);
            orderItemCheckbox.prop('id', object['name']);

            orderItemCheckbox.click(sendItemToOrder);
        }
    });
}

function loadOrder() {

}

function sendAddressToOrder() {

}

function sendItemToOrder() {
    let itemType = $(this).val();
    let itemName = $(this).prop('id');
    let isChecked = $(this).is(':checked');

    let data = {
        name: itemName,
        checked: isChecked
    };

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8000/order/' + itemType,
        data: JSON.stringify(data),
        success: function (success) {
            console.log(success);
        },
        error: function (error) {
            console.log(error);
        },
        done: function () {
            console.log("Done");
        }
    });
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


