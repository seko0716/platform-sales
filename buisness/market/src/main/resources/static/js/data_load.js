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
        "<h4 class=\"pull-right\">{{price}}</h4>" +
        "<h4><a href=\"/market/view/product/{{id}}\">{{title}}</a></h4>" +
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
            "    <div class=\"caption\">" +
            "      <h4>{{title}}" +
            "      </h4>" +
            "    </div>" +
            "  </div>" +
            "</div></a>{{/.}}";

        if (products.length === 0) {
            document.getElementById('history_label').hidden = true
        }
        _fillData(template, products, "#history");
    });
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

function createCategory() {
    $.ajax({
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: "/market/category",
        dataType: "json",
        data: JSON.stringify({title: "title", description: "description", parentId: "parentId"})
    }, function (category) {
        console.log(category)
    }).fail(function (error) {
        alert("error");
    });
}