// Para esconder e mostrar ao clicar em "adicionar vacina"; esconder e mostrar vacinas salvas também
var btn = document.querySelector("#botao");
var container = document.querySelector("#container");
var listaV = document.querySelector("#lista-vacinas");
btn.addEventListener("click", function () {

    if (container.style.display === "block") {
        container.style.display = "none";
        listaV.style.display = "block";
    } else {
        container.style.display = "block";
        listaV.style.display = "none";
    }
});


function PassarParametro() {
    const urlParams = new URLSearchParams(window.location.search);
    var idCadastro = urlParams.get('id');
    console.log(idCadastro)
    window.location.href = "home.html?id=" + idCadastro
}

/*Para as vacinas salvas aparecerem só ao clicar em confirmar   BUG - VACINAS SOMEM AO REINICIAR PÁGINA*/
var bot = document.querySelector("#confirm");
var lista = document.querySelector("#lista-vacinas");
bot.addEventListener("click", function () {
    if (lista.style.display === "none") {
        lista.style.display = "block";
        exibirInformacoesSalvas();
    } else {
        lista.style.display = "block";
    }
});
/*Para as vacinas salvas aparecerem só ao clicar em confirmar*/

//Aparecer o formulário
const urlParams = new URLSearchParams(window.location.search);
var idCadastro = urlParams.get('id');
const form = document.querySelector("#form")
const nomeInput = document.querySelector("#nome")
const dataInput = document.querySelector("#data")
const reforcoInput = document.querySelector("#ref")
const obsInput = document.querySelector("#obs")

function salvaDadosp(dados) {
    let strDados = localStorage.getItem('db');
    let salvaDados = JSON.parse(strDados)
    salvaDados[idCadastro - 1].vacinatp.push(dados)
    strDados = JSON.stringify(salvaDados);
    localStorage.setItem('db', strDados); 

}

form.addEventListener("submit", (evento) => {
    evento.preventDefault();
    const vacina = {
        "nome": nomeInput.value,
        "data": dataInput.value,
        "obs": obsInput.value,
        "ref": reforcoInput.value
    }
    if (localStorage.getItem("db") === null) {
        //localStorage.setItem("db", JSON.stringify([vacina]))
        let strDados = localStorage.getItem('db');
        let objDados = [];
        objDados = JSON.stringify(strDados);
        salvaDadosp(objDados)
    }
    else {
        //const vacinasSalvas = JSON.parse(localStorage.vacinas)
        //vacinasSalvas.push(vacina)
        salvaDadosp(vacina)
    }
    container.style.display = "none";
    /*Manda as informações salvas para a tela*/
    exibirInformacoesSalvas();

    form.reset()
});

function exibirInformacoesSalvas() {
    if (localStorage.getItem("db") !== null) {
        vacinasSalvas = JSON.parse(localStorage.getItem("db"));
        vacinasSalvas = vacinasSalvas[idCadastro - 1].vacinatp
        const listaVacinas = document.querySelector("#lista-vacinas");
        listaVacinas.innerHTML = ""; // Limpa a lista antes de exibir as informações

        vacinasSalvas.forEach((vacina) => {
            const listaVacina = document.createElement("div");
            listaVacina.classList.add("vacina");

            const nomeVacina = document.createElement("p");
            nomeVacina.textContent = `Nome: ${vacina.nome}`;

            const dataVacina = document.createElement("p");
            dataVacina.textContent = `Data: ${vacina.data}`;

            const reforcoVacina = document.createElement("p");
            reforcoVacina.textContent = `Reforço: ${vacina.ref}`;

            const obsVacina = document.createElement("p");
            obsVacina.textContent = `Observação: ${vacina.obs}`;

            // Criação do botão
            const envia = document.createElement("button");
            envia.classList.add("btn-img");

            const img = document.createElement("img");
            img.src = "";
            envia.appendChild(img);
            envia.addEventListener("click", function () {

                // Manda as informações da vacina para o container 2
                enviarParaContainer2(vacina);
                // Remove a vacina do primeiro container
                listaVacinas.removeChild(listaVacina);
                console.log(vacina)
            });

            listaVacina.appendChild(nomeVacina);
            listaVacina.appendChild(dataVacina);
            listaVacina.appendChild(reforcoVacina);
            listaVacina.appendChild(obsVacina);

            listaVacina.appendChild(envia); // Adiciona o botão à vacina

            listaVacinas.appendChild(listaVacina);
        });
    }
}
// Chama a função para exibir as informações salvas ao carregar a página
window.addEventListener("DOMContentLoaded", () => {
    exibirInformacoesSalvas();
});

function enviarParaContainer2(vacina) {
    const listaTomadas = document.querySelector("#lista-tomadas");

    const listaTomada = document.createElement("div");
    listaTomada.classList.add("tomada");

    const nomeVacina = document.createElement("p");
    nomeVacina.textContent = `Nome: ${vacina.nome}`;

    const dataVacina = document.createElement("p");
    dataVacina.textContent = `Data: ${vacina.data}`;

    const reforcoVacina = document.createElement("p");
    reforcoVacina.textContent = `Reforço: ${vacina.ref}`;

    const obsVacina = document.createElement("p");
    obsVacina.textContent = `Observação: ${vacina.obs}`;

    listaTomada.appendChild(nomeVacina);
    listaTomada.appendChild(dataVacina);
    listaTomada.appendChild(reforcoVacina);
    listaTomada.appendChild(obsVacina);

    listaTomadas.appendChild(listaTomada);
}

//Vacinas Tomadas

// Para esconder e mostrar ao clicar em "adicionar vacina"; esconder e mostrar vacinas salvas também
var botao = document.querySelector("#bt");
var container2 = document.querySelector("#container2");
var listaT = document.querySelector("#lista-tomadas");
botao.addEventListener("click", function () {
    if (container2.style.display === "block") {
        container2.style.display = "none";
        listaT.style.display = "block";
    } else {
        container2.style.display = "block";
        listaT.style.display = "none";
    }
});
//Para esconder e mostrar ao clicar em "adicionar vacina"


/*Para as vacinas salvas aparecerem só ao clicar em confirmar   BUG - VACINAS SOMEM AO REINICIAR PÁGINA*/
var aperta = document.querySelector("#confirm2");
var tomada = document.querySelector("#lista-tomadas");
aperta.addEventListener("click", function () {
    if (tomada.style.display === "none") {
        tomada.style.display = "block";
        exibirVacTomadas(); // Chama a função para exibir as vacinas tomadas
    } else {
        tomada.style.display = "none";
    }
});
/*Para as vacinas salvas aparecerem só ao clicar em confirmar*/

function salvaDadost(dados) {
    let strDados = localStorage.getItem('db');
    let salvaDados = JSON.parse(strDados)
    salvaDados[idCadastro - 1].vacinat.push(dados)
    strDados = JSON.stringify(salvaDados);
    localStorage.setItem('db', strDados); 

}

const formu = document.querySelector("#formu")
const nameInput = document.querySelector("#name")
const diaInput = document.querySelector("#dia")
const dosesInput = document.querySelector("#doses")
const observacaoInput = document.querySelector("#observacao")
formu.addEventListener("submit", (evento) => {
    evento.preventDefault();
    const vacTomada = {
        "nome": nameInput.value,
        "data": diaInput.value,
        "obs": observacaoInput.value,
        "ref": dosesInput.value
    }
    if (localStorage.getItem("db") === null) {
        let strDados = localStorage.getItem('db');
        let objDados = [];
        objDados = JSON.stringify(strDados);
        salvaDadost(objDados)
    }
    else {
        console.log(vacTomada)
        salvaDadost(vacTomada)
    }
    container2.style.display = "none";

    /*Manda as informações salvas para a tela*/
    exibirVacTomadas();
    formu.reset()
});


/*  MOSTRAR DADOS DE VACINAS*/
function exibirVacTomadas() {
    if (localStorage.getItem("db") !== null) {
        vacSalvas = JSON.parse(localStorage.getItem("db"));
        vacSalvas = vacSalvas[idCadastro - 1].vacinat
        const listaTomadas = document.querySelector("#lista-tomadas");
        listaTomadas.innerHTML = ""; // Limpa a lista antes de exibir as informações

        vacSalvas.forEach((vacTomada) => {
            const listaTomada = document.createElement("div");
            listaTomada.classList.add("tomada");

            const name = document.createElement("p");
            name.textContent = `Nome: ${vacTomada.nome}`;

            const dia = document.createElement("p");
            dia.textContent = `Data: ${vacTomada.data}`;

            const doses = document.createElement("p");
            doses.textContent = `Reforço: ${vacTomada.ref}`;

            const observacao = document.createElement("p");
            observacao.textContent = `Observação: ${vacTomada.obs}`;

            listaTomada.appendChild(name);
            listaTomada.appendChild(dia);
            listaTomada.appendChild(doses);
            listaTomada.appendChild(observacao);

            listaTomadas.appendChild(listaTomada);
        });
    }
}
// Chama a função para exibir as informações salvas ao carregar a página
window.addEventListener("DOMContentLoaded", exibirVacTomadas);
