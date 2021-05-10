package fr.isika.cdi7.fouille.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.dao.EspaceUtilisateurRepository;
import fr.isika.cdi7.fouille.dao.PersonneRepository;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.Personne;

@Service
public class EspaceUtilisateurServiceImpl implements EspaceUtilisateurService {
	
	@Autowired
	private EspaceUtilisateurRepository espaceUtilisateurRepo; //axelle

	@Autowired
	private PersonneRepository personneRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


	@Override
	public Personne save(Personne personne) {
		return personneRepo.save(personne);
	}

	@Override
	public EspaceUtilisateur save(EspaceUtilisateur espaceUtilisateur) {
		return espaceUtilisateurRepo.save(espaceUtilisateur);
	}

	@Override
	public EspaceUtilisateur findByEmail(String email) { //axelle
		return espaceUtilisateurRepo.findByEmail(email);
	}

	@Override
	public EspaceUtilisateur getEspaceUserParIdPersonne(Long idPersonne) {
		return espaceUtilisateurRepo.getEspaceUserParIdPersonne(idPersonne);
	
	}
}
