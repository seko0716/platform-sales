function showLoginForm() {
    window.location.replace("/view/login");
}

$(window).load(function () {

    var user = getCurrentUser();

    if (!user) {
        showLoginForm();
    }
});


function getCurrentUser() {

    var token = getOauthTokenFromStorage();
    var user = null;

    if (token) {
        $.ajax({
            url: '/uaa/users/current',
            datatype: 'json',
            type: 'get',
            headers: {'Authorization': 'Bearer ' + token},
            async: false,
            success: function (data) {
                user = data;
            },
            error: function () {
                removeOauthTokenFromStorage();
            }
        });
    }

    return user;
}