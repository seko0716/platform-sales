function post(url, data, success, fail) {
    request("post", url, data, success, fail)
}

function put(url, data, success, fail) {
    request("put", url, data, success, fail)
}

function del(url, data, success, fail) {
    request("delete", url, data, success, fail)
}

function request(method, url, data, success, fail) {
    function failDefault(error) {
        console.log(error)
    }

    $.ajax({
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: url,
        dataType: "json",
        data: JSON.stringify(data)
    }).fail(function (error) {
        fail(error)
    }).done(function (data) {
        success(data)
    });
}

function getId() {
    return window.location.href.split("/").slice(-1)[0]
}