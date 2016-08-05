package fr.aoufi.springsamplejava.runtime;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.aoufi.springsamplejava.conf.ApplicationConfiguration;
import fr.aoufi.springsamplejava.model.Personne;
import fr.aoufi.springsamplejava.service.PersonneService;

public class Runtime {

	public static void main(String[] args) {

		System.setProperty("spring.profiles.active", "jpa");

		ConfigurableApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				ApplicationConfiguration.class);

		for (String string : applicationContext.getBeanDefinitionNames()) {
			System.out.println(string);
		}
		
		PersonneService personneService = (PersonneService) applicationContext.getBean("personneService");

		personneService = applicationContext.getBean(PersonneService.class);

		personneService = applicationContext.getBean("personneService", PersonneService.class);

		personneService.save(new Personne());

		applicationContext.close();
	}

}