package org.libertas.rest;

import java.io.BufferedOutputStream;

import java.io.IOException;
import java.sql.Date;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.libertas.dao.UsuarioDaoHibernate;
import org.libertas.model.Usuario;

import com.google.gson.Gson;
import com.ibm.icu.util.Calendar;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Servlet implementation class AutenticacaoServlet
 */
@WebServlet("/AutenticacaoServlet")
public class AutenticacaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  private static final String FRASE_SEGREDO = "Frase segredo para gerar chave única"
		  + "que precisa ser uma frase com ao menos 128 caracteres, ou seja, com 512 bites"
		  + "nunca compartilhe esta frase";
	
    public AutenticacaoServlet() {
        super();
       
    }

	
	private void enviarResposta(HttpServletResponse response, String json, int codigo) throws IOException {
	response.addHeader("Content - Type", "application/text; charset=UTF-8");
	response.setStatus(codigo);
	BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());

	out.write(json.getBytes("UTF-8"));

	out.close();

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			Gson gson = new Gson();
			try {
			String json = request.getReader().lines().collect(Collectors.joining());
			Usuario u = gson.fromJson(json, Usuario.class);
			UsuarioDaoHibernate udao = new UsuarioDaoHibernate();
			if (udao.verificar(u)) {
			String token = gerarToken(u.getUsuario(), 1);
			enviarResposta(response, token, 200);
			} else {
			enviarResposta(response, gson.toJson(new Response(false, "Unauthorized")), 401);
			}
			} catch (Exception e) {
			e.printStackTrace();
			enviarResposta(response, gson.toJson(new Response(false, "Internal Server Error")), 500);

			}

	}


private String gerarToken(String login, Integer expiraEmDias) {
	SignatureAlgorithm algo = SignatureAlgorithm.HS512;
	Date agora = new Date();
	Calendar expira = Calendar.getInstance();
	expira.add(Calendar.DAY_OF_MONTH, expiraEmDias);
	byte[] secret = DatatypeConverter.parseBase64Binary(FRASE_SEGREDO);

	SecretKeySpec key = new SecretKeySpec(secret, algo.getJcaName());
	
	JwtBuilder construtor = Jwts.builder()
			.setIssuedAt(agora)
			.setIssuer(login)
			.signWith(key, algo)
			.setExpiration(expira.getTime());
	
			JwtBuilder jwt;
			return jwt.compact();
       
}


       
public boolean validaToken(String authorizationHeader) {
try {
if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
return false;

}

String token = authorizationHeader.substring("Bearer".length()).trim();
byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(FRASE_SEGREDO);
byte[] key;
Object Claims = Jwts.parserBuilder()
.setSigningKey(key)
.build()
.parseClaimsJws(token)
.getBody();

if (Claims == null) { 
	return false;
}

System.out.println(((io.jsonwebtoken.Claims) Claims).getIssuer());
return true;
} catch (Exception e) {
  e.printStackTrace();
  return false;

}

}

}

