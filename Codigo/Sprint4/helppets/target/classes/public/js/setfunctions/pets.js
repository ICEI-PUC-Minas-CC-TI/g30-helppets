const api = new ApiManager();

(() => {
    const token = document.cookie.split(";")[0].split("=")[1];

    if (token == null) location.replace("/");

    Utilites.setupFooterContent();

    document.querySelector("#calendario-button").addEventListener("click", () => {
        location.replace("/calendario");
    });

    document.querySelector("#home-button").addEventListener("click", () => {
        location.replace("/home");
    });

    const formAddPet = document.querySelector("#pets-add-pet-form");

    formAddPet.addEventListener("submit", async (e) => {
        e.preventDefault();

        const formElements = {};

        formAddPet.childNodes.forEach((n) => {
            if (n["id"] && n["id"].includes("pets-add-pet") && n["type"] != "submit" && n["type"] != "file") {
                formElements[n["name"]] = n.value;
                n.value = "";        
            }
        });

        const image = document.querySelector("#pets-add-pet-imagem");
        
        if (image["files"][0] != undefined) {
            const reader = new FileReader();

            reader.readAsDataURL(image["files"][0]);
            
            reader.onload = async () => {
                formElements["foto"] = reader.result.split(",")[1];
                await api.insertPet(token, formElements);
            }
        }
        else {
            formElements["foto"] = null;
            console.log(formElements);
            await api.insertPet(token, formElements);
        }

        location.replace("/home");
    });
})();