const socket = new SockJS('/ws');
const client = Stomp.over(socket);

const user = {
    'sessionId': null
};

const onConnected = () => {
    user.sessionId = /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
    client.send('/app/user.connect', {}, JSON.stringify(user));
};

const onError = () => {
    console.log('Error');
};

window.addEventListener('beforeunload', function (e) {
    e.preventDefault();
    client.send('/app/user.disconnect', {}, JSON.stringify(user));
    socket.close();
    client.disconnect();
    e.returnValue = '';
});

client.connect({}, onConnected, onError);