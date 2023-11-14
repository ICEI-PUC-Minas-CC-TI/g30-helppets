const api = new ApiManager();

(() => {
    const token = document.cookie.split(";")[0].split("=")[1];

    Utilites.setupFooterContent();

    const formAddPet = document.querySelector("#pets-add-pet-form");

    formAddPet.addEventListener("submit", (e) => {
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
            
            reader.onload = () => {
                formElements["foto"] = reader.result.split(",")[1];
                api.insertPet(token, formElements);
                image.value = "";
            }
        }
        else {
            formElements["foto"] = null;
            console.log(formElements);
            api.insertPet(token, formElements);
        }
    });
})();