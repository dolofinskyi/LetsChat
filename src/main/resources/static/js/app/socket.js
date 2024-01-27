const socket = new SockJS('/api/v1/websocket');
const client = Stomp.over(socket);

const onMessage = message => {
    console.log(message);
}

const onConnected = () => {
    client.subscribe(`/user/${user}/messages/queue`, onMessage);
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