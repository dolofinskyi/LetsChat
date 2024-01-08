const chatList = document.querySelector(".chat-list");
const emptyElement = document.querySelector(".people-list .empty-element");
const inputSearch = document.querySelector(".input-search");
const searchButton = document.querySelector(".search-button");
const searchUrl = "/api/user/list";

inputSearch.oninput = async () => {
    if (inputSearch.value == '') {
        await removeChildrens(chatList);
        // Get all user chats
    }
}

searchButton.onclick = async () => {
    await removeChildrens(chatList);
    let params = new URLSearchParams({'query': inputSearch.value});
    let data = await fetch(searchUrl + "?" + params.toString()).then(resp => resp.json());
    data.forEach(user => createHtmlUser(user));
}

async function createHtmlUser(username) {
    const container = document.createElement('li');
    const label = document.createElement('label');
    const avatar = document.createElement('div');
    const about = document.createElement('div');
    const name = document.createElement('div');
    const status = document.createElement('small');
    const radio = document.createElement('input');

    label.classList.add('user-container');
    avatar.classList.add('avatar');
    about.classList.add('about');
    name.classList.add('name');
    status.classList.add('status');

    radio.setAttribute("type", "radio");
    radio.setAttribute("name", "user");

    avatar.innerHTML = username[0].toUpperCase();
    name.innerHTML = username;
    status.innerHTML = 'Offline';

    chatList.appendChild(container);
    container.appendChild(label);
    label.appendChild(avatar);
    label.appendChild(about);
    label.appendChild(radio);
    about.appendChild(name);
    about.appendChild(status);
}