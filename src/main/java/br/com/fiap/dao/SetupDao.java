package br.com.fiap.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.fiap.model.Setup;

public class SetupDao {
	
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("progamer-persistence-unit");
	private EntityManager manager = factory.createEntityManager();

	public void create(Setup setup) {
		
		manager.getTransaction().begin();
		manager.persist(setup);
		manager.getTransaction().commit();
		
		manager.clear();
	}
	
	public List<Setup> listAll(){
		TypedQuery<Setup> query = 
				manager.createQuery("SELECT s FROM Setup s", Setup.class);
		
		return query.getResultList();
	}

	public void delete(Setup setup) {
		manager.getTransaction().begin();
		setup = manager.merge(setup);
		manager.remove(setup);
		manager.getTransaction().commit();
		
	}

}
