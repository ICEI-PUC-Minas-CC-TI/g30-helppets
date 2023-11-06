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
        // TODO: add redirect
    })

    const listPetsContainer = document.querySelector("#list-pet-container"),
          deletePetContainer = document.querySelector("#delete-pet-container");

    const token = document.cookie.split(";")[0].split("=")[1];

    //const pets = await api.listRegisteredPets(token, 10); -> Later uncoment this.

    pets = [{nome: "teste", raca: "teste", petsId: -1}, {nome: "teste", raca: "teste", petsId: -1},
    {nome: "teste", raca: "teste", petsId: -1}, {nome: "teste", raca: "teste", petsId: -1},
    {nome: "teste", raca: "teste", petsId: -1}, {nome: "teste", raca: "teste", petsId: -1},
    {nome: "teste", raca: "teste", petsId: -1}, {nome: "teste", raca: "teste", petsId: -1},
    {nome: "teste", raca: "teste", petsId: -1}, {nome: "teste", raca: "teste", petsId: -1},
    {nome: "teste", raca: "teste", petsId: -1}, {nome: "teste", raca: "teste", petsId: -1},
    {nome: "teste", raca: "teste", petsId: -1}]

    pets.forEach(p => {
        const petContainer = returnPetContainer(p);
        
        const listPetContainerFirst = petContainer.cloneNode(true);
        
        const deletePetContainerFirst = petContainer.cloneNode(true);

        deletePetContainerFirst.addEventListener("click", async () => {
            await api.deletePetById(token, p.petsId);
            location.reload();
        });

        listPetContainerFirst.addEventListener("click", () => {
            // TODO: add pet with backend integration
        })

        listPetsContainer.appendChild(listPetContainerFirst);

        deletePetContainer.appendChild(deletePetContainerFirst);
    })
})();