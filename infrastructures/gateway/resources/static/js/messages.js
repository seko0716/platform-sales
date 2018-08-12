function sendOrderComment() {
    var message = {
        mess: $("#comment").val(),
        protocol: "internal",
        to: $("#product-shop").text(),
        entityId: getId(),
        type: "ORDER_COMMENT"

    };
    post("/communication/send", message, function (data) {
        $("#comment").val("");
        getOrderComments()
    })
}


function sendAccountInternalMessage(message, accountId, id) {
    var mess = {
        mess: message,
        protocol: "internal",
        to: accountId,
        entityId: id,
        type: "MARKET_EVENT"

    };
    post("/communication/send", mess, function (data) {
    })

}



function getOrderComments() {
    const id = getId();
    const type = "ORDER_COMMENT";

    get("/communication/messages/" + type + "/" + id, function (data) {
        console.log(data);

        const template = "{{#.}}<article class=\"comment\">\n" +
            "                    <a class=\"comment-img\" href=\"#non\">\n" +
            "                        <img src=\"https://pbs.twimg.com/profile_images/444197466133385216/UA08zh-B.jpeg\" alt=\"\"\n" +
            "                             width=\"50\" height=\"50\">\n" +
            "                    </a>\n" +
            "                    <div class=\"comment-body\">\n" +
            "                        <div class=\"text\">\n" +
            "                            <p>{{mess}}</p>\n" +
            "                        </div>\n" +
            "                        <p class=\"attribution\">by <a href=\"#non\">{{from}}</a> {{creationDate}}</p>\n" +
            "                    </div>\n" +
            "                </article>{{/.}}";
        _fillData(template, data, "#comments")
    })
}