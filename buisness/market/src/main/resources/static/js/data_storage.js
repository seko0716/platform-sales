function createCategory() {
    post("/market/category", {title: "title", description: "description", parentId: "parentId"}, function (category) {
        console.log(category)
    }, function (error) {
        alert("error");
    });
}