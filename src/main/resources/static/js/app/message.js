const chatHistory = document.querySelector('.chat-history ul');
const inputSend = document.querySelector(".input-send");
const sendButton = document.querySelector(".send-button");

const onMessage = async(data) => {
    const message = JSON.parse(data.body);

    if(user == message.from && selectedUser == message.to ||
       user == message.to && selectedUser == message.from) {
        await createMessage(message);
    }
}

async function createMessage(message) {
    let messageContainer = document.createElement('li');
    let messageData = document.createElement('div');

    let fromUser = document.createElement('div');
    let designElement = document.createElement('span');
    let dataTime = document.createElement('div');

    let messageContent = document.createElement('div');

    messageData.classList.add('message-data');
    fromUser.classList.add('from-user');
    dataTime.classList.add('message-data-time');
    messageContent.classList.add('message');

    if (user == message.from) {
        messageContainer.classList.add('float-right');
        messageContainer.classList.add('my-container');
        messageContent.classList.add('my-message');
    } else {
        messageContainer.classList.add('other-container');
        messageContent.classList.add('other-message');
    }


    fromUser.innerHTML = message.from;
    designElement.innerHTML = "->";
    dataTime.innerHTML = new Date().toLocaleString();

    messageContent.innerHTML = message.content;

    chatHistory.appendChild(messageContainer);
    messageContainer.appendChild(messageData);
    messageData.appendChild(fromUser);
    messageData.appendChild(designElement);
    messageData.appendChild(dataTime);
    messageContainer.appendChild(messageContent);
}

sendButton.onclick = () => {
    let message = {
        'to': selectedUser,
        'content': inputSend.value
    }
    inputSend.value = '';
    client.send("/api/v1/message/send", {}, JSON.stringify(message));
}