const api = new ApiManager();

function returnLoginForm() {
    const titleP = document.createElement("p"),
    emailInput = document.createElement("input"),
    passwordInput = document.createElement("input"),
    submitInput = document.createElement("input"),
    changeFormP = document.createElement("p");
    
    titleP.innerText = "Entrar";

    emailInput.type = "text";
    passwordInput.type = "text";

    submitInput.type = "submit";
    submitInput.value = "Enviar";

    changeFormP.id = "change-register-login-form";
    changeFormP.innerText = "Criar conta";

    changeFormP.addEventListener("click", () => {
            changeForm(false);
        }
    );
    
    submitInput.addEventListener("click", async () => {
        try{
            token = await api.login(
                {
                    email: emailInput.value,
                    senha: passwordInput.value
                }
            );
        
            if (token.token != null) {
                document.cookie = "token=".concat(token.token);
                location.reload();
            }
            else {
                Utilites.popupError(token);
            }
        }
        catch(e) {
            Utilites.popupError(e);
        }
    })

    return [titleP, emailInput, passwordInput, submitInput, changeFormP];
}

function returnRegisterForm() {
    
}

function changeForm(isRegister) {
    const registerLoginForm = document.querySelector("#register-login-form");

    const nodes = isRegister ? returnLoginForm() : returnRegisterForm();

    registerLoginForm.innerHTML = "";

    nodes.forEach((node => {
            registerLoginForm.appendChild(node);
        })
    )
}

(() => {
    const registerLoginForm = document.querySelector("#register-login-form");

    registerLoginForm.addEventListener("submit", (e) => {
        e.preventDefault();
    })

    changeForm(true);
})();