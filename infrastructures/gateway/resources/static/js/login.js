function login() {
    var credential = {
        client_id: "browser",
        username: document.getElementById("username").value,
        password: document.getElementById("password").value,
        grant_type: "password"
    };

    $.ajax({
        type: "POST",
        headers: {
            'Authorization': 'Basic YnJvd3Nlcjo=',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        url: "/uaa/oauth/token",
        data: credential,
        success: function (msg) {
            document.cookie = "access_token=" + msg.access_token;
            document.cookie = "token_type=" + msg.token_type;
            window.location.replace("/view/products/");
        },
        error: function (msg) {
            console.log("error");
            console.log(msg);
        }
    });
}
