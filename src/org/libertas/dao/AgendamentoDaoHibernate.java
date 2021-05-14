package org.libertas.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.libertas.model.Agendamento;

public class AgendamentoDaoHibernate {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ConexaoHibernate");
	private static EntityManager em = emf.createEntityManager();
	
	public List<Agendamento> listar() {
		Query query = em.createQuery("SELECT p FROM agendamento p");
		List<Agendamento> lista = (List<Agendamento>) query.getResultList();
		
		return lista;
	}
	
	
	public void inserir(Agendamento a) {
		em.getTransaction().begin();
		em.persist(a);
		em.getTransaction().commit();
	}
	
	public void excluir(Agendamento a) {
		em.getTransaction().begin();
		em.remove(em.merge(a));
		em.getTransaction().commit();
	}
	
	public Agendamento consultar(Long id) {
		Agendamento a = em.find(Agendamento.class, id);
		return a;
	}
	public void alterar(Agendamento a) {
		em.getTransaction().begin();
		em.merge(a);
		em.getTransaction().commit();
	}
	
}
