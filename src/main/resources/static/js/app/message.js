const inputSend = document.querySelector(".input-send");
const sendButton = document.querySelector(".send-button");

sendButton.onclick = () => {
    let message = {
        'from': user,
        'to': selectedUser,
        'content': inputSend.value
    }
    inputSend.value = '';
}