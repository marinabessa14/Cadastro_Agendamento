
var idatual = 0;
var modalCadastro;
var modalAlerta;

window.onload = function(e) {
   listar();
}

function sair() {
	sessionStorage.setItem("meu token", undefined);
	window.location.href = "index.html";
}

function listar() {
    // limpar tabela
    var tab = document.getElementById("tabela");
    for (var i=tab.rows.length -1; i>0; i--) {
        tab.deleteRow(i);
    }

     if (sessionStorage.getItem('meutoken') == undefined) {
	alert("Permissão negada");
	window.location.href = "index.html";
	return;
}
   
      var myHeaders = new Headers();
      myHeaders.append("AUTHORIZATION", "Bearer" + sessionStorage.getItem('meutoken'));
   
    fetch("http://localhost:8080/CadastroAgendamento/agendamento", {method: "GET", headers: myHeaders})
    .then(response => response.json())
    .then(data => {
         for (const item of data) {
             var tab = document.getElementById("tabela");
             var row = tab.insertRow(-1);
             row.insertCell(-1).innerHTML = item.idagendamento;
             row.insertCell(-1).innerHTML = item.data;
             row.insertCell(-1).innerHTML = item.horario;
             row.insertCell(-1).innerHTML = item.nome;
             row.insertCell(-1).innerHTML = item.telefone;
             row.insertCell(-1).innerHTML = item.valor;
             row.insertCell(-1).innerHTML = "<button type='button' class='btn btn-primary' "
             + "onclick='alterar("+item.idagendamento+")'> "
             + "<i class'bi bi-pencil'></i></button>"
             + "<button type='button' class='btn btn-danger' "
             + "onclick='excluir("+item.idagendamento+")'> "
             + "<i class'bi bi-trash'></i></button>";
          }
         })
         .catch(error => console.log("Erro", error));

    }




function novo() {

    idatual = 0;
    document.getElementById("txtData").value = "";
    document.getElementById("txtHorario").value = "";
    document.getElementById("txtNome").value = "";
    document.getElementById("txtTelefone").value = "";
    document.getElementById("txtValor").value = "";
  
     modalCadastro = new bootstrap.Modal(document.getElementById("modalCadastro"));
     modalCadastro.show();

}

function alterar(id) {
    idatual = id;

    fetch("http://localhost:8080/CadastroAgendamento/AgendamentoServlet/"+idatual,{method: "GET"})
    .then(response => response.json())
    .then(data => {

        document.getElementById("txtData").value = data.data;
        document.getElementById("txtHorario").value = data.horario;
        document.getElementById("txtNome").value = data.nome;
        document.getElementById("txtTelefone").value = data.telefone;
        document.getElementById("txtValor").value = data.valor;

        modalCadastro = new bootstrap.Modal(document.getElementById("modalCadastro"));
        modalCadastro.show();
         
         })
         .catch(error => console.log("Erro", error));

    


}


function excluir(id) {
idatual = id;
document.getElementById("modalAlertaBody").style.backgroundColor = "FFFFFF";
document.getElementById("modalAlertaBody").innerHTML = "<h5>Confirma a exclusão do registro? </h5>"
+'<button type="button" class="btn btn-primary" onclick="excluirSim()">Sim</button>'
+'<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Não</button>';
modalAlerta = new bootstrap.Modal(document.getElementById("modalAlerta"));
modalAlerta.show();

}

function excluirSim() {
    fetch("http://localhost:8080/CadastroAgendamento/AgendamentoServlet", + idatual, {method: "DELETE"})
    .then(response => response.json())
    .then(result => {
        modalAlerta.hide();
        listar();
        if (result.success) {
            mostrarAlerta("Registro excluído com sucesso!", true);

        }else {
            mostrarAlerta("Falha ao excluir registro!", false);
        }

         })
         .catch(error => console.log("Erro", error));

    


}

function salvar() {
    var p = {
        idagendamento: idatual,
        data: document.getElementById("txtData").value,
        horario: document.getElementById("txtHorario").value,
        nome: document.getElementById("txtNome").value,
        telefone: document.getElementById("txtTelefone").value,
        valor: document.getElementById("txtValor").value,

    };

    var json = JSON.stringify(p);

    var url;
    var metodo;
    if (idatual==0) {
        url = "http://localhost:8080/CadastroAgendamento/AgendamentoServlet";
        metodo = "POST";
    } else {
        url = "http://localhost:8080/CadastroAgendamento/AgendamentoServlet/" + idatual;
        metodo = "PUT";
    }
    fetch(url, {method: metodo, body: json})
    .then(response => response.json())
    .then(result => {
        alert(result.message);
        if (result.success) {
            mostrarAlerta(result.message, true);
        modalCadastro.hide();
        listar();
        } else {
            mostrarAlerta(result.message, false);
        }

    })

}

function mostrarAlerta(msg, sucess) {
    if (success) {
        document.getElementById("modalAlertaBody").style.backgroundColor = "#E0F2F1";

    } else {
        document.getElementById("modalAlertaBody").style.backgroundColor = "#FFEBEE";
    }
    document.getElementById("modalAlertaBody").innerHTML = msg;
    modalAlerta = new bootstrap.Modal(document.getElementById("modalAlerta"));
    modalAlerta.show();
    window.setTimeout(function(){
        modalAlerta.hide();
    }, 3000);

}