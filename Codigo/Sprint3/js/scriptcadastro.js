const urlParams = new URLSearchParams(window.location.search);
var idCadastro = urlParams.get('id');

function converterimg() {
    let receberimg = document.getElementById("imagem").files

    if (receberimg.length > 0) {
        let carregarimg = receberimg[0]
        let leraquivo = new FileReader()

        leraquivo.onload = function (x) {
            let imgbase64 = x.target.result
            let imgtela = document.getElementById('imgpet')

            imgtela.src = imgbase64

        }
        leraquivo.readAsDataURL(carregarimg)
    }
}

function PassarParametro(){
    window.location.href = "home.html?id=" + idCadastro
}

function leDados() {
   
    let strDados = localStorage.getItem('db');
    let objDados = [];
    objDados = JSON.parse(strDados);
    objDados = objDados[idCadastro - 1].petBase
    return objDados;
}

function salvaDados(dados) {
    let strDados = localStorage.getItem('db');
    let salvaDado = JSON.parse(strDados)
    salvaDado[idCadastro - 1].petBase.push(dados)
    strDados = JSON.stringify(salvaDado);
    localStorage.setItem('db', strDados); 

}

function cadastrarAnimal() {
    let objDados = leDados();
    let strNome = document.getElementById('nome').value;
    let str_raca = document.getElementById('raca').value;
    if (strNome != "" && str_raca != "" ) {
        if (verificarnome(strNome)){
            let retirar_img = document.getElementById('imgpet')
            let foto = retirar_img.src
            var num = objDados.length + 1
            let novopets = {
                id: num,
                nome: strNome,
                raca: str_raca,
                foto: foto
            };
            salvaDados(novopets);
            document.getElementById('nome').value = ""
            document.getElementById('raca').value = ""
            document.getElementById('imagem').value = ""
            document.getElementById('imgpet').src = ""
        }else{
            alert("Insira um nome diferente para o seu pet")
        }
    }
    else {
        alert("preencha o nome e raça do pets")
    }
}

function verificarnome(nome){
    let objDados = leDados()
    if(objDados[0] != null){
        for(x of objDados){
            if(nome == x.nome){
                return false
            }
        }
        return true
    }else{
        return true
    }
    
}
