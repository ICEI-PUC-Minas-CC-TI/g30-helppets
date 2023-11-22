class Utilites {
    static popupError(e) {
        const centerElementDiv = this.generateCenterElement(),
              errorH1 = document.createElement("h1");

        errorH1.className = "red";

        errorH1.innerText = "Erro: ".concat(e.error == null ? e : e.error);

        console.log(e);

        centerElementDiv.appendChild(errorH1);
    }

    static generateInsertPopupEvents(api) {
        const centerElementDiv = this.generateCenterElement(),
              dataInput = document.createElement("input"),
              descricaoTextarea = document.createElement("textarea"),
              insertEventButton = document.createElement("button");

        dataInput.type = "date";
        descricaoTextarea.placeholder = "Inserir descrição";

        insertEventButton.innerHTML = "Inserir evento"

        insertEventButton.addEventListener("click", async (e) => {
            const token = document.cookie.split(";")[0].split("=")[1];

            try {
                const apiReturn = await api.insertEvent(token, {
                    data: dataInput.value,
                    descricao: descricaoTextarea.value
                });

                if (apiReturn["error"]) this.popupError(apiReturn["error"]);
                else location.reload();
            }
            catch(e) {
                this.popupError(e);
            }
        });

        centerElementDiv.appendChild(dataInput);
        centerElementDiv.appendChild(descricaoTextarea);
        centerElementDiv.appendChild(insertEventButton);
    }

    static generatePopupEvents(events, api) {
        const centerElementDiv = this.generateCenterElement(),
              eventsContainerDiv = document.createElement("div"),
              inserirEventoH1 = document.createElement("h1");

        eventsContainerDiv.id = "events-container";

        inserirEventoH1.innerText = "Inserir evento";

        inserirEventoH1.addEventListener("click", (e) => {
            this.generateInsertPopupEvents(api);
        });

        events["event"].forEach((ev) => {
            const eventContainerDiv = document.createElement("div"),
                  dataP = document.createElement("p"),
                  descricaoP = document.createElement("p");

            dataP.innerText = "Data: " + new Date(ev["data"]).toISOString().split("T")[0].replaceAll("-", "/");
            descricaoP.innerText = "Descrição: " + ev["descricao"];

            eventContainerDiv.appendChild(dataP);
            eventContainerDiv.appendChild(descricaoP);
            
            eventContainerDiv.className = "event-container";

            eventsContainerDiv.appendChild(eventContainerDiv);
        });

        centerElementDiv.appendChild(inserirEventoH1);

        centerElementDiv.appendChild(eventsContainerDiv);
    }

    static generateInsertVacina(api = ApiManager, petId) {
        const centerElementDiv = this.generateCenterElement(),
              titleH1 = document.createElement("h1"),
              formInserirVacina = document.createElement("form"),
              vacinaData = document.createElement("input"),
              vacinaNome = document.createElement("input"),
              vacinaTomouFalse = document.createElement("input"),
              vacinaTomouTrue = document.createElement("input"),
              vacinaDescricao = document.createElement("textarea"),
              vacinaSubmit = document.createElement("input"),
              vacinaTomouFalseLabel = document.createElement("label"),
              vacinaTomouTrueLabel = document.createElement("label");
              
        titleH1.innerText = "Inserir vacina";

        vacinaTomouFalseLabel.innerText = "Não tomou";
        vacinaTomouTrueLabel.innerText = "Tomou";

        vacinaTomouFalseLabel.htmlFor = "vacina-tomou-false";
        vacinaTomouTrueLabel.htmlFor = "vacina-tomou-true"

        vacinaTomouTrue.id = "vacina-tomou-true";
        vacinaTomouFalse.id = "vacina-tomou-false";

        vacinaData.type = "date";
        vacinaNome.type = "text";
        vacinaTomouFalse.type = "radio";
        vacinaTomouTrue.type = "radio";
        vacinaSubmit.type = "submit";

        vacinaData.name = "data";
        vacinaNome.name = "nome";
        vacinaDescricao.name = "descricao";

        vacinaTomouFalse.name = "tomou";
        vacinaTomouTrue.name = "tomou";

        vacinaTomouFalse.value = false;
        vacinaTomouTrue.value = true;

        vacinaSubmit.value = "Enviar";

        vacinaNome.placeholder = "Nome";
        vacinaDescricao.placeholder = "Descrição";

        formInserirVacina.id = "inserir-vacina-form";

        vacinaSubmit.addEventListener("click", async (e) => {
            e.preventDefault();
            
            const formData = {};

            formInserirVacina.childNodes.forEach((n) => {
                if (n.name != "") if ((n.name == "tomou" && n.checked == true) || n.name != "tomou") formData[n.name] = n.value;
            });

            try {
                const token = document.cookie.split(";")[0].split("=")[1];
                
                formData["pets_petsId"] = petId;

                try {
                    const apiReturn = await api.insertVacina(token, formData);
                    if (apiReturn["error"]) this.popupError(apiReturn["error"]);
                    else location.reload();
                }
                catch (e) {
                    this.popupError(e);
                }
            }
            catch(e) {
                this.popupError(e);
            }
        })

        formInserirVacina.appendChild(vacinaData);
        formInserirVacina.appendChild(vacinaNome);
        formInserirVacina.appendChild(vacinaTomouTrueLabel);
        formInserirVacina.appendChild(vacinaTomouTrue);
        formInserirVacina.appendChild(vacinaTomouFalseLabel);
        formInserirVacina.appendChild(vacinaTomouFalse);
        formInserirVacina.appendChild(vacinaDescricao);
        formInserirVacina.appendChild(vacinaSubmit);

        centerElementDiv.appendChild(titleH1);
        centerElementDiv.appendChild(formInserirVacina);
    }
    
    static generateCenterElement() {
        const mainElement = document.querySelector("main"),
              body = document.querySelector("body"),
              centerElementDiv = document.createElement("div"),
              deleteCenterElementDiv = document.createElement("img");

        mainElement.className = "blur-effect";
        
        centerElementDiv.id = "center-element-div";

        centerElementDiv.appendChild(deleteCenterElementDiv);

        deleteCenterElementDiv.alt = "Retornar";
        deleteCenterElementDiv.id = "delete-center-element-div"
        deleteCenterElementDiv.src = "/images/arrow_back.svg";

        deleteCenterElementDiv.addEventListener("click", (e) => {
            mainElement.className = "";
            document.querySelectorAll("#center-element-div").forEach((n) => n.remove());
        })

        body.insertBefore(centerElementDiv, mainElement);

        return centerElementDiv;
    }

    static setupFooterContent(e) {
        const footer = document.querySelector("footer"),
              textFooterP = document.createElement("p");

        textFooterP.innerText = "Contato : Help-pet@gmail.com";

        footer.appendChild(textFooterP);
    }
}