package fr.isika.cdi7.fouille.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.isika.cdi7.fouille.dao.EspaceUtilisateurRepository;
import fr.isika.cdi7.fouille.dao.inmemory.InMemoryEspaceUtilisateurRepository;
import fr.isika.cdi7.fouille.model.form_objects.InscriptionForm;

class UserServiceTest {

	private UserService
	service;
	
	@BeforeEach
	public void setUp() {
		EspaceUtilisateurRepository inMemoryRepo = new InMemoryEspaceUtilisateurRepository();
		service = new UserServiceImpl(inMemoryRepo);
	}
	
	@Test
	void shouldTellThatEmailExists() {
		assertTrue(service.emailExists("test@test.com"));
	}
	
	void testSave() {
		service.addUtilisateur(new InscriptionForm());
	}

}
