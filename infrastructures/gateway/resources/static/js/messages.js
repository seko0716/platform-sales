function sendOrderComment() {
    var message = {
        mess: $("#comment").val(),
        protocol: "INNER",
        to: $("#product-shop").text(),
        entityId: getId(),
        type: "ORDER_COMMENT"

    };
    post("/communication/send", message, function (data) {
        console.log(data)
    })
}