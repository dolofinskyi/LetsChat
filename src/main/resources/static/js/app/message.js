const inputSend = document.querySelector(".input-send");
const sendButton = document.querySelector(".send-button");

sendButton.onclick = () => {
    inputSend.value = '';
    console.log("click");
}