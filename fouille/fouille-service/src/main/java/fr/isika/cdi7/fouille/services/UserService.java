package fr.isika.cdi7.fouille.services;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;


import fr.isika.cdi7.fouille.model.Discussion;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.Personne;

import fr.isika.cdi7.fouille.model.form_objects.InscriptionForm;

public interface UserService extends UserDetailsService {

	void addUtilisateur(InscriptionForm formInscription);

	boolean emailExists(String email);

	Discussion getDiscussion(Long id);

	List<Discussion> getAllDiscussionUtilisatateur(Long id);

	Discussion creationDiscussionEtEnvoieMessage(String sujet, String message, EspaceUtilisateur user);

	Discussion getDiscussionConseiller(Long id);

	
	

}

