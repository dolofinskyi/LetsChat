const socket = new SockJS('/api/v1/websocket');
const client = Stomp.over(socket);
var selectedUser;

const onConnected = () => {
    let sessionId = socket._transport.url.split('/')[7];
    client.subscribe(`/user/${sessionId}/queue/messages`, onMessage);
};

const onError = () => {
    console.log('Error');
};

window.addEventListener('beforeunload', function (e) {
    e.preventDefault();
    socket.close();
    client.disconnect();
    e.returnValue = '';
});

client.connect({}, onConnected, onError);