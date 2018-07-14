function showNotification(message, url) {
    $.notifyDefaults({
        url_target: "_self"
    });
    $.notify({
        message: message,
        url: url
    });
}