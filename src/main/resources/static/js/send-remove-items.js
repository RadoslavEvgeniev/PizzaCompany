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

    let data = {};

    if (itemType === 'pizza') {
        data.description = itemName;
        data.added = added;
    } else {
        data = {
            name: itemName,
            added: added
        };
    }

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

