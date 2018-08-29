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
    let templateUrl = '/templates/order-items-template.html';

    if (tab === 'pizza') {
        templateUrl = templateUrl.replace('items', 'pizza');
    }

    $.get(templateUrl, function (template) {
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

            let pizzaDetailsBtn = $('#pizza-details-btn');
            pizzaDetailsBtn.val('Order ' + tab);
            pizzaDetailsBtn.prop('id', menuItem['name']);

            pizzaDetailsBtn.click(orderPizzaDetails);

            let orderItemBtn = $('#order-item-btn');
            orderItemBtn.val('Order ' + tab);
            orderItemBtn.prop('id', menuItem['name']);

            orderItemBtn.click(sendItemToOrder);
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

        let table =
            '<table class="table table-striped">' +
                '<thead>' +
                    '<tr>' +
                        '<th scope="col">Image</th>' +
                        '<th scope="col">Name</th>' +
                        '<th scope="col">Description</th>' +
                        '<th scope="col">Price</th>' +
                    '</tr>' +
                '</thead>' +
                '<tbody>' +
                    '{{tableRows}}' +
                '</tbody>' +
            '</table>'
        ;

        table = loadOrderDrinks(order, table);
        table = loadOrderDips(order, table);
        table = loadOrderPasta(order, table);
        table = loadOrderPizza(order, table);
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

function loadOrderPizza(order, table) {
    let rows = '{{tableRows}}';

    let itemType = "pizza";
    for (const pizza of order.pizzas) {
        rows +=
            '<tr >' +
                '<td>' +
                '<img src="https://res.cloudinary.com/rado-cloud/image/upload/v1534929580/pizza-anime-image.jpg" class="img img-thumbnail img-order img-fluid">' +
                '</td>' +
            '<td>' +
                'Pizza'+
            '</td>' +
            '<td>' +
                pizza.description +
            '</td>'+
            '<td class="price">' +
                Number(pizza.price).toFixed(2) + '$' +
            '</td>' +
            '</tr>'

        ;

        rows = rows.replace('{{imageUrl}}', pizza.imageUrl);
    }

    table = table.replace('{{tableRows}}', rows);

    return table;
}

function loadOrderPasta(order, table) {
    let rows = '{{tableRows}}';

    let itemType = "pasta";
    for (const pasta of order.pastas) {
        let name = pasta.name;
        rows +=
                '<tr >' +
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

function loadOrderDips(order, table) {
    let rows = '{{tableRows}}';

    for (const dip of order.dips) {
        rows +=
            '<tr >' +
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

function loadOrderDrinks(order, table) {
    let rows = '{{tableRows}}';

    for (const drink of order.drinks) {
        rows +=
            '<tr >' +
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

function calculatePizzaPrice() {
    let pizzaPrice = Number($('#pizza-size').val());

    for (const obj of $('.form-row input:checked')) {
        pizzaPrice += Number($(obj).val());
    }

    $('#pizza-price').text(pizzaPrice.toFixed(2) + '$');
}




