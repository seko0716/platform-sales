$(window).load(function () {

    let user = getCurrentUser();

    if (!user) {
        showLoginForm();
    }
});


function getCurrentUser() {

    if (localStorage.getItem('user')) {
        return localStorage.getItem('user');
    }

    const token = getOauthTokenFromStorage();
    let user = null;

    if (token) {
        $.ajax({
            url: '/uaa/users/current',
            datatype: 'json',
            type: 'get',
            headers: {'Authorization': 'Bearer ' + token},
            async: false,
            success: function (data) {
                user = data;
                localStorage.setItem('user', data);
            },
            error: function () {
                removeOauthTokenFromStorage();
            }
        });
    }

    return user;
}