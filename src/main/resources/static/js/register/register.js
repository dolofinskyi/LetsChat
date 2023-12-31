const registerButton = document.querySelector(".register-btn");
const usernameField = document.querySelector("#username");
const passwordField = document.querySelector("#password");
const repeatPasswordField = document.querySelector("#repeat-password");

registerButton.onclick = async () => {
    const data = {
        "username": usernameField.value,
        "password": passwordField.value,
        "repeatPassword": repeatPasswordField.value,
    };

    const settings = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    };

    await fetch("/auth/register", settings)
    .then(resp => resp.json())
    .then(data => {
        if(data.token !== null) {
            window.location.replace("/app");
        }
    });
}