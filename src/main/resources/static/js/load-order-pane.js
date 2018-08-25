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
                    loadOrder(data);
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

function loadAddresses(addresses) {
    $.get('/templates/order-addresses-template.html', function (template) {
        let templateObj = $(template);
        let select = $('<select class="form-control">');
        select.change(sendAddressToOrder);

        select.append($('<option disabled selected>Select address</option>'));

        for (const address of addresses) {
            let option = $('<option>');
            option.val(address.id);
            option.text(`${address.municipality}, ${address.street} ${address.number}`);

            if (address.id === localStorage.getItem("selectedAddress")) {

                option.prop('selected', true);
            }

            select.append(option);
        }

        templateObj.append(select);

        $('#order').append(templateObj);
    })
}

function loadMenuItems(menuItems, tab) {
    $.get('/templates/order-items-template.html', function (template) {
        if (tab === 'pizza') {
            $('#order').append(getMakeOurOwnTemplate());
        }

        for (const menuItem of menuItems) {
            let renderedTemplate = template
                .replace('{{imageUrl}}', menuItem['imageUrl'])
                .replace('{{name}}', menuItem['name'])
                .replace('{{description}}', menuItem['description'])
                .replace('{{price}}', Number(menuItem['price']).toFixed(2))
                .replace('{{item}}', tab);


            if (menuItem['description'] === undefined) {
                renderedTemplate = renderedTemplate.replace(undefined, menuItem['name']);
            }

            $('#order').append(renderedTemplate);

            let orderItemCheckbox = $('#order-item-checkbox');
            orderItemCheckbox.val(tab);
            orderItemCheckbox.prop('id', menuItem['name']);

            let isChecked = localStorage.getItem(menuItem.name);

            if (isChecked === 'true')  {
                orderItemCheckbox.prop('checked', true);
            } else {
                orderItemCheckbox.prop('checked', false);
            }

            orderItemCheckbox.click(sendItemToOrder);
        }
    });
}

function loadOrder(order) {
    $.get('/templates/order-template.html', function (template) {

        let renderedTemplate = template
            .replace('{{address.municipality}}', order.address.municipality)
            .replace('{{address.street}}', order.address.street)
            .replace('{{address.number}}', order.address.number)
            .replace('{{address.phoneNumber}}', order.address.phoneNumber);

        let index = 1;

        let table =
            '<table class="table table-striped table-responsive">' +
                '<thead>' +
                    '<tr>' +
                        '<th scope="col">#</th>' +
                        '<th scope="col">Image</th>' +
                        '<th scope="col">Name</th>' +
                        '<th scope="col" class="col col-md-2">Description</th>' +
                        '<th scope="col">Price</th>' +
                    '</tr>' +
                '</thead>' +
                '<tbody>' +
                    '{{tableRows}}' +
                '</tbody>' +
            '</table>'
        ;

        table = loadOrderDrinks(order, table, index);
        table = loadOrderDips(order, table, index);
        table = loadOrderPasta(order, table, index);
        table = loadOrderPizza(order, table, index);
        table = table.replace('{{tableRows}}', '');


        renderedTemplate = renderedTemplate.replace('{{table}}', table);
        renderedTemplate = renderedTemplate.replace('{{id}}', order.id);
        renderedTemplate = renderedTemplate.replace('{{id}}', order.id);

        $('#order').append(renderedTemplate);

        let totalPrice = 0;

        for (const td of $('.price')) {
            totalPrice += Number(td.innerText.replace('$', ''));
        }

        let totalPriceDiv = $('#total-price');
        totalPriceDiv.append($('<div class="text">Total Price'));

        let priceHeader = $('<h4 class="text text-success">');
        priceHeader.text(totalPrice.toFixed(2) + '$');

        totalPriceDiv.append(priceHeader);

        $('#order-btn').click(sendOrder);
        $('#cancel-btn').click(cancelOrder);
    });
}

function sendAddressToOrder() {
    let addressId = $(this).val();

    let data = {
        id: addressId
    };



    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8000/order/address',
        data: JSON.stringify(data),
        success: function (success) {
            localStorage.setItem('selectedAddress', addressId);
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
            localStorage.setItem(itemName, isChecked);
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

function sendOrder() {
    let orderId = $(this).val();

    let data = {
        id: orderId
    };

    $.ajax({
        type : 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8000/order/order',
        data: data,
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

function cancelOrder() {
    let orderId = $(this).val();
}

function loadOrderPizza(order, table, index) {
    let rows = '{{tableRows}}';

    return table;
}

function loadOrderPasta(order, table, index) {
    let rows = '{{tableRows}}';

    for (const pasta of order.pastas) {
        rows +=
                '<tr >' +
                    '<th scope="row">' +
                        index++ +
                    '</th>' +
                    '<td>' +
                        '<img src="{{imageUrl}}" class="img img-thumbnail img-order img-fluid">' +
                    '</td>' +
                    '<td>' +
                        pasta.name +
                    '</td>' +
                    '<td>' +
                        pasta.description +
                    '</td>'+
                    '<td class="price">' +
                        Number(pasta.price).toFixed(2) + '$' +
                    '</td>' +
                '</tr>'

        ;

        rows = rows.replace('{{imageUrl}}', pasta.imageUrl);
    }

    table = table.replace('{{tableRows}}', rows);

    return table;
}

function loadOrderDips(order, table, index) {
    let rows = '{{tableRows}}';

    for (const dip of order.dips) {
        rows +=
            '<tr >' +
                '<th scope="row">' +
                    index++ +
                '</th>' +
                '<td>' +
                    '<img src="{{imageUrl}}" class="img img-thumbnail img-order img-fluid">' +
                '</td>' +
                '<td>' +
                    dip.name +
                '</td>' +
                '<td>' +
                    dip.name +
                '</td>'+
                '<td class="price">' +
                    Number(dip.price).toFixed(2) + '$' +
                '</td>' +
            '</tr>'
        ;

        rows = rows.replace('{{imageUrl}}', dip.imageUrl);
    }

    table = table.replace('{{tableRows}}', rows);

    return table;
}

function loadOrderDrinks(order, table, index) {
    let rows = '{{tableRows}}';

    for (const drink of order.drinks) {
        rows +=
            '<tr >' +
                '<th scope="row">' +
                    index++ +
                '</th>' +
                '<td>' +
                    '<img src="{{imageUrl}}" class="img img-thumbnail img-order img-fluid">' +
                '</td>' +
                '<td>' +
                    drink.name +
                '</td>' +
                '<td>' +
                    drink.name +
                '</td>' +
                '<td class="price">' +
                    Number(drink.price).toFixed(2) + '$' +
                '</td>' +
            '</tr>'
        ;

        rows = rows.replace('{{imageUrl}}', drink.imageUrl);
    }

    table = table.replace('{{tableRows}}', rows);

    return table;
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
