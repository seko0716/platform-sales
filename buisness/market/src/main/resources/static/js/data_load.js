function _fillData(template, products, id) {
    $(document).ready(function () {
        $(id).html(Mustache.to_html(template, products));
    });
}

function _fillProductList(products) {
    var template =
        "{{#.}}<div class=\"col-sm-4 col-lg-4 col-md-4\">" +
        "<div class=\"thumbnail\">" +
        "<img alt=\"\" src=\"http://placehold.it/320x150\">" +
        "<div class=\"caption\">" +
        "<h4 class=\"pull-right\">$ {{price}}</h4>" +
        "<h4><a style=\"white-space: normal\" href=\"/market/view/product/{{id}}\">{{title}}</a></h4>" +
        "<p>{{description}}</p>" +
        "</div>" +
        "<div class=\"ratings\">" +
        "<p class=\"pull-right\">15 reviews</p>" +
        "<p>" +
        "<span class=\"glyphicon glyphicon-star\"></span>" +
        "<span class=\"glyphicon glyphicon-star\"></span>" +
        "<span class=\"glyphicon glyphicon-star\"></span>" +
        "<span class=\"glyphicon glyphicon-star\"></span>" +
        "<span class=\"glyphicon glyphicon-star\"></span>" +
        "</p>" +
        "</div>" +
        "</div>" +
        "</div>{{/.}}";

    _fillData(template, products, "#products");
}

function loadProducts() {
    $.get("/market/products", function (products) {
        _fillProductList(products);
    });
}


function loadChart() {
    $.get("/market/products/chart", function (products) {
        var template =
            "{{#.}}<a href=\"/market/view/product/{{id}}\"><div class=\"col-md-6\">" +
            "  <div class=\"thumbnail\">" +
            "    <img src=\"http://placehold.it/320x150\" alt=\"\">" +
            // "    <div class=\"caption\">" +
            // "      <h4>{{title}}</h4>" +
            // "    </div>" +
            "  </div>" +
            "</div></a>{{/.}}";

        if (products.length === 0) {
            document.getElementById('history_label').hidden = true
        }
        _fillData(template, products, "#history");
    });
}

function searchProducts() {
    var title = document.getElementById("search").value;
    var pay = document.getElementById("pay").value.split(",");
    var data = {priceLeft: pay[0], priceRight: pay[1], title: title};

    post("/market/products", data, function (data) {
        _fillProductList(data)
    }, function (error) {
        console.log(error)
    });
}

function getElement(id) {
    return document.getElementById(id);
}

function loadProduct() {
    var productId = getId();

    $.get("/market/product/" + productId, function (product) {
        console.log(product);

        getElement("product-title").innerText = product.title;
        getElement("product-desc").innerText = product.description;
        getElement("product-price").innerText = '$ ' + product.price;
        getElement("product-shop").innerText = product.account.marketName;


        getElement("product-info").innerHTML = product.productInfo;
        var template = "<table class=\"table table-striped\">" +
            "    <thead>" +
            "    <tr>" +
            "        <th scope=\"col\">Title</th>" +
            "        <th scope=\"col\">Value</th>" +
            "    </tr>" +
            "    </thead>" +
            "    <tbody>" +
            "    {{#.}}<tr>" +
            "        <td>{{name}}</td>" +
            "        <td>{{value}}</td>" +
            "    </tr> {{/.}}" +
            "    </tbody>" +
            "</table>";

        _fillData(template, product.characteristic, "#characteristics");
    })
}


function getCharacteristics(category, characteristics = []) {
    if (category.parent) {
        getCharacteristics(category.parent, characteristics)
    }
    category.characteristics.forEach(function (value) {
        characteristics.push(value);
    });
    return characteristics;
}

var categoriesStorage;

function loadAvailableCategories() {
    $.get("/market/categories", function (categories) {
        categoriesStorage = categories;

        var template = "<option selected disabled>select category</option>" +
            "{{#.}}<option value=\"{{id}}\">{{title}}</option>{{/.}}";
        _fillData(template, categories, "#categories");
        console.log(categoriesStorage)
    })
}

function fillCharacteristic() {
    var categoryId = getElement("categories").value;
    var category = categoriesStorage.filter(function (it) {
        return it.id === categoryId;
    });
    var characteristics = getCharacteristics(category[0]);

    var template = "{{#.}}<label class=\"col-sm-2 col-form-label\">{{name}}</label>" +
        "<div class=\"col-sm-10\">" +
        "    <input type=\"text\" id='{{name}}' class=\"form-control characteristic\">" +
        "</div>{{/.}}";
    _fillData(template, characteristics, "#characteristics")
}


function loadProductsByShop() {
    var shopName = getId();
    $.get("/market/products/market/" + shopName, function (products) {
        _fillProductList(products)
    })
}

function loadOrders() {
    $.get("/market/orders", function (orders) {
        orders.forEach(function (value) {
            if (value.status === "CREATED") {
                value.created = true;
            } else if (value.status === "PROCESSING") {
                value.processing = true
            } else if (value.status === "COMPLETED") {
                value.completed = true
            } else if (value.status === "CANCELED") {
                value.canceled = true
            }
            if (value.status === "PROCESSED") {
                value.processed = true
            }
        });
        console.log(orders);

        var template = "<table id='table_orders' class=\"table table-sm\">" +
            "<thead>" +
            "<tr>" +
            "    <th>CREATED</th>" +
            "    <th>PROCESSING</th>" +
            "    <th>PROCESSED</th>" +
            "    <th>COMPLETED</th>" +
            "    <th>CANCELED</th>" +
            "    <th>|||</th>" +
            "</tr>" +
            "</thead>" +
            "<tbody>" +
            "{{#.}}<tr>" +
            "    <td class=\"warning\">{{#created}}<a href='/market/view/order/{{id}}'>{{title}}</a>{{/created}}</td>" +
            "    <td class=\"info\">{{#processing}}<a href='/market/view/order/{{id}}'>{{title}}</a>{{/processing}}</td>" +
            "    <td class=\"primary\">{{#processed}}<a href='/market/view/order/{{id}}'>{{title}}</a>{{/processed}}</td>" +
            "    <td class=\"success\">{{#completed}}<a href='/market/view/order/{{id}}'>{{title}}</a>{{/completed}}</td>" +
            "    <td class=\"active\">{{#canceled}}<a href='/market/view/order/{{id}}'>{{title}}</a>{{/canceled}}</td>" +
            "    <td>" +
            "        {{#created}}<button onclick='cancelOrderById(\"{{id}}\")' type=\"button\" class=\"btn btn-default btn-sm\">" +
            "            <span class=\"glyphicon glyphicon-ban-circle\"></span>" +
            "        </button>{{/created}}" +
            "        {{#completed}}<button onclick='deleteOrderById(\"{{id}}\",loadOrders)' type=\"button\" class=\"btn btn-default btn-sm\">" +
            "            <span class=\"glyphicon glyphicon-trash\"></span>" +
            "        </button>{{/completed}}" +
            "        {{#canceled}}<button onclick='deleteOrderById(\"{{id}}\",loadOrders)' type=\"button\" class=\"btn btn-default btn-sm\">" +
            "            <span class=\"glyphicon glyphicon-trash\"></span>" +
            "        </button>{{/canceled}}" +
            "    </td>" +
            "</tr>{{/.}}" +
            "</tbody>" +
            "</table>";
        _fillData(template, orders, "#orders");

        $('#table_orders').dataTable({
            "aoColumns": [
                null,
                null,
                null,
                null,
                null,
                {"bSortable": false}
            ]
        });
        // $('#table_orders').DataTable();
    });



}


function loadOrder() {
    var orderId = getId();
    $("#cancel").hide();
    $("#complete").hide();
    $("#delete").hide();
    $("#complete").hide();
    $.get("/market/order/" + orderId, function (order) {
        console.log(order);
        getElement("product-title").innerText = order.title;
        getElement("product-desc").innerText = order.product.description;
        getElement("product-price").innerText = '$ ' + order.product.price;
        getElement("product-count").innerText = 'count: ' + order['count'];
        getElement("product-shop").innerText = order.product.account.marketName;

        if (order.statusHistory.find(function (value) {
                return "CANCELED" === value.first
            })) {
            getElement("progress").innerHTML = "<center>Canceled</center>";
        } else {
            if (order.statusHistory.find(function (value) {
                    return "CREATED" === value.first
                })) {
                getElement("created").hidden = false;
            }
            if (order.statusHistory.find(function (value) {
                    return "COMPLETED" === value.first
                })) {
                getElement("completed").hidden = false;
            }
            if (order.statusHistory.find(function (value) {
                    return "PROCESSING" === value.first
                })) {
                getElement("processing").hidden = false;

            }
        }
        if (order.status === "CREATED") {
            $("#cancel").show();
        } else if (order.status === "PROCESSING") {
            $("#complete").show();
        } else if (order.status === "PROCESSED") {
            $("#complete").show();
        } else if (order.status === "COMPLETED") {
            $("#delete").show();
        } else if (order.status === "CANCELED") {
            $("#delete").show();
        }
    })
}

function cancelOrderById(id) {
    post("/market/order/cancel/" + id, null, loadOrders, function (err) {
        console.log(err)
    });
}

function deleteOrderById(id, operation=loadOrders) {
    post("/market/order/delete/" + id, null, operation, function (err) {
        console.log(err)
    });
}

function cancelOrder() {
    var id = getId();
    post("/market/order/cancel/" + id, null, loadOrder, function (err) {
        console.log(err)
    });
}

function deleteOrder() {
    var id = getId();
    post("/market/order/delete/" + id, null, function () {
        window.location.replace("/market/view/orders")
    }, function (err) {
        console.log(err)
    });

}

function completeOrder() {
    var id = getId();
    post("/market/order/complete/" + id, null, loadOrder, function (err) {
        console.log(err)
    });
}


function loadCart() {
    $.get("/market/orders/cart", function (orders) {
        var template = "<table class=\"table table-hover\">" +
            "<thead>" +
            "<tr>" +
            "    <th>Product</th>" +
            "    <th>Quantity</th>" +
            "    <th class=\"text-center\">Price</th>" +
            "    <th class=\"text-center\">Total</th>" +
            "    <th> </th>" +
            "</tr>" +
            "</thead>" +
            "<tbody>" +

            "{{#.}}<tr>" +
            "    <td class=\"col-sm-8 col-md-6\">" +
            "        <div class=\"media\">" +
            "            <a class=\"thumbnail pull-left\" href=\"#\">" +
            " <img class=\"media-object\"" +
            "        src=\"http://icons.iconarchive.com/icons/custom-icon-design/flatastic-2/72/product-icon.png\"" +
            "                                                          style=\"width: 72px; height: 72px;\">" +
            "            </a>" +
            "            <div class=\"media-body\">" +
            "<span hidden class='orderId'>{{id}}</span>" +
            "                <h4 class=\"media-heading\"><a href=\"/market/view/product/{{product.id}}\">{{title}}</a></h4>" +
            "                <h5 class=\"media-heading\"> by " +
            "<a href=\"/market/view/shop/{{product.account.marketName}}\" class=\"text-success\">{{product.account.marketName}}</a></h5>" +

            "            </div>" +
            "        </div>" +
            "    </td>" +
            "    <td class=\"col-sm-1 col-md-1\" style=\"text-align: center\">" +
            "        <input type=\"email\" class=\"form-control\" id=\"exampleInputEmail1\" " +
            "onchange=\"targetSum({{product.price}}, this.value, '{{id}}')\" value=\"{{count}}\">" +
            "    </td>" +
            "    <td class=\"col-sm-1 col-md-1 text-center\"><strong>${{product.price}}</strong></td>" +
            "    <td class=\"col-sm-1 col-md-1 text-center\"><strong id='{{id}}' class='Total'>$!!!!</strong></td>" +
            "    <td class=\"col-sm-1 col-md-1\">" +
            "        <button type=\"button\" class=\"btn btn-danger\" onclick='deleteOrderById(\"{{id}}\",loadCart)'>" +
            "            <span class=\"glyphicon glyphicon-remove\"></span>" +
            "        </button>" +
            "        <button type=\"button\" class=\"btn btn-success buy\" onclick='buyOrder(\"{{id}}\")'> " +
            "            <span class=\"glyphicon glyphicon-play\"></span>" +
            "        </button>" +
            "    </td>" +
            "</tr>{{/.}}" +

            "<tr>" +
            "    <td>  </td>" +
            "    <td>  </td>" +
            "    <td>  </td>" +
            "    <td><h5>Subtotal</h5></td>" +
            "    <td class=\"text-right\"><h5><strong id='sum'>$24.59</strong></h5></td>" +
            "</tr>" +
            "<tr>" +
            "    <td>  </td>" +
            "    <td>  </td>" +
            "    <td>  </td>" +
            "    <td>" +
            "        <button type=\"button\" class=\"btn btn-default\" onclick='window.location.replace(\"/market/view/products\")'>" +
            "            <span class=\"glyphicon glyphicon-shopping-cart\"></span> Continue Shopping" +
            "        </button>" +
            "    </td>" +
            "    <td>" +
            "        <button type=\"button\" class=\"btn btn-success\" onclick='buyAll()'>" +
            "            Checkout <span class=\"glyphicon glyphicon-play\"></span>" +
            "        </button>" +
            "    </td>" +
            "</tr>" +
            "</tbody>" +
            "</table>";

        _fillData(template, orders, "#cart");

        orders.forEach(function (value) {
            targetSum(value.product.price, value.count, value.id, false)
        });
        calculateAllSum()
    });
}


function targetSum(price, count, id, send=true) {
    getElement(id).innerText = "$" + price * count;
    calculateAllSum();
    if (send) {
        post("/market/order/cart/" + id + "/" + count, null, function () {

        }, function (err) {

        })
    }
}

function calculateAllSum() {
    var sum = 0;
    Array.apply(null, document.getElementsByClassName("Total")).map(function (it) {
        return Number.parseInt(it.innerText.split("$")[1])
    }).forEach(function (value) {
        sum += value;
    });
    getElement("sum").innerText = sum;
}


function updateCartCount() {
    $.get("/market/orders/cart", function (orders) {
        getElement("cart_count").innerText = " Count: " + orders.length
    });
}

function addToCart() {
    var id = getId();
    put("/market/order/cart/" + id, null, function () {
        updateCartCount()
    }, function (err) {
        console.log(err)
    })
}

function buyOrder(orderId) {
    post("/market/order/buyCart/" + orderId, null, function (order) {
        window.location.replace("/market/view/order/" + order.id);
    }, function (err) {
        console.log(err)
    })
}

function buy(id = getId()) {
    put("/market/order", {productId: id, count: 1}, function (order) {
        window.location.replace("/market/view/order/" + order.id);
    }, function (err) {
        console.log(err)
    })
}

function buyAll() {
    Array.apply(null, document.getElementsByClassName("orderId")).forEach(function (value) {
        var id = value.innerText;
        post("/market/order/buyCart/" + id, null, function () {

        }, function (err) {
            console.log(err)
        });
    });
    window.location.replace("/market/view/orders");
}






















