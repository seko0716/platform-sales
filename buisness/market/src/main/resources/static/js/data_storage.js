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