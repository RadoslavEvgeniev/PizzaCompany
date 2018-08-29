function sendOrder() {
    let orderId = $(this).val();

    let data = {
        id: orderId,
        finished: true
    };

    $.ajax({
        type : 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8000/order/order',
        data: data,
        success: function (success) {
            location.href = 'http://localhost:8000/orders/my';
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

    let data = {
        id: orderId,
        finished: false
    };

    $.ajax({
        type : 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8000/order/order',
        data: data,
        success: function (success) {
            localStorage.removeItem('selectedAddress');
            location.href = 'http://localhost:8000/';

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
