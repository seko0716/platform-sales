function updateDB() {
    $(document).ready(function () {
        $.ajax({
            url: rootUrl + "/rest/write/init"
        }).then(function (data) {
            console.log(data);
        });
    });
}
