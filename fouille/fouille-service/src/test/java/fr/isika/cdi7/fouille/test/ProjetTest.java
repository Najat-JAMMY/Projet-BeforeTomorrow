package fr.isika.cdi7.fouille.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.services.ConsulterProjetService;
import fr.isika.cdi7.fouille.services.ConsulterProjetServiceImpl;
import fr.isika.cdi7.fouille.services.DonService;

@SpringBootTest
public class ProjetTest {
	
	@Autowired
	private ConsulterProjetService cps;
	
	@Autowired
	private DonService ds;

	@Test
	public void test() {

		ContenuCollecte cc = cps.getContenuCollecteParCollecteId(10L);
		assertTrue(true);

	}

}
