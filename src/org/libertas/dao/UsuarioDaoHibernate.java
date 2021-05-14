package org.libertas.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.libertas.model.Agendamento;
import org.libertas.model.Usuario;

public class UsuarioDaoHibernate {
private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ConexaoHibernate");
private static EntityManager em = emf.createEntityManager();

public List<Usuario> listar() {
	Query query = em.createQuery("SELECT p FROM Usuario p");
	List<Usuario> lista = query.getResultList();
	
	return lista;
}


public boolean verificar(Usuario u) {
	Query query = em.createQuery("SELECT p FROM Usuario p" 
			+ "WHERE p.usuario = :usuario AND p.senha = :senha");
	 List<Usuario> lista = (List<Usuario>) query;
	query.setParameter("usuario", u.getUsuario())
	.setParameter("senha", u.getSenha())
	.getResultList();
	
	if (lista.size()>0) {
		return true;
	} else {
		return false;
	}
	
	
}

public void inserir(Usuario u) {
	em.getTransaction().begin();
	em.persist(u);
	em.getTransaction().commit();
}

public void excluir(Usuario u) {
	em.getTransaction().begin();
	em.remove(em.merge(u));
	em.getTransaction().commit();
}

public Usuario consultar(Long id) {
	Usuario u = em.find(Usuario.class, id);
	return u;
}
public void alterar(Usuario u) {
	em.getTransaction().begin();
	em.merge(u);
	em.getTransaction().commit();
}

}



