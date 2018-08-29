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
            $('#pizzaRadio').prop('disabled', false);
            $('#pastaRadio').prop('disabled', false);
            $('#dipsRadio').prop('disabled', false);
            $('#drinksRadio').prop('disabled', false);
            $('#orderRadio').prop('disabled', false);
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