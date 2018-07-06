function createCategory() {
    var category_name = getElement('category_name').value;
    var description = getElement('description').value;
    var parentId = getElement('categories').value;
    var characteristics = Array.apply(null, getElement('properties').options).map(function (it) {
        return {name: it.value, value: null};
    });
    var data = {title: category_name, description: description, parentId: parentId, characteristics: characteristics};
    put("/market/category", data, function (category) {
        console.log(category)
    }, function (error) {
        alert("error");
    });
}

function createProduct() {
    var product_title = getElement('product_title').value;
    var description = getElement('description').value;
    var categoryId = getElement('categories').value;
    var price = getElement('price').value;
    var productInfo = getElement('product_info').value;
    var characteristics = Array.apply(null, document.getElementsByClassName("characteristic")).map(function (it) {
        return {name: it.id, value: it.value};
    });
    var data = {
        title: product_title,
        description: description,
        price: price,
        categoryId: categoryId,
        characteristics: characteristics,
        productInfo: productInfo
    };
    put("/market/product", data, function (product) {
        window.location.replace("/market/view/product/" + product.id);
    }, function (error) {
        alert("error");
    });
}