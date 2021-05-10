package fr.isika.cdi7.fouille.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

/* base package des composants : fr.isika.cdi7.fouille */
@ComponentScan(basePackages = "fr.isika.cdi7.fouille")

@EntityScan(basePackages = "fr.isika.cdi7.fouille.model")
@EnableJpaRepositories("fr.isika.cdi7.fouille.dao")
public class FouilleApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(FouilleApplicationStarter.class, args);
	}

}
