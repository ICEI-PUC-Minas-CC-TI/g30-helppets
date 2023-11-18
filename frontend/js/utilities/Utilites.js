class Utilites {
    static popupError(e) {
        const centerElementDiv = this.generateCenterElement();

        console.log(e);
    }

    static generateInsertVacina(api = ApiManager) {
        const queryParam = new URLSearchParams();

        //if (!queryParam.has("petId")) return;

        const petId = queryParam.get("petId");

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

                console.log(formData);
                
                await api.insertVacina(token, formData);
                
                location.reload();
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