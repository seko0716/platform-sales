function showNotification(message) {
    console.log(message);
    $.notify({
        message: message
    });
}

function notify(message) {
    showNotification(message.mess);

}

var stompFailureCallback = function (error) {
    setTimeout(connect, 1000);
    console.log('STOMP: Reconecting in 1 seconds');
};

function connect() {
    // Create and init the SockJS object
    const socket = new SockJS('/internalsender/ws' + '?access_token=' + getOauthTokenFromStorage());
    const stompClient = Stomp.over(socket);
    // Subscribe the '/notify' channell
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/user/queue/notify', function (notification) {
            // Call the notify function when receive a notification
            notify(JSON.parse(notification.body));
        });
    }, stompFailureCallback);
}