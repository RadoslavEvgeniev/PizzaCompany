function orderPizzaDetails() {
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
                    option.data('size', size.size);

                    select.append(option);
                }

                for (const dough of orderPizzaViewModel.ingredients.doughs) {
                    let select = $('#pizza-dough');

                    let option = $('<option>');
                    option.val(dough.name);
                    option.text(dough.name);
                    option.data('dough', dough.name);

                    select.append(option);
                }

                for (const sauce of orderPizzaViewModel.ingredients.sauces) {
                    let select = $('#pizza-sauce');

                    let option = $('<option>');
                    option.val(sauce.name);
                    option.text(sauce.name);
                    option.data('sauce', sauce.name);

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
                    option.data('spice', spice.name);

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
                    input.data('cheese', cheese.name);
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
                    input.data('vegetable', vegetable.name);
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
                    input.data('meat', meat.name);
                    label.text(meat.name);

                    if (pizza.meats.map(m => m.name).indexOf(meat.name) > -1) {
                        input.prop('checked', true);
                    }

                    inputDiv.append(input);
                    inputDiv.append(label);

                    $('#pizza-meats').append(inputDiv);


                }

                calculatePizzaPrice();

                let orderBtn = $('#order-pizza-btn');

                orderBtn.click(sendPizzaToOrder);
            });
        }
    });

}

function sendPizzaToOrder() {
    let pizza = {};

    pizza.name = $('#order-pizza-modal-label').text();
    pizza.price = Number($('#pizza-price').text().replace('$', ''));
    pizza.description = '';

    for (const size of $('#pizza-size option:selected')) {
        pizza.size = $(size).data('size');
        pizza.description += $(size).data('size') + ' ';
    }

    for (const dough of $('#pizza-dough option:selected')) {
        pizza.dough = $(dough).data('dough');
        pizza.description += $(dough).data('dough') + ' ';
    }

    for (const sauce of $('#pizza-sauce option:selected')) {
        pizza.sauce = $(sauce).data('sauce');
        pizza.description += $(sauce).data('sauce') + ' ';
    }

    pizza.spices = [];

    for (const spice of $('#pizza-spices option:selected')) {
        pizza.spices.push($(spice).data('spice'));
        pizza.description += $(spice).data('spice') + ' ';
    }

    pizza.cheeses = [];

    for (const cheese of $('#pizza-cheeses input:checked')) {
        pizza.cheeses.push($(cheese).data('cheese'));
        pizza.description += $(cheese).data('cheese') + ' ';
    }

    pizza.vegetables = [];

    for (const vegetable of $('#pizza-vegetables input:checked')) {
        pizza.vegetables.push($(vegetable).data('vegetable'));
        pizza.description += $(vegetable).data('vegetable') + ' ';
    }

    pizza.meats = [];

    for (const meat of $('#pizza-meats input:checked')) {
        pizza.meats.push($(meat).data('meat'));
        pizza.description += $(meat).data('meat') + ' ';
    }

    pizza.added = true;

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8000/order/pizza',
        data: JSON.stringify(pizza),
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
