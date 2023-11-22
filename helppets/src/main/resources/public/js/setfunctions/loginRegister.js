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

    emailInput.placeholder = "Email";
    passwordInput.placeholder = "Senha";
    

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
                    senha: passwordInput.value,
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
    const titleP = document.createElement("p"),
    emailInput = document.createElement("input"),
    nomeInput = document.createElement("input"),
    passwordInput = document.createElement("input"),
    submitInput = document.createElement("input"),
    changeFormP = document.createElement("p");
    
    titleP.innerText = "Criar conta";

    nomeInput.type = "text";
    emailInput.type = "text";
    passwordInput.type = "text";

    nomeInput.placeholder = "Nome";
    emailInput.placeholder = "Email";
    passwordInput.placeholder = "Senha";

    submitInput.type = "submit";
    submitInput.value = "Enviar";

    changeFormP.id = "change-register-login-form";
    changeFormP.innerText = "Entrar";

    changeFormP.addEventListener("click", () => {
            changeForm(true);
        }
    );
    
    submitInput.addEventListener("click", async () => {
        try{
            const userData = await api.register(
                {
                    nome: nomeInput.value,
                    email: emailInput.value,
                    senha: passwordInput.value
                }
            );

            if (!userData["error"]) location.reload();
            else Utilites.popupError(userData["error"]);
        }
        catch(e) {
            Utilites.popupError(e);
        }
    })

    return [titleP, nomeInput, emailInput, passwordInput, submitInput, changeFormP];
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
    const token = document.cookie.split(";")[0].split("=")[1];

    if (token != null) location.replace("/home");

    Utilites.setupFooterContent();

    const registerLoginForm = document.querySelector("#register-login-form");

    registerLoginForm.addEventListener("submit", (e) => {
        e.preventDefault();
    })

    changeForm(true);
})();