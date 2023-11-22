window.addEventListener('load', comecar());

const urlParams = new URLSearchParams(window.location.search);
var idCadastro = urlParams.get('id');

function comecar() {
    const urlParams = new URLSearchParams(window.location.search);
    let idCadastro = urlParams.get('id');
    var dados = leDados()
    dados = dados[idCadastro - 1]
    vacinas(dados)
    dados = dados.petBase
    homepage(dados)
}

function PassarParametro(x) {
    const urlParams = new URLSearchParams(window.location.search);
    var idCadastro = urlParams.get('id');
    if (x == 1) {
        window.location.href = "calendario.html?id=" + idCadastro
    } else if (x == 2) {
        window.location.href = "cadastropets.html?id=" + idCadastro
    } else {
        window.location.href = "vacinas.html?id=" + idCadastro
    }
}

function homepage(dados) {
    foto_pri = document.getElementById("foto_pet")
    nome_pri = document.getElementById("nome")
    lista = document.querySelector("#lista");
    lista2 = document.querySelector("#lista2");
    lista.innerHTML = ''
    lista2.innerHTML = ''
    if (dados[0] != null) {
        nome_pri.textContent = dados[0].nome
        foto_pri.src = dados[0].foto
        for (x of dados) {
            id = x.id
            nome = x.nome
            lista.innerHTML += `
            <ul style="list-style-type:none;">
                <li>
                    <a href="" class="pgn_inicial" onclick="trocarAnimal('${id}')">${nome}</a>
                </li>
            </ul>
        `
            lista2.innerHTML += `
        <ul style="list-style-type:none;">
                <li>
                    <a href="" class="pgn_inicial" onclick="excluir_animal('${id}')">${nome}</a>
                </li>
            </ul>
        `
        }
    } else {
        alert("Adicione um animal")
    }
}

function trocarAnimal(x) {
    // const urlParams = new URLSearchParams(window.location.search);
    // var idCadastro = urlParams.get('id');
    var dados = leDados()
    dados = dados[idCadastro - 1]
    dados = dados.petBase
    let foto_pri = document.getElementById("foto_pet")
    let nome_pri = document.getElementById("nome")
    foto_pri.src = ""
    for (y of dados) {
        if (x == y.id) {
            foto_pri.src = y.foto
            nome_pri.textContent = y.nome
        }
    }
}

function salvaDados(dados) {
    // const urlParams = new URLSearchParams(window.location.search);
    // var idCadastro = urlParams.get('id');
    let strDados = localStorage.getItem('db');
    let salvaDado = JSON.parse(strDados)
    salvaDado[idCadastro - 1].petBase = dados
    strDados = JSON.stringify(salvaDado);
    localStorage.setItem('db', strDados);

}

function construtor(x) {
    let retorno = ""
    for (y of x) {
        data = y.data
        nome = y.nome
        obs = y.obs
        ref = y.ref
        retorno +=`
            <ul style="list-style-type:none;">
                <li>
                    <div class="bbb">
                        <p class="itens_lista">Nome :${nome}</p>
                        <p class="itens_lista">Data :${data}</p>
                        <p class="itens_lista">Observação :${obs}</p>
                        <p class="itens_lista">Doses de reforço :${ref}</p>
                    </div>
                </li>
            </ul>
        `
        console.log(retorno)
    }
    return retorno
}

function vacinas(dados) {
    vacinap_box = document.getElementById("vacp")
    vacinat_box = document.getElementById("vact")
    let vact = dados.vacinat
    let vacp = dados.vacinatp
    vacinap_box.innerHTML += construtor(vacp)
    vacinat_box.innerHTML += construtor(vact)
}

function excluir_animal(x) {
    const urlParams = new URLSearchParams(window.location.search);
    var idCadastro = urlParams.get('id');
    let objDados = leDados()
    objDados = objDados[idCadastro - 1].petBase
    objDados.splice(x - 1, 1)

    salvaDados(objDados)
    alert("Pet Removido")
    comecar()
}

function leDados() {
    let strDados = localStorage.getItem('db');
    let objDados = [];

    if (strDados) {
        objDados = JSON.parse(strDados);
    } else {
        alert("algo deu errado")
    }
    return objDados;
}
