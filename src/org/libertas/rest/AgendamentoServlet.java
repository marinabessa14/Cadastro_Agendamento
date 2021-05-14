package org.libertas.rest;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.libertas.dao.AgendamentoDaoHibernate;
import org.libertas.model.Agendamento;

import com.google.gson.Gson;

/**
 * Servlet implementation class AgendamentoServlet
 */
@WebServlet("/AgendamentoServlet")
public class AgendamentoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private void enviaResposta(HttpServletResponse response, String json, int codigo) throws
	IOException {

	response.addHeader("Content-Type", "application/json; charset=UTF-8");

	response.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5501");

	response.addHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");

	response.setStatus(codigo);

	BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());

	out.write(json.getBytes("UTF-8"));

	out.close();

	}
	
    public AgendamentoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		
		String authorizationHeader = request.getHeader("AUTHORIZATION");
		AutenticacaoServlet aut = new AutenticacaoServlet();
		if (!aut.validaToken(authorizationHeader)) {
		enviaResposta(response, gson.toJson(new Response(false, "Unauthorized")), 401);
		return;
		}
		
		
		AgendamentoDaoHibernate adao = new AgendamentoDaoHibernate();

		int id = 0;

		if (request.getPathInfo()!=null) {

		String info = request.getPathInfo().replace("/", "");

		id = Integer.parseInt(info);

		}

		if (id > 0) {

		enviaResposta(response, gson.toJson(adao.consultar(id)), 200);

		} else {

		enviaResposta(response, gson.toJson(adao.listar()), 200);

		}

		}
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AgendamentoDaoHibernate adao = new AgendamentoDaoHibernate();

		Gson gson = new Gson();

		String json = request.getReader().lines().collect(Collectors.joining());

		Agendamento a = gson.fromJson(json, Agendamento.class);

		adao.inserir(a);

		enviaResposta(response, gson.toJson(new Response(true, "registro inserido")), 201);

		}
		


protected void doPut(HttpServletRequest request, HttpServletResponse response) throws
ServletException, IOException {


AgendamentoDaoHibernate adao = new AgendamentoDaoHibernate();

Gson gson = new Gson();

int id = 0;

if (request.getPathInfo()!=null) {

String info = request.getPathInfo().replace("/", "");

id = Integer.parseInt(info);

}

String json = request.getReader().lines().collect(Collectors.joining());

Agendamento a = gson.fromJson(json, Agendamento.class);

a.setIdAgendamento(id);

adao.alterar(a);

enviaResposta(response, gson.toJson(new Response(true, "registro alterado")), 200);

}


protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws
ServletException, IOException {

//excluir

AgendamentoDaoHibernate adao = new AgendamentoDaoHibernate();

Gson gson = new Gson();

int id = 0;

if (request.getPathInfo()!=null) {

String info = request.getPathInfo().replace("/", "");

id = Integer.parseInt(info);

}

Agendamento a = new Agendamento();

a.setIdAgendamento(id);

adao.excluir(a);

enviaResposta(response, gson.toJson(new Response(true, "registro excluido")), 200);

}


protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws
ServletException, IOException {

Gson gson = new Gson();

enviaResposta(response, gson.toJson(new Response(true, "Options")), 200);

}

}


