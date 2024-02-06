const registerButton = document.querySelector(".login-btn");
const usernameField = document.querySelector("#username");
const passwordField = document.querySelector("#password");

registerButton.onclick = async () => {
    const data = {
        "username": usernameField.value,
        "password": passwordField.value,
    };

    const settings = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    };

    await fetch("/auth/login", settings)
    .then(resp => window.location.replace(resp.url));
}