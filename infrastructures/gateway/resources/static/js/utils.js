function post(url, data, success, fail) {
    request("post", url, data, success, fail)
}

function put(url, data, success, fail) {
    request("put", url, data, success, fail)
}

function del(url, data, success, fail) {
    request("delete", url, data, success, fail)
}

function get(url, success, fail) {
    request("get", url, null, success, fail)
}

function failDefault(error) {
    console.log(error)
}

function request(method, url, data, success, fail = failDefault) {
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
    return window.location.href.split("/").slice(-1)[0].split("#")[0].split("?")[0]
}

function getAuthorization() {
    const token = getOauthTokenFromStorage();
    if (token == null) {
        return ""
    }
    return "Bearer " + token
}

function getOauthTokenFromStorage() {
    return localStorage.getItem('token');
}

function removeOauthTokenFromStorage() {
    return localStorage.removeItem('token');
}

function showLoginForm() {
    window.location.replace("/view/login");
}