package fr.aoufi.springsamplejava.conf;
 
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import fr.aoufi.springsamplejava.dao.PersonneDAO;
import fr.aoufi.springsamplejava.dao.PersonneJDBCDAO;
import fr.aoufi.springsamplejava.dao.PersonneJPADAO;
import fr.aoufi.springsamplejava.service.PersonneService;
import fr.aoufi.springsamplejava.service.PersonneServiceImpl;
 
@Configuration  // Stéréotype de bean de type configuration
@Import(value = { DataConfiguration.class })
@ComponentScan(basePackages={"fr.aoufi.springsamplejava.service"})
@PropertySource(value = "classpath:/config.properties")  // Location du fichier de property (on peut en mettre plusieurs avec l'annotation @PropertySources
public class ApplicationConfiguration {
	
	@PersistenceContext
	private EntityManager em;
	
	/*
	  Bean obligatoire pour que les ${...} soient remplacés par les valeurs des properties. 
	  Doit être static pour s'assurer que cette phase s'exécute avant celles des autres beans.
	*/
	@Bean 
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	/*
	  Bean métier. L'id du bean est par défaut le nom de la méthode. initMethod et destroyMethod 
	  sont respectivement les méthodes d'initatialisation et de destruction du bean.
	*/
	@Bean(initMethod="init", destroyMethod="destroy")
	//@Lazy //ATTENTION !!! ça peut ruiner les perf
	public PersonneService personneService(PersonneDAO personneDAO) {
		PersonneServiceImpl personneService = new PersonneServiceImpl();
		personneService.setPersonneDAO(personneDAO);
		return personneService;
	}

	@Bean(name = "personneDAO") // Renommage le bean en personneDAO
	@Profile("jpa")  // Bean instancié si le profile jpa est activé
	public PersonneDAO personneJPADAO() {
		PersonneJPADAO personneJPADAO = new PersonneJPADAO();
		personneJPADAO.setEm(em);
		return personneJPADAO;
	}

	@Bean(name = "personneDAO")
	@Profile("jdbc")  // Bean instancié si le profile jdbc est activé
	public PersonneDAO personneJDBCDAO() {
		return new PersonneJDBCDAO();
	}

}