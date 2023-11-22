const api = new ApiManager();

function returnPetContainer(petData = {}) {
    const petContainerDiv = document.createElement("div"),
          petInfoContainerDiv = document.createElement("div"),
          petNomeP = document.createElement("p"),
          racaP = document.createElement("p"),
          fotoImg = document.createElement("img");

    petNomeP.innerText = petData.nome;
    racaP.innerText = petData.raca;

    fotoImg.src = "data:image/jpeg;base64,".concat(petData["foto"]);
    fotoImg.alt = "";

    petContainerDiv.appendChild(fotoImg);
    
    petInfoContainerDiv.appendChild(petNomeP);
    petInfoContainerDiv.appendChild(racaP);

    petContainerDiv.appendChild(petInfoContainerDiv);

    petContainerDiv.className = "pet-container-div";

    return petContainerDiv;
}

function returnVacinaHomeContainer(vacinaData) {
    const vacinaHomeContainerDiv = document.createElement("div"),
          vacinaNomeP = document.createElement("p"),
          vacinaDataP = document.createElement("p"),
          vacinaDescricaoP = document.createElement("p"),
          vacinaStatusP = document.createElement("p");

    vacinaNomeP.innerText = "Nome: " + vacinaData["nome"];
    vacinaDataP.innerText = "Data: " + new Date(vacinaData["data"]).toISOString()
        .split("T")[0].replaceAll("-", "/");
    vacinaDescricaoP.innerText = "Descrição: " + vacinaData["descricao"];
    vacinaStatusP.innerText = "Status: " + (Boolean(vacinaData["tomou"]) ? "Tomou" : "Não tomou");
    
    vacinaHomeContainerDiv.appendChild(vacinaNomeP);
    vacinaHomeContainerDiv.appendChild(vacinaDataP);
    vacinaHomeContainerDiv.appendChild(vacinaDescricaoP);
    vacinaHomeContainerDiv.appendChild(vacinaStatusP);

    vacinaHomeContainerDiv.className = "vacina-home-container";

    return vacinaHomeContainerDiv;
}

(async () => {
    const token = document.cookie.split(";")[0].split("=")[1];

    if (token == null) location.replace("/");

    Utilites.setupFooterContent();

    const calendarioButton = document.querySelector("#calendario-button");
    
    const addPetButton = document.querySelector("#add-pet-button");

    calendarioButton.addEventListener("click", () => {
        location.replace("/calendario");
    })

    addPetButton.addEventListener("click", () => {
        location.replace("/pets");
    })

    const listPetsContainer = document.querySelector("#list-pet-container"),
          deletePetContainer = document.querySelector("#delete-pet-container");

    let pets = [];

    try {
        pets = await api.listRegisteredPets(token, 10);
        pets = pets["pets"];
    }
    catch (e) {
        Utilites.popupError(e);
    }

    pets.forEach((p) => {
        const petContainer = returnPetContainer(p);
        
        const listPetContainerFirst = petContainer.cloneNode(true);
        
        const deletePetContainerFirst = petContainer.cloneNode(true);

        deletePetContainerFirst.addEventListener("click", async () => {
            try{
                const apiReturn = await api.deletePetById(token, p["petsId"]);

                if (apiReturn.error) {
                    Utilites.popupError(apiReturn.e);
                }

                else {
                    location.reload();
                }
            }
            catch(e) {
                Utilites.popupError(e);
            }
        });

        listPetContainerFirst.addEventListener("click", async () => {
            const petInfoContainerDiv = document.querySelector("#pet-info-container");

            petInfoContainerDiv.innerHTML = "";

            const nomePetHomeH1 = document.createElement("h1"),
                petImageHomeImg = document.createElement("img"),
                vacinasHomeDiv = document.createElement("div"),
                adicionarVacinasHomeButton = document.createElement("button");;

            petInfoContainerDiv.appendChild(petImageHomeImg);
            petInfoContainerDiv.appendChild(nomePetHomeH1);
            petInfoContainerDiv.appendChild(vacinasHomeDiv);
            petInfoContainerDiv.appendChild(adicionarVacinasHomeButton);

            petImageHomeImg.id = "pet-image-home";

            nomePetHomeH1.id = "nome-pet-home";

            vacinasHomeDiv.id = "vacinas-home";

            adicionarVacinasHomeButton.id = "adicionar-vacinas-home";

            adicionarVacinasHomeButton.innerText = "Adicionar vacinas";

            adicionarVacinasHomeButton.addEventListener("click", () => {
                Utilites.generateInsertVacina(api, p["petsId"]);
            });

            nomePetHomeH1.innerText = p.nome;

            petImageHomeImg.src = p.foto != null ? "data:image/jpg;base64,".concat(p["foto"]) : "";
            petImageHomeImg.alt = "";

            let vacinas = [];

            try {
                vacinas = await api.returnVacinasByPetId(token, p["petsId"], 10);

                vacinas = vacinas["vacinas"];
            }
            catch (e) {
                Utilites.popupError(e);
            }

            vacinas.forEach((v) => {
                const vacinaHomeContainer = returnVacinaHomeContainer(v);

                vacinasHomeDiv.appendChild(vacinaHomeContainer);
            });
        })

        listPetsContainer.appendChild(listPetContainerFirst);

        deletePetContainer.appendChild(deletePetContainerFirst);
    })
})();