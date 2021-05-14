function entrar(){
	var login = {
		"usuario" : document.getElementById("txtUsuario").value,
		"senha" : document.getElementById("txtSenha").value,
	}
	var json = JSON.stringify(login);
	var url = "http://localhost:8080/CRUD_Agendamento/autenticacao"
	var metodo = "POST";
	
	
	fetch(url, {method: metodo, body: json})
	.then(response => response.text())
    .then(response => {
		if(response.includes("Unauthorized")){
			alert("Usuário ou senha inválidos")
		}else{
			sessionStorage.setItem("meutoken", response)
			window.location.href = 'agendamento.html'	
		}	
	})
    .catch(error => {
		alert("Falha ao efeturar login");
		console.log(error)
	})
	
	
}