function createCategory() {
    const category_name = getElement('category_name').value;
    const description = getElement('description').value;
    const parentId = getElement('categories').value;
    const characteristics = Array.apply(null, getElement('properties').options).map(function (it) {
        return {name: it.value, value: null};
    });
    const data = {
        title: category_name,
        description: description,
        parentId: parentId,
        characteristics: characteristics,
        accountId: $('#market_id').text()
    };
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
        productInfo: productInfo,
        accountId: $('#market_id').text()
    };
    put("/market/product", data, function (product) {
        window.location.replace("/view/product/" + product.id);
    }, function (error) {
        console.log(error)
    });
}

function calculateGender() {
    if (getElement("femaleRadio").checked)
        return "FEMALE";
    if (getElement("maleRadio").checked)
        return "MALE";
    if (getElement("uncknownRadio").checked)
        return "UNCKNOWN";
}

function createAccount() {
    const data = {
        fullName: getElement("fullName").value,
        firstName: getElement("fist_name").value,
        lastName: getElement("last_name").value,
        email: getElement("email").value,
        password: getElement("password").value,
        marketName: getElement("market_name").value,
        birthDay: getElement("birthDate").value,
        country: getElement("country").value,
        gender: calculateGender()

    };
    put("/account/create", data, function (data) {
        console.log(data)
    })
}

function updateAccount() {

    const data = {
        marketName: $('#marketName').val(),
        marketDescription: $('#marketDescription').val(),
        marketId: $('#market_id').text(),
        marketImages: Array.apply(null, getElement("images").children).map(it => it.children[0].currentSrc;)
}
    post("/account/update", data, function (data) {
        location.reload();
    })

}

function updateUser() {
    const data = {
        fullName: getElement("fullName").value,
        firstName: getElement("fist_name").value,
        lastName: getElement("last_name").value,
        email: getElement("email").value,
        password: getElement("password").value,
        birthDay: getElement("birthDate").value,
        country: getElement("country").value,
        gender: calculateGender()

    };
    post("/account/user/update", data, function (data) {
        location.reload();
    })
}