const api = new ApiManager();

function returnPetContainer(petData = {}) {
    const petContainerDiv = document.createElement("div"),
          petInfoContainerDiv = document.createElement("div"),
          petNomeP = document.createElement("p"),
          racaP = document.createElement("p"),
          fotoImg = document.createElement("img");

    petNomeP.innerText = petData.nome;
    racaP.innerText = petData.raca;

    fotoImg.src = "data:image/jpeg;base64,".concat(btoa(String.fromCharCode(...new Uint8Array(petData.foto))));
    fotoImg.alt = "Foto";

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
    vacinaDataP.innerText = "Data: " + vacinaData["data"];
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
    Utilites.setupFooterContent();
    
    const adicionarVacinasHomeButton = document.querySelector("#adicionar-vacinas-home");

    const calendarioButton = document.querySelector("#calendario-button");
    
    const addPetButton = document.querySelector("#add-pet-button");

    calendarioButton.addEventListener("click", () => {
        // TODO: add redirect
    })

    addPetButton.addEventListener("click", () => {
        // TODO: add redirect
    })

    adicionarVacinasHomeButton.addEventListener("click", () => {
        Utilites.generateInsertVacina();
    })

    const listPetsContainer = document.querySelector("#list-pet-container"),
          deletePetContainer = document.querySelector("#delete-pet-container"),
          vacinasHome = document.querySelector("#vacinas-home");
          queryParam = new URLSearchParams(),
          petId = queryParam.get("petId");

    const token = document.cookie.split(";")[0].split("=")[1];

    // const pets = await api.listRegisteredPets(token, 10); -> Later uncoment this.

    // const vacinas = await api.returnVacinasByPetId(token, petId, 10); -> Later uncoment this.

    const pets = [{nome: "teste", raca: "teste", petsId: -1}, {nome: "teste", raca: "teste", petsId: -1},
    {nome: "teste", raca: "teste", petsId: -1}, {nome: "teste", raca: "teste", petsId: -1},
    {nome: "teste", raca: "teste", petsId: -1}, {nome: "teste", raca: "teste", petsId: -1},
    {nome: "teste", raca: "teste", petsId: -1}, {nome: "teste", raca: "teste", petsId: -1},
    {nome: "teste", raca: "teste", petsId: -1}, {nome: "teste", raca: "teste", petsId: -1},
    {nome: "teste", raca: "teste", petsId: -1}, {nome: "teste", raca: "teste", petsId: -1},
    {nome: "teste", raca: "teste", petsId: -1}]

    const vacinas = [
        {nome: "teste", data: "teste", tomou: false, descricao: "descricao"},
        {nome: "teste", data: "teste", tomou: true, descricao: "descricao"},
        {nome: "teste", data: "teste", tomou: false, descricao: "descricao"},
        {nome: "teste", data: "teste", tomou: true, descricao: "descricao"},
        {nome: "teste", data: "teste", tomou: false, descricao: "descricao"},
        {nome: "teste", data: "teste", tomou: true, descricao: "descricao"},
        {nome: "teste", data: "teste", tomou: false, descricao: "descricao"},
        {nome: "teste", data: "teste", tomou: true, descricao: "descricao"},
        {nome: "teste", data: "teste", tomou: false, descricao: "descricao"},
        {nome: "teste", data: "teste", tomou: true, descricao: "descricao"}
];

    vacinas.forEach((v) => {
        const vacinaHomeContainer = returnVacinaHomeContainer(v);

        vacinasHome.appendChild(vacinaHomeContainer);
    });

    pets.forEach((p) => {
        const petContainer = returnPetContainer(p);
        
        const listPetContainerFirst = petContainer.cloneNode(true);
        
        const deletePetContainerFirst = petContainer.cloneNode(true);

        deletePetContainerFirst.addEventListener("click", async () => {
            try{
                await api.deletePetById(token, p.petsId);
                location.reload();
            }
            catch(e) {
                Utilites.popupError(e);
            }
        });

        listPetContainerFirst.addEventListener("click", () => {
            // TODO: add pet with backend integration
        })

        listPetsContainer.appendChild(listPetContainerFirst);

        deletePetContainer.appendChild(deletePetContainerFirst);
    })
})();