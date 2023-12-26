const registerButton = document.querySelector(".register-btn");
const usernameField = document.querySelector("#username");
const passwordField = document.querySelector("#password");
const repeatPasswordField = document.querySelector("#repeat-password");

console.log(window.frames.top.document.referrer);

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

    const response = await fetch("/auth/register", settings);
    const result = await response.json();

    if (result.authorization !== null) {
        localStorage.setItem("authorization", result.authorization);
        localStorage.setItem("subject", usernameField.value);

        const redirect = await fetch("/app", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + result.authorization,
                "Subject": usernameField.value,
            },
        }).then(page => page.text()).then(content => {
            window.history.pushState({location: "app"}, "App", "/app");
            document.open();
            document.write(content);
            document.close();
        });
    }
}