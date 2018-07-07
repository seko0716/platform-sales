function createCategory() {
    const category_name = getElement('category_name').value;
    const description = getElement('description').value;
    const parentId = getElement('categories').value;
    const characteristics = Array.apply(null, getElement('properties').options).map(function (it) {
        return {name: it.value, value: null};
    });
    const data = {title: category_name, description: description, parentId: parentId, characteristics: characteristics};
    put("/market/category", data, function (category) {
        console.log(category)
    }, function (error) {
        console.log(error)
    });
}

function createProduct() {
    const product_title = getElement('product_title').value;
    const description = getElement('description').value;
    const categoryId = getElement('categories').value;
    const price = getElement('price').value;
    const productInfo = getElement('product_info').value;
    const characteristics = Array.apply(null, document.getElementsByClassName("characteristic")).map(function (it) {
        return {name: it.id, value: it.value};
    });
    const data = {
        title: product_title,
        description: description,
        price: price,
        categoryId: categoryId,
        characteristics: characteristics,
        productInfo: productInfo
    };
    put("/market/product", data, function (product) {
        window.location.replace("/view/product/" + product.id);
    }, function (error) {
        console.log(error)
    });
}