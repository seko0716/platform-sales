function createCategory() {
    var category_name = getElement('category_name').value;
    var description = getElement('description').value;
    var parentId = getElement('parent_category').value;
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