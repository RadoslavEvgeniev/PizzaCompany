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
        let  renderedTemplate = template;
        if (tab === 'pizza') {
            renderedTemplate = getMakeYourOwnPizzaTemplate() + renderedTemplate;
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

            let orderPizzaBtn = $('#order-pizza-btn');
            orderPizzaBtn.val('Order ' + tab);
            orderPizzaBtn.prop('id', menuItem['name']);

            orderPizzaBtn.click(preparePizzaForOrder);

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

        let index = 1;

        let table =
            '<table class="table table-striped">' +
                '<thead>' +
                    '<tr>' +
                        '<th scope="col">#</th>' +
                        '<th scope="col">Image</th>' +
                        '<th scope="col">Name</th>' +
                        '<th scope="col">Description</th>' +
                        '<th scope="col">Price</th>' +
                        '<th scope="col">Remove</th>' +
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

function preparePizzaForOrder() {
    $('#order-pizza-form').empty();
    $('#order-pizza-modal-label').text($(this).prop('id'));

    let data = {
        pizzaName : $(this).prop('id')
    };

    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: 'http://localhost:8000/order/prepare-pizza',
        data: data,
        success: function (orderPizzaViewModel) {
            $.get('/templates/order-pizza-form.html', function (template) {
                let pizza = orderPizzaViewModel['pizza'];

                $('#order-pizza-form').append(template);

                $('#order-pizza-form').change(calculatePizzaPrice);

                for (const size of orderPizzaViewModel.ingredients.sizes) {
                    let select = $('#pizza-size');

                    let option = $('<option>');
                    option.prop('id', size.size);
                    option.val(size.price);
                    option.text(`${size.size} (${size.numberOfSlices} slices)`);

                    select.append(option);
                }

                for (const dough of orderPizzaViewModel.ingredients.doughs) {
                    let select = $('#pizza-dough');

                    let option = $('<option>');
                    option.val(dough.name);
                    option.text(dough.name);

                    select.append(option);
                }

                for (const sauce of orderPizzaViewModel.ingredients.sauces) {
                    let select = $('#pizza-sauce');

                    let option = $('<option>');
                    option.val(sauce.name);
                    option.text(sauce.name);

                    if (pizza.sauce.name === sauce.name) {
                        option.prop('selected', true);
                    }

                    select.append(option);
                }

                for (const spice of orderPizzaViewModel.ingredients.spices) {
                    let select = $('#pizza-spices');

                    let option = $('<option>');
                    option.val(spice.name);
                    option.text(spice.name);

                    if (pizza.spices.map(s => s.name).indexOf(spice.name) > -1) {
                        option.prop('selected', true);
                    }

                    select.append(option);
                }

                for (const cheese of orderPizzaViewModel.ingredients.cheeses) {
                    let inputDiv = $('<div class="row"  style="overflow: hidden; white-space: nowrap">');

                    let input = $('<input class="form-check-input" type="checkbox" id="'+ cheese.name + '" name="cheeses">');
                    let label = $('<label class="form-check-label" for="'+ cheese.name + '" id="'+ cheese.name + '-label"></label>');
                    input.val(cheese.price);
                    label.text(cheese.name);

                    if (pizza.cheeses.map(ch => ch.name).indexOf(cheese.name) > -1) {
                        input.prop('checked', true);
                    }

                    inputDiv.append(input);
                    inputDiv.append(label);

                    $('#pizza-cheeses').append(inputDiv);
                }

                for (const vegetable of orderPizzaViewModel.ingredients.vegetables) {
                    let inputDiv = $('<div class="row "  style="overflow: hidden; white-space: nowrap">');

                    let input = $('<input class="form-check-input" type="checkbox" id="'+ vegetable.name + '" name="vegetables">');
                    let label = $('<label class="form-check-label" for="'+ vegetable.name + '" id="'+ vegetable.name + '-label"></label>');
                    input.val(vegetable.price);
                    label.text(vegetable.name);

                    if (pizza.vegetables.map(v => v.name).indexOf(vegetable.name) > -1) {
                        input.prop('checked', true);
                    }

                    inputDiv.append(input);
                    inputDiv.append(label);

                    $('#pizza-vegetables').append(inputDiv);
                }

                for (const meat of orderPizzaViewModel.ingredients.meats) {
                    let inputDiv = $('<div class="row "  style="overflow: hidden; white-space: nowrap">');

                    let input = $('<input class="form-check-input" type="checkbox" id="'+ meat.name + '" name="meats">');
                    let label = $('<label class="form-check-label" for="'+ meat.name + '" id="'+ meat.name + '-label"></label>');
                    input.val(meat.price);
                    label.text(meat.name);

                    if (pizza.meats.map(m => m.name).indexOf(meat.name) > -1) {
                        input.prop('checked', true);
                    }

                    inputDiv.append(input);
                    inputDiv.append(label);

                    $('#pizza-meats').append(inputDiv);
                }

                calculatePizzaPrice();
            });
        }
    });

}

function sendItemToOrder() {
    let itemType = $(this).val().replace('Order ', '');
    let itemName = $(this).prop('id');
    let added = true;

    let data = {
        name: itemName,
        added: added
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

function removeItemFromOrder() {
    let itemParams = $('#remove-item-btn').val().split(':');

    let itemType = itemParams[0];
    let itemName = itemParams[1];
    let added = false;

    let data = {
        name: itemName,
        added: added
    };

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8000/order/' + itemType,
        data: JSON.stringify(data),
        success: function (success) {
            $('#orderRadio').click();
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

    let itemType = "pasta";
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
                    '<td>' +
                        '<button class="btn btn-danger" id="remove-item-btn" value="'+ 'pasta:' + pasta.name +'" onclick="removeItemFromOrder()">' +
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
                '<td>' +
                    '<button class="btn btn-danger" id="remove-item-btn" value="'+ 'dips:' + dip.name +'" onclick="removeItemFromOrder()">' +
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
                '<td>' +
                    '<button class="btn btn-danger" id="remove-item-btn" value="'+ 'drinks:' + drink.name +'" onclick="removeItemFromOrder()">' +
                '</td>' +
            '</tr>'
        ;

        rows = rows.replace('{{imageUrl}}', drink.imageUrl);
    }

    table = table.replace('{{tableRows}}', rows);

    return table;
}

function getMakeYourOwnPizzaTemplate() {
    return '<div class="col col-md-3 d-flex justify-content-center mb-4"><div class="card bg-forms text-center d-flex justify-content-center"><img class="card-img-top img-menu img-fluid" src="https://res.cloudinary.com/rado-cloud/image/upload/v1534929580/pizza-anime-image.jpg" alt="Card image cap">         <div class="card-body" style="height: auto">             <div class="d-flex justify-content-center" style="height: auto">                 <h3 class="text-danger">Make Your Own</h3>             </div>             <div class="d-flex justify-content-center">                 Choose from our products and create a pizza by your own design.             </div>         </div>         <div class="card-footer d-flex justify-content-center" style="height: auto">             <form>                 <label class="form-check-label justify-content-center" for="order-own-pizza-btn"><input type="button" class="btn btn-success" id="order-own-pizza-btn"></label></form></div></div></div>';
}

function calculatePizzaPrice() {
    let pizzaPrice = Number($('#pizza-size').val());

    for (const obj of $('.form-row input:checked')) {
        pizzaPrice += Number($(obj).val());
    }

    $('#pizza-price').text(pizzaPrice.toFixed(2) + '$');
}
