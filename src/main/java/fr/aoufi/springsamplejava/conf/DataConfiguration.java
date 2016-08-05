package fr.aoufi.springsamplejava.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement // IL ne faut pas oublié à mettre cette annotation qui gére les transactions
public class DataConfiguration {

	/*
	  Environment : contient les propriétés systèmes, les variables
	  d'environnements et les properties déclarées en propertysource
	*/
	@Autowired
	private Environment environment;

	/*
	  Autre façon de récupérer les properties mais on doit obligatoirement
	  déclarer un PropertySourcesPlaceholderConfigurer
	*/
	@Value("${db.driverclassname}")
	private String driverClassName;

	@Value("${db.url}")
	private String url;

	@Value("${db.username}")
	private String username;

	@Value("${db.password}")
	private String password;

	/*
	  Bean à partir d'un objet provenant d'une autre librairie et non du projet.
	  Difficile à faire en configuration par annotation
	*/
	@Bean
	public DataSource dataSource() {

		System.out.println("VALEUR driverclassname avec @Value = " + driverClassName);

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getProperty("db.driverClassName"));
		dataSource.setUrl(environment.getProperty("db.url"));
		dataSource.setUsername(environment.getProperty("db.username"));
		dataSource.setPassword(environment.getProperty("db.password"));
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);

		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

		localContainerEntityManagerFactoryBean.setDataSource(dataSource());

		Properties properties = new Properties();
		properties.put("hibernate.generate_statistics", true);

		localContainerEntityManagerFactoryBean.setJpaProperties(properties);
		localContainerEntityManagerFactoryBean.setPackagesToScan("fr.aoufi.springsamplejava.model");

		return localContainerEntityManagerFactoryBean;

	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	// @Bean
	// public PlatformTransactionManager transactionManager(){
	// JpaTransactionManager transactionManager = new JpaTransactionManager();
	// transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
	//
	// return transactionManager;
	// }

}
