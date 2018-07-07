function post(url, data, success, fail) {
    request("post", url, data, success, fail)
}

function put(url, data, success, fail) {
    request("put", url, data, success, fail)
}

function del(url, data, success, fail) {
    request("delete", url, data, success, fail)
}

function get(url, data, success, fail) {
    request("get", url, data, success, fail)
}

function get(url, success, fail) {
    request("get", url, null, success, fail)
}

function request(method, url, data, success, fail) {
    function failDefault(error) {
        console.log(error)
    }

    $.ajax({
        method: method,
        headers: {
            'Authorization': getAuthorization(),
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

function getCookie(name) {
    var matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

function getAuthorization() {
    return getCookie("token_type") + " " + getCookie("access_token")
}

function isAuthorize(authorize = function () {

}, anon = function () {
    window.location.replace("/view/login");
}) {
    get("/uaa/users/current", function (message) {
        authorize()
    }, function (error) {
        anon()
    });
}