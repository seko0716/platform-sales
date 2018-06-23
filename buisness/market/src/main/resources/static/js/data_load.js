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

function loadAvailableCategories() {
    $.get("/market/categories", function (categories) {
        console.log(categories)
    })
}

function loadCategoryById(id) {
    $.get("/market/category/" + id, function (category) {

    })
}

function loadProductsByShop(shopName) {
    $.get("/market/products/market/" + shopName, function (products) {
        _fillProductList(products)
    })
}