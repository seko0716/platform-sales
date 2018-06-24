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
        });
        console.log(orders);

        var template = "<table class=\"table table-sm\">" +
            "<thead>" +
            "<tr>" +
            "    <th>CREATED</th>" +
            "    <th>PROCESSING</th>" +
            "    <th>COMPLETED</th>" +
            "    <th>CANCELED</th>" +
            "    <th>|||</th>" +
            "</tr>" +
            "</thead>" +
            "<tbody>" +
            "{{#.}}<tr>" +
            "    <td class=\"warning\"><a href='/market/view/order/{{id}}'>{{#created}}{{title}}{{/created}}</a></td>" +
            "    <td class=\"info\"><a href='/market/view/order/{{id}}'>{{#processing}}{{title}}{{/processing}}</a></td>" +
            "    <td class=\"success\"><a href='/market/view/order/{{id}}'>{{#completed}}{{title}}{{/completed}}</a></td>" +
            "    <td class=\"active\"><a href='/market/view/order/{{id}}'>{{#canceled}}{{title}}{{/canceled}}</a></td>" +
            "    <td>" +
            "        {{#created}}<button onclick='cancelOrderById(\"{{id}}\")' type=\"button\" class=\"btn btn-default btn-sm\">" +
            "            <span class=\"glyphicon glyphicon-ban-circle\"></span>" +
            "        </button>{{/created}}" +
            "        {{#processing}}<button onclick='cancelOrderById(\"{{id}}\")' type=\"button\" class=\"btn btn-default btn-sm\">" +
            "            <span class=\"glyphicon glyphicon-ban-circle\"></span>" +
            "        </button>{{/processing}}" +
            "        {{#completed}}<button onclick='deleteOrderById(\"{{id}}\")' type=\"button\" class=\"btn btn-default btn-sm\">" +
            "            <span class=\"glyphicon glyphicon-trash\"></span>" +
            "        </button>{{/completed}}" +
            "        {{#canceled}}<button onclick='deleteOrderById(\"{{id}}\")' type=\"button\" class=\"btn btn-default btn-sm\">" +
            "            <span class=\"glyphicon glyphicon-trash\"></span>" +
            "        </button>{{/canceled}}" +
            "    </td>" +
            "</tr>{{/.}}" +
            "</tbody>" +
            "</table>";
        _fillData(template, orders, "#orders")
    })
}


function loadOrder() {
    var orderId = getId();
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
            $("#cancel").show();
        } else if (order.status === "COMPLETED") {
            $("#delete").show();
        } else if (order.status === "CANCELED") {
            $("#delete").show();
        }
    })
}

function cancelOrderById(id) {
    console.log("cancel " + id);
    loadOrders();
}

function deleteOrderById(id) {
    console.log("delete " + id);
    loadOrder();
}

function cancelOrder() {
    var id = getId();
    loadOrder();
}

function deleteOrder() {
    var id = getId();
    window.location.replace("/market/view/orders");
}

function completeOrder() {
    var id = getId();
    loadOrder();
}


function loadCategoryById() {
    var id = getId();
    $.get("/market/category/" + id, function (category) {

    })
}























