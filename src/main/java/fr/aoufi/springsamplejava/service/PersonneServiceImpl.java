package fr.aoufi.springsamplejava.service;

import org.springframework.transaction.annotation.Transactional;

import fr.aoufi.springsamplejava.dao.PersonneDAO;
import fr.aoufi.springsamplejava.model.Personne;


public class PersonneServiceImpl implements PersonneService {

	private PersonneDAO personneDAO;

	public PersonneServiceImpl() {
	}

	public PersonneServiceImpl(PersonneDAO personneDAO) {
		this.personneDAO = personneDAO;
	}

	public void init() {
		System.out.println("Methode d'initialisation");
	}
	
	public void destroy() {
		System.out.println("Methode de destruction");
	}

	@Override
	@Transactional
	public void save(Personne personne) {
		personneDAO.save(personne);
	}

	public PersonneDAO getPersonneDAO() {
		return personneDAO;
	}

	public void setPersonneDAO(PersonneDAO personneDAO) {
		this.personneDAO = personneDAO;
	}

}