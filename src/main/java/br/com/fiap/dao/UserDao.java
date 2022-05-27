package br.com.fiap.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.fiap.model.User;

public class UserDao {

	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("progamer-persistence-unit");
	private EntityManager manager = factory.createEntityManager();

	private User sessionUser = new User();

	public void create(User user) {

		manager.getTransaction().begin();
		manager.persist(user);
		manager.getTransaction().commit();

		manager.clear();
	}

	public List<User> listAll(){
		TypedQuery<User> query = 
				manager.createQuery("SELECT u FROM User u", User.class);

		return query.getResultList();
	}

	public User findUser(User user)  {

		String jpql = "SELECT u FROM User u "
				+ "WHERE email=:email "
				+ "AND password=:password";

		TypedQuery<User> query = manager.createQuery(jpql , User.class);
		query.setParameter("email", user.getEmail());
		query.setParameter("password", user.getPassword());


		System.err.println(sessionUser);
		
		return query.getSingleResult();
	}
	
	public boolean exist(User user) {
		try{
			return findUser(user) == null ? false: true;
		}catch(Exception e) {
			throw e;
		}
	}

	public User getSessionUser() {
		System.out.println("getSessionUser()");
		System.err.print(this.sessionUser);
		return sessionUser;
	}

}
