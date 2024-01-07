const socket = new SockJS('/ws');
const client = Stomp.over(socket);

const onConnected = () => {

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