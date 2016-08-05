package fr.aoufi.springsamplejava.dao;

import fr.aoufi.springsamplejava.model.Personne;


public class PersonneJDBCDAO implements PersonneDAO {

	public Personne save(Personne personne) {
		System.out.println("Méthode JDBC");
		return null;
	}

}
