package fr.aoufi.springsamplejava.dao;

import javax.persistence.EntityManager;

import fr.aoufi.springsamplejava.model.Personne;


public class PersonneJPADAO implements PersonneDAO {
	
	private EntityManager em;
	
	@Override
	public Personne save(Personne personne) {
		em.persist(personne);
		return personne;
	}

	public EntityManager getEm() {
		return em;
	}
	
	//@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	
}
